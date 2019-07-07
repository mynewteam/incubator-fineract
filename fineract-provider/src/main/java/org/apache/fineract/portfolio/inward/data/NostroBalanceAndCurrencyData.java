package org.apache.fineract.portfolio.inward.data;

public class NostroBalanceAndCurrencyData {

	private Double nostroBalance;
	private String currency;
	public Double getAmount() {
		return nostroBalance;
	}
	public void setAmount(Double amount) {
		this.nostroBalance = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public NostroBalanceAndCurrencyData(Double nostroBalance, String currency) {
		super();
		this.nostroBalance = nostroBalance;
		this.currency = currency;
	}
	
}
