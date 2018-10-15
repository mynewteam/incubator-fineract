package org.apache.fineract.portfolio.collateral.landcollateral.service;

import java.util.Collection;

import org.apache.fineract.portfolio.collateral.landcollateral.data.LandCollateralData;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class LandReadPlatformServiceImplement implements LandReadPlatformService {

	private final LandCollateralData landCollateralData;

	public LandReadPlatformServiceImplement(LandCollateralData landCollateralData) {
		super();
		this.landCollateralData = landCollateralData;
	}

	@Override
	public LandCollateralData retrieveOne(Long landCollateralId) {
		try {
//			final AppUser currentUser = this.context.authenticatedUser();
//            final String hierarchy = currentUser.getOffice().getHierarchy();
//            final String hierarchySearchString = hierarchy + "%";

			final StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("select ");

		} catch (final EmptyResultDataAccessException e) {

		}
		return null;
	}

	@Override
	public Collection<LandCollateralData> retrieveLandCollateralList(Long collateralId) {

		return null;
	}

}
