package org.apache.fineract.accounting.classify.data;

import java.util.Date;

import software.amazon.ion.Decimal;

public class LoanSubTypeData {
	
	private Long loanId;
	private int loan_subtype_status_id;
	private Date overdue_since_date_derived;
	private Integer days_in_arrears;
	private Long product_id;
	
	public LoanSubTypeData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoanSubTypeData(Long loanId, int loan_subtype_status_id, Date overdue_since_date_derived,
			Integer days_in_arrears, Long product_id) {
		
		super();
		this.loanId = loanId;
		this.loan_subtype_status_id = loan_subtype_status_id;
		this.overdue_since_date_derived = overdue_since_date_derived;
		this.days_in_arrears = days_in_arrears;
		this.product_id = product_id;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public int getLoan_subtype_status_id() {
		return loan_subtype_status_id;
	}

	public void setLoan_subtype_status_id(int loan_subtype_status_id) {
		this.loan_subtype_status_id = loan_subtype_status_id;
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

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	
}
