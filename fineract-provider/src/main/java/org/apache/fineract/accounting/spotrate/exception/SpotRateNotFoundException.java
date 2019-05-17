package org.apache.fineract.accounting.spotrate.exception;


import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class SpotRateNotFoundException extends AbstractPlatformResourceNotFoundException
{
	public SpotRateNotFoundException(final String transactionDate)
	{
		super("error.msg.spotrate.date.invalid", "Spot rate in " + transactionDate + " does not exist ");
	}
}
