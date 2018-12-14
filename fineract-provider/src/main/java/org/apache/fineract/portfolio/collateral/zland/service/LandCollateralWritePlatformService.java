package org.apache.fineract.portfolio.collateral.zland.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;

public interface LandCollateralWritePlatformService  {
    
    CommandProcessingResult addLandCollateral(Long LoanId, JsonCommand command);

    CommandProcessingResult updateLandCollateral(Long LoanId, Long landId, JsonCommand command);

    CommandProcessingResult deleteLandCollateral(Long LoanId, Long landId, Long commandId);
}
