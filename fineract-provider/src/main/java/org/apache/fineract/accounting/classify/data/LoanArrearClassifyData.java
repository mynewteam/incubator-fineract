package org.apache.fineract.accounting.classify.data;

import java.util.Date;

public class LoanArrearClassifyData {

	private String clientAccountNo;
	private Long accountNumber;
	private Long productId;
	private Long loanSubtypeStatusId;
	private double loanOutstanding;
	private Date overdueSinceDateDerived;
	private Integer daysInArrears;
	
	public LoanArrearClassifyData(String clientAccountNo, Long accountNumber, Long productId, Long loanSubtypeStatusId,
			double loanOutstanding, Date overdueSinceDateDerived, Integer daysInArrears) {
		super();
		this.clientAccountNo = clientAccountNo;
		this.accountNumber = accountNumber;
		this.productId = productId;
		this.loanSubtypeStatusId = loanSubtypeStatusId;
		this.loanOutstanding = loanOutstanding;
		this.overdueSinceDateDerived = overdueSinceDateDerived;
		this.daysInArrears = daysInArrears;
	}
	public String getClientAccountNo() {
		return clientAccountNo;
	}
	public void setClientAccountNo(String clientAccountNo) {
		this.clientAccountNo = clientAccountNo;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getLoanSubtypeStatusId() {
		return loanSubtypeStatusId;
	}
	public void setLoanSubtypeStatusId(Long loanSubtypeStatusId) {
		this.loanSubtypeStatusId = loanSubtypeStatusId;
	}
	public double getLoanOutstanding() {
		return loanOutstanding;
	}
	public void setLoanOutstanding(double loanOutstanding) {
		this.loanOutstanding = loanOutstanding;
	}
	public Date getOverdueSinceDateDerived() {
		return overdueSinceDateDerived;
	}
	public void setOverdueSinceDateDerived(Date overdueSinceDateDerived) {
		this.overdueSinceDateDerived = overdueSinceDateDerived;
	}
	public Integer getDaysInArrears() {
		return daysInArrears;
	}
	public void setDaysInArrears(Integer daysInArrears) {
		this.daysInArrears = daysInArrears;
	}


}
