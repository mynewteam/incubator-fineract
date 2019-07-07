package org.apache.fineract.portfolio.outward.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class ApplyToDatabaseException extends AbstractPlatformDomainRuleException{

	public ApplyToDatabaseException(final String paramName) {
		super("error.msg.Apply.Database.Parameter." + paramName, "The Data was cancel to apply");
	}

	
}
