package org.apache.fineract.portfolio.collateral.landcollateral.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.collateral.exception.CollateralNotFoundException;
import org.apache.fineract.portfolio.collateral.landcollateral.data.LandCollateralData;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.CollateralName;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.CollateralStatus;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.NaturalCollateral;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.Province;
import org.apache.fineract.portfolio.collateral.landcollateral.exception.LandNotFound;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LandReadPlatformServiceImplement implements LandReadPlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;

    @Autowired
    public LandReadPlatformServiceImplement(final RoutingDataSource dataSource, final PlatformSecurityContext context) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.context = context;
    }
   
    private static final class LandCollateralMapper implements RowMapper<LandCollateralData> {

        private final StringBuilder sqlbuilder = new StringBuilder("land_collateral_id, collateral_id, name_enum, date_issue, size, old_price, price, number_of_copy, ")
                .append("status_enum, detail_location, owner_name_1, gender_1, passport_id_1, owner_name_2, gender_2, passport_id_2, ")
                .append(" mlcn.name, mlcnature.name as nature_name , mp.pro_name as province_name")
                .append(" from m_land_collateral mlc ")
                .append(" left join m_loan_collateral_name mlcn on mlc.m_loan_collateral_name_id = mlcn.id ")
                .append(" left join m_loan_collateral_nature mlcnature on mlc.m_loan_collateral_nature_id = mlcnature.id ")
                .append(" left join m_province mp on mlc.Province_id = mp.id");

        public String schema() {
            return this.sqlbuilder.toString();
        }

        
        public LandCollateralData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
           
             final Long id = rs.getLong("land_collateral_id");
             final Long collateralId = rs.getLong("collateral_id");
             final String name = rs.getString("name");
             final Date dateIssue = rs.getDate("date_issue");
             final String natural = rs.getString("nature_name");
             final String size = rs.getString("size");
             final BigDecimal oldPrice = rs.getBigDecimal("old_price");
             final BigDecimal price = rs.getBigDecimal("price");
             final String province = rs.getString("province_name");
             final Integer numberOfCopy = rs.getInt("number_of_copy");
             final Integer status = rs.getInt("status_enum");
             final String detailLocation = rs.getString("detail_location"); 
             final String ownerName1 = rs.getString("owner_name_1");
             final Integer gender1 = rs.getInt("gender_1");
             final String passportid1 = rs.getString("passport_id_1");
             final String ownerName2 = rs.getString("owner_name_2");
             final Integer gender2 = rs.getInt("gender_2");
             final String passportid2 = rs.getString("passport_id_2");
             
            return LandCollateralData.instance(
                    id, 
                    name, 
                    collateralId, 
                    dateIssue, 
                    natural, 
                    size, 
                    oldPrice,
                    price, 
                    province, 
                    numberOfCopy, 
                    status, 
                    detailLocation,
                    ownerName1, 
                    gender1, 
                    passportid1, 
                    ownerName2,
                    gender2, 
                    passportid2);
        }

    }

    @Override
    public LandCollateralData retrieveOne(Long landCollateralId) {
        try{
            final LandCollateralMapper lm = new LandCollateralMapper();
            String sql = "select "+lm.schema();
            sql += " where mlc.land_collateral_id=?";
            return this.jdbcTemplate.queryForObject(sql, lm, new Object[] {landCollateralId});
        }catch(final EmptyResultDataAccessException e) {
            throw new LandNotFound(landCollateralId);
        }
    }

    @Override
    public List<LandCollateralData> retrieveLandCollateralList(Long collateralId) {
        this.context.authenticatedUser();
        
        final LandCollateralMapper lm = new LandCollateralMapper();
        
        final String sql = "select "+lm.schema()+" where mlc.land_collateral_id=? order by land_collateral_id ASC";
        
        return this.jdbcTemplate.query(sql, lm, new Object[] {collateralId});
    }
}