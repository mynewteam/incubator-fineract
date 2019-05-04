package org.apache.fineract.accounting.classify.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import org.apache.fineract.accounting.classify.data.LoanArrearClassifyData;
import org.apache.fineract.accounting.classify.data.LoanProductSubtypeMappingData;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class LoanSubtypeMappingReadPlatformServiceImplement implements LoanSubtypeMappingReadPlatformService {
	private final JdbcTemplate jdbctemplate;
//	private final static Logger logger = LoggerFactory.getLogger(ProductClassifyReadPlatformServiceImpl.class);
	private final String schema;
	
	@Autowired
	public LoanSubtypeMappingReadPlatformServiceImplement(final RoutingDataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
		
		final StringBuilder builder = new StringBuilder(400);
		
		builder.append(" id, ");
		builder.append("product_id, ");
		builder.append("loan_subtype_status_id, ");
		builder.append("min_age, ");
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

		return  this.jdbctemplate.queryForObject(sqlBuilder.toString(), mapper, new Object[] { id });
	}

	@Override
	public Collection<LoanProductSubtypeMappingData> retrieveLoanProductSubtypeMappings(Long productId, Integer age) {

			LoanProductSubtypeMappingDataMapper mapper = new LoanProductSubtypeMappingDataMapper();
			final StringBuilder sqlBuilder = new StringBuilder(200);
			sqlBuilder.append("select SQL_CALC_FOUND_ROWS ");
			sqlBuilder.append(this.schema());
			sqlBuilder.append(" WHERE  product_id = ? AND (? BETWEEN min_age AND max_age)  OR ((min_age <= ? and max_age=-1))");
			return this.jdbctemplate.query(sqlBuilder.toString(), mapper, new Object[] { productId, age , age });
			
	}
	
	@Override
	public Collection<LoanProductSubtypeMappingData> retrieveLoanProductSubtypeMappingBySubtypeStatusId(Long productId, Long loanSubtypeStatusId) {

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
			Long loanSubtypeStatusId = rs.getLong("loan_subtype_status_id");
			Integer minAge = rs.getInt("min_age");
			Integer maxAge = rs.getInt("max_age");
			Long portfolioAccId = rs.getLong("portfolio_acc_id");
			Long intReceivableAccId = rs.getLong("int_receivable_acc_id");
			Long incomeAccId = rs.getLong("income_acc_id");
			
//			logger.debug("public LoanArrearClassifyData mapRow(ResultSet rs, int rowNum) throws SQLException {");
			return new LoanProductSubtypeMappingData(id, productId, loanSubtypeStatusId, minAge, maxAge, portfolioAccId, intReceivableAccId, incomeAccId);
		}
	}
	
	
	
	private static final class LoanArrearClassifyDataMapper implements RowMapper<LoanArrearClassifyData> {
		@Override
		public LoanArrearClassifyData mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			 String clientAccountNo= rs.getString("client_account_no");
			 Long accountNumber =rs.getLong("account_number");
			 Long productId = rs.getLong("product_id");
			 Long loanSubtypeStatusId = rs.getLong("loan_subtype_status_id");
			 double loanOutstanding = rs.getDouble("loan_outstanding");
			 Date overdueSinceDateDerived = rs.getDate("overdue_since_date_derived") ;
			 String daysInArrears = rs.getString("days_in_arrears");
//			 Integer daysInArrears = JdbcSupport.getInteger(rs, "days_in_arrear days_in_arrear");
			
			 return new LoanArrearClassifyData(clientAccountNo, accountNumber, productId, loanSubtypeStatusId, loanOutstanding, overdueSinceDateDerived, Integer.parseInt(daysInArrears));
		}
	}

	@Override
	public Collection<LoanArrearClassifyData> retrieveLoanArrearsClassifyData(org.joda.time.LocalDate tilldate) {
		
		LoanArrearClassifyDataMapper mapper = new LoanArrearClassifyDataMapper();
		
		final StringBuilder sqlBuilder = new StringBuilder(400);
		
		sqlBuilder.append(" select SQL_CALC_FOUND_ROWS ");
		sqlBuilder.append(" v.client_account_no,v.account_number, v.product_id, v.loan_subtype_status_id , v.loan_outstanding, v.overdue_since_date_derived ");
		sqlBuilder.append(" , ( TO_DAYS(?) - TO_DAYS( v.overdue_since_date_derived )) AS days_in_arrears ");
		sqlBuilder.append(" from v_loan_aging_detail v");
		sqlBuilder.append(" where v.overdue_since_date_derived < ? ");
		
		return this.jdbctemplate.query(sqlBuilder.toString(), mapper, new Object[] { tilldate, tilldate });
	}

}
