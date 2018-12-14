package org.apache.fineract.portfolio.collateral.zland.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.portfolio.addresskhmer.data.ProvinceKhmerData;
import org.apache.fineract.portfolio.collateral.api.CollateralApiConstants.COLLATERAL_JSON_INPUT_PARAMS;
import org.apache.fineract.portfolio.collateral.data.CollateralData;
import org.apache.fineract.portfolio.collateral.zland.api.LandCollateralApiConstrants.LAND_COLLATERAL_JSON_INPUT_PARAMS;
import org.joda.time.LocalDate;

public class LandCollateralCommand {

    private final LocalDate dateIssue;
    private final String size;
    private final BigDecimal oldPrice;
    private final Integer NumberOfCopy;
    private final Long status;
    private final String detailLocation;
    private final String ownerName1;
    private final Long gender1;
    private final String passportId1;
    private final String ownerName2;
    private final Long gender2;
    private final String passportId2;
    private final Long collateralId;
    private final Long provinceId;
    private final Long collateralNameId;
    private final Long collateralNatureId;
    
    public LandCollateralCommand(LocalDate dateIssue, String size, BigDecimal oldPrice, Integer numberOfCopy, Long status,
            String detailLocation, String ownerName1, Long gender1, String passportId1, String ownerName2, Long gender2,
            String passportId2, Long collateralId, Long provinceId, Long collateralNameId, Long collateralNatureId) {
       
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
        this.collateralId = collateralId;
        this.provinceId = provinceId;
        this.collateralNameId = collateralNameId;
        this.collateralNatureId = collateralNatureId;
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



    
    public Long getStatus() {
        return this.status;
    }



    
    public String getDetailLocation() {
        return this.detailLocation;
    }



    
    public String getOwnerName1() {
        return this.ownerName1;
    }



    
    public Long getGender1() {
        return this.gender1;
    }



    
    public String getPassportId1() {
        return this.passportId1;
    }



    
    public String getOwnerName2() {
        return this.ownerName2;
    }



    
    public Long getGender2() {
        return this.gender2;
    }



    
    public String getPassportId2() {
        return this.passportId2;
    }



    
    public Long getCollateralId() {
        return this.collateralId;
    }



    
    public Long getProvinceId() {
        return this.provinceId;
    }



    
    public Long getCollateralNameId() {
        return this.collateralNameId;
    }

    
    public Long getCollateralNatureId() {
        return this.collateralNatureId;
    }


    public void validateForCreateAndUpdate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("Landcollateral");
        baseDataValidator.reset().parameter(LAND_COLLATERAL_JSON_INPUT_PARAMS.COLLATERAL_ID.getValue()).value(this.collateralId).notNull().longGreaterThanZero();
        baseDataValidator.reset().parameter(LAND_COLLATERAL_JSON_INPUT_PARAMS.PROVINCE_ID.getValue()).value(this.provinceId).notNull().longGreaterThanZero();
        baseDataValidator.reset().parameter(LAND_COLLATERAL_JSON_INPUT_PARAMS.COLLATERAL_NAME.getValue()).value(this.collateralNameId).notNull().longGreaterThanZero();
        baseDataValidator.reset().parameter(LAND_COLLATERAL_JSON_INPUT_PARAMS.COLLATERAL_NATURE.getValue()).value(this.collateralNatureId).notNull().longGreaterThanZero();
        
        if(!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation error exist.", dataValidationErrors);
        }
    }
    
    
    
}
