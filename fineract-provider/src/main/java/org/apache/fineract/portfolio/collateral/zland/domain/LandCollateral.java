package org.apache.fineract.portfolio.collateral.zland.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.addresskhmer.domain.ProvinceKhmer;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateral;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
import org.joda.time.LocalDate;

import software.amazon.ion.Decimal;

@Entity
@Table(name="m_land_collateral")
public class LandCollateral extends AbstractPersistableCustom<Long> {
    
    @ManyToOne
    @JoinColumn(name="m_loan_collateral_id", nullable = false)
    private LoanCollateral collateral;
    
    @ManyToOne
    @JoinColumn(name="tbl_province_id")
    private ProvinceKhmer provinceKhmer;
    
    @ManyToOne
    @JoinColumn(name="m_loan_collateral_name_id")
    private CollateralName collateralName;
   
    
    @ManyToOne
    @JoinColumn(name="m_loan_collateral_nature_id")
    private CollateralNature collateralNature;
    
    
    @Column(name="date_issue")
    private LocalDate dateIssue;
    
    @Column(name="size")
    private String size;
    
    @Column(name="old_price")
    private Decimal oldPrice;
    
    @Column(name="number_of_copy")
    private Integer NumberOfCopy;
    
    @Column(name="status_enum")
    private CodeValueData status;
    
    @Column(name="detail_location")
    private String detailLocation;
    
    @Column(name="owner_name_1")
    private String ownerName1;
    
    @Column(name="gender_1")
    private CodeValueData gender1;
    
    @Column(name="passport_id_1")
    private String passportId1;
    
    @Column(name="owner_name_2")
    private String ownerName2;
    
    @Column(name="gender_2")
    private CodeValueData gender2;
    
    @Column(name="passport_id_2")
    private String passportId2;
    

    public LandCollateral(LoanCollateral collateral, ProvinceKhmer provinceKhmer, CollateralName collateralName,
            CollateralNature collateralNature, LocalDate dateIssue, String size, Decimal oldPrice, Integer numberOfCopy,
            CodeValueData status, String detailLocation, String ownerName1, CodeValueData gender1, String passportId1, String ownerName2,
            CodeValueData gender2, String passportId2) {
        super();
        this.collateral = collateral;
        this.provinceKhmer = provinceKhmer;
        this.collateralName = collateralName;
        this.collateralNature = collateralNature;
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
}
