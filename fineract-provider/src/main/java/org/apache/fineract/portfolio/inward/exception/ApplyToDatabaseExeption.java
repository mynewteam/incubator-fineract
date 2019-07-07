package org.apache.fineract.portfolio.inward.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;

public class ApplyToDatabaseExeption extends AbstractPlatformDomainRuleException {

	public ApplyToDatabaseExeption(final String paramName) {
		super("error.msg.Apply.Database.Paramater." + paramName, " The Data was cancel to apply.");
		// TODO Auto-generated constructor stub
	}

	
}
