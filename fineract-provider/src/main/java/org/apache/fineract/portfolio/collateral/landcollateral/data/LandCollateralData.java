package org.apache.fineract.portfolio.collateral.landcollateral.data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

import org.apache.fineract.portfolio.collateral.landcollateral.domain.CollateralName;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.CollateralStatus;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.NaturalCollateral;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.Province;
import org.joda.time.LocalDate;

public class LandCollateralData {

    private final Long id;
    private final Long collateralId;
    private final String name;
    private final Date dateIssue;
    private final String natural;
    private final String size;
    private final BigDecimal oldPrice;
    private final BigDecimal price;
    private final String province;
    private final Integer numberOfCopy;
    private final Integer status;
    private final String detailLocation;
    private final String ownerName1;
    private final Integer gender1;
    private final String passportid1;
    private final String ownerName2;
    private final Integer gender2;
    private final String passportid2;
    
    private LandCollateralData(
            final Long id, 
            final String name, 
            final Long collateralId, 
            final Date dateIssue,
            final String natural, 
            final String size, 
            final BigDecimal oldPrice, 
            final BigDecimal price,
            final String province, 
            final Integer numberOfCopy, 
            final Integer status,
            final String detailLocation, 
            final String ownerName1, 
            final Integer gender1, 
            final String passportid1, 
            final String ownerName2,
            final Integer gender2, 
            final String passportid2) {

        this.id = id;
        this.name = name;
        this.collateralId = collateralId;
        this.dateIssue = dateIssue;
        this.natural = natural;
        this.size = size;
        this.oldPrice = oldPrice;
        this.price = price;
        this.province = province;
        this.numberOfCopy = numberOfCopy;
        this.status = status;
        this.detailLocation = detailLocation;
        this.ownerName1 = ownerName1;
        this.gender1 = gender1;
        this.passportid1 = passportid1;
        this.ownerName2 = ownerName2;
        this.gender2 = gender2;
        this.passportid2 = passportid2;
    }

   public static LandCollateralData instance( 
           final Long id, 
           final String name, 
           final Long collateralId, 
           final Date dateIssue,
           final String natural, 
           final String size, 
           final BigDecimal oldPrice, 
           final BigDecimal price,
           final String province, 
           final Integer numberOfCopy, 
           final Integer status,
           final String detailLocation, 
           final String ownerName1, 
           final Integer gender1, 
           final String passportid1, 
           final String ownerName2,
           final Integer gender2, 
           final String passportid2) {
    return new LandCollateralData(id, name, collateralId, dateIssue, natural, size, oldPrice, price, province, numberOfCopy, status, detailLocation, ownerName1, gender1, passportid1, ownerName2, gender2, passportid2);
       
   }
    
    
    public Long getId() {
        return this.id;
    }

    
    public Long getCollateralId() {
        return this.collateralId;
    }

    
    public String getName() {
        return this.name;
    }

    
    public Date getDateIssue() {
        return this.dateIssue;
    }

    
    public String getNatural() {
        return this.natural;
    }

    
    public String getSize() {
        return this.size;
    }

    
    public BigDecimal getOldPrice() {
        return this.oldPrice;
    }

    
    public BigDecimal getPrice() {
        return this.price;
    }

    
    public String getProvince() {
        return this.province;
    }

    
    public Integer getNumberOfCopy() {
        return this.numberOfCopy;
    }

    
    public Integer getStatus() {
        return this.status;
    }

    
    public String getDetailLocation() {
        return this.detailLocation;
    }

    
    public String getOwnerName1() {
        return this.ownerName1;
    }

    
    public Integer getGender1() {
        return this.gender1;
    }

    
    public String getPassportid1() {
        return this.passportid1;
    }

    
    public String getOwnerName2() {
        return this.ownerName2;
    }

    
    public Integer getGender2() {
        return this.gender2;
    }

    
    public String getPassportid2() {
        return this.passportid2;
    }
    
    
}
