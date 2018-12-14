package org.apache.fineract.portfolio.collateral.zland.domain;

import org.apache.fineract.portfolio.collateral.zland.exception.LandNotFoundDetection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LandCollateralRepositoryImplement {

    private final LandCollateralRepository repository;

    @Autowired
    public LandCollateralRepositoryImplement(LandCollateralRepository repository) {
        this.repository = repository;
    }
    
    public LandCollateral findOneWithNotFoundDetection(final Long id) {
        return this.findOneWithNotFoundDetection(id);
    }
    
    @Transactional(readOnly = true)
    public LandCollateral findOneWithNotFoundDetection(final Long id, boolean LandCollections) {
        final LandCollateral landCollateral = this.findOneWithNotFoundDetection(id);
        if(landCollateral==null){throw new LandNotFoundDetection(id);}
        if(LandCollections) {
            landCollateral.initializeLazyCollections();
        }
        return landCollateral;
    }
    
    
}
