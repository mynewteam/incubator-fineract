package org.apache.fineract.portfolio.inward.data;

public class NostroAccountData {

	private Long id;
	private String name;
//	private Long amount;
//	private String currency;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//	public Long getAmount() {
//		return amount;
//	}
//	public void setAmount(Long amount) {
//		this.amount = amount;
//	}
//	public String getCurrency() {
//		return currency;
//	}
//	public void setCurrency(String currency) {
//		this.currency = currency;
//	}
	public NostroAccountData(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
		
	}
	
}
