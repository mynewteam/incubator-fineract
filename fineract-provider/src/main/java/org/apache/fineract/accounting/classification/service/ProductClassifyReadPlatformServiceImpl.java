package org.apache.fineract.accounting.classification.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.fineract.accounting.classification.data.ProductClassifyMappingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class ProductClassifyReadPlatformServiceImpl implements ProductClassifyReadPlatformService {

	private final JdbcTemplate jdbctemplate;
	
	@Autowired
	public ProductClassifyReadPlatformServiceImpl(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	@Override
	public List<ProductClassifyMappingData> retrieveProductClassifyList(Long ProductId, Integer Agging) {
		ProductClassifyDataMapper mapper = new ProductClassifyDataMapper();
		final String sql = "SELECT id, description, agging, acc_dr_id, acc_cr_id, acc_product_mapping_id, type FROM acc_product_classify_mapping  WHERE acc_product_mapping_id = ? AND agging = ?";
		return this.jdbctemplate.query(sql, mapper, new Object[] {ProductId, Agging} );
	}
	
	private static final class ProductClassifyDataMapper implements RowMapper<ProductClassifyMappingData>{

		@Override
		public ProductClassifyMappingData mapRow(ResultSet rs, int rowNum) throws SQLException {
			String description =rs.getString("description");
			Integer agging = rs.getInt("agging");
			Integer accDrId = rs.getInt("acc_dr_id");
			Integer accCrId = rs.getInt("acc_cr_id");
			Integer type = rs.getInt("type");
			Long productMappingId = rs.getLong("acc_product_mapping_id");

			return new ProductClassifyMappingData(description, agging, accDrId, accCrId, type, productMappingId);
		}
	}

}
