package org.apache.fineract.portfolio.collateral.zland.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;

public interface LandCollateralWritePlatformService  {
    
    CommandProcessingResult addLandCollateral(Long loanCollateralId, JsonCommand command);

    CommandProcessingResult updateLandCollateral(Long loanCollateralId, Long landId, JsonCommand command);

    CommandProcessingResult deleteLandCollateral(Long loanCollateralId, Long landId, Long commandId);
}
