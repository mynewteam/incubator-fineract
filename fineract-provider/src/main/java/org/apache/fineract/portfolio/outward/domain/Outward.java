
package org.apache.fineract.portfolio.outward.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.outward.api.OutwardApiConstants;
import org.apache.fineract.portfolio.outward.data.OutwardData;


@Entity
@Table(name = "OUTWARD_CLEARANCE")
public final class Outward extends AbstractPersistableCustom<Long> {

//	@Column(name = "id")
//	private Long id;
	
	@Column(name = "date_time", nullable = true)
	private Date dateTime;
	
	@Column(name = "deposit_account_no", length = 200, nullable = false)
	private String depositAcccountNo;
	
	@Column(name = "name", length = 50, nullable = true)
	private String name;
	
	@Column(name = "cheque_no", length = 200, nullable = false)
	private String chequeNo;
	
	@Column(name = "cheque_type", length = 30, nullable = true)
	private String chequeType;
	
	@Column(name = "from_account_no", length = 200, nullable = false)
	private String fromAccountNo;
	
	@Column(name = "currency", length = 10, nullable = true)
	private String currency;
	
	@Column(name = "amount", nullable = true)
	private Long amount;

	@Column(name = "debitor_name", length = 200, nullable = true)
	private String debitorName;
	
	@Column(name = "status", length = 20, nullable = true)
	private String status;

	
	public Outward(Date dateTime, String depositAcccountNo, String name, String chequeNo, String chequeType,
			String fromAccountNo, String currency, Long amount, String debitorName, String status) {
		super();
		this.dateTime = dateTime;
		this.depositAcccountNo = depositAcccountNo;
		this.name = name;
		this.chequeNo = chequeNo;
		this.chequeType = chequeType;
		this.fromAccountNo = fromAccountNo;
		this.currency = currency;
		this.amount = amount;
		this.debitorName = debitorName;
		this.status = status;
	}

	public Map<Object, Object> update(OutwardData data){
		final Map<Object, Object> actualChanges = new HashMap<>(10);
		
		if (data.getDateTime() != null) {
			this.dateTime = data.getDateTime();
		}
		
		if (data.getDepositAccountNo() != null) {
			this.depositAcccountNo = data.getDepositAccountNo();
		}
		
		if (data.getChequeNo() != null) {
			this.chequeNo = data.getChequeNo();
		}
		
		if (data.getChequeType() != null) {
			this.chequeType = data.getChequeType();
		}
		
		if (data.getFromAccountNo() != null) {
			this.fromAccountNo = data.getFromAccountNo();
		}
		
		if (data.getCurrency() != null) {
			this.currency = data.getCurrency();
		}
		
		if (data.getAmount() != null) {
			this.amount = data.getAmount();
		}

		if (data.getDebitorName() != null) {
			this.debitorName = data.getDebitorName();
		}
		
		if (data.getStatus() != null) {
			this.status = data.getStatus();
		}
		
		return actualChanges;
	}
	

	
	
	
}
