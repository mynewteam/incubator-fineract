package org.apache.fineract.portfolio.collateral.landcollateral.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LandCollateralRepository extends JpaRepository<LandCollateral, Long>, JpaSpecificationExecutor<LandCollateral> {
    LandCollateral findByCollateralId(Long collateralId, Long landid);
}
