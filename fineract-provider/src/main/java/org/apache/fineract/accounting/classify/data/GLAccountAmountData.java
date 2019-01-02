package org.apache.fineract.accounting.classify.data;

import java.util.Date;

public class GLAccountAmountData {
	
		private Long loanId ;
		private Long glAccountId;
		private String glAccountName;
		private Date transactionDate;
		private Double balance;
		
		public GLAccountAmountData() {
			super();
			// TODO Auto-generated constructor stub
		}

		public GLAccountAmountData(Long loanId, Long glAccountId, String glAccountName, Date transactionDate,
				Double balance) {
			super();
			this.loanId = loanId;
			this.glAccountId = glAccountId;
			this.glAccountName = glAccountName;
			this.transactionDate = transactionDate;
			this.balance = balance;
		}

		public Long getLoanId() {
			return loanId;
		}

		public void setLoanId(Long loanId) {
			this.loanId = loanId;
		}

		public Long getGlAccountId() {
			return glAccountId;
		}

		public void setGlAccountId(Long glAccountId) {
			this.glAccountId = glAccountId;
		}

		public String getGlAccountName() {
			return glAccountName;
		}

		public void setGlAccountName(String glAccountName) {
			this.glAccountName = glAccountName;
		}

		public Date getTransactionDate() {
			return transactionDate;
		}

		public void setTransactionDate(Date transactionDate) {
			this.transactionDate = transactionDate;
		}

		public Double getBalance() {
			return balance;
		}

		public void setBalance(Double balance) {
			this.balance = balance;
		}
		
}
