package org.apache.fineract.accounting.spotrate.domain;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.fineract.accounting.spotrate.api.SpotRateJsonInputParams;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.joda.time.LocalDate;

@Entity
@Table(name = "spotrate")
public class mSpotRate  {
   
	@Column(name = "spotRateID")
    private Long spotRateID;
    
    @Column(name = "currency_code")
    private String currency_code;
    
    @Column(name = "buyingRate")
    private BigDecimal buyingRate;
    
    @Column(name = "sellingRate")
    private BigDecimal sellingRate;
    
    @Column(name = "spotRate")
    private BigDecimal spotRate;
    
    @Column(name = "transactionDate")
    private LocalDate transactionDate;

    
    
    
    public mSpotRate(String currency_code, BigDecimal buyingRate, BigDecimal sellingRate, BigDecimal spotRate,
			LocalDate transactionDate)
	{
    	this.currency_code = currency_code;
    	this.buyingRate = buyingRate;
    	this.sellingRate = sellingRate;
    	this.spotRate = spotRate;
    	this.transactionDate = transactionDate;
	}


	public Long getspotRateID() {
        return this.spotRateID;
    }
    
    
    public void setspotRateID(Long spotRateID) {
        this.spotRateID = spotRateID;
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
        return this.transactionDate;
    }

    
    public void settransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }


	public static mSpotRate fromJson(JsonCommand command)
	{
		final String currency_code = command.stringValueOfParameterNamed(SpotRateJsonInputParams.CURRENCY_CODE.getValue());
        final BigDecimal buyingRate = command.bigDecimalValueOfParameterNamed(SpotRateJsonInputParams.BUYING_RATE.getValue());
        final BigDecimal sellingRate = command.bigDecimalValueOfParameterNamed(SpotRateJsonInputParams.SELLING_RATE.getValue());
        final BigDecimal spotRate = command.bigDecimalValueOfParameterNamed(SpotRateJsonInputParams.SPOTRATE.getValue());
        final LocalDate transactionDate = command.localDateValueOfParameterNamed(SpotRateJsonInputParams.TRANSACTION_DATE.getValue());
		return new mSpotRate(currency_code, buyingRate, sellingRate, spotRate, transactionDate);
	}
    
    
}