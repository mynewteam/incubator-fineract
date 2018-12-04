package org.apache.fineract.portfolio.collateral.zland.service;

import java.util.List;

import org.apache.fineract.portfolio.collateral.zland.data.LandCollateralData;

public interface LandCollateralReadPlatformService {
    List<LandCollateralData> retrieveLandCollateralsForValidLoan(Long landId);

    List<LandCollateralData> retrieveLandCollaterals(Long landId);

    LandCollateralData retrieveLandCollateral(Long CollateralId, Long landId);
}
