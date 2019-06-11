package org.apache.fineract.accounting.spotrate.exception;


import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class SpotRateNotFoundException extends AbstractPlatformResourceNotFoundException
{
	public SpotRateNotFoundException()
	{
		super("error.msg.spotrate.existed", "Spotrate already existed today.");
	}
	
}
