package org.apache.fineract.accounting.classify.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.fineract.accounting.classify.data.GLAccountAmountData;
import org.apache.fineract.accounting.classify.data.LoanArrearClassifyData;
import org.apache.fineract.accounting.classify.data.LoanProductSubtypeMappingData;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

@Service
public class LoanSubtypeMappingReadPlatformServiceImplement implements LoanSubtypeMappingReadPlatformService {
	private final NamedParameterJdbcTemplate namedJdbctemplate;
	private final JdbcTemplate jdbctemplate;
	// private final static Logger logger =
	// LoggerFactory.getLogger(ProductClassifyReadPlatformServiceImpl.class);
	private final String schema;

	@Autowired
	public LoanSubtypeMappingReadPlatformServiceImplement(final RoutingDataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
		this.namedJdbctemplate = new NamedParameterJdbcTemplate(dataSource);

		final StringBuilder builder = new StringBuilder(400);

		builder.append(" id, ");
		builder.append(" product_id, ");
		builder.append(" loan_subtype_status_id, ");
		builder.append(" min_age, ");
		builder.append(" max_age, ");
		builder.append(" portfolio_acc_id, ");
		builder.append(" int_receivable_acc_id, ");
		builder.append(" income_acc_id ");
		builder.append(" from  loan_product_subtype_mapping ");
		this.schema = builder.toString();
	}

	public String schema() {
		return this.schema;
	}

	@Override
	public LoanProductSubtypeMappingData retrieveLoanProductSubtypeMappingOne(Long id) {
		LoanProductSubtypeMappingDataMapper mapper = new LoanProductSubtypeMappingDataMapper();

		final StringBuilder sqlBuilder = new StringBuilder(200);

		sqlBuilder.append("select SQL_CALC_FOUND_ROWS ");
		sqlBuilder.append(this.schema());
		sqlBuilder.append(" WHERE  id = ? ");

		return this.jdbctemplate.queryForObject(sqlBuilder.toString(), mapper, new Object[] { id });
	}

	@Override
	public Collection<LoanProductSubtypeMappingData> retrieveLoanProductSubtypeMappings(Long productId, Integer age) {
		LoanProductSubtypeMappingDataMapper mapper = new LoanProductSubtypeMappingDataMapper();
		final StringBuilder sqlBuilder = new StringBuilder(200);
		sqlBuilder.append("select SQL_CALC_FOUND_ROWS ");
		sqlBuilder.append(this.schema());
		sqlBuilder.append(
				" WHERE product_id = ? AND ((? BETWEEN min_age AND max_age)  OR (min_age <= ? AND max_age = - 1))");
		return this.jdbctemplate.query(sqlBuilder.toString(), mapper, new Object[] { productId, age, age });

	}

	@Override
	public Collection<LoanProductSubtypeMappingData> retrieveLoanProductSubtypeMappingBySubtypeStatusId(Long productId,
			Long loanSubtypeStatusId) {
		LoanProductSubtypeMappingDataMapper mapper = new LoanProductSubtypeMappingDataMapper();
		final StringBuilder sqlBuilder = new StringBuilder(200);
		sqlBuilder.append("select SQL_CALC_FOUND_ROWS ");
		sqlBuilder.append(this.schema());
		sqlBuilder.append(" WHERE  product_id = ? AND  loan_subtype_status_id=?");
		return this.jdbctemplate.query(sqlBuilder.toString(), mapper, new Object[] { productId, loanSubtypeStatusId });

	}

	@Override
	public Collection<LoanProductSubtypeMappingData> retrieveAllLoanProductSubtypeMappings() {
		// TODO Auto-generated method stub
		LoanProductSubtypeMappingDataMapper mapper = new LoanProductSubtypeMappingDataMapper();
		final StringBuilder sqlBuilder = new StringBuilder(200);
		sqlBuilder.append("select SQL_CALC_FOUND_ROWS ");
		sqlBuilder.append(this.schema());
		return this.jdbctemplate.query(sqlBuilder.toString(), mapper);
	}

	/// JdbcTemplate RowMapper
	private static final class LoanProductSubtypeMappingDataMapper implements RowMapper<LoanProductSubtypeMappingData> {
		@Override
		public LoanProductSubtypeMappingData mapRow(ResultSet rs, int rowNum) throws SQLException {

			Long id = rs.getLong("id");
			Long productId = rs.getLong("product_id");
			int loanSubtypeStatusId = rs.getInt("loan_subtype_status_id");
			Integer minAge = rs.getInt("min_age");
			Integer maxAge = rs.getInt("max_age");
			Long portfolioAccId = rs.getLong("portfolio_acc_id");
			Long intReceivableAccId = rs.getLong("int_receivable_acc_id");
			Long incomeAccId = rs.getLong("income_acc_id");

			// logger.debug("public LoanArrearClassifyData mapRow(ResultSet rs, int rowNum)
			// throws SQLException {");
			return new LoanProductSubtypeMappingData(id, productId, loanSubtypeStatusId, minAge, maxAge, portfolioAccId,
					intReceivableAccId, incomeAccId);
		}
	}

	private static final class LoanArrearClassifyDataMapper implements RowMapper<LoanArrearClassifyData> {
		@Override
		public LoanArrearClassifyData mapRow(ResultSet rs, int rowNum) throws SQLException {

			Long officeId = rs.getLong("office_id");
			String clientAccountNo = rs.getString("client_account_no");
			Long accountNumber = rs.getLong("account_number");

			Long productId = rs.getLong("product_id");
			Long loanSubtypeStatusId = rs.getLong("loan_subtype_status_id");
			double loanOutstanding = rs.getDouble("loan_outstanding");
			Date overdueSinceDateDerived = rs.getDate("overdue_since_date_derived");
			String currencyCode = rs.getString("currency_code");
			Integer daysInArrears = JdbcSupport.getInteger(rs, "days_in_arrea");
			// Integer daysInArrears = JdbcSupport.getInteger(rs, "days_in_arrear
			// days_in_arrear");
			// return null;
			return new LoanArrearClassifyData(officeId, clientAccountNo, accountNumber, productId, loanSubtypeStatusId,
					loanOutstanding, currencyCode, overdueSinceDateDerived, daysInArrears);
		}
	}

	@Override
	public Collection<LoanArrearClassifyData> retrieveLoanArrearsClassifyData(org.joda.time.LocalDate tilldate) {

		LoanArrearClassifyDataMapper mapper = new LoanArrearClassifyDataMapper();

		final StringBuilder sqlBuilder = new StringBuilder(600);

		sqlBuilder.append(" select ");
		sqlBuilder.append(
				" v.office_id, v.currency_code,v.client_account_no,v.account_number, v.product_id, v.loan_subtype_status_id , v.loan_outstanding, v.overdue_since_date_derived ");
		sqlBuilder.append(" , datediff(date(:tilldate) , date(v.overdue_since_date_derived)) AS days_in_arrea ");
		sqlBuilder.append(" from v_loan_aging_detail v");
		sqlBuilder.append(" where v.overdue_since_date_derived < :tilldate ");
		SqlParameterSource paramSource = new MapSqlParameterSource().addValue("tilldate", tilldate.toString());
		return this.namedJdbctemplate.query(sqlBuilder.toString(), paramSource, mapper);
	}
	
	@Override
	public Collection<LoanArrearClassifyData> retrieveLoanArrearsClassifyDataByDateAndLoanId(Date tilldate,Long loanId) {

		LoanArrearClassifyDataMapper mapper = new LoanArrearClassifyDataMapper();
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(tilldate);

		final StringBuilder sqlBuilder = new StringBuilder(600);

		sqlBuilder.append(" select ");
		sqlBuilder.append(
				" v.office_id, v.currency_code,v.client_account_no,v.account_number, v.product_id, v.loan_subtype_status_id , v.loan_outstanding, v.overdue_since_date_derived ");
		sqlBuilder.append(" , datediff(date('"+date+"') , date(v.overdue_since_date_derived)) AS days_in_arrea ");
		sqlBuilder.append(" from v_loan_aging_detail v");
		sqlBuilder.append(" where v.account_number =:loanId and v.overdue_since_date_derived < '"+date+"' ");
		
		
	
		System.out.println(date);
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

//		namedParameters.addValue("tilldate", tilldate.toString());
		namedParameters.addValue("loanId", loanId);
		
		return this.namedJdbctemplate.query(sqlBuilder.toString(), namedParameters, mapper);
	}

	@Override
	public Collection<GLAccountAmountData> retrieveLoanGLAccountAmountData(Long officeId,
			org.joda.time.LocalDate tilldate, Long loanId, Long glAccountId) {

		GLAccountAmountDataMapper mapper = new GLAccountAmountDataMapper();

		final StringBuilder sqlBuilder = new StringBuilder(600);

		sqlBuilder.append(" SELECT  " + "    loan_id AS loanId, " + "    glAccountId, " + "    glAccountName, "
				+ "    max(transactionDate) AS transactionDate, "
				+ "    SUM(IFNULL(debitAmount, 0) - IFNULL(creditAmount, 0)) AS balance " + " FROM " + "    (SELECT  "
				+ "        glAccount.classification_enum AS classification, "
				+ "            journalEntry.transaction_id, " + "            journalEntry.loan_transaction_id, "
				+ "            loanTransaction.id AS loanTransactionId, "
				+ "            loanTransaction.loan_id AS loanTransactionLoanId, " + "            (SELECT  "
				+ "                    s.loan_id " + "                FROM "
				+ "                    m_loan_transaction s " + "                WHERE "
				+ "                    s.id = journalEntry.loan_transaction_id "
				+ "                LIMIT 1) AS loan_id, " + "            glAccount.name AS glAccountName, "
				+ "            glAccount.gl_code AS glAccountCode, " + "            glAccount.id AS glAccountId, "
				+ "            journalEntry.office_id AS officeId, " + "            office.name AS officeName, "
				+ "            journalEntry.ref_num AS referenceNumber, "
				+ "            journalEntry.manual_entry AS manualEntry, "
				+ "            journalEntry.entry_date AS transactionDate, "
				+ "            journalEntry.type_enum AS entryType, " + "            journalEntry.amount AS amount, "
				+ "            IF(journalEntry.type_enum = 1, journalEntry.amount, 0) AS creditAmount, "
				+ "            IF(journalEntry.type_enum = 2, journalEntry.amount, 0) AS debitAmount, "
				+ "            journalEntry.transaction_id AS transactionId, "
				+ "            journalEntry.entity_type_enum AS entityType, "
				+ "            journalEntry.entity_id AS entityId, "
				+ "            creatingUser.id AS createdByUserId, "
				+ "            creatingUser.username AS createdByUserName, "
				+ "            journalEntry.reversed AS reversed, "
				+ "            journalEntry.currency_code AS currencyCode " + "    FROM  "
				+ "        acc_gl_journal_entry journalEntry  "
				+ "    LEFT JOIN acc_gl_account glAccount ON glAccount.id = journalEntry.account_id  "
				+ "    LEFT JOIN m_office office ON office.id = journalEntry.office_id  "
				+ "    LEFT JOIN m_appuser creatingUser ON creatingUser.id = journalEntry.createdby_id  "
				+ "    JOIN m_currency curr ON curr.code = journalEntry.currency_code  "
				+ "    JOIN m_loan_transaction loanTransaction ON loanTransaction.id = journalEntry.loan_transaction_id "
				+ "    WHERE " + "        journalEntry.office_id = :officeId  "
				+ "            AND loanTransaction.loan_id = :loanId  "
				+ "            AND journalEntry.entry_date <= :tilldate  "
				+ "            AND journalEntry.account_id = :accountId  "
				+ "    ORDER BY journalEntry.entry_date , journalentry.id) AS Table1  "
				+ "GROUP BY loan_id , glAccountId , glAccountName; ");

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("officeId", officeId);
		namedParameters.addValue("loanId", loanId);
		namedParameters.addValue("tilldate", tilldate);
		namedParameters.addValue("accountId", glAccountId);
		// namedParameterJdbcTemplate.update(SQL, namedParameters);

		// SqlParameterSource paramSource = new
		// MapSqlParameterSource().addValue("tilldate", tilldate);
		return this.namedJdbctemplate.query(sqlBuilder.toString(), namedParameters, mapper);
	}

	private static final class GLAccountAmountDataMapper implements RowMapper<GLAccountAmountData> {
		@Override
		public GLAccountAmountData mapRow(ResultSet rs, int rowNum) throws SQLException {

			Long loanId = rs.getLong("loanId");
			Long glAccountId = rs.getLong("glAccountId");
			String glAccountName = rs.getString("glAccountName");
			Date transactionDate = rs.getDate("transactionDate");
			double balance = rs.getDouble("balance");
			return new GLAccountAmountData(loanId, glAccountId, glAccountName, transactionDate, balance);
		}
	}

	@Override
	public LoanProductSubtypeMappingData retrieveProductSubtypeMappingDataByProductId(Long loanId) {
		String sql = "SELECT ID, PRODUCT_ID, LOAN_SUBTYPE_STATUS_ID, MIN_AGE, MAX_AGE, PORTFOLIO_ACC_ID, INT_RECEIVABLE_ACC_ID, INCOME_ACC_ID  FROM "
				+ " loan_product_subtype_mapping WHERE product_id = (SELECT PRODUCT_ID FROM M_LOAN WHERE ID = ?) "
				+ "    AND ( SELECT to_days(curdate()) - to_days(overdue_since_date_derived) FROM m_loan_arrears_aging WHERE  loan_id = ? "
				+ "    ) BETWEEN min_age AND max_age "
				+ "    OR ( ( SELECT to_days(curdate()) - to_days(overdue_since_date_derived) FROM m_loan_arrears_aging WHERE loan_id = ? ) >= min_age AND max_age = - 1 )";
		LoanProductSubtypeMappingDataMapper mapper = new LoanProductSubtypeMappingDataMapper();

		return this.jdbctemplate.queryForObject(sql, mapper, new Object[] { loanId, loanId, loanId });
	}
}
