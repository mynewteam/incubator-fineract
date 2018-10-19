package org.apache.fineract.portfolio.collateral.landcollateral.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;

public interface LandWritePlatformService {
    CommandProcessingResult addLandCollateral(Long collateralId, JsonCommand command);
    
    CommandProcessingResult updateLandCollateral(Long colateralId, JsonCommand command);
    
    CommandProcessingResult deleteLandCollateral(Long collateralId, Long commandId);
}
