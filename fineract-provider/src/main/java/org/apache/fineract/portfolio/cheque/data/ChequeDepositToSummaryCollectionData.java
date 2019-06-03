package org.apache.fineract.portfolio.cheque.data;

import java.util.Collection;

import org.apache.fineract.portfolio.accountdetails.data.SavingsAccountSummaryData;
import org.apache.fineract.portfolio.client.data.ClientData;


@SuppressWarnings("unused")
public class ChequeDepositToSummaryCollectionData {
	 private final Collection<SavingsAccountSummaryData> deposittoaccount;
	  private final ClientData clientinfo;
	  public ChequeDepositToSummaryCollectionData(Collection<SavingsAccountSummaryData> deposittoaccount,
				ClientData clientinfo) {			
			this.deposittoaccount = defaultLoanAccountsIfEmpty(deposittoaccount);
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
