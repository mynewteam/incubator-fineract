package org.apache.fineract.portfolio.collateral.zland.service;

import java.util.List;

import org.apache.fineract.infrastructure.codes.domain.CodeValueRepositoryWrapper;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateralRepository;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateralRepositoryWrapper;
import org.apache.fineract.portfolio.collateral.serialization.CollateralCommandFromApiJsonDeserializer;
import org.apache.fineract.portfolio.collateral.zland.data.LandCollateralData;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LandCollateralWritePlatformServiceImpl implements LandCollateralWritePlatformService {

    private final PlatformSecurityContext context;
    private final CodeValueRepositoryWrapper codeValueRepository;
    
    
    @Autowired
    public LandCollateralWritePlatformServiceImpl(PlatformSecurityContext context, CodeValueRepositoryWrapper codeValueRepository) {
        this.context = context;
        this.codeValueRepository = codeValueRepository;
    }


    @Transactional
    @Override
    public CommandProcessingResult addLandCollateral(final Long loanCollateralId, final JsonCommand command) {
        this.context.authenticatedUser();
        
        return null;
    }


    @Override
    public CommandProcessingResult updateLandCollateral(Long loanCollateralId, Long landId, JsonCommand command) {
        
        return null;
    }


    @Override
    public CommandProcessingResult deleteLandCollateral(Long loanCollateralId, Long landId, Long commandId) {

        return null;
    }

   
}
