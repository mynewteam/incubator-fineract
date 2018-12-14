package org.apache.fineract.portfolio.collateral.domain;

import org.apache.fineract.portfolio.collateral.exception.CollateralNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanCollateralRepositoryWrapper {
    
    private final LoanCollateralRepository repository;

    @Autowired
    public LoanCollateralRepositoryWrapper(final LoanCollateralRepository repository) {
        this.repository = repository;
    }
    
    public LoanCollateral fineOneWithNotFoundDetection(final Long id) {
        return this.fineOneWithNotFoundDetection(id);
    }
    
    @Transactional(readOnly = true)
    public LoanCollateral findOneWithNotFoundDetection(final Long id, boolean loadLandCollections) {
        final LoanCollateral loanCollateral = this.fineOneWithNotFoundDetection(id);
        if(loanCollateral == null) {throw new CollateralNotFoundException(id); }
        if(loadLandCollections) {
//            loanCollateral.initializeLazyCollections();
        }
        return loanCollateral;
    }
}
