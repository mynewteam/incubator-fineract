package org.apache.fineract.portfolio.cheque.data;

import java.util.Collection;

import org.apache.fineract.portfolio.account.data.PortfolioAccountData;
import org.apache.fineract.portfolio.accountdetails.data.SavingsAccountSummaryData;
import org.apache.fineract.portfolio.client.data.ClientData;

@SuppressWarnings("unused")
public class ChequeSummaryCollectionData {

	  private final Collection<SavingsAccountSummaryData> savingaccount;
//	  private final Collection<SavingsAccountSummaryData> deposittoaccount;
	  private final ClientData clientinfo;

	public ChequeSummaryCollectionData(Collection<SavingsAccountSummaryData> savingaccount,
			ClientData clientinfo) {
		
		this.savingaccount = defaultLoanAccountsIfEmpty(savingaccount);
//		this.deposittoaccount = defaultdepositAccountsIfEmpty(deposittoaccount);
		this.clientinfo = clientinfo;
	}


	private Collection<SavingsAccountSummaryData> defaultLoanAccountsIfEmpty(
			Collection<SavingsAccountSummaryData> collection) {
		 Collection<SavingsAccountSummaryData> returnCollection = null;
	        if (collection != null && !collection.isEmpty()) {
	            returnCollection = collection;
	        }
	        return returnCollection;
	}
	

	  
}
