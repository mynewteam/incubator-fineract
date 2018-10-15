package org.apache.fineract.portfolio.collateral.landcollateral.service;

import java.util.Collection;

import org.apache.fineract.portfolio.collateral.landcollateral.data.LandCollateralData;

public interface LandReadPlatformService {
	
	LandCollateralData retrieveOne(Long landCollateralId);
	Collection<LandCollateralData> retrieveLandCollateralList(Long collateralId);
	
}
