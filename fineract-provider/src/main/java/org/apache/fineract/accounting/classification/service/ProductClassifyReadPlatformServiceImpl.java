package org.apache.fineract.accounting.classification.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.fineract.accounting.classification.data.LoanArriaClassifyData;
import org.apache.fineract.accounting.classification.data.LoanLastValueAccForMoveData;
import org.apache.fineract.accounting.classification.data.ProductClassifyMappingData;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import software.amazon.ion.Decimal;

@Service
public class ProductClassifyReadPlatformServiceImpl implements ProductClassifyReadPlatformService {

	private final JdbcTemplate jdbctemplate;
	
	@Autowired
	public ProductClassifyReadPlatformServiceImpl(final RoutingDataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<ProductClassifyMappingData> retrieveProductClassifyList(Long ProductId, Integer Agging) {
		ProductClassifyDataMapper mapper = new ProductClassifyDataMapper();
		final String sql = "SELECT id, description, min_aging,max_aging, acc_dr_id, acc_cr_id, acc_product_mapping_id, type FROM acc_product_classify_mapping  WHERE acc_product_mapping_id = ? AND (? between min_aging and max_aging)";
		return this.jdbctemplate.query(sql, mapper, new Object[] {ProductId, Agging} );
	}
	
	@Override
	public List<LoanArriaClassifyData> retrieveLoanArriaClassifyDataByLoanId(Long LoanId) {
		LoanArriaClassifyDataMapper mapper = new LoanArriaClassifyDataMapper();
		final String sql = "select client_account_no, account_number, loan_outstanding, overdue_since_date_derived, days_in_arrears from v_loan_aging_detail where account_number = ?";
		return this.jdbctemplate.query(sql, mapper, new Object[] {LoanId} );
	}
	@Override
	public List<LoanLastValueAccForMoveData> retrieveLoanLastValueAccForMoveDataByLoanIdAndAccId(Long LoanId,Long AccId) {
		LoanLastValueAccForMoveDataMapper mapper = new LoanLastValueAccForMoveDataMapper();
		final String sql = "SELECT loan_id, account_id,  SUM(office_running_balance) AS last_running_balance  FROM v_m_loan_transation_acc_gl_journal_entry where loan_id=? and account_id=? group by   loan_id, account_id";
		return this.jdbctemplate.query(sql, mapper, new Object[] {LoanId, AccId} );
	}
	
	
	private static final class ProductClassifyDataMapper implements RowMapper<ProductClassifyMappingData>{

		@Override
		public ProductClassifyMappingData mapRow(ResultSet rs, int rowNum) throws SQLException {
			String description =rs.getString("description");
			Integer min_aging = rs.getInt("min_aging");
			Integer max_aging = rs.getInt("max_aging");
			Integer accDrId = rs.getInt("acc_dr_id");
			Integer accCrId = rs.getInt("acc_cr_id");
			Integer type = rs.getInt("type");
			Long productMappingId = rs.getLong("acc_product_mapping_id");

			return new ProductClassifyMappingData(description,min_aging,max_aging , accDrId, accCrId, type, productMappingId);
		}
	}
	
	private static final class LoanArriaClassifyDataMapper implements RowMapper<LoanArriaClassifyData>{

		@Override
		public LoanArriaClassifyData mapRow(ResultSet rs, int rowNum) throws SQLException {
			
//			String description =rs.getString("description");
//			Integer agging = rs.getInt("agging");
//			Integer accDrId = rs.getInt("acc_dr_id");
//			Integer accCrId = rs.getInt("acc_cr_id");
//			Integer type = rs.getInt("type");
//			Long productMappingId = rs.getLong("acc_product_mapping_id");
			
			String client_account_no=rs.getString("client_account_no");
			Integer account_number=rs.getInt("account_number");
			double loan_outstanding = rs.getDouble("loan_outstanding");
			Date overdue_since_date_derived = rs.getDate("overdue_since_date_derived");
			Integer days_in_arrears = rs.getInt("days_in_arrears");
			
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
