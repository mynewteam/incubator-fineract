package org.apache.fineract.portfolio.inward.service;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.portfolio.inward.data.InwardData;
import org.apache.fineract.portfolio.inward.data.NostroAccountData;
import org.apache.fineract.portfolio.inward.data.NostroBalanceAndCurrencyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class InwardReadPlatformServiceImpl  implements InwardReadPlatformService{

	private final JdbcTemplate  jdbcTemplate;
	
	@Autowired
	public InwardReadPlatformServiceImpl(final RoutingDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);;
	}

	@Override
	public List<InwardData> retrieveOneInward(String chequeNo) {
		// TODO Auto-generated method stub
		String sql = String.format("SELECT * FROM INWARD_CLEARANCE WHERE CHEQUE_NO = '%s'", chequeNo);
		
		return jdbcTemplate.query(sql, new InwardDataMapper());
	}


	
	public List<InwardData> retrieveAllInward() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM INWARD_CLEARANCE";
		return jdbcTemplate.query(sql, new InwardDataMapper());
	}
	
	public List<NostroAccountData> retrieveNostro(){
		String sql = "SELECT ID, NAME FROM ACC_GL_ACCOUNT WHERE NAME LIKE '%(Nostro)%'";
		//String sql = "SELECT ACC_GL_ACCOUNT.ID, ACC_GL_ACCOUNT.NAME, ACC_GL_JOURNAL_ENTRY.AMOUNT, ACC_GL_JOURNAL_ENTRY.CURRENCY_CODE FROM ACC_GL_ACCOUNT INNER JOIN ACC_GL_JOURNAL_ENTRY ON ACC_GL_ACCOUNT.ID = ACC_GL_JOURNAL_ENTRY.ACCOUNT_ID";
		return jdbcTemplate.query(sql, new NostroAccountDataMapper());
		
		
	}
	
	public List<NostroBalanceAndCurrencyData> retrieveOneNostro(Long nostroId) {
		String sql = "SELECT AMOUNT, CURRENCY_CODE FROM (SELECT AMOUNT, CURRENCY_CODE, ENTRY_DATE FROM ACC_GL_JOURNAL_ENTRY WHERE ACCOUNT_ID = " + nostroId + " ORDER BY ENTRY_DATE DESC) WHERE ROWNUM = 1";
		return jdbcTemplate.query(sql, new NostroAccountBalanceAndCurrencyDataMapper());
	}
	
	public static class InwardDataMapper implements RowMapper<InwardData>{
		
		@Override
		public InwardData mapRow(ResultSet rs, int rowNum) throws SQLException{
			Long id = rs.getLong("ID");
			String nostroAccount = rs.getString("NOSTRO_ACCOUNT");
			Long amount = rs.getLong("AMOUNT");
			Long nostroBalance = rs.getLong("NOSTRO_BALANCE");
			String currency = rs.getString("CURRENCY");
			String fromAccountNo = rs.getString("FROM_ACCOUNT_NO");
			String chequeNo = rs.getString("CHEQUE_NO");
			Long availableBalance = rs.getLong("AVAILABLE_BALANCE");
			String name = rs.getString("NAME");
			
			return new InwardData(id, nostroAccount, amount, nostroBalance,
					currency, fromAccountNo, chequeNo,
					availableBalance, name);
		}
	
}
	
	public static class NostroAccountDataMapper implements RowMapper<NostroAccountData>{
		public NostroAccountData mapRow(ResultSet rs, int rowNum) throws SQLException{
			
			Long id = rs.getLong("ID");
			String nostroAccount = rs.getString("NAME");
			
			return new NostroAccountData(id, nostroAccount);
		}
	}

	public static class NostroAccountBalanceAndCurrencyDataMapper implements RowMapper<NostroBalanceAndCurrencyData>{
		@Override
		public NostroBalanceAndCurrencyData mapRow(ResultSet rs, int rowNum) throws SQLException{
			
			Double nostroBalance = rs.getDouble("AMOUNT");
			String currency = rs.getString("CURRENCY_CODE");
			
			return new NostroBalanceAndCurrencyData(nostroBalance, currency);
		}
	}
	



	


}
