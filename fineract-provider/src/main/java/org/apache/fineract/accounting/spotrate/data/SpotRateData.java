package org.apache.fineract.accounting.spotrate.data;

import org.joda.time.LocalDate;
import java.math.BigDecimal;

public class SpotRateData {
    private Long id;
    private String currencyCode;
    private BigDecimal buyingRate;
    private BigDecimal sellingRate;
    private BigDecimal spotRate;
    private LocalDate transactionDate;
    
    public SpotRateData(Long id, String currencyCode, BigDecimal buyingRate, BigDecimal sellingRate, BigDecimal spotRate, LocalDate transactionDate) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
        this.spotRate = spotRate;
        this.transactionDate = transactionDate;
    }
    
    public SpotRateData instance(
            final Long id,
            final String currency_code,
            final BigDecimal buyingRate,
            final BigDecimal sellingRate,
            final BigDecimal spotRate,
            final LocalDate transactionDate) {
       return new SpotRateData(id, currency_code, buyingRate, sellingRate, spotRate, transactionDate);
    }

    
    public Long getspotRateID() {
        return this.id;
    }

    
    public void setspotRateID(Long id) {
        this.id = id;
    }

    
    public String getcurrencyCode() {
        return this.currencyCode;
    }

    
    public void setcurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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
    
    
}