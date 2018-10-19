package org.apache.fineract.portfolio.collateral.landcollateral.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class LandNotFound extends AbstractPlatformDomainRuleException{

   public LandNotFound (final Long CollateralId, final Long LandId) {
       super("err.msg.loan.collateral.land", "Land collateral with Id "+ LandId + " does not exist for collateral with Id "+ CollateralId, CollateralId, LandId);
   }
   
   public LandNotFound (final Long LandId) {
       super("error.msg.loan.collateral.landcollateral.invalid","Land collateral with identifier "+LandId+" does not exist", LandId);
   }
}
