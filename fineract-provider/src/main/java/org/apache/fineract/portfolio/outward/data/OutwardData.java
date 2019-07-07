package org.apache.fineract.portfolio.outward.data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.portfolio.outward.api.OutwardApiConstants;
import org.apache.tomcat.jni.Local;

import com.sun.java.swing.plaf.windows.WindowsTreeUI.CollapsedIcon;

import net.fortuna.ical4j.model.DateTime;

final public class OutwardData implements Comparable<OutwardData> {

	private Long id;
	private Date dateTime;
	private String depositAccountNo;
	private String name;
	private String chequeNo;
	private String chequeType;
	private String fromAccountNo;
	private String currency;
	private Long amount;
	private String debitorName;
	private String status;
	
	
	

	public OutwardData(Long id, Date dateTime, String depositAccountNo, String name, String chequeNo, String chequeType,
			String fromAccountNo, String currency, Long amount, String debitorName, String status) {
		
		this.id = id;
		this.dateTime = dateTime;
		this.depositAccountNo = depositAccountNo;
		this.name = name;
		this.chequeNo = chequeNo;
		this.chequeType = chequeType;
		this.fromAccountNo = fromAccountNo;
		this.currency = currency;
		this.amount = amount;
		this.debitorName = debitorName;
		this.status = status;

	}

	

	public OutwardData() {
		
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getDateTime() {
		return dateTime;
	}


	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}


	public String getDepositAccountNo() {
		return depositAccountNo;
	}


	public void setDepositAccountNo(String depositAccountNo) {
		this.depositAccountNo = depositAccountNo;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getChequeNo() {
		return chequeNo;
	}


	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}


	public String getChequeType() {
		return chequeType;
	}


	public void setChequeType(String chequeType) {
		this.chequeType = chequeType;
	}


	public String getFromAccountNo() {
		return fromAccountNo;
	}


	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public Long getAmount() {
		return amount;
	}


	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getDebitorName(){
		return debitorName;	
	}

	public void setDebitorName(String debitorName){
		this.debitorName = debitorName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public int compareTo(OutwardData o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
