package org.apache.fineract.accounting.classify.data;

public class LoanProductSubtypeMappingData {

	private Long id;
	private Long productId;
	private int loanSubtypeStatusId;
	private int minAge;
	private int maxAge;
	private Long portfolioAccId;
	private Long intReceivableAccId;
	private Long incomeAccId;
	
	public LoanProductSubtypeMappingData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LoanProductSubtypeMappingData(Long id, Long productId, int loanSubtypeStatusId, int minAge, int maxAge,
			Long portfolioAccId, Long intReceivableAccId, Long incomeAccId) {
		super();
		this.id = id;
		this.productId = productId;
		this.loanSubtypeStatusId = loanSubtypeStatusId;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.portfolioAccId = portfolioAccId;
		this.intReceivableAccId = intReceivableAccId;
		this.incomeAccId = incomeAccId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getLoanSubtypeStatusId() {
		return loanSubtypeStatusId;
	}
	public void setLoanSubtypeStatusId(int loanSubtypeStatusId) {
		this.loanSubtypeStatusId = loanSubtypeStatusId;
	}
	public int getMinAge() {
		return minAge;
	}
	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}
	public int getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
	public Long getPortfolioAccId() {
		return portfolioAccId;
	}
	public void setPortfolioAccId(Long portfolioAccId) {
		this.portfolioAccId = portfolioAccId;
	}
	public Long getIntReceivableAccId() {
		return intReceivableAccId;
	}
	public void setIntReceivableAccId(Long intReceivableAccId) {
		this.intReceivableAccId = intReceivableAccId;
	}
	public Long getIncomeAccId() {
		return incomeAccId;
	}
	public void setIncomeAccId(Long incomeAccId) {
		this.incomeAccId = incomeAccId;
	}
	
}
