package org.apache.fineract.portfolio.collateral.zland.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.portfolio.addresskhmer.data.ProvinceKhmerData;
import org.apache.fineract.portfolio.collateral.data.CollateralData;
import org.apache.fineract.portfolio.collateral.zland.data.CollateralNameData;
import org.apache.fineract.portfolio.collateral.zland.data.CollateralNatureData;
import org.joda.time.LocalDate;

public class LandCollateralCommand {
    private final Long id;
    private final LocalDate dateIssue;
    private final String size;
    private final BigDecimal oldPrice;
    private final Integer NumberOfCopy;
    private final CodeValueData status;
    private final String detailLocation;
    private final String ownerName1;
    private final CodeValueData gender1;
    private final String passportId1;
    private final String ownerName2;
    private final CodeValueData gender2;
    private final String passportId2;
    private final CollateralData collateralData;
    private final ProvinceKhmerData provinceKhmerData;
    private final CollateralNameData collateralName;
    private final CollateralNatureData collateralNature;
    
    public LandCollateralCommand(Long id, LocalDate dateIssue, String size, BigDecimal oldPrice, Integer numberOfCopy, CodeValueData status,
            String detailLocation, String ownerName1, CodeValueData gender1, String passportId1, String ownerName2, CodeValueData gender2,
            String passportId2, CollateralData collateralData, ProvinceKhmerData provinceKhmerData, CollateralNameData collateralName,
            CollateralNatureData collateralNature) {
        this.id = id;
        this.dateIssue = dateIssue;
        this.size = size;
        this.oldPrice = oldPrice;
        this.NumberOfCopy = numberOfCopy;
        this.status = status;
        this.detailLocation = detailLocation;
        this.ownerName1 = ownerName1;
        this.gender1 = gender1;
        this.passportId1 = passportId1;
        this.ownerName2 = ownerName2;
        this.gender2 = gender2;
        this.passportId2 = passportId2;
        this.collateralData = collateralData;
        this.provinceKhmerData = provinceKhmerData;
        this.collateralName = collateralName;
        this.collateralNature = collateralNature;
    }

    
    public Long getId() {
        return this.id;
    }

    
    public LocalDate getDateIssue() {
        return this.dateIssue;
    }

    
    public String getSize() {
        return this.size;
    }

    
    public BigDecimal getOldPrice() {
        return this.oldPrice;
    }

    
    public Integer getNumberOfCopy() {
        return this.NumberOfCopy;
    }

    
    public CodeValueData getStatus() {
        return this.status;
    }

    
    public String getDetailLocation() {
        return this.detailLocation;
    }

    
    public String getOwnerName1() {
        return this.ownerName1;
    }

    
    public CodeValueData getGender1() {
        return this.gender1;
    }

    
    public String getPassportId1() {
        return this.passportId1;
    }

    
    public String getOwnerName2() {
        return this.ownerName2;
    }

    
    public CodeValueData getGender2() {
        return this.gender2;
    }

    
    public String getPassportId2() {
        return this.passportId2;
    }

    
    public CollateralData getCollateralData() {
        return this.collateralData;
    }

    
    public ProvinceKhmerData getProvinceKhmerData() {
        return this.provinceKhmerData;
    }

    
    public CollateralNameData getCollateralName() {
        return this.collateralName;
    }

    
    public CollateralNatureData getCollateralNature() {
        return this.collateralNature;
    }
    
    public void validateForCreate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("LandCollateral");
        
        
    }
    
    public void validateForUpdate() {
        
    }
    
}
