package org.apache.fineract.accounting.classify.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.fineract.accounting.classify.data.LoanArriaClassifyData;
import org.apache.fineract.accounting.classify.data.LoanLastValueAccForMoveData;
import org.apache.fineract.accounting.classify.data.ProductClassifyMappingData;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class LoanSubTypeReadPlatformServiceImpl implements ProductClassifyReadPlatformService {

	private final JdbcTemplate jdbctemplate;
	private final static Logger logger = LoggerFactory.getLogger(LoanSubTypeReadPlatformServiceImpl.class);
	
	@Autowired
	public LoanSubTypeReadPlatformServiceImpl(final RoutingDataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<ProductClassifyMappingData> retrieveProductClassifyList(Long ProductId, Integer Agging) {
		ProductClassifyDataMapper mapper = new ProductClassifyDataMapper();
		final String sql = "SELECT id, description, min_aging,max_aging, acc_dr_id, acc_cr_id, acc_product_mapping_id, type,class_acc FROM acc_product_classify_mapping  WHERE  acc_product_mapping_id = ? AND (? between min_aging and max_aging)";
		logger.debug(sql + ":ProductId:"+ProductId+", Agging:"+Agging);
		return this.jdbctemplate.query(sql, mapper, new Object[] {ProductId, Agging} );
	}
	
	@Override
	public List<LoanArriaClassifyData> retrieveLoanArriaClassifyDataByLoanId(Long LoanId) {
		LoanArriaClassifyDataMapper mapper = new LoanArriaClassifyDataMapper();
		final String sql = "select client_account_no, account_number, loan_outstanding, overdue_since_date_derived, days_in_arrears from v_loan_aging_detail where account_number = ?";
		return this.jdbctemplate.query(sql, mapper, new Object[] {LoanId} );
	}
	
	@Override
	public List<LoanArriaClassifyData> retrieveLoanArriaClassifyDataByLoanId(Long LoanId, Date AccrDate) {
		
		LoanArriaClassifyDataMapper mapper = new LoanArriaClassifyDataMapper();
		final String sql = "select client_account_no, account_number, loan_outstanding, overdue_since_date_derived, (TO_DAYS(?) - TO_DAYS(overdue_since_date_derived)) AS days_in_arrear from v_loan_aging_detail where account_number = ? and  overdue_since_date_derived < ?";
		logger.debug(sql + ":LoanId:"+LoanId+", AccrDate:"+AccrDate+",  AccrDate:"+AccrDate+"");
		return this.jdbctemplate.query(sql, mapper, new Object[] {AccrDate, LoanId, AccrDate} );
		
	}
	
	@Override
	public List<LoanLastValueAccForMoveData> retrieveLoanLastValueAccForMoveDataByLoanIdAndAccId(Long LoanId,Long AccId) {
		LoanLastValueAccForMoveDataMapper mapper = new LoanLastValueAccForMoveDataMapper();
		final String sql = "SELECT loan_id, account_id,  SUM(amount) AS last_running_balance  FROM v_m_loan_transation_acc_gl_journal_entry where loan_id=? and account_id=? group by loan_id, account_id";
		logger.trace("trace:" + sql + ":LoanId:"+LoanId+", AccId:"+AccId);
		return this.jdbctemplate.query(sql, mapper, new Object[] {LoanId, AccId} );
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public int isFirstAccraul(Long LoanId)  {
				String sql = "select max(salary) from employee"; 
//				this.jdbctemplate.queryForInt(query);
				return this.jdbctemplate.queryForInt(sql);
	}
	
	@Override
	public List<LoanLastValueAccForMoveData> retrieveLoanLastValueAccForMoveDataByLoanIdForFirstAcc(Long LoanId,Long ProductId,Long AccId)  {
		LoanLastValueAccForMoveDataMapper mapper = new LoanLastValueAccForMoveDataMapper();
		final String sql = "SELECT loan_id, account_id,  SUM(amount) AS last_running_balance  FROM v_m_loan_transation_acc_gl_journal_entry where loan_id=? and account_id in (select ap.acc_cr_id from acc_product_classify_mapping ap where  ap.acc_product_mapping_id=? and ap.type=2 and ap.acc_cr_id !=? order by id desc) group by loan_id, account_id ";
		logger.debug("trace:" + "SELECT loan_id, account_id,  SUM(amount) AS last_running_balance  FROM v_m_loan_transation_acc_gl_journal_entry where loan_id="+LoanId+" and account_id in (select ap.acc_cr_id from acc_product_classify_mapping ap where  ap.acc_product_mapping_id="+ProductId+" and ap.type=2 and ap.acc_cr_id !="+AccId+" order by id desc) group by loan_id, account_id ");
		return this.jdbctemplate.query(sql, mapper, new Object[] {LoanId,ProductId,AccId} );
	}
//	retrieveAccForCurrentAccStatusDataByLoanIdAndAccId
	
	private static final class ProductClassifyDataMapper implements RowMapper<ProductClassifyMappingData>{
		@Override
		public ProductClassifyMappingData mapRow(ResultSet rs, int rowNum) throws SQLException {
			String description =rs.getString("description");
			Integer min_aging = rs.getInt("min_aging");
			Integer max_aging = rs.getInt("max_aging");
			Integer accDrId = rs.getInt("acc_dr_id");
			Integer accCrId = rs.getInt("acc_cr_id");
			Integer type = rs.getInt("type");
			Integer classAcc = rs.getInt("class_acc");
			Long productMappingId = rs.getLong("acc_product_mapping_id");
			return new ProductClassifyMappingData(description,min_aging,max_aging , accDrId, accCrId, type,classAcc, productMappingId);
		}
	}
	
	private static final class LoanArriaClassifyDataMapper implements RowMapper<LoanArriaClassifyData>{

		@Override
		public LoanArriaClassifyData mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String client_account_no=rs.getString("client_account_no");
			Integer account_number=rs.getInt("account_number");
			double loan_outstanding = rs.getDouble("loan_outstanding");
			Date overdue_since_date_derived = rs.getDate("overdue_since_date_derived");
			Integer days_in_arrears = rs.getInt("days_in_arrear");
			logger.debug("public LoanArriaClassifyData mapRow(ResultSet rs, int rowNum) throws SQLException {");
			return new LoanArriaClassifyData(client_account_no, account_number, loan_outstanding, overdue_since_date_derived, days_in_arrears);
		}
	}
	
	private static final class LoanLastValueAccForMoveDataMapper implements RowMapper<LoanLastValueAccForMoveData>{

		@Override
		public LoanLastValueAccForMoveData mapRow(ResultSet rs, int rowNum) throws SQLException {
			 Long loan_id=rs.getLong("loan_id");
			 Long account_id=rs.getLong("account_id");
			 double last_running_balance=rs.getDouble("last_running_balance");
			return new LoanLastValueAccForMoveData(loan_id, account_id, last_running_balance);
		}
	}

}
