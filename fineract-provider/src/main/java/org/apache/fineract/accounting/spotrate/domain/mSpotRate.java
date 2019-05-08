package org.apache.fineract.accounting.spotrate.domain;


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
    private double buyingRate;
    
    @Column(name = "sellingRate")
    private double sellingRate;
    
    @Column(name = "spotRate")
    private double spotRate;
    
    @Column(name = "transactionDate")
    private LocalDate transactionDate;

    
    
    
    public mSpotRate(String currency_code, double buyingRate, double sellingRate, double spotRate,
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

    
    public double getbuyingRate() {
        return this.buyingRate;
    }

    
    public void setbuyingRate(double buyingRate) {
        this.buyingRate = buyingRate;
    }
    
    public double getsellingRate() {
        return this.sellingRate;
    }
    
    
    public void setsellingRate(double sellingRate) {
        this.sellingRate = sellingRate;
    }


    public double getspotRate() {
        return this.spotRate;
    }

    
    public void setspotRate(double spotRate) {
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
        final double buyingRate = command.doubleValueofParameterNamed(SpotRateJsonInputParams.BUYING_RATE.getValue());
        final double sellingRate = command.doubleValueofParameterNamed(SpotRateJsonInputParams.SELLING_RATE.getValue());
        final double spotRate = command.doubleValueofParameterNamed(SpotRateJsonInputParams.SPOTRATE.getValue());
        final LocalDate transactionDate = command.localDateValueOfParameterNamed(SpotRateJsonInputParams.TRANSACTION_DATE.getValue());
		return new mSpotRate(currency_code, buyingRate, sellingRate, spotRate, transactionDate);
	}
    
    
}