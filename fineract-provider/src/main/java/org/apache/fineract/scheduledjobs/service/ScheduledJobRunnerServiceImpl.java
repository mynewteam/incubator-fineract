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
package org.apache.fineract.scheduledjobs.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.core.service.RoutingDataSourceServiceFactory;
import org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil;
import org.apache.fineract.infrastructure.jobs.annotation.CronTarget;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
import org.apache.fineract.infrastructure.jobs.service.JobName;
import org.apache.fineract.portfolio.savings.DepositAccountType;
import org.apache.fineract.portfolio.savings.DepositAccountUtils;
import org.apache.fineract.portfolio.savings.data.DepositAccountData;
import org.apache.fineract.portfolio.savings.data.SavingsAccountAnnualFeeData;
import org.apache.fineract.portfolio.savings.service.DepositAccountReadPlatformService;
import org.apache.fineract.portfolio.savings.service.DepositAccountWritePlatformService;
import org.apache.fineract.portfolio.savings.service.SavingsAccountChargeReadPlatformService;
import org.apache.fineract.portfolio.savings.service.SavingsAccountWritePlatformService;
import org.apache.fineract.portfolio.shareaccounts.service.ShareAccountDividendReadPlatformService;
import org.apache.fineract.portfolio.shareaccounts.service.ShareAccountSchedularService;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "scheduledJobRunnerService")
public class ScheduledJobRunnerServiceImpl implements ScheduledJobRunnerService {

	private final static Logger logger = LoggerFactory.getLogger(ScheduledJobRunnerServiceImpl.class);
	private final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
	private final DateTimeFormatter formatterWithTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	private final RoutingDataSourceServiceFactory dataSourceServiceFactory;
	private final SavingsAccountWritePlatformService savingsAccountWritePlatformService;
	private final SavingsAccountChargeReadPlatformService savingsAccountChargeReadPlatformService;
	private final DepositAccountReadPlatformService depositAccountReadPlatformService;
	private final DepositAccountWritePlatformService depositAccountWritePlatformService;
	private final ShareAccountDividendReadPlatformService shareAccountDividendReadPlatformService;
	private final ShareAccountSchedularService shareAccountSchedularService;

	@Autowired
	public ScheduledJobRunnerServiceImpl(final RoutingDataSourceServiceFactory dataSourceServiceFactory,
			final SavingsAccountWritePlatformService savingsAccountWritePlatformService,
			final SavingsAccountChargeReadPlatformService savingsAccountChargeReadPlatformService,
			final DepositAccountReadPlatformService depositAccountReadPlatformService,
			final DepositAccountWritePlatformService depositAccountWritePlatformService,
			final ShareAccountDividendReadPlatformService shareAccountDividendReadPlatformService,
			final ShareAccountSchedularService shareAccountSchedularService) {
		this.dataSourceServiceFactory = dataSourceServiceFactory;
		this.savingsAccountWritePlatformService = savingsAccountWritePlatformService;
		this.savingsAccountChargeReadPlatformService = savingsAccountChargeReadPlatformService;
		this.depositAccountReadPlatformService = depositAccountReadPlatformService;
		this.depositAccountWritePlatformService = depositAccountWritePlatformService;
		this.shareAccountDividendReadPlatformService = shareAccountDividendReadPlatformService;
		this.shareAccountSchedularService = shareAccountSchedularService;
	}

	@Transactional
	@Override
	@CronTarget(jobName = JobName.UPDATE_LOAN_SUMMARY)
	public void updateLoanSummaryDetails() {

		final JdbcTemplate jdbcTemplate = new JdbcTemplate(
				this.dataSourceServiceFactory.determineDataSourceService().retrieveDataSource());

		final StringBuilder updateSqlBuilder = new StringBuilder(900);
		updateSqlBuilder.append("UPDATE (");
		updateSqlBuilder.append(" SELECT x.loanid,");
		updateSqlBuilder
				.append(" x.principal_disbursed_derived, x.principal_repaid_derived,x.principal_writtenoff_derived,");
		updateSqlBuilder.append(
				" x.interest_charged_derived, x.interest_repaid_derived, x.interest_waived_derived, x.interest_writtenoff_derived,");
		updateSqlBuilder.append(
				" x.fee_charges_charged_derived, x.fee_charges_repaid_derived, x.fee_charges_waived_derived, x.fee_charges_writtenoff_derived,");
		updateSqlBuilder.append(
				" x.penalty_charges_charged_derived, x.penalty_charges_repaid_derived, x.penalty_charges_waived_derived, x.penalty_charges_writtenoff_derived,");
		updateSqlBuilder.append(
				" m_loan.principal_disbursed_derived AS principal_disbursed_derived_ml, m_loan.principal_repaid_derived as principal_repaid_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.principal_writtenoff_derived as principal_writtenoff_derived_ml, m_loan.principal_outstanding_derived as principal_outstanding_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.interest_charged_derived as interest_charged_derived_ml, m_loan.interest_repaid_derived as interest_repaid_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.interest_waived_derived as interest_waived_derived_ml, m_loan.interest_writtenoff_derived as interest_writtenoff_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.interest_outstanding_derived as interest_outstanding_derived_ml, m_loan.fee_charges_charged_derived as fee_charges_charged_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.fee_charges_repaid_derived as fee_charges_repaid_derived_ml, m_loan.fee_charges_waived_derived as fee_charges_waived_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.fee_charges_writtenoff_derived as fee_charges_writtenoff_derived_ml, m_loan.fee_charges_outstanding_derived as fee_charges_outstanding_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.penalty_charges_charged_derived as penalty_charges_charged_derived_ml, m_loan.penalty_charges_repaid_derived as penalty_charges_repaid_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.penalty_charges_waived_derived as penalty_charges_waived_derived_ml, m_loan.penalty_charges_writtenoff_derived as penalty_charges_writtenoff_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.penalty_charges_outstanding_derived as penalty_charges_outstanding_derived_ml, m_loan.total_expected_repayment_derived as total_expected_repayment_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.total_repayment_derived as total_repayment_derived_ml, m_loan.total_expected_costofloan_derived as total_expected_costofloan_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.total_costofloan_derived as total_costofloan_derived_ml, m_loan.total_waived_derived as total_waived_derived_ml,");
		updateSqlBuilder.append(
				" m_loan.total_writtenoff_derived as total_writtenoff_derived_ml, m_loan.total_outstanding_derived as total_outstanding_derived_ml");
		updateSqlBuilder.append(" FROM ( SELECT ml.id AS loanid,");
		updateSqlBuilder.append(
				" SUM(mr.principal_amount) AS principal_disbursed_derived, SUM(nvl(mr.principal_completed_derived, 0)) AS principal_repaid_derived,");
		updateSqlBuilder.append(
				" SUM(nvl(mr.principal_writtenoff_derived, 0)) AS principal_writtenoff_derived, SUM(nvl(mr.interest_amount, 0)) AS interest_charged_derived,");
		updateSqlBuilder.append(
				" SUM(nvl(mr.interest_completed_derived, 0)) AS interest_repaid_derived, SUM(nvl(mr.interest_waived_derived, 0)) AS interest_waived_derived,");
		updateSqlBuilder.append(
				" SUM(nvl(mr.interest_writtenoff_derived, 0)) AS interest_writtenoff_derived, SUM(nvl(mr.fee_charges_amount, 0)) + nvl((");
		updateSqlBuilder.append(
				" SELECT SUM(lc.amount) FROM m_loan_charge lc WHERE lc.loan_id = ml.id AND lc.is_active = 1 AND lc.charge_time_enum = 1 ), 0) AS fee_charges_charged_derived,");
		updateSqlBuilder.append(
				" SUM(nvl(mr.fee_charges_completed_derived, 0)) + nvl(( SELECT SUM(lc.amount_paid_derived) FROM m_loan_charge lc");
		updateSqlBuilder.append(
				" WHERE lc.loan_id = ml.id AND lc.is_active = 1 AND lc.charge_time_enum = 1 ), 0) AS fee_charges_repaid_derived,");
		updateSqlBuilder.append(
				" SUM(nvl(mr.fee_charges_waived_derived, 0)) AS fee_charges_waived_derived, SUM(nvl(mr.fee_charges_writtenoff_derived, 0)) AS fee_charges_writtenoff_derived,");
		updateSqlBuilder.append(
				" SUM(nvl(mr.penalty_charges_amount, 0)) AS penalty_charges_charged_derived, SUM(nvl(mr.penalty_charges_completed_derived, 0)) AS penalty_charges_repaid_derived,");
		updateSqlBuilder.append(
				" SUM(nvl(mr.penalty_charges_waived_derived, 0)) AS penalty_charges_waived_derived, SUM(nvl(mr.penalty_charges_writtenoff_derived, 0)) AS penalty_charges_writtenoff_derived");
		updateSqlBuilder.append(
				" FROM m_loan ml INNER JOIN m_loan_repayment_schedule   mr ON mr.loan_id = ml.id WHERE ml.disbursedon_date IS NOT NULL GROUP BY ml.id ) x");
		updateSqlBuilder.append("  JOIN m_loan ON x.loanid = m_loan.id)");
		updateSqlBuilder.append(
				" SET principal_disbursed_derived_ml = principal_disbursed_derived, principal_repaid_derived_ml = principal_repaid_derived ,");
		updateSqlBuilder.append(
				" principal_writtenoff_derived_ml = principal_writtenoff_derived, principal_outstanding_derived_ml = ( principal_disbursed_derived - ( principal_repaid_derived + principal_writtenoff_derived ) ),");
		updateSqlBuilder.append(
				" interest_charged_derived_ml = interest_charged_derived, interest_repaid_derived_ml = interest_repaid_derived, interest_waived_derived_ml = interest_waived_derived,");
		updateSqlBuilder.append(" interest_writtenoff_derived_ml = interest_writtenoff_derived,");
		updateSqlBuilder.append(
				" interest_outstanding_derived_ml = ( interest_charged_derived - ( interest_repaid_derived + interest_waived_derived + interest_writtenoff_derived ) ),");
		updateSqlBuilder.append(" fee_charges_charged_derived_ml = fee_charges_charged_derived,");
		updateSqlBuilder.append(" fee_charges_repaid_derived_ml = fee_charges_repaid_derived,");
		updateSqlBuilder.append(
				" fee_charges_waived_derived_ml = fee_charges_waived_derived, fee_charges_writtenoff_derived_ml = fee_charges_writtenoff_derived,");
		updateSqlBuilder.append(
				" fee_charges_outstanding_derived_ml = ( fee_charges_charged_derived - ( fee_charges_repaid_derived + fee_charges_waived_derived + fee_charges_writtenoff_derived ) ),");
		updateSqlBuilder.append(
				" penalty_charges_charged_derived_ml = penalty_charges_charged_derived, penalty_charges_repaid_derived_ml = penalty_charges_repaid_derived,");
		updateSqlBuilder.append(
				" penalty_charges_waived_derived_ml = penalty_charges_waived_derived,penalty_charges_writtenoff_derived_ml = penalty_charges_writtenoff_derived,");
		updateSqlBuilder.append(
				" penalty_charges_outstanding_derived_ml = ( penalty_charges_charged_derived - ( penalty_charges_repaid_derived + penalty_charges_waived_derived + penalty_charges_writtenoff_derived ) ),");
		updateSqlBuilder.append(
				" total_expected_repayment_derived_ml = ( principal_disbursed_derived + interest_charged_derived + fee_charges_charged_derived + penalty_charges_charged_derived ),");
		updateSqlBuilder.append(
				" total_repayment_derived_ml = ( principal_repaid_derived + interest_repaid_derived + fee_charges_repaid_derived + penalty_charges_repaid_derived ),");
		updateSqlBuilder.append(
				" total_expected_costofloan_derived_ml = ( interest_charged_derived + fee_charges_charged_derived + penalty_charges_charged_derived),");
		updateSqlBuilder.append(
				" total_costofloan_derived_ml = (interest_repaid_derived + fee_charges_repaid_derived + penalty_charges_repaid_derived ),");
		updateSqlBuilder.append(
				" total_waived_derived_ml = ( interest_waived_derived + fee_charges_waived_derived + penalty_charges_waived_derived ),");
		updateSqlBuilder.append(
				" total_writtenoff_derived_ml = ( interest_writtenoff_derived + fee_charges_writtenoff_derived + penalty_charges_writtenoff_derived ),");
		updateSqlBuilder.append(
				" total_outstanding_derived_ml = ( principal_disbursed_derived - ( principal_repaid_derived + principal_writtenoff_derived ) ) + ");
		updateSqlBuilder.append(
				" ( interest_charged_derived - ( interest_repaid_derived + interest_waived_derived + interest_writtenoff_derived )) + ");
		updateSqlBuilder.append(
				" ( fee_charges_charged_derived - ( fee_charges_repaid_derived + fee_charges_waived_derived + fee_charges_writtenoff_derived) ) + ");
		updateSqlBuilder.append(
				" ( penalty_charges_charged_derived - ( penalty_charges_repaid_derived + penalty_charges_waived_derived + penalty_charges_writtenoff_derived) )");

		final int result = jdbcTemplate.update(updateSqlBuilder.toString());

		logger.info(ThreadLocalContextUtil.getTenant().getName() + ": Results affected by update: " + result);
	}

	@Transactional
	@Override
	@CronTarget(jobName = JobName.UPDATE_LOAN_PAID_IN_ADVANCE)
	public void updateLoanPaidInAdvance() {

		final JdbcTemplate jdbcTemplate = new JdbcTemplate(
				this.dataSourceServiceFactory.determineDataSourceService().retrieveDataSource());

		jdbcTemplate.execute("truncate table m_loan_paid_in_advance");

		final StringBuilder updateSqlBuilder = new StringBuilder(900);

		updateSqlBuilder.append(
				"INSERT INTO m_loan_paid_in_advance(loan_id, principal_in_advance_derived, interest_in_advance_derived, fee_charges_in_advance_derived, penalty_charges_in_advance_derived, total_in_advance_derived)");
		updateSqlBuilder.append(" select ml.id as loanId,");
		updateSqlBuilder.append(" SUM(nvl(mr.principal_completed_derived, 0)) as principal_in_advance_derived,");
		updateSqlBuilder.append(" SUM(nvl(mr.interest_completed_derived, 0)) as interest_in_advance_derived,");
		updateSqlBuilder.append(" SUM(nvl(mr.fee_charges_completed_derived, 0)) as fee_charges_in_advance_derived,");
		updateSqlBuilder
				.append(" SUM(nvl(mr.penalty_charges_completed_derived, 0)) as penalty_charges_in_advance_derived,");
		updateSqlBuilder.append(
				" (SUM(nvl(mr.principal_completed_derived, 0)) + SUM(nvl(mr.interest_completed_derived, 0)) + SUM(nvl(mr.fee_charges_completed_derived, 0)) + SUM(nvl(mr.penalty_charges_completed_derived, 0))) as total_in_advance_derived");
		updateSqlBuilder.append(" FROM m_loan ml ");
		updateSqlBuilder.append(" INNER JOIN m_loan_repayment_schedule mr on mr.loan_id = ml.id ");
		updateSqlBuilder.append(" WHERE ml.loan_status_id = 300 ");
		updateSqlBuilder.append(" and mr.duedate >= SYSDATE ");
		updateSqlBuilder.append(" GROUP BY ml.id");
		updateSqlBuilder.append(
				" HAVING (SUM(nvl(mr.principal_completed_derived, 0)) + SUM(nvl(mr.interest_completed_derived, 0)) +");
		updateSqlBuilder.append(
				" SUM(nvl(mr.fee_charges_completed_derived, 0)) + SUM(nvl(mr.penalty_charges_completed_derived, 0))) > 0.0");

		final int result = jdbcTemplate.update(updateSqlBuilder.toString());

		logger.info(ThreadLocalContextUtil.getTenant().getName() + ": Results affected by update: " + result);
	}

	@Override
	@CronTarget(jobName = JobName.APPLY_ANNUAL_FEE_FOR_SAVINGS)
	public void applyAnnualFeeForSavings() {

		final Collection<SavingsAccountAnnualFeeData> annualFeeData = this.savingsAccountChargeReadPlatformService
				.retrieveChargesWithAnnualFeeDue();

		for (final SavingsAccountAnnualFeeData savingsAccountReference : annualFeeData) {
			try {
				this.savingsAccountWritePlatformService.applyAnnualFee(savingsAccountReference.getId(),
						savingsAccountReference.getAccountId());
			} catch (final PlatformApiDataValidationException e) {
				final List<ApiParameterError> errors = e.getErrors();
				for (final ApiParameterError error : errors) {
					logger.error("Apply annual fee failed for account:" + savingsAccountReference.getAccountNo()
							+ " with message " + error.getDeveloperMessage());
				}
			} catch (final Exception ex) {
				// need to handle this scenario
			}
		}

		logger.info(ThreadLocalContextUtil.getTenant().getName() + ": Savings accounts affected by update: "
				+ annualFeeData.size());
	}

	@Override
	@CronTarget(jobName = JobName.PAY_DUE_SAVINGS_CHARGES)
	public void applyDueChargesForSavings() throws JobExecutionException {
		final Collection<SavingsAccountAnnualFeeData> chargesDueData = this.savingsAccountChargeReadPlatformService
				.retrieveChargesWithDue();
		final StringBuilder errorMsg = new StringBuilder();

		for (final SavingsAccountAnnualFeeData savingsAccountReference : chargesDueData) {
			try {
				this.savingsAccountWritePlatformService.applyChargeDue(savingsAccountReference.getId(),
						savingsAccountReference.getAccountId());
			} catch (final PlatformApiDataValidationException e) {
				final List<ApiParameterError> errors = e.getErrors();
				for (final ApiParameterError error : errors) {
					logger.error("Apply Charges due for savings failed for account:"
							+ savingsAccountReference.getAccountNo() + " with message " + error.getDeveloperMessage());
					errorMsg.append("Apply Charges due for savings failed for account:")
							.append(savingsAccountReference.getAccountNo()).append(" with message ")
							.append(error.getDeveloperMessage());
				}
			}
		}

		logger.info(ThreadLocalContextUtil.getTenant().getName() + ": Savings accounts affected by update: "
				+ chargesDueData.size());

		/*
		 * throw exception if any charge payment fails.
		 */
		if (errorMsg.length() > 0) {
			throw new JobExecutionException(errorMsg.toString());
		}
	}

	@Transactional
	@Override
	@CronTarget(jobName = JobName.UPDATE_NPA)
	public void updateNPA() {

		final JdbcTemplate jdbcTemplate = new JdbcTemplate(
				this.dataSourceServiceFactory.determineDataSourceService().retrieveDataSource());

		final StringBuilder resetNPASqlBuilder = new StringBuilder(900);
		resetNPASqlBuilder.append(" UPDATE m_loan SET is_npa = 0 ");
		resetNPASqlBuilder.append(" WHERE m_loan.id IN ( ");
		resetNPASqlBuilder.append(
				" SELECT loan.id FROM m_loan loan LEFT JOIN m_loan_arrears_aging   laa ON laa.loan_id = loan.id ");
		resetNPASqlBuilder.append(
				" INNER JOIN m_product_loan mpl ON mpl.id = loan.product_id AND mpl.overdue_days_for_npa IS NOT NULL  AND loan.loan_status_id = 300 AND mpl.account_moves_out_of_npa_only_on_arrears_completion = 0 ");
		resetNPASqlBuilder.append(
				" OR ( mpl.account_moves_out_of_npa_only_on_arrears_completion = 1 AND laa.overdue_since_date_derived IS NULL )) ");

		jdbcTemplate.update(resetNPASqlBuilder.toString());

		final StringBuilder updateSqlBuilder = new StringBuilder(900);

		updateSqlBuilder.append(" UPDATE m_loan ml SET ml.is_npa = 1 ");
		updateSqlBuilder.append(" WHERE ml.id IN ( ");
		updateSqlBuilder.append(" SELECT loan.id FROM m_loan_arrears_aging laa ");
		updateSqlBuilder.append(
				" INNER JOIN m_loan loan ON laa.loan_id = loan.id INNER JOIN m_product_loan mpl ON mpl.id = loan.product_id AND mpl.overdue_days_for_npa IS NOT NULL ");
		updateSqlBuilder.append(
				" AND loan.loan_status_id = 300 AND laa.overdue_since_date_derived < ( SYSDATE - nvl(mpl.overdue_days_for_npa, 0))) ");

		final int result = jdbcTemplate.update(updateSqlBuilder.toString());

		logger.info(ThreadLocalContextUtil.getTenant().getName() + ": Results affected by update: " + result);
	}

	@Override
	@CronTarget(jobName = JobName.UPDATE_DEPOSITS_ACCOUNT_MATURITY_DETAILS)
	public void updateMaturityDetailsOfDepositAccounts() {

		final Collection<DepositAccountData> depositAccounts = this.depositAccountReadPlatformService
				.retrieveForMaturityUpdate();

		for (final DepositAccountData depositAccount : depositAccounts) {
			try {
				final DepositAccountType depositAccountType = DepositAccountType
						.fromInt(depositAccount.depositType().getId().intValue());
				this.depositAccountWritePlatformService.updateMaturityDetails(depositAccount.id(), depositAccountType);
			} catch (final PlatformApiDataValidationException e) {
				final List<ApiParameterError> errors = e.getErrors();
				for (final ApiParameterError error : errors) {
					logger.error("Update maturity details failed for account:" + depositAccount.accountNo()
							+ " with message " + error.getDeveloperMessage());
				}
			} catch (final Exception ex) {
				// need to handle this scenario
			}
		}

		logger.info(ThreadLocalContextUtil.getTenant().getName() + ": Deposit accounts affected by update: "
				+ depositAccounts.size());
	}

	@Override
	@CronTarget(jobName = JobName.GENERATE_RD_SCEHDULE)
	public void generateRDSchedule() {
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(
				this.dataSourceServiceFactory.determineDataSourceService().retrieveDataSource());
		final Collection<Map<String, Object>> scheduleDetails = this.depositAccountReadPlatformService
				.retriveDataForRDScheduleCreation();
		String insertSql = "INSERT INTO m_mandatory_savings_schedule (savings_account_id, duedate, installment, deposit_amount, completed_derived, created_date, lastmodified_date) VALUES ";
		StringBuilder sb = new StringBuilder();
		String currentDate = formatterWithTime.print(DateUtils.getLocalDateTimeOfTenant());
		int iterations = 0;
		for (Map<String, Object> details : scheduleDetails) {
			Long count = (Long) details.get("futureInstallemts");
			if (count == null) {
				count = 0l;
			}
			final Long savingsId = (Long) details.get("savingsId");
			final BigDecimal amount = (BigDecimal) details.get("amount");
			final String recurrence = (String) details.get("recurrence");
			Date date = (Date) details.get("dueDate");
			LocalDate lastDepositDate = new LocalDate(date);
			Integer installmentNumber = (Integer) details.get("installment");
			while (count < DepositAccountUtils.GENERATE_MINIMUM_NUMBER_OF_FUTURE_INSTALMENTS) {
				count++;
				installmentNumber++;
				lastDepositDate = DepositAccountUtils.calculateNextDepositDate(lastDepositDate, recurrence);

				if (sb.length() > 0) {
					sb.append(", ");
				}
				sb.append("(");
				sb.append(savingsId);
				sb.append(",'");
				sb.append(formatter.print(lastDepositDate));
				sb.append("',");
				sb.append(installmentNumber);
				sb.append(",");
				sb.append(amount);
				sb.append(", b'0','");
				sb.append(currentDate);
				sb.append("','");
				sb.append(currentDate);
				sb.append("')");
				iterations++;
				if (iterations > 200) {
					jdbcTemplate.update(insertSql + sb.toString());
					sb = new StringBuilder();
				}

			}
		}

		if (sb.length() > 0) {
			jdbcTemplate.update(insertSql + sb.toString());
		}

	}

	@Override
	@CronTarget(jobName = JobName.POST_DIVIDENTS_FOR_SHARES)
	public void postDividends() throws JobExecutionException {
		List<Map<String, Object>> dividendDetails = this.shareAccountDividendReadPlatformService
				.retriveDividendDetailsForPostDividents();
		StringBuilder errorMsg = new StringBuilder();
		for (Map<String, Object> dividendMap : dividendDetails) {
			Long id = null;
			Long savingsId = null;
			if (dividendMap.get("id") instanceof BigInteger) { // Drizzle is returning BigInteger
				id = ((BigInteger) dividendMap.get("id")).longValue();
				savingsId = ((BigInteger) dividendMap.get("savingsAccountId")).longValue();
			} else { // MySQL connector is returning Long
				id = (Long) dividendMap.get("id");
				savingsId = (Long) dividendMap.get("savingsAccountId");
			}
			try {
				this.shareAccountSchedularService.postDividend(id, savingsId);
			} catch (final PlatformApiDataValidationException e) {
				final List<ApiParameterError> errors = e.getErrors();
				for (final ApiParameterError error : errors) {
					logger.error("Post Dividends to savings failed for Divident detail Id:" + id + " and savings Id: "
							+ savingsId + " with message " + error.getDeveloperMessage());
					errorMsg.append("Post Dividends to savings failed for Divident detail Id:").append(id)
							.append(" and savings Id:").append(savingsId).append(" with message ")
							.append(error.getDeveloperMessage());
				}
			} catch (final Exception e) {
				logger.error("Post Dividends to savings failed for Divident detail Id:" + id + " and savings Id: "
						+ savingsId + " with message " + e.getLocalizedMessage());
				errorMsg.append("Post Dividends to savings failed for Divident detail Id:").append(id)
						.append(" and savings Id:").append(savingsId).append(" with message ")
						.append(e.getLocalizedMessage());
			}
		}

		if (errorMsg.length() > 0) {
			throw new JobExecutionException(errorMsg.toString());
		}
	}

}
