package org.apache.fineract.portfolio.inward.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class ChequeNotFoundException extends AbstractPlatformResourceNotFoundException{

	 public ChequeNotFoundException(final Long chequeNo) {
		// TODO Auto-generated constructor stub
		 super("error.msg.Cheque.Number.invalid", "Cheque with identifier " + chequeNo + " does not exist", chequeNo);
	} 
	
}