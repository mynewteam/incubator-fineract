package org.apache.fineract.portfolio.cheque.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
import org.apache.fineract.portfolio.accountdetails.data.SavingsAccountSummaryData;
import org.apache.fineract.portfolio.accountdetails.service.AccountEnumerations;
import org.apache.fineract.portfolio.cheque.data.ChequeDepositToSummaryCollectionData;
import org.apache.fineract.portfolio.cheque.data.ChequeSummaryCollectionData;
//import org.apache.fineract.portfolio.cheque.data.ChequeDepositToSummaryCollectionData;
import org.apache.fineract.portfolio.client.data.ClientData;
import org.apache.fineract.portfolio.client.service.ClientReadPlatformService;
import org.apache.fineract.portfolio.savings.data.SavingsAccountApplicationTimelineData;
import org.apache.fineract.portfolio.savings.data.SavingsAccountStatusEnumData;
import org.apache.fineract.portfolio.savings.data.SavingsAccountSubStatusEnumData;
import org.apache.fineract.portfolio.savings.service.SavingsEnumerations;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.apache.fineract.infrastructure.security.utils.ColumnValidator;

@Service
public class ChequeDetailsReadPlatformServiceJpaRepositoryImpl implements ChequeDetailReadPlatformService {
	private final JdbcTemplate jdbcTemplate;
	private final ColumnValidator columnValidator;
//	 private final PortfolioAccountReadPlatformService portfolioAccountReadPlatformService;
	 private final ClientReadPlatformService clientReadPlatformService;
	@Autowired
	public ChequeDetailsReadPlatformServiceJpaRepositoryImpl(final RoutingDataSource dataSource,
			final ColumnValidator columnValidator,  final ClientReadPlatformService clientReadPlatformService) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.columnValidator = columnValidator;
		this.clientReadPlatformService=clientReadPlatformService;
	}
	
	@Override
	public ChequeDepositToSummaryCollectionData retrieveClientAccountdepositto(String accountsaving) {
		ClientData clientinfo = null;
		 long clientid;
		final String savingswhereClause = " where sa.account_no = ? order by sa.status_enum ASC, sa.account_no ASC";
		final List<SavingsAccountSummaryData> deposittoaccount = retrieveSavingDetails(savingswhereClause,
				new Object[] { accountsaving });
		
		clientid =deposittoaccount.get(0).getClientid();
//		
		clientinfo = this.clientReadPlatformService.retrieveOne(clientid);
		
		return new ChequeDepositToSummaryCollectionData(deposittoaccount,clientinfo);
	}

	@Override
	public ChequeSummaryCollectionData retrieveClientAccountDetails(String accountsaving) {
		ClientData clientinfo = null;
		 long clientid;
//		
		final String savingswhereClause = " where sa.account_no = ? order by sa.status_enum ASC, sa.account_no ASC";
		final List<SavingsAccountSummaryData> savingsAccounts = retrieveSavingDetails(savingswhereClause,
				new Object[] { accountsaving });
		
		clientid =savingsAccounts.get(0).getClientid();
//		
		clientinfo = this.clientReadPlatformService.retrieveOne(clientid);
		
		return new ChequeSummaryCollectionData(savingsAccounts,clientinfo);
	}

	private List<SavingsAccountSummaryData> retrieveSavingDetails(String savingswhereClause, Object[] inputs) {
		final SavingsAccountSummaryDataMapper savingsAccountSummaryDataMapper = new SavingsAccountSummaryDataMapper();
		final String savingsSql = "select " + savingsAccountSummaryDataMapper.schema() + savingswhereClause;
		this.columnValidator.validateSqlInjection(savingsAccountSummaryDataMapper.schema(), savingswhereClause);
		return this.jdbcTemplate.query(savingsSql, savingsAccountSummaryDataMapper, inputs);
	}

	private static final class SavingsAccountSummaryDataMapper implements RowMapper<SavingsAccountSummaryData> {

		final String schemaSql;

		public SavingsAccountSummaryDataMapper() {
			final StringBuilder accountsSummary = new StringBuilder();
			accountsSummary.append(
					"sa.id as id, sa.account_no as accountNo, sa.external_id as externalId, sa.status_enum as statusEnum, ");
			accountsSummary.append("sa.account_type_enum as accountType, ");
			accountsSummary.append("sa.account_balance_derived as accountBalance, ");

			accountsSummary.append("sa.submittedon_date as submittedOnDate,");
			accountsSummary.append("sbu.username as submittedByUsername,");
			accountsSummary.append("sbu.firstname as submittedByFirstname, sbu.lastname as submittedByLastname,");

			accountsSummary.append("sa.rejectedon_date as rejectedOnDate,");
			accountsSummary.append("rbu.username as rejectedByUsername,");
			accountsSummary.append("rbu.firstname as rejectedByFirstname, rbu.lastname as rejectedByLastname,");

			accountsSummary.append("sa.withdrawnon_date as withdrawnOnDate,");
			accountsSummary.append("wbu.username as withdrawnByUsername,");
			accountsSummary.append("wbu.firstname as withdrawnByFirstname, wbu.lastname as withdrawnByLastname,");

			accountsSummary.append("sa.approvedon_date as approvedOnDate,");
			accountsSummary.append("abu.username as approvedByUsername,");
			accountsSummary.append("abu.firstname as approvedByFirstname, abu.lastname as approvedByLastname,");

			accountsSummary.append("sa.activatedon_date as activatedOnDate,");
			accountsSummary.append("avbu.username as activatedByUsername,");
			accountsSummary.append("avbu.firstname as activatedByFirstname, avbu.lastname as activatedByLastname,");

			accountsSummary.append("sa.sub_status_enum as subStatusEnum, ");
			accountsSummary.append("(select nvl(max(sat.transaction_date),sa.activatedon_date) ");
			accountsSummary.append("from m_savings_account_transaction sat ");
			accountsSummary.append("where sat.is_reversed = 0 ");
			accountsSummary.append("and sat.transaction_type_enum in (1,2) ");
			accountsSummary.append("and sat.savings_account_id = sa.id) as lastActiveTransactionDate, ");

			accountsSummary.append("sa.closedon_date as closedOnDate,");
			accountsSummary.append("sa.client_id as clientid,");//
			accountsSummary.append("cbu.username as closedByUsername,");
			accountsSummary.append("cbu.firstname as closedByFirstname, cbu.lastname as closedByLastname,");

			accountsSummary.append(
					"sa.currency_code as currencyCode, sa.currency_digits as currencyDigits, sa.currency_multiplesof as inMultiplesOf, ");
			
			  accountsSummary.append(
			  "curr.name as currencyName, curr.internationalized_name_code as currencyNameCode, "
			  ); accountsSummary.append("curr.display_symbol as currencyDisplaySymbol, ");
			  accountsSummary.append(
			  "sa.product_id as productId, p.name as productName, p.short_name as shortProductName, "
			 ); accountsSummary.append("sa.deposit_type_enum as depositType ");
			 
			accountsSummary.append("from m_savings_account sa ");
			accountsSummary.append("join m_savings_product p on p.id = sa.product_id ");
			accountsSummary.append("join m_currency curr on curr.code = sa.currency_code ");
			accountsSummary.append("left join m_appuser sbu on sbu.id = sa.submittedon_userid ");
			accountsSummary.append("left join m_appuser rbu on rbu.id = sa.rejectedon_userid ");
			accountsSummary.append("left join m_appuser wbu on wbu.id = sa.withdrawnon_userid ");
			accountsSummary.append("left join m_appuser abu on abu.id = sa.approvedon_userid ");
			accountsSummary.append("left join m_appuser avbu on rbu.id = sa.activatedon_userid ");
			accountsSummary.append("left join m_appuser cbu on cbu.id = sa.closedon_userid ");

			this.schemaSql = accountsSummary.toString();
		}

		public String schema() {
			return this.schemaSql;
		}

		@Override
		public SavingsAccountSummaryData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
				throws SQLException {

			final Long id = JdbcSupport.getLong(rs, "id");
			final String accountNo = rs.getString("accountNo");
			final String externalId = rs.getString("externalId");
			final Long productId = JdbcSupport.getLong(rs, "productId");
			final String productName = rs.getString("productName");
			final String shortProductName = rs.getString("shortProductName");
			final Integer statusId = JdbcSupport.getInteger(rs, "statusEnum");
			final BigDecimal accountBalance = JdbcSupport.getBigDecimalDefaultToNullIfZero(rs, "accountBalance");
			final SavingsAccountStatusEnumData status = SavingsEnumerations.status(statusId);
			final Integer accountType = JdbcSupport.getInteger(rs, "accountType");
			final Long clientid = JdbcSupport.getLong(rs, "clientid");
			final EnumOptionData accountTypeData = AccountEnumerations.loanType(accountType);
			final Integer depositTypeId = JdbcSupport.getInteger(rs, "depositType");
			final EnumOptionData depositTypeData = SavingsEnumerations.depositType(depositTypeId);

			final String currencyCode = rs.getString("currencyCode");
			final String currencyName = rs.getString("currencyName");
			final String currencyNameCode = rs.getString("currencyNameCode");
			final String currencyDisplaySymbol = rs.getString("currencyDisplaySymbol");
			final Integer currencyDigits = JdbcSupport.getInteger(rs, "currencyDigits");
			final Integer inMultiplesOf = JdbcSupport.getInteger(rs, "inMultiplesOf");
			final CurrencyData currency = new CurrencyData(currencyCode, currencyName, currencyDigits, inMultiplesOf,
					currencyDisplaySymbol, currencyNameCode);

			
			  final LocalDate submittedOnDate = JdbcSupport.getLocalDate(rs,
			  "submittedOnDate"); final String submittedByUsername =
			  rs.getString("submittedByUsername"); final String submittedByFirstname =
			  rs.getString("submittedByFirstname"); final String submittedByLastname =
			  rs.getString("submittedByLastname");
			  
			  final LocalDate rejectedOnDate = JdbcSupport.getLocalDate(rs,
			  "rejectedOnDate"); final String rejectedByUsername =
			  rs.getString("rejectedByUsername"); final String rejectedByFirstname =
			  rs.getString("rejectedByFirstname"); final String rejectedByLastname =
			  rs.getString("rejectedByLastname");
			  
			  final LocalDate withdrawnOnDate = JdbcSupport.getLocalDate(rs,
			  "withdrawnOnDate"); final String withdrawnByUsername =
			  rs.getString("withdrawnByUsername"); final String withdrawnByFirstname =
			  rs.getString("withdrawnByFirstname"); final String withdrawnByLastname =
			  rs.getString("withdrawnByLastname");
			  
			  final LocalDate approvedOnDate = JdbcSupport.getLocalDate(rs,
			  "approvedOnDate"); final String approvedByUsername =
			  rs.getString("approvedByUsername"); final String approvedByFirstname =
			  rs.getString("approvedByFirstname"); final String approvedByLastname =
			  rs.getString("approvedByLastname");
			  
			  final LocalDate activatedOnDate = JdbcSupport.getLocalDate(rs,
			  "activatedOnDate"); final String activatedByUsername =
			  rs.getString("activatedByUsername"); final String activatedByFirstname =
			  rs.getString("activatedByFirstname"); final String activatedByLastname =
			  rs.getString("activatedByLastname");
			  
			  final LocalDate closedOnDate = JdbcSupport.getLocalDate(rs, "closedOnDate");
			  final String closedByUsername = rs.getString("closedByUsername"); final
			  String closedByFirstname = rs.getString("closedByFirstname"); final String
			  closedByLastname = rs.getString("closedByLastname"); final Integer
			  subStatusEnum = JdbcSupport.getInteger(rs, "subStatusEnum"); final
			  SavingsAccountSubStatusEnumData subStatus =
			  SavingsEnumerations.subStatus(subStatusEnum);
			  
			  
			  
			  final LocalDate lastActiveTransactionDate = JdbcSupport.getLocalDate(rs,
			  "lastActiveTransactionDate");
			  
			  final SavingsAccountApplicationTimelineData timeline = new
			  SavingsAccountApplicationTimelineData( submittedOnDate, submittedByUsername,
			  submittedByFirstname, submittedByLastname, rejectedOnDate,
			  rejectedByUsername, rejectedByFirstname, rejectedByLastname, withdrawnOnDate,
			  withdrawnByUsername, withdrawnByFirstname, withdrawnByLastname,
			  approvedOnDate, approvedByUsername, approvedByFirstname, approvedByLastname,
			  activatedOnDate, activatedByUsername, activatedByFirstname,
			  activatedByLastname, closedOnDate, closedByUsername, closedByFirstname,
			  closedByLastname);
			 
			return new SavingsAccountSummaryData(id,clientid, accountNo, externalId, productId, productName, shortProductName,
					status, currency, accountBalance, accountTypeData, timeline, depositTypeData, subStatus, lastActiveTransactionDate);
		}
	}



}
