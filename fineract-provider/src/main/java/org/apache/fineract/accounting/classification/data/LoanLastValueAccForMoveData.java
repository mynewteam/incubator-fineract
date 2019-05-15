package org.apache.fineract.accounting.classification.data;

public class LoanLastValueAccForMoveData {
	private Long loan_id;
	private Long account_id;
	private double last_running_balance;
	public LoanLastValueAccForMoveData(Long loan_id, Long account_id, double last_running_balance) {
		super();
		this.loan_id = loan_id;
		this.account_id = account_id;
		this.last_running_balance = last_running_balance;
	}
	public Long getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(Long loan_id) {
		this.loan_id = loan_id;
	}
	public Long getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Long account_id) {
		this.account_id = account_id;
	}
	public double getLast_running_balance() {
		return last_running_balance;
	}
	public void setLast_running_balance(double last_running_balance) {
		this.last_running_balance = last_running_balance;
	}
	
	

}
