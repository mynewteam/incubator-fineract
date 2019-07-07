package org.apache.fineract.portfolio.inward.data;

import java.util.Date;

import org.apache.fineract.portfolio.outward.data.OutwardData;

final public class InwardData implements Comparable<InwardData> {


	private Long id;
	private Date dateTime;
	private String nostroAccount;
	private Long amount;
	private Long nostroBalance;
	private String currency;
	private String fromAccountNo;
	private String chequeNo;
	private Long availableBalance;
	private String name;
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public void setDateTime(Date dateTime){
		this.dateTime = dateTime;
	}

	public Date getDateTime(){
		return this.dateTime;
	} 

	public String getNostroAccount() {
		return nostroAccount;
	}



	public void setNostroAccount(String nostroAccount) {
		this.nostroAccount = nostroAccount;
	}



	public Long getAmount() {
		return amount;
	}



	public void setAmount(Long amount) {
		this.amount = amount;
	}



	public Long getNostroBalance() {
		return nostroBalance;
	}



	public void setNostroBalance(Long nostroBalance) {
		this.nostroBalance = nostroBalance;
	}



	public String getCurrency() {
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public String getFromAccountNo() {
		return fromAccountNo;
	}



	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}



	public String getChequeNo() {
		return chequeNo;
	}



	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}



	public InwardData(Long id, String nostroAccount, Long amount, Long nostroBalance, String currency, String fromAccountNo,
			String chequeNo, Long availableBalance, String name) {
		this.id = id;
		this.nostroAccount = nostroAccount;
		this.amount = amount;
		this.nostroBalance = nostroBalance;
		this.currency = currency;
		this.fromAccountNo = fromAccountNo;
		this.chequeNo = chequeNo;
		this.availableBalance = availableBalance;
		this.name = name;
	}

	



	public Long getAvailableBalance() {
		return availableBalance;
	}



	public void setAvailableBalance(Long availableBalance) {
		this.availableBalance = availableBalance;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	
	



	@Override
	public int compareTo(InwardData arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public InwardData() {
	}
	
	
	
	
	
	
	
	
}
