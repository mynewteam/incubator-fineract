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
    private String currencyCode;
    
    @Column(name = "buying_rate")
    private BigDecimal buyingRate;
    
    @Column(name = "selling_rate")
    private BigDecimal sellingRate;
    
    @Column(name = "spot_rate")
    private BigDecimal spotRate;
    
    @Column(name = "transaction_date")
    private Date transactionDate;
    

    public SpotRate(String currencyCode, BigDecimal buyingRate, BigDecimal sellingRate, BigDecimal spotRate,
			LocalDate transactionDate)
	{
		super();
		this.currencyCode = currencyCode;
		this.buyingRate = buyingRate;
		this.sellingRate = sellingRate;
		this.spotRate = spotRate;
		this.transactionDate = transactionDate.toDate();
	}
    

	public String getCurrencyCode()
	{
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}


	public BigDecimal getBuyingRate()
	{
		return buyingRate;
	}


	public void setBuyingRate(BigDecimal buyingRate)
	{
		this.buyingRate = buyingRate;
	}


	public BigDecimal getSellingRate()
	{
		return sellingRate;
	}


	public void setSellingRate(BigDecimal sellingRate)
	{
		this.sellingRate = sellingRate;
	}


	public BigDecimal getSpotRate()
	{
		return spotRate;
	}


	public void setSpotRate(BigDecimal spotRate)
	{
		this.spotRate = spotRate;
	}


	public LocalDate gettransactionDate() {
		return (LocalDate) ObjectUtils.defaultIfNull(new LocalDate(this.transactionDate), null);
	}

	public static SpotRate fromJson(JsonCommand command)
	{
		final String currencyCode = command.stringValueOfParameterNamed(SpotRateJsonInputParams.CURRENCY_CODE.getValue());
        final BigDecimal buyingRate = command.bigDecimalValueOfParameterNamed(SpotRateJsonInputParams.BUYING_RATE.getValue());
        final BigDecimal sellingRate = command.bigDecimalValueOfParameterNamed(SpotRateJsonInputParams.SELLING_RATE.getValue());
        final BigDecimal spotRate = command.bigDecimalValueOfParameterNamed(SpotRateJsonInputParams.SPOTRATE.getValue());
        final LocalDate transactionDate = command.localDateValueOfParameterNamed(SpotRateJsonInputParams.TRANSACTION_DATE.getValue());
		return new SpotRate(currencyCode, buyingRate, sellingRate, spotRate, transactionDate);
	}
    
    
}