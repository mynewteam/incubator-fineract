package org.apache.fineract.portfolio.outward.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.codes.service.CodeValueReadPlatformService;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.portfolio.outward.api.OutwardApiConstants;
import org.apache.fineract.portfolio.outward.data.CheckType;
import org.apache.fineract.portfolio.outward.data.DebitorName;
import org.apache.fineract.portfolio.outward.data.OutwardData;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class OutwardReadPlatformServiceImpl implements OutwardReadPlatformService{

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public OutwardReadPlatformServiceImpl(final RoutingDataSource dataSource) {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	@Override
	public List<CheckType> retrieveTemplate() {
		
		String sql = "select * from cheque_type";
		return jdbcTemplate.query(sql, new CheckTypeDataMapper());
	}
	
	public List<OutwardData> retrieveAllOutward(){
		String sql = "select * from outward_clearance";
		return jdbcTemplate.query(sql, new OutwardDataMapper() );
	}
	
	public List<DebitorName> retrieveDebitorName(){
		String sql = "select * from debitor_name";
		return jdbcTemplate.query(sql, new DebitorNameDataMapper() );
	}

public static class CheckTypeDataMapper implements RowMapper<CheckType>{

		

		@Override
		public CheckType mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long id = rs.getLong("ID");
			String type = rs.getString("NAME");
			return new CheckType(id, type);
		}
		
	}
	
public static class DebitorNameDataMapper implements RowMapper<DebitorName>{
		@Override
		public DebitorName mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long id = rs.getLong("ID");
			String name = rs.getString("NAME");
			String des = rs.getString("DESCRIPTION");
			return new DebitorName(id, name, des);
		}
		
	}
	
	
public static class OutwardDataMapper implements RowMapper<OutwardData>{
		
		@Override
		public OutwardData mapRow(ResultSet rs, int rowNum) throws SQLException{
			Long id = rs.getLong("ID");
			Date dateTime = rs.getDate("DATE_TIME");
			String accountNo = rs.getString("DEPOSIT_ACCOUNT_NO");
			String name = rs.getString("NAME");
			String chequeNo = rs.getString("CHEQUE_NO");
			String chequeType = rs.getString("CHEQUE_TYPE");
			String fromAccount = rs.getString("FROM_ACCOUNT_NO");
			String currency = rs.getString("CURRENCY");
			Long amount = rs.getLong("AMOUNT");
			String debitorName = rs.getString("DEBITOR_NAME");
			String status = rs.getString("STATUS");
			return new OutwardData(id, dateTime, accountNo,
					name, chequeNo, chequeType, fromAccount,
					currency, amount, debitorName, status);
		}
	}
	
	

	@Override
	public List<OutwardData> retrieveOneOutward(String chequeNo) {
		// TODO Auto-generated method stub
		String sql = String.format("SELECT * FROM OUTWARD_CLEARANCE WHERE CHEQUE_NO = '%s'", chequeNo);
		return jdbcTemplate.query(sql, new OutwardDataMapper());
	}
}
