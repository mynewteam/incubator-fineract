package org.apache.fineract.accounting.classify.data;

public class ProductSubTypeMappingData {
	
	private Integer id;
	private Integer product_id;
	private Integer loan_subtype_status_id;
	private Integer min_age;
	private Integer max_age;
	private Long portfolio_acc_id;
	private Long int_receivable_acc_id;
	private Long income_acc_id;
	
	public ProductSubTypeMappingData() {
		super();
	}

	public ProductSubTypeMappingData(Integer id, Integer product_id, Integer loan_subtype_status_id, Integer min_age,
			Integer max_age, Long portfolio_acc_id, Long int_receivable_acc_id, Long income_acc_id) {
		super();
		this.id = id;
		this.product_id = product_id;
		this.loan_subtype_status_id = loan_subtype_status_id;
		this.min_age = min_age;
		this.max_age = max_age;
		this.portfolio_acc_id = portfolio_acc_id;
		this.int_receivable_acc_id = int_receivable_acc_id;
		this.income_acc_id = income_acc_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public Integer getLoan_subtype_status_id() {
		return loan_subtype_status_id;
	}

	public void setLoan_subtype_status_id(Integer loan_subtype_status_id) {
		this.loan_subtype_status_id = loan_subtype_status_id;
	}

	public Integer getMin_age() {
		return min_age;
	}

	public void setMin_age(Integer min_age) {
		this.min_age = min_age;
	}

	public Integer getMax_age() {
		return max_age;
	}

	public void setMax_age(Integer max_age) {
		this.max_age = max_age;
	}

	public Long getPortfolio_acc_id() {
		return portfolio_acc_id;
	}

	public void setPortfolio_acc_id(Long portfolio_acc_id) {
		this.portfolio_acc_id = portfolio_acc_id;
	}

	public Long getInt_receivable_acc_id() {
		return int_receivable_acc_id;
	}

	public void setInt_receivable_acc_id(Long int_receivable_acc_id) {
		this.int_receivable_acc_id = int_receivable_acc_id;
	}

	public Long getIncome_acc_id() {
		return income_acc_id;
	}

	public void setIncome_acc_id(Long income_acc_id) {
		this.income_acc_id = income_acc_id;
	}
	
	
}
