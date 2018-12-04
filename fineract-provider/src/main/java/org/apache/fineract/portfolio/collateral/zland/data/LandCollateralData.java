package org.apache.fineract.portfolio.collateral.zland.data;

import java.math.BigDecimal;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
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
    private final CollateralData collateralData;
    private final ProvinceKhmerData provinceKhmerData;
    private final CollateralNameData collateralName;
    private final CollateralNatureData collateralNature;
    public LandCollateralData(Long id, LocalDate dateIssue, String size, BigDecimal oldPrice, Integer numberOfCopy, CodeValueData status,
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
    
    
}
