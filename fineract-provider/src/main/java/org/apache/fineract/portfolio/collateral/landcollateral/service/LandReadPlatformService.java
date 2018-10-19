package org.apache.fineract.portfolio.collateral.landcollateral.service;


import java.util.List;

import org.apache.fineract.portfolio.collateral.landcollateral.data.LandCollateralData;

public interface LandReadPlatformService {
	
	LandCollateralData retrieveOne(Long landCollateralId);
	List<LandCollateralData> retrieveLandCollateralList(Long collateralId);
	
}
