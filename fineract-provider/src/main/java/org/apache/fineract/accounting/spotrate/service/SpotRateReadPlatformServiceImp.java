package org.apache.fineract.accounting.spotrate.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.fineract.accounting.spotrate.data.SpotRateData;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class SpotRateReadPlatformServiceImp implements SpotRateReadPlatformService
{
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public SpotRateReadPlatformServiceImp(final RoutingDataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static final class SpotRateMapper implements RowMapper<SpotRateData>
	{

		public String schema() {
            StringBuilder sb = new StringBuilder();
            sb.append("sr.id, sr.currency_code, sr.buying_rate, sr.selling_rate, sr.spot_rate, sr.transaction_date FROM spotrate sr");
            return sb.toString();
        }
		
		@Override
		public SpotRateData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException
		{

			final Long id = rs.getLong("id");
			final String currencyCode = rs.getString("currency_code");
			final BigDecimal buyingRate = rs.getBigDecimal("buying_rate");
			final BigDecimal sellingRate = rs.getBigDecimal("selling_rate");
			final BigDecimal spotRate = rs.getBigDecimal("spot_rate");
			final LocalDate transactionDate = new LocalDate(rs.getDate("transaction_Date"));

			return new SpotRateData(id, currencyCode, buyingRate, sellingRate, spotRate, transactionDate);
		}
	}

	@Override
	public List<SpotRateData> retrieveTodaySpotRate(String transactionDate)
	{
		final SpotRateMapper rm = new SpotRateMapper();
		final StringBuilder sql = new StringBuilder();
		sql.append("select ").append(rm.schema());

        sql.append(" where sr.transaction_date = ?");

        return this.jdbcTemplate.query(sql.toString(), rm, new Object[] { transactionDate });
	}
}
