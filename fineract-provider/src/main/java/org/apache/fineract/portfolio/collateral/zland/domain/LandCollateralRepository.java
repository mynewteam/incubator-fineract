package org.apache.fineract.portfolio.collateral.zland.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LandCollateralRepository extends JpaRepository<LandCollateral, Long>, JpaSpecificationExecutor<LandCollateral> {
    LandCollateral findLandCollateralById(Long landCollateralId, Long id);
}
