package org.apache.fineract.accounting.spotrate.data;

import org.joda.time.LocalDate;

public class SpotRateData {
    private int spotRateID;
    private String currency_code;
    private double buyingRate;
    private double sellingRate;
    private double spotRate;
    private LocalDate transactionDate;
    
    public SpotRateData(int spotRateID, String currency_code, double buyingRate, double sellingRate, double spotRate, LocalDate transactionDate) {
        this.spotRateID=spotRateID;
        this.currency_code = currency_code;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
        this.spotRate = spotRate;
        this.transactionDate = transactionDate;
    }
    
    public SpotRateData instance(
            final Integer spotRateID,
            final String currency_code,
            final double buyingRate,
            final double sellingRate,
            final double spotRate,
            final LocalDate transactionDate) {
       return new SpotRateData(spotRateID, currency_code, buyingRate, sellingRate, spotRate, transactionDate);
    }

    
    public Integer getspotRateID() {
        return this.spotRateID;
    }

    
    public void setspotRateID(Integer spotRateID) {
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
    
    
}