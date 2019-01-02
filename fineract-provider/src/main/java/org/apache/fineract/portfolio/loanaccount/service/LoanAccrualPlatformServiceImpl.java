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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.fineract.accounting.classify.data.GLAccountAmountData;
import org.apache.fineract.accounting.classify.data.LoanArrearClassifyData;
import org.apache.fineract.accounting.classify.data.LoanProductSubtypeMappingData;
import org.apache.fineract.accounting.classify.service.LoanSubtypeMappingReadPlatformService;
import org.apache.fineract.accounting.glaccount.domain.GLAccount;
import org.apache.fineract.accounting.glaccount.domain.GLAccountRepository;
import org.apache.fineract.accounting.journalentry.service.AccountingProcessorHelper;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.jobs.annotation.CronTarget;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
import org.apache.fineract.infrastructure.jobs.service.JobName;
import org.apache.fineract.organisation.office.domain.Office;
import org.apache.fineract.organisation.office.domain.OfficeRepository;
import org.apache.fineract.organisation.office.service.OfficeReadPlatformService;
import org.apache.fineract.portfolio.loanaccount.data.LoanScheduleAccrualData;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransactionType;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import sun.rmi.runtime.Log;

@Service
public class LoanAccrualPlatformServiceImpl implements LoanAccrualPlatformService {

	private final LoanReadPlatformService loanReadPlatformService;
	private final LoanAccrualWritePlatformService loanAccrualWritePlatformService;
	private final LoanSubtypeMappingReadPlatformService loanSubtypeMappingReadPlatformService;
	private final OfficeRepository officeRepository;
	private final GLAccountRepository glAccountRepository;
	private final AccountingProcessorHelper helper;
	private final JdbcTemplate jdbctemplate;
	private final static Logger logger = LoggerFactory.getLogger(LoanAccrualPlatformServiceImpl.class);

	@Autowired
	public LoanAccrualPlatformServiceImpl(
			final LoanReadPlatformService loanReadPlatformService,
			final LoanAccrualWritePlatformService loanAccrualWritePlatformService,
			final LoanSubtypeMappingReadPlatformService loanSubtypeMappingReadPlatformService,
			final OfficeReadPlatformService officeReadPlatformService, 
			final AccountingProcessorHelper helper,
			final GLAccountRepository glAccountRepository,
			final OfficeRepository officeRepository,
			final RoutingDataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
//		this.namedJdbctemplate = new NamedParameterJdbcTemplate(dataSource);
		this.loanReadPlatformService = loanReadPlatformService;
		this.loanAccrualWritePlatformService = loanAccrualWritePlatformService;
		this.loanSubtypeMappingReadPlatformService = loanSubtypeMappingReadPlatformService;
		this.helper = helper;
		this.glAccountRepository = glAccountRepository;
		this.officeRepository = officeRepository;
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
	@SuppressWarnings("unused")
	private void changeLoanProductSubtype(final LocalDate tilldate) {
		// Get area loan for classifies
		Collection<LoanArrearClassifyData> loanArrearClassifyDatas = this.loanSubtypeMappingReadPlatformService
				.retrieveLoanArrearsClassifyData(tilldate);
		 // Change Loan SubtypeStatus By ArreaAging

		for (final LoanArrearClassifyData loanArrearClassifyData : loanArrearClassifyDatas) {
			Long loanId = loanArrearClassifyData.getAccountNumber();
			String clientAccountNo = loanArrearClassifyData.getClientAccountNo();
			Long accountNumber = loanArrearClassifyData.getAccountNumber();
			Long productId = loanArrearClassifyData.getProductId();
			Long currentloanSubtypeStatusId = loanArrearClassifyData.getLoanSubtypeStatusId();
			double loanOutstanding = loanArrearClassifyData.getLoanOutstanding();
			Date overdueSinceDateDerived = loanArrearClassifyData.getOverdueSinceDateDerived();
			Integer daysInArrears = loanArrearClassifyData.getDaysInArrears();
			Long officeId = loanArrearClassifyData.getOfficeId();
			String currencyCode = loanArrearClassifyData.getCurrencyCode();

			// get Current SubtypeStatus with CurrentData 
			Collection<LoanProductSubtypeMappingData> newLoanProductSubtypeMappingDatas = this.loanSubtypeMappingReadPlatformService
					.retrieveLoanProductSubtypeMappings(productId, daysInArrears);
			// Long productId;
			int loanSubtypeStatusId = 0;
			int minAge;
			int maxAge;
			Long portfolioAccId = null;
			Long intReceivableAccId = null;
			Long incomeAccId = null;
			Date transactionDate = tilldate.toDate();
			Long currentPortfolioAccId = null;
//			Long currentloanSubtypeStatusId = null;
			Long currentIntReceivableAccId = null;
			Long currentIncomeAccId = null;
			// get Current SubtypeStatus with CurrentData

			for (LoanProductSubtypeMappingData loanProductSubtypeMappingData : newLoanProductSubtypeMappingDatas) {
//				loanId=loanProductSubtypeMappingData.getLoanSubtypeStatusId();
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

					// Get Current Current loan glBalance
					// portfolio_acc_id

					double portfolioGLAmount = 0;
					double interestReceivableGLAmount = 0;
					double incomeGLAmount = 0;

					// int_receivable_acc_id
					// int_receivable
					// income_acc_id
					// income_acc

					Collection<GLAccountAmountData> glPortfolioAmountDatas = this.loanSubtypeMappingReadPlatformService
							.retrieveLoanGLAccountAmountData(officeId, tilldate, loanId, currentPortfolioAccId);
					for (GLAccountAmountData glAccountAmountData : glPortfolioAmountDatas) {
						portfolioGLAmount = glAccountAmountData.getBalance();
					}

					Collection<GLAccountAmountData> glInterestReceivableGLAmountAmountDatas = this.loanSubtypeMappingReadPlatformService
							.retrieveLoanGLAccountAmountData(officeId, tilldate, loanId, currentIntReceivableAccId);
					for (GLAccountAmountData glAccountAmountData : glInterestReceivableGLAmountAmountDatas) {
						interestReceivableGLAmount = glAccountAmountData.getBalance();
					}

					Collection<GLAccountAmountData> glIncomeGLAmountDatas = this.loanSubtypeMappingReadPlatformService
							.retrieveLoanGLAccountAmountData(officeId, tilldate, loanId, currentIncomeAccId);
					for (GLAccountAmountData glAccountAmountData : glIncomeGLAmountDatas) {
						incomeGLAmount = glAccountAmountData.getBalance();
					}

					// Move Accounting
					// Get Current Loan Ledger
					// Move Portfolio Ledger

				
					if (portfolioGLAmount != 0) {
						
						try {
							
							GLAccount debitGLAccount = glAccountRepository.findOne(portfolioAccId);
							GLAccount creditGLAccount = glAccountRepository.findOne(currentPortfolioAccId);
							BigDecimal amount = BigDecimal.valueOf((Math.ceil(portfolioGLAmount)));
							
							addChangeSubTypeTransaction(loanId, loanSubtypeStatusId, officeId, currencyCode,
									debitGLAccount, creditGLAccount, transactionDate, amount);
							
						} catch (Exception e) {
							// TODO: handle exception
							//Log.d("portfolioGLAmount",e.toString());
							System.out.print(e.toString());	
						}
					}

					// Move Interest Receivable Ledger
					if (interestReceivableGLAmount != 0) {

						GLAccount debitGLAccount = glAccountRepository.findOne(currentIncomeAccId);
						GLAccount creditGLAccount = glAccountRepository.findOne(incomeAccId);
						BigDecimal amount = BigDecimal.valueOf((Math.ceil(interestReceivableGLAmount)));
						
						addChangeSubTypeTransaction(loanId, loanSubtypeStatusId, officeId, currencyCode, debitGLAccount,
								creditGLAccount, transactionDate, amount);

					}

					if (incomeGLAmount != 0) {
						
						GLAccount debitGLAccount = glAccountRepository.findOne(currentIncomeAccId);
						GLAccount creditGLAccount = glAccountRepository.findOne(incomeAccId);
						BigDecimal amount = BigDecimal.valueOf((Math.ceil(portfolioGLAmount)));
						
						addChangeSubTypeTransaction(loanId, loanSubtypeStatusId, officeId, currencyCode, debitGLAccount,
								creditGLAccount, transactionDate, amount);
						addChangeSubTypeTransaction(loanId, loanSubtypeStatusId, officeId, currencyCode, debitGLAccount, creditGLAccount, transactionDate, amount);
						
					}
					
					// Move Income Ledger
					// Add New Loan Transaction
					// Move Ledger Amount
					// Update SubType Status
					// Get New Loan Ledger
					
				}
			}

		}

		// Move Accounting
		// Move portfolio account
		// Move receivable account
		// Move income account
		// End Move Accounting
	}

//	final Office office, 
//	final String currencyCode, 
//	final GLAccount account,
//	final Long loanId, 
//	final String transactionId, 
//	final Date transactionDate, 
//	final BigDecimal amount

	private Long addChangeSubTypeTransaction(final Long loanId, final int loanSubtypeStatusId, final Long officeId,
			final String currencyCode, final GLAccount debitGLAccount, final GLAccount creditGLAccount,
			final Date transactionDate, final BigDecimal amount) throws DataAccessException {

		String transactionSql = " INSERT INTO m_loan_transaction (" + "loan_id," + "office_id," + "is_reversed,"
				+ "transaction_type_enum, " + "transaction_date, " + "amount, " + "submitted_on_date) " + " VALUES ("
				+ "?, " + "?, " + "0, " + "?, " + "?, " + "?, " + "?)";
		
		this.jdbctemplate.update(transactionSql, loanId, officeId, LoanTransactionType.CHANGE_SUBTYPE.getValue(), transactionDate, amount, DateUtils.getDateOfTenant());
		
		@SuppressWarnings("deprecation")
		final Long transactionId = this.jdbctemplate.queryForLong("SELECT LAST_INSERT_ID()");
		String updateLoan = " UPDATE m_loan  SET loan_subtype_status_id=?  WHERE  id=? ";
		this.jdbctemplate.update(updateLoan, loanSubtypeStatusId, loanId);

		Office office = officeRepository.findOne(officeId);

		String tranId = "L" + transactionId;

		helper.createDebitJournalEntryForLoan(office, currencyCode, debitGLAccount, loanId, tranId, transactionDate,
				amount);
		helper.createCreditJournalEntryForLoan(office, currencyCode, creditGLAccount, loanId, tranId, transactionDate,
				amount);

		return transactionId;
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
		if (loanIds != null && loanIds.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (Long loanId : loanIds) {
				try {
					
					this.loanAccrualWritePlatformService.addIncomeAndAccrualTransactions(loanId);
					
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
