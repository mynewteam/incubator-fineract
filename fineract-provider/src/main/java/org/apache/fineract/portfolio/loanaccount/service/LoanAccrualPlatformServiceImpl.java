/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.portfolio.loanaccount.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.fineract.accounting.classify.data.LoanArrearClassifyData;
import org.apache.fineract.accounting.classify.data.LoanProductSubtypeMappingData;
import org.apache.fineract.accounting.classify.service.LoanSubtypeMappingReadPlatformService;
import org.apache.fineract.infrastructure.jobs.annotation.CronTarget;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
import org.apache.fineract.infrastructure.jobs.service.JobName;
import org.apache.fineract.portfolio.loanaccount.data.LoanScheduleAccrualData;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanAccrualPlatformServiceImpl implements LoanAccrualPlatformService {

	private final LoanReadPlatformService loanReadPlatformService;
	private final LoanAccrualWritePlatformService loanAccrualWritePlatformService;

	private final LoanSubtypeMappingReadPlatformService loanSubtypeMappingReadPlatformService;

	private final static Logger logger = LoggerFactory.getLogger(LoanAccrualPlatformServiceImpl.class);

	@Autowired
	public LoanAccrualPlatformServiceImpl(final LoanReadPlatformService loanReadPlatformService,
			final LoanAccrualWritePlatformService loanAccrualWritePlatformService,
			final LoanSubtypeMappingReadPlatformService loanSubtypeMappingReadPlatformService) {
		this.loanReadPlatformService = loanReadPlatformService;
		this.loanAccrualWritePlatformService = loanAccrualWritePlatformService;
		this.loanSubtypeMappingReadPlatformService = loanSubtypeMappingReadPlatformService;
	}

	@Override
	@CronTarget(jobName = JobName.ADD_ACCRUAL_ENTRIES)
	public void addAccrualAccounting() throws JobExecutionException {

		Collection<LoanScheduleAccrualData> loanScheduleAccrualDatas = this.loanReadPlatformService
				.retriveScheduleAccrualData();
		StringBuilder sb = new StringBuilder();
		Map<Long, Collection<LoanScheduleAccrualData>> loanDataMap = new HashMap<>();
		for (final LoanScheduleAccrualData accrualData : loanScheduleAccrualDatas) {
			if (loanDataMap.containsKey(accrualData.getLoanId())) {
				loanDataMap.get(accrualData.getLoanId()).add(accrualData);
			} else {
				Collection<LoanScheduleAccrualData> accrualDatas = new ArrayList<>();
				accrualDatas.add(accrualData);
				loanDataMap.put(accrualData.getLoanId(), accrualDatas);
			}
		}

		for (Map.Entry<Long, Collection<LoanScheduleAccrualData>> mapEntry : loanDataMap.entrySet()) {
			try {
				this.loanAccrualWritePlatformService.addAccrualAccounting(mapEntry.getKey(), mapEntry.getValue());
			} catch (Exception e) {
				Throwable realCause = e;
				if (e.getCause() != null) {
					realCause = e.getCause();
				}
				sb.append("failed to add accural transaction for loan " + mapEntry.getKey() + " with message "
						+ realCause.getMessage());
			}
		}

		if (sb.length() > 0) {
			throw new JobExecutionException(sb.toString());
		}
	}

	@Override
	@CronTarget(jobName = JobName.ADD_PERIODIC_ACCRUAL_ENTRIES)
	public void addPeriodicAccruals() throws JobExecutionException {
		String errors = addPeriodicAccruals(LocalDate.now());
		if (errors.length() > 0) {
			throw new JobExecutionException(errors);
		}
	}

	@Override
	public String addPeriodicAccruals(final LocalDate tilldate) {

		Collection<LoanScheduleAccrualData> loanScheduleAccrualDatas = this.loanReadPlatformService
				.retrivePeriodicAccrualData(tilldate);
		changeLoanProductSubtype(tilldate);
		return addPeriodicAccruals(tilldate, loanScheduleAccrualDatas);
	}

	// Loan classification
//	@SuppressWarnnings
	@SuppressWarnings("unused")
	private void changeLoanProductSubtype(final LocalDate tilldate) {
		// Get area loan for classifies
		Collection<LoanArrearClassifyData> loanArrearClassifyDatas = this.loanSubtypeMappingReadPlatformService
				.retrieveLoanArrearsClassifyData(tilldate);
		// Change Loan SubtypeStatus By ArreaAging

		for (final LoanArrearClassifyData loanArrearClassifyData : loanArrearClassifyDatas) {
			
			String clientAccountNo = loanArrearClassifyData.getClientAccountNo();
			Long accountNumber = loanArrearClassifyData.getAccountNumber();
			Long productId = loanArrearClassifyData.getProductId();
			Long currentloanSubtypeStatusId = loanArrearClassifyData.getLoanSubtypeStatusId();
			double loanOutstanding = loanArrearClassifyData.getLoanOutstanding();
			Date overdueSinceDateDerived = loanArrearClassifyData.getOverdueSinceDateDerived();
			Integer daysInArrears = loanArrearClassifyData.getDaysInArrears();

			// get Current SubtypeStatus with CurrentData
			Collection<LoanProductSubtypeMappingData> newLoanProductSubtypeMappingDatas = this.loanSubtypeMappingReadPlatformService
					.retrieveLoanProductSubtypeMappings(productId, daysInArrears);
			// Long id;
			// Long productId;
			Long loanSubtypeStatusId = null;
			int minAge;
			int maxAge;
			Long portfolioAccId;
			Long intReceivableAccId;
			Long incomeAccId;

			Long currentPortfolioAccId = null;
//			Long currentloanSubtypeStatusId = null;
			Long currentIntReceivableAccId = null;
			Long currentIncomeAccId = null;

			// get Current SubtypeStatus with CurrentData

			for (LoanProductSubtypeMappingData loanProductSubtypeMappingData : newLoanProductSubtypeMappingDatas) {

				loanSubtypeStatusId = loanProductSubtypeMappingData.getLoanSubtypeStatusId();
				minAge = loanProductSubtypeMappingData.getMinAge();
				maxAge = loanProductSubtypeMappingData.getMaxAge();
				portfolioAccId = loanProductSubtypeMappingData.getPortfolioAccId();
				intReceivableAccId = loanProductSubtypeMappingData.getIntReceivableAccId();
				incomeAccId = loanProductSubtypeMappingData.getIncomeAccId();

			}

			Collection<LoanProductSubtypeMappingData> currentLoanProductSubtypeMappingDatas = this.loanSubtypeMappingReadPlatformService
					.retrieveLoanProductSubtypeMappingBySubtypeStatusId(productId, currentloanSubtypeStatusId);

			for (LoanProductSubtypeMappingData currentloanProductSubtypeMappingData : currentLoanProductSubtypeMappingDatas) {

				currentPortfolioAccId = currentloanProductSubtypeMappingData.getPortfolioAccId();
				currentIntReceivableAccId = currentloanProductSubtypeMappingData.getIntReceivableAccId();
				currentIncomeAccId = currentloanProductSubtypeMappingData.getIncomeAccId();

			}

			if (currentPortfolioAccId != null) {
				if (currentloanSubtypeStatusId != loanSubtypeStatusId) {
					
					System.out.println("Do Change Subtype status");
					logger.debug("if (currentloanSubtypeStatusId:"+ currentloanSubtypeStatusId +" != loanSubtypeStatusId:"+ loanSubtypeStatusId +")");

				}
				
				logger.debug("if (currentloanSubtypeStatusId:"+ currentloanSubtypeStatusId +" != loanSubtypeStatusId:"+ loanSubtypeStatusId +")");
			}

		}

		// Get area loan with current SubstypeStatus

//			if (loanArrearClassifyData.getDaysInArrears() > 0) {
//				
//			}

		// Move Accounting

		// Move portfolio account

		// Move receivable account

		// Move income account

		// End Move Accounting

	}

	// classification
	@Override
	public String addPeriodicAccruals(final LocalDate tilldate,
			Collection<LoanScheduleAccrualData> loanScheduleAccrualDatas) {

		StringBuilder sb = new StringBuilder();

		Map<Long, Collection<LoanScheduleAccrualData>> loanDataMap = new HashMap<>();

		//

		for (final LoanScheduleAccrualData accrualData : loanScheduleAccrualDatas) {

			if (loanDataMap.containsKey(accrualData.getLoanId())) {

				loanDataMap.get(accrualData.getLoanId()).add(accrualData);

			} else {

				Collection<LoanScheduleAccrualData> accrualDatas = new ArrayList<>();

				accrualDatas.add(accrualData);
				loanDataMap.put(accrualData.getLoanId(), accrualDatas);
			}

		}

		for (Map.Entry<Long, Collection<LoanScheduleAccrualData>> mapEntry : loanDataMap.entrySet()) {
			try {
				this.loanAccrualWritePlatformService.addPeriodicAccruals(tilldate, mapEntry.getKey(),
						mapEntry.getValue());
			} catch (Exception e) {
				Throwable realCause = e;
				if (e.getCause() != null) {
					realCause = e.getCause();
				}

				sb.append("failed to add accural transaction for loan " + mapEntry.getKey() + " with message "
						+ realCause.getMessage());
			}
		}

		return sb.toString();
	}

	@Override
	@CronTarget(jobName = JobName.ADD_PERIODIC_ACCRUAL_ENTRIES_FOR_LOANS_WITH_INCOME_POSTED_AS_TRANSACTIONS)
	public void addPeriodicAccrualsForLoansWithIncomePostedAsTransactions() throws JobExecutionException {

		Collection<Long> loanIds = this.loanReadPlatformService.retrieveLoanIdsWithPendingIncomePostingTransactions();

		logger.debug("Collection<Long> loanIds" + loanIds.toString()
				+ " = this.loanReadPlatformService.retrieveLoanIdsWithPendingIncomePostingTransactions();");

		if (loanIds != null && loanIds.size() > 0) {
			StringBuilder sb = new StringBuilder();

			for (Long loanId : loanIds) {
				try {

					this.loanAccrualWritePlatformService.addIncomeAndAccrualTransactions(loanId);

					logger.trace("  this.loanAccrualWritePlatformService.addIncomeAndAccrualTransactions(loanId:"
							+ loanId + ");");

				} catch (Exception e) {
					Throwable realCause = e;
					if (e.getCause() != null) {
						realCause = e.getCause();
					}

					sb.append("failed to add income and accrual transaction for loan " + loanId + " with message "
							+ realCause.getMessage());
				}
			}
			if (sb.length() > 0) {
				throw new JobExecutionException(sb.toString());
			}
		}
	}
}
