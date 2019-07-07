package org.apache.fineract.portfolio.inward.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class NoFieldUpdatedExecption extends AbstractPlatformResourceNotFoundException{

	public NoFieldUpdatedExecption() {
		super("error.msg.No.Field.Updated", "Data not recorded.");
	}
	
}
