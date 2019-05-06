package org.apache.fineract.accounting.classification.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.fineract.accounting.classification.data.LoanArriaClassifyData;
import org.apache.fineract.accounting.classification.data.LoanLastValueAccForMoveData;
import org.apache.fineract.accounting.classification.data.LoanSubTypeData;
import org.apache.fineract.accounting.classification.data.ProductClassifyMappingData;
import org.apache.fineract.accounting.classification.data.ProductSubTypeMappingData;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductClassifyReadPlatformServiceImpl implements ProductClassifyReadPlatformService {

	private final JdbcTemplate jdbctemplate;
	private final static Logger logger = LoggerFactory.getLogger(ProductClassifyReadPlatformServiceImpl.class);

	@Autowired
	public ProductClassifyReadPlatformServiceImpl(final RoutingDataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<ProductClassifyMappingData> retrieveProductClassifyList(Long ProductId, Integer Agging) {
		ProductClassifyDataMapper mapper = new ProductClassifyDataMapper();
		final String sql = "SELECT id, description, min_aging,max_aging, acc_dr_id, acc_cr_id, acc_product_mapping_id, type,class_acc FROM acc_product_classify_mapping  WHERE  acc_product_mapping_id = ? AND (? between min_aging and max_aging)";
		// logger.debug(sql + ":ProductId:"+ProductId+", Agging:"+Agging);
		return this.jdbctemplate.query(sql, mapper, new Object[] { ProductId, Agging });
	}

	@Override
	public List<ProductSubTypeMappingData> retrieveProductSubTypeMappingList(Long ProductId, Integer Age) {
		ProductSubTypeMappingDataMapper mapper = new ProductSubTypeMappingDataMapper();
		final String sql = "select st.* from loan_product_subtype_mapping st where ((? between min_age and max_age ) or (? >= min_age  and max_age=-1)) and product_id=?";
		logger.debug(sql + ":ProductId:" + ProductId + ", Age:" + Age);
		return this.jdbctemplate.query(sql, mapper, new Object[] { Age, Age, Age, ProductId });
	}

	@Override
	public List<ProductSubTypeMappingData> retrieveProductSubTypeMappingListForChangeType(Long loan_subtype_status_id,
			Long product_id) {
		ProductSubTypeMappingDataMapper mapper = new ProductSubTypeMappingDataMapper();
		final String sql = "select st.* from loan_product_subtype_mapping st where st.loan_subtype_status_id not in(?) and product_id=?";
		logger.debug(sql + ":loan_subtype_status_id:" + loan_subtype_status_id + ", product_id:" + product_id);
		return this.jdbctemplate.query(sql, mapper, new Object[] { loan_subtype_status_id, product_id });
	}

	//
	@SuppressWarnings("deprecation")
	@Override
	public List<LoanSubTypeData> retrieveSubTypeByLoanIdAndDate(Long LoanId, Date TranDate) {
		LoanSubTypeDataMapping mapper = new LoanSubTypeDataMapping();
		final String sql = "select ml.id as loanid, ml.loan_subtype_status_id, ag.*,(TO_DAYS(?) - TO_DAYS(ag.overdue_since_date_derived)) AS days_in_arrears from m_loan ml left join m_loan_arrears_aging ag on ml.id=ag.loan_id where ml.id =? and TO_DAYS(ag.overdue_since_date_derived)<TO_DAYS(?)";
		logger.debug(sql + ":LoanId:" + LoanId + "TranDate:" + TranDate);
		return this.jdbctemplate.query(sql, mapper, new Object[] { TranDate, LoanId, TranDate });
	}

	// retrieveAccForCurrentAccStatusDataByLoanIdAndAccIdLoanSubTypeData
	private static final class LoanSubTypeDataMapping implements RowMapper<LoanSubTypeData> {
		@Override
		public LoanSubTypeData mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long loanId = rs.getLong("loanId");
			int loan_subtype_status_id = rs.getInt("loan_subtype_status_id");
			Date overdue_since_date_derived = rs.getDate("overdue_since_date_derived");
			Integer days_in_arrears = rs.getInt("days_in_arrears");
			Long product_id = rs.getLong("product_id");

			return new LoanSubTypeData(loanId, loan_subtype_status_id, overdue_since_date_derived, days_in_arrears,
					product_id);
		}
	}

	@Override
	public List<LoanArriaClassifyData> retrieveLoanArriaClassifyDataByLoanId(Long LoanId) {
		LoanArriaClassifyDataMapper mapper = new LoanArriaClassifyDataMapper();
		final String sql = "select client_account_no, product_id,  account_number, loan_outstanding, overdue_since_date_derived, days_in_arrears from v_loan_aging_detail where account_number = ?";
		return this.jdbctemplate.query(sql, mapper, new Object[] { LoanId });
	}

	@Override
	public List<LoanArriaClassifyData> retrieveLoanArriaDataByLoanIdAndDate(Long LoanId, Date TranDate) {

		LoanArriaClassifyDataMapper mapper = new LoanArriaClassifyDataMapper();
		final String sql = "select client_account_no, product_id,  account_number, loan_outstanding, overdue_since_date_derived, days_in_arrears from v_loan_aging_detail1 where account_number = ?";
		logger.debug(sql + ":LoanId:" + LoanId + "TranDate:" + TranDate);
		return this.jdbctemplate.query(sql, mapper, new Object[] { LoanId, TranDate });
	}

	@Override
	public List<LoanArriaClassifyData> retrieveLoanArriaClassifyDataByLoanId(Long LoanId, Date AccrDate) {

		LoanArriaClassifyDataMapper mapper = new LoanArriaClassifyDataMapper();
		final String sql = "select client_account_no, product_id,  account_number, loan_outstanding, overdue_since_date_derived,(TO_DAYS(?) - TO_DAYS(overdue_since_date_derived)) AS days_in_arrear from v_loan_aging_detail where account_number = ? and  overdue_since_date_derived < ?";
		logger.debug(sql + ":LoanId:" + LoanId + ", AccrDate:" + AccrDate + ",  AccrDate:" + AccrDate + "");
		return this.jdbctemplate.query(sql, mapper, new Object[] { AccrDate, LoanId, AccrDate });

	}

	@Override
	public List<LoanArriaClassifyData> retrieveLoanArriaByDate(Date AccrDate) {
		LoanArriaClassifyDataMapper mapper = new LoanArriaClassifyDataMapper();
		final String sql = "select client_account_no, product_id,  account_number, loan_outstanding, overdue_since_date_derived, (TO_DAYS(?) - TO_DAYS(overdue_since_date_derived)) AS days_in_arrear from v_loan_aging_detail where overdue_since_date_derived < ?";
		logger.debug(sql + ":AccrDate:" + AccrDate);
		return this.jdbctemplate.query(sql, mapper, new Object[] { AccrDate, AccrDate });

	}

	@Override
	public List<LoanLastValueAccForMoveData> retrieveLoanLastValueAccForMoveDataByLoanIdAndAccId(Long LoanId,
			Long AccId) {
		LoanLastValueAccForMoveDataMapper mapper = new LoanLastValueAccForMoveDataMapper();
		final String sql = "SELECT loan_id, account_id,  SUM(amount) AS last_running_balance  FROM v_m_loan_transation_acc_gl_journal_entry where loan_id=? and account_id=? group by loan_id, account_id";
		// logger.trace("trace:" + sql + ":LoanId:"+LoanId+", AccId:"+AccId);
		return this.jdbctemplate.query(sql, mapper, new Object[] { LoanId, AccId });
	}

	@SuppressWarnings("deprecation")
	@Override
	public int isFirstAccraul(Long LoanId) {
		String sql = "select max(salary) from employee";
		// this.jdbctemplate.queryForInt(query);
		return this.jdbctemplate.queryForInt(sql);
	}

	@Override
	public List<LoanLastValueAccForMoveData> retrieveLoanLastValueAccForMoveDataByLoanIdForFirstAcc(Long LoanId,
			Long ProductId, Long AccId) {
		LoanLastValueAccForMoveDataMapper mapper = new LoanLastValueAccForMoveDataMapper();
		final String sql = "SELECT loan_id, account_id,  SUM(amount) AS last_running_balance  FROM v_m_loan_transation_acc_gl_journal_entry where loan_id=? and account_id in (select ap.acc_cr_id from acc_product_classify_mapping ap where  ap.acc_product_mapping_id=? and ap.type=2 and ap.acc_cr_id !=? order by id desc) group by loan_id, account_id ";
		logger.debug("trace:"
				+ "SELECT loan_id, account_id,  SUM(amount) AS last_running_balance  FROM v_m_loan_transation_acc_gl_journal_entry where loan_id="
				+ LoanId
				+ " and account_id in (select ap.acc_cr_id from acc_product_classify_mapping ap where  ap.acc_product_mapping_id="
				+ ProductId + " and ap.type=2 and ap.acc_cr_id !=" + AccId
				+ " order by id desc) group by loan_id, account_id ");
		return this.jdbctemplate.query(sql, mapper, new Object[] { LoanId, ProductId, AccId });
	}

	// retrieveAccForCurrentAccStatusDataByLoanIdAndAccId
	private static final class ProductClassifyDataMapper implements RowMapper<ProductClassifyMappingData> {
		@Override
		public ProductClassifyMappingData mapRow(ResultSet rs, int rowNum) throws SQLException {
			String description = rs.getString("description");
			Integer min_aging = rs.getInt("min_aging");
			Integer max_aging = rs.getInt("max_aging");
			Integer accDrId = rs.getInt("acc_dr_id");
			Integer accCrId = rs.getInt("acc_cr_id");
			Integer type = rs.getInt("type");
			Integer classAcc = rs.getInt("class_acc");
			Long productMappingId = rs.getLong("acc_product_mapping_id");
			return new ProductClassifyMappingData(description, min_aging, max_aging, accDrId, accCrId, type, classAcc,
					productMappingId);
		}
	}

	private static final class ProductSubTypeMappingDataMapper implements RowMapper<ProductSubTypeMappingData> {
		@Override
		public ProductSubTypeMappingData mapRow(ResultSet rs, int rowNum) throws SQLException {
			Integer id = rs.getInt("id");
			;
			Integer product_id = rs.getInt("product_id");
			Integer loan_subtype_status_id = rs.getInt("loan_subtype_status_id");
			Integer min_age = rs.getInt("min_age");
			Integer max_age = rs.getInt("max_age");
			Long portfolio_acc_id = rs.getLong("portfolio_acc_id");
			Long int_receivable_acc_id = rs.getLong("int_receivable_acc_id");
			Long income_acc_id = rs.getLong("income_acc_id");
			return new ProductSubTypeMappingData(id, product_id, loan_subtype_status_id, min_age, max_age,
					portfolio_acc_id, int_receivable_acc_id, income_acc_id);
		}
	}

	private static final class LoanArriaClassifyDataMapper implements RowMapper<LoanArriaClassifyData> {

		@Override
		public LoanArriaClassifyData mapRow(ResultSet rs, int rowNum) throws SQLException {

			String client_account_no = rs.getString("client_account_no");
			Integer account_number = rs.getInt("account_number");
			double loan_outstanding = rs.getDouble("loan_outstanding");
			Date overdue_since_date_derived = rs.getDate("overdue_since_date_derived");
			Integer days_in_arrears = rs.getInt("days_in_arrear");
			Long product_id = rs.getLong("product_id");
			// logger.debug("public LoanArriaClassifyData mapRow(ResultSet rs, int rowNum)
			// throws SQLException {");
			return new LoanArriaClassifyData(client_account_no, account_number, loan_outstanding,
					overdue_since_date_derived, days_in_arrears, product_id);
		}
	}

	private static final class LoanLastValueAccForMoveDataMapper implements RowMapper<LoanLastValueAccForMoveData> {

		@Override
		public LoanLastValueAccForMoveData mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long loan_id = rs.getLong("loan_id");
			Long account_id = rs.getLong("account_id");
			double last_running_balance = rs.getDouble("last_running_balance");
			return new LoanLastValueAccForMoveData(loan_id, account_id, last_running_balance);
		}
	}

	public int updateLoanChangeSubType(Long LoanId, Date AccrTillDate) {

		return 0;
	}

	@Override
	public int retrieveProductSubTypeByLoanId(Long LoanId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLoanSubTypeStatus(Long LoanId) {
		// check loan area aging
		int arreaAging = jdbctemplate.queryForObject(
				"select ( to_days(curdate()) - to_days(overdue_since_date_derived) )  from m_loan_arrears_aging where loan_id = ?",
				Integer.class, new Object[] { LoanId });

		int loanSubTypeStatusId = 1;
		// if loan Area aging from 31 => 60 sub standard
		// else if loan Area aging from 61 => 90 Doubtful
		// else if loan Area aging from
		if (arreaAging >= 31 && arreaAging <= 60) {
			loanSubTypeStatusId = 2;
		} else if (arreaAging >= 61 && arreaAging <= 90) {
			loanSubTypeStatusId = 3;
		} else if (arreaAging >= 91) {
			loanSubTypeStatusId = 4;
		}
		return loanSubTypeStatusId;
	}

	@Override
	public ProductSubTypeMappingData retrieveProductSubtypeMappingDataByProductId(Long productId,
			int loanSubTypeStatus) {
		String sql = "SELECT id, product_id, loan_subtype_status_id,  min_age, max_age, portfolio_acc_id, int_receivable_acc_id income_acc_id FROM loan_product_subtype_mapping WHERE product_id = ? and loan_subtype_status_id = ?";
		ProductSubTypeMappingDataMapper mapper = new ProductSubTypeMappingDataMapper();
		return this.jdbctemplate.queryForObject(sql, mapper, new Object[] { productId, loanSubTypeStatus });
	}

}
