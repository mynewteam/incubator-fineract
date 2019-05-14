package org.apache.fineract.accounting.spotrate.domain;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;
import org.apache.fineract.accounting.spotrate.api.SpotRateJsonInputParams;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.joda.time.LocalDate;

@Entity
@Table(name = "spotrate")
public class SpotRate extends AbstractPersistableCustom<Long> {
    
    @Column(name = "currency_code")
    private String currency_code;
    
    @Column(name = "buyingRate")
    private BigDecimal buyingRate;
    
    @Column(name = "sellingRate")
    private BigDecimal sellingRate;
    
    @Column(name = "spotRate")
    private BigDecimal spotRate;
    
    @Column(name = "transactionDate")
    private Date transactionDate;
    
    public SpotRate(String currency_code, BigDecimal buyingRate, BigDecimal sellingRate, BigDecimal spotRate, LocalDate transactionDate)
	{
    	this.currency_code = currency_code;
    	this.buyingRate = buyingRate;
    	this.sellingRate = sellingRate;
    	this.spotRate = spotRate;
    	this.transactionDate = transactionDate.toDate();
	}


    public String getcurrency_code() {
        return this.currency_code;
    }

    
    public void setcurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    
    public BigDecimal getbuyingRate() {
        return this.buyingRate;
    }

    
    public void setbuyingRate(BigDecimal buyingRate) {
        this.buyingRate = buyingRate;
    }
    
    public BigDecimal getsellingRate() {
        return this.sellingRate;
    }
    
    
    public void setsellingRate(BigDecimal sellingRate) {
        this.sellingRate = sellingRate;
    }


    public BigDecimal getspotRate() {
        return this.spotRate;
    }

    
    public void setspotRate(BigDecimal spotRate) {
        this.spotRate = spotRate;
    }

    public LocalDate gettransactionDate() {
		return (LocalDate) ObjectUtils.defaultIfNull(new LocalDate(this.transactionDate), null);
	}

	public static SpotRate fromJson(JsonCommand command)
	{
		final String currency_code = command.stringValueOfParameterNamed(SpotRateJsonInputParams.CURRENCY_CODE.getValue());
        final BigDecimal buyingRate = command.bigDecimalValueOfParameterNamed(SpotRateJsonInputParams.BUYING_RATE.getValue());
        final BigDecimal sellingRate = command.bigDecimalValueOfParameterNamed(SpotRateJsonInputParams.SELLING_RATE.getValue());
        final BigDecimal spotRate = command.bigDecimalValueOfParameterNamed(SpotRateJsonInputParams.SPOTRATE.getValue());
        final LocalDate transactionDate = command.localDateValueOfParameterNamed(SpotRateJsonInputParams.TRANSACTION_DATE.getValue());
		return new SpotRate(currency_code, buyingRate, sellingRate, spotRate, transactionDate);
	}
    
    
}