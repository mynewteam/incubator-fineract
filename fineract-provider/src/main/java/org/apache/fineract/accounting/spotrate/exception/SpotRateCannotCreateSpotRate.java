package org.apache.fineract.accounting.spotrate.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class SpotRateCannotCreateSpotRate extends AbstractPlatformResourceNotFoundException {

	public SpotRateCannotCreateSpotRate(String globalisationMessageCode, String defaultUserMessage,
			Object[] defaultUserMessageArgs)
	{
		super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
		// TODO Auto-generated constructor stub
	}
    
}
