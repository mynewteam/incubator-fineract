package org.apache.fineract.portfolio.cheque.service;

import org.apache.fineract.portfolio.cheque.data.ChequeDepositToSummaryCollectionData;
import org.apache.fineract.portfolio.cheque.data.ChequeSummaryCollectionData;


public interface ChequeDetailReadPlatformService {
	public ChequeSummaryCollectionData retrieveClientAccountDetails(final String accountsaving);
	public ChequeDepositToSummaryCollectionData retrieveClientAccountdepositto(final String accountsaving);
}
