package org.apache.fineract.portfolio.collateral.zland.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class LandNotFoundDetection extends AbstractPlatformResourceNotFoundException {

    public LandNotFoundDetection(final Long collateralId, final Long landId) {
        super("error.msg.loan.collateral.land.", "land with Id " + collateralId + " does not exist for loan with Id " + collateralId, collateralId, landId);
    }

    public LandNotFoundDetection(final Long id) {
        super("error.msg.loan.collateral.land.id.invalid", "Loan land collateral with identifier " + id + " does not exist", id);
    }
}
