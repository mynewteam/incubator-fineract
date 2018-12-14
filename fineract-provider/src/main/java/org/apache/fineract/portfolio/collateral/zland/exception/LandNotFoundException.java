package org.apache.fineract.portfolio.collateral.zland.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class LandNotFoundException extends AbstractPlatformResourceNotFoundException {

    public LandNotFoundException(final Long loanId, final Long landId) {
        super("error.msg.loan.landcollateral.", "land Collateral with Id " + landId + " does not exist for loan with Id " + loanId, loanId,
                landId);
    }
    
    public LandNotFoundException (final Long Id) {
        super("error.msg.loan.land.collateral.id.invalid", "Loan land collateral with identifier " + Id + " does not exist", Id);
        
    }

}
