package org.apache.fineract.portfolio.inward.domain;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.inward.api.InwardApiConstant;
import org.apache.fineract.portfolio.inward.data.InwardData;
import org.apache.fineract.portfolio.inward.exception.NoFieldUpdatedExecption;
import org.apache.fineract.portfolio.outward.data.OutwardData;

@Entity
@Table(name = "Inward_Clearance")
public final class Inward extends AbstractPersistableCustom<Long> {

//	@Column(name = "id")
//	private Long id;
	
	@Column(name = "date_time", nullable = false)
	private Date dateTime;

	@Column(name = "nostro_account", length = 200, nullable = false)
	private String nostrAccount;
	
	@Column(name = "amount", nullable = false)
	private Long amount;
	
	@Column(name = "nostro_balance", nullable = true)
	private Long nostroBalance;
	
	@Column(name = "currency", length = 10)
	private String currency;
	
	@Column(name = "from_account_no", length = 200, nullable = false)
	private String fromAccountNo;
	
	@Column(name = "cheque_no", length = 200, nullable = false)
	private String chequeNo;
	
	@Column(name = "available_balance", nullable = true)
	private Long availableBalance;
	
	@Column(name = "name", length = 200, nullable = false)
	private String name;

	
	public Inward(Date dateTime, String nostrAccount, Long amount, Long nostroBalance, String currency, String fromAccountNo,
			String chequeNo, Long availableBalance, String name) {
		super();
		//this.id = id;
		this.dateTime = dateTime;
		this.nostrAccount = nostrAccount;
		this.amount = amount;
		this.nostroBalance = nostroBalance;
		this.currency = currency;
		this.fromAccountNo = fromAccountNo;
		this.chequeNo = chequeNo;
		this.availableBalance = availableBalance;
		this.name = name;
	}
	
	
	public Map<Object, Object> update(InwardData data){
		
		final Map<Object, Object> actualChanges = new HashMap<>(8);
		if (data.getDateTime() != null){
			this.dateTime = data.getDateTime();
		}

		if (data.getNostroAccount() != null) {
			this.nostrAccount = data.getNostroAccount();
		}
		
		if (data.getAmount() != null) {
			this.amount = data.getAmount();
		}

		if (data.getNostroBalance() != null) {
			this.nostroBalance = data.getNostroBalance();
		}

		if (data.getCurrency() != null) {
			this.currency = data.getCurrency();
		}
		
		if (data.getFromAccountNo() != null) {
			this.fromAccountNo = data.getFromAccountNo();
		}
		
		if (data.getChequeNo() != null) {
			this.chequeNo = data.getChequeNo();
			//actualChanges.put(this.fromAcc, newValue);
		}
		
		if (data.getAvailableBalance() != null) {
			this.availableBalance = data.getAvailableBalance();
		}

		if (data.getName() != null) {
			this.name = data.getName();
		}
		return actualChanges;
	}

	
	
	
	
}
