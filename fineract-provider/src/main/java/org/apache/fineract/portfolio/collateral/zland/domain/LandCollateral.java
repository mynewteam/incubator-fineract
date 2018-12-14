package org.apache.fineract.portfolio.collateral.zland.domain;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
import org.apache.fineract.portfolio.addresskhmer.data.ProvinceKhmerData;
import org.apache.fineract.portfolio.addresskhmer.domain.ProvinceKhmer;
import org.apache.fineract.portfolio.collateral.data.CollateralData;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateral;
import org.apache.fineract.portfolio.collateral.zland.api.LandCollateralApiConstrants.LAND_COLLATERAL_JSON_INPUT_PARAMS;
import org.apache.fineract.portfolio.collateral.zland.data.LandCollateralData;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.joda.time.LocalDate;

@Entity
@Table(name = "m_land_collateral")
public class LandCollateral extends AbstractPersistableCustom<Long> {

    @ManyToOne
    @JoinColumn(name = "m_loan_id", nullable = false)
    private Loan loan;

    @ManyToOne
    @JoinColumn(name = "tbl_province_id")
    private ProvinceKhmer provinceKhmer;

    @Column(name = "date_issue")
    private LocalDate dateIssue;

    @Column(name = "size")
    private String size;

    @Column(name = "old_price")
    private BigDecimal oldPrice;

    @Column(name = "number_of_copy")
    private Integer NumberOfCopy;

    @Column(name = "status_enum")
    private CodeValue status;

    @Column(name = "detail_location")
    private String detailLocation;

    @Column(name = "owner_name_1")
    private String ownerName1;

    @Column(name = "gender_1")
    private CodeValue gender1;

    @Column(name = "passport_id_1")
    private String passportId1;

    @Column(name = "owner_name_2")
    private String ownerName2;

    @Column(name = "gender_2")
    private CodeValue gender2;

    @Column(name = "passport_id_2")
    private String passportId2;

    @Column(name = "collateral_name")
    private CodeValue collateralName;

    @Column(name = "collateral_nature")
    private CodeValue CollateralNature;

    public LandCollateral(Loan loan, ProvinceKhmer provinceKhmer, CodeValue collateralName, CodeValue collateralNature, LocalDate dateIssue,
            String size, BigDecimal oldPrice, Integer numberOfCopy, CodeValue status, String detailLocation, String ownerName1,
            CodeValue gender1, String passportId1, String ownerName2, CodeValue gender2, String passportId2) {
        this.loan = loan;
        this.provinceKhmer = provinceKhmer;
        this.collateralName = collateralName;
        this.CollateralNature = collateralNature;
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
    }

    public void associateWith(final Loan loan) {
        this.loan = loan;
    }

    public LandCollateralData toData() {
        final CodeValueData status = this.status.toData();
        final CodeValueData gender1 = this.gender1.toData();
        final CodeValueData gender2 = this.gender2.toData();
        final CodeValueData collateralName = this.collateralName.toData();
        final CodeValueData collateralNature = this.CollateralNature.toData();
        final ProvinceKhmerData provinceKhmer = this.provinceKhmer.toData();
        return LandCollateralData.instance(getId(), this.dateIssue, this.size, this.oldPrice, this.NumberOfCopy, status,
                this.detailLocation, this.ownerName1, gender1, this.passportId1, this.ownerName2, gender2, this.passportId2,
                provinceKhmer, collateralName, collateralNature);
    }

    public static LandCollateral from(
            ProvinceKhmer provinceKhmer, 
            CodeValue collateralName, 
            CodeValue collateralNature,
            LocalDate dateIssue, 
            String size, 
            BigDecimal oldPrice, 
            Integer numberOfCopy, 
            CodeValue status, 
            String detailLocation,
            String ownerName1, 
            CodeValue gender1, 
            String passportId1, 
            String ownerName2, 
            CodeValue gender2,
            String passportId2) {
        return new LandCollateral(null, provinceKhmer, collateralName, collateralNature, dateIssue, size, oldPrice, numberOfCopy, status,
                detailLocation, ownerName1, gender1, passportId1, ownerName2, gender2, passportId2);
    }

    public static LandCollateral instance(Loan loan, ProvinceKhmer provinceKhmer, CodeValue collateralName, CodeValue collateralNature,
            LocalDate dateIssue, String size, BigDecimal oldPrice, Integer numberOfCopy, CodeValue status, String detailLocation,
            String ownerName1, CodeValue gender1, String passportId1, String ownerName2, CodeValue gender2, String passportId2) {
        return new LandCollateral(loan, provinceKhmer, collateralName, collateralNature, dateIssue, size, oldPrice, numberOfCopy, status,
                detailLocation, ownerName1, gender1, passportId1, ownerName2, gender2, passportId2);
    }

    public void assembleFrom(final ProvinceKhmer provinceKhmer, final CodeValue collateralName,
            final CodeValue collateralNature, final LocalDate dateIssue, final String size, final BigDecimal oldPrice,
            final Integer numberOfCopy, final CodeValue status, final String detailLocation, final String ownerName1,
            final CodeValue gender1, final String passportId1, final String ownerName2, final CodeValue gender2, final String passportId2) {
        this.provinceKhmer = provinceKhmer;
        this.collateralName = collateralName;
        this.CollateralNature = collateralNature;
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
    }

    public static LandCollateral fromJson(Loan loan, ProvinceKhmer provinceKhmer, CodeValue collateralName, CodeValue collateralNature,
            CodeValue status, CodeValue gender1, CodeValue gender2, final JsonCommand command) {

        final LocalDate dateIssue = command.localDateValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.DATE_ISSUE.getValue());
        final String size = command.stringValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.SIZE.getValue());
        final BigDecimal oldPrice = command.bigDecimalValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.OLD_PRICE.getValue());
        final Integer numberOfCopy = command.integerValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.NUMBER_OF_COPY.getValue());
        // final Integer status =
        // command.integerValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.STATUS.getValue());
        final String detailLocation = command.stringValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.DETAIL_LOCATION.getValue());
        final String ownerName1 = command.stringValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.OWNER_NAME_1.getValue());
        // final Integer gender1 =
        // command.integerValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.GENER_1.getValue());
        final String passportId1 = command.stringValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.PASSPORT_ID_1.getValue());
        final String ownerName2 = command.stringValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.OWNER_NAME_2.getValue());
        // final Integer gender2 =
        // command.integerValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.GENDER_2.getValue());
        final String passportId2 = command.stringValueOfParameterNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.PASSPORT_ID_2.getValue());

        // return new LandCollateral(collateral, provinceKhmer, collateralName,
        // collateralNature, dateIssue, size, oldPrice, numberOfCopy, status,
        // detailLocation, ownerName1, gender1, passportId1, ownerName2,
        // gender2, passportId2);
        return new LandCollateral(loan, provinceKhmer, collateralName, collateralNature, dateIssue, size, oldPrice, numberOfCopy, status,
                detailLocation, ownerName1, gender1, passportId1, ownerName2, gender2, passportId2);
    }

    public void initializeLazyCollections() {

    }

}
