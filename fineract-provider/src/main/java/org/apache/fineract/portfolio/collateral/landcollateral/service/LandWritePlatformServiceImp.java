package org.apache.fineract.portfolio.collateral.landcollateral.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateralRepository;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.LandCollateralRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class LandWritePlatformServiceImp implements LandWritePlatformService {

    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(LandWritePlatformServiceImp.class);
    
    private final PlatformSecurityContext context;
    private final LoanCollateralRepository loanRepositoryWrapper;
    private final LandCollateralRepository landRepositoryWrapper;
    
    @Autowired
    public LandWritePlatformServiceImp(PlatformSecurityContext context, LoanCollateralRepository loanRepositoryWrapper,
            LandCollateralRepository landRepositoryWrapper) {
        super();
        this.context = context;
        this.loanRepositoryWrapper = loanRepositoryWrapper;
        this.landRepositoryWrapper = landRepositoryWrapper;
    }

    @Override
    public CommandProcessingResult addLandCollateral(Long collateralId, JsonCommand command) {
        
        return null;
    }

    @Override
    public CommandProcessingResult updateLandCollateral(Long colateralId, JsonCommand command) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandProcessingResult deleteLandCollateral(Long collateralId, Long commandId) {
        // TODO Auto-generated method stub
        return null;
    }
 
}
