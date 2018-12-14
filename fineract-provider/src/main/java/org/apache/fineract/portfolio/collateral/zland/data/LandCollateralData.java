package org.apache.fineract.portfolio.collateral.zland.data;

import java.math.BigDecimal;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.portfolio.addresskhmer.data.ProvinceKhmerData;
import org.apache.fineract.portfolio.collateral.data.CollateralData;
import org.apache.poi.ss.formula.ptg.TblPtg;
import org.joda.time.LocalDate;

import software.amazon.ion.Decimal;

public class LandCollateralData {
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
    private final ProvinceKhmerData provinceKhmerData;
    private final CodeValueData collateralName;
    private final CodeValueData collateralNature;
    public LandCollateralData(Long id, LocalDate dateIssue, String size, BigDecimal oldPrice, Integer numberOfCopy, CodeValueData status,
            String detailLocation, String ownerName1, CodeValueData gender1, String passportId1, String ownerName2, CodeValueData gender2,
            String passportId2,ProvinceKhmerData provinceKhmerData, CodeValueData collateralName,
            CodeValueData collateralNature) {
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
        this.provinceKhmerData = provinceKhmerData;
        this.collateralName = collateralName;
        this.collateralNature = collateralNature;
    }
    
    public static LandCollateralData instance(
            final Long id,
            final LocalDate dateIssue,
            final String size,
            final BigDecimal oldPrice,
            final Integer numberOfCopy,
            final CodeValueData status,
            final String detailLocation,
            final String ownerName1,
            final CodeValueData gender1,
            final String passportId1,
            final String ownerName2,
            final CodeValueData gender2,
            final String passportId2,
            final ProvinceKhmerData provinceKhmerData,
            final CodeValueData collateralName,
            final CodeValueData collateralNature) {
        return new LandCollateralData(id, dateIssue, size, oldPrice, numberOfCopy, status, detailLocation, ownerName1, gender1, passportId1, ownerName2, gender2, passportId2, provinceKhmerData, collateralName, collateralNature);
    }
    
    
}
