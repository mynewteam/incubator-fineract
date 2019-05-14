package org.apache.fineract.accounting.spotrate.data;

import org.joda.time.LocalDate;
import java.math.BigDecimal;

public class SpotRateData {
    private Long spotRateID;
    private String currency_code;
    private BigDecimal buyingRate;
    private BigDecimal sellingRate;
    private BigDecimal spotRate;
    private LocalDate transactionDate;
    
    public SpotRateData(Long spotRateID, String currency_code, BigDecimal buyingRate, BigDecimal sellingRate, BigDecimal spotRate, LocalDate transactionDate) {
        this.spotRateID=spotRateID;
        this.currency_code = currency_code;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
        this.spotRate = spotRate;
        this.transactionDate = transactionDate;
    }
    
    public SpotRateData instance(
            final Long spotRateID,
            final String currency_code,
            final BigDecimal buyingRate,
            final BigDecimal sellingRate,
            final BigDecimal spotRate,
            final LocalDate transactionDate) {
       return new SpotRateData(spotRateID, currency_code, buyingRate, sellingRate, spotRate, transactionDate);
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
    
    
}