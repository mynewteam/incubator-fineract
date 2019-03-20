package org.apache.fineract.accounting.classification.data;

import java.util.Date;

import software.amazon.ion.Decimal;

public class LoanArriaClassifyData {
//client_account_no
//client_name
//account_number
//loan_amount
//original_principal
//original_lnterest
//principal_paid
//interest_paid
//principal_overdue
//interest_overdue
//loanoutstanding
//overdue_since_date_derived
//days_in_Arrears
	private String client_account_no;
	private Integer account_number;
	private double loan_outstanding;
	private Date overdue_since_date_derived;
	private Integer days_in_arrears;
	public LoanArriaClassifyData(String client_account_no, Integer account_number, double loan_outstanding,
			Date overdue_since_date_derived, Integer days_in_arrears) {
		super();
		this.client_account_no = client_account_no;
		this.account_number = account_number;
		this.loan_outstanding = loan_outstanding;
		this.overdue_since_date_derived = overdue_since_date_derived;
		this.days_in_arrears = days_in_arrears;
	}
	public String getClient_account_no() {
		return client_account_no;
	}
	public void setClient_account_no(String client_account_no) {
		this.client_account_no = client_account_no;
	}
	public Integer getAccount_number() {
		return account_number;
	}
	public void setAccount_number(Integer account_number) {
		this.account_number = account_number;
	}
	public double getLoan_outstanding() {
		return loan_outstanding;
	}
	public void setLoan_outstanding(double loan_outstanding) {
		this.loan_outstanding = loan_outstanding;
	}
	public Date getOverdue_since_date_derived() {
		return overdue_since_date_derived;
	}
	public void setOverdue_since_date_derived(Date overdue_since_date_derived) {
		this.overdue_since_date_derived = overdue_since_date_derived;
	}
	public Integer getDays_in_arrears() {
		return days_in_arrears;
	}
	public void setDays_in_arrears(Integer days_in_arrears) {
		this.days_in_arrears = days_in_arrears;
	}

	
}
