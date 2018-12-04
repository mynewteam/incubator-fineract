package org.apache.fineract.portfolio.collateral.zland.service;

import java.util.List;

import javax.sql.DataSource;

import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateralRepositoryWrapper;
import org.apache.fineract.portfolio.collateral.zland.data.LandCollateralData;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.Context;

@Service
public class LandCollateralReadPlatformServiceImpl implements LandCollateralReadPlatformService {
    
    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
    private final DataSource dataSource;
    
   
    @Autowired
    public LandCollateralReadPlatformServiceImpl(final RoutingDataSource dataSource, final PlatformSecurityContext context) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.context = context;
    }
    
    
    
    @Override
    public List<LandCollateralData> retrieveLandCollateralsForValidLoan(Long landId) {
        return null;
    }

    @Override
    public List<LandCollateralData> retrieveLandCollaterals(Long landId) {
        return null;
    }

    @Override
    public LandCollateralData retrieveLandCollateral(Long CollateralId, Long landId) {
        return null;
    }
    
}
