package org.apache.fineract.portfolio.collateral.landcollateral.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateral;
import org.joda.time.LocalDate;

@Entity
@Table(name = "m_land_collateral")
public class LandCollateral extends AbstractPersistableCustom<Long> {

	@ManyToOne
	@JoinColumn(name = "collateral_id", nullable = false)
	private LoanCollateral loanCollateral;
	
	@Column(name = "name_enum", length = 100)
	private Integer name;
	
	@Column(name = "date_issue", nullable = true)
	private Date dateIssue;
	
	@Column(name = "natural_enum", length = 100)
	private Integer natural;
	
	@Column(name = "size", nullable = true)
	private String size;
	
	@Column(name = "old_price", scale = 6, precision = 19, nullable = true)
	private BigDecimal oldPrice;
	
	@Column(name = "price", scale = 6, precision = 19, nullable = true)
	private BigDecimal price;
	
	@Column(name = "province_enum", length = 26)
	private Integer province;
	
	@Column(name = "number_of_copy", nullable = true)
	private Integer numberOfCopy;
	
	@Column(name = "status_enum", length = 10)
	private Integer status;
	
	@Column(name = "detail_location", nullable = true)
	private String detailLocation;
	
	@Column(name = "owner_name_1", nullable = true)
	private String ownerName1;
	
	@Column(name = "gender_1", length = 6)
	private Long gender1;
	
	@Column(name = "passport_id_1", nullable = true)
	private String passportid1;
	
	@Column(name = "owner_name_2", nullable = true)
	private String ownerName2;
	
	@Column(name = "gender_2")
	private Long gender2;
	
	@Column(name = "passport_id_2", nullable = true)
	private String passportid2;

    
    public LoanCollateral getLoanCollateral() {
        return this.loanCollateral;
    }

    
    public void setLoanCollateral(LoanCollateral loanCollateral) {
        this.loanCollateral = loanCollateral;
    }

    
    public Integer getName() {
        return this.name;
    }

    
    public void setName(Integer name) {
        this.name = name;
    }

    
    public Date getDateIssue() {
        return this.dateIssue;
    }

    
    public void setDateIssue(Date dateIssue) {
        this.dateIssue = dateIssue;
    }

    
    public Integer getNatural() {
        return this.natural;
    }

    
    public void setNatural(Integer natural) {
        this.natural = natural;
    }

    
    public String getSize() {
        return this.size;
    }

    
    public void setSize(String size) {
        this.size = size;
    }

    
    public BigDecimal getOldPrice() {
        return this.oldPrice;
    }

    
    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    
    public BigDecimal getPrice() {
        return this.price;
    }

    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    
    public Integer getProvince() {
        return this.province;
    }

    
    public void setProvince(Integer province) {
        this.province = province;
    }

    
    public Integer getNumberOfCopy() {
        return this.numberOfCopy;
    }

    
    public void setNumberOfCopy(Integer numberOfCopy) {
        this.numberOfCopy = numberOfCopy;
    }

    
    public Integer getStatus() {
        return this.status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    
    public String getDetailLocation() {
        return this.detailLocation;
    }

    
    public void setDetailLocation(String detailLocation) {
        this.detailLocation = detailLocation;
    }

    
    public String getOwnerName1() {
        return this.ownerName1;
    }

    
    public void setOwnerName1(String ownerName1) {
        this.ownerName1 = ownerName1;
    }

    
    public Long getGender1() {
        return this.gender1;
    }

    
    public void setGender1(Long gender1) {
        this.gender1 = gender1;
    }

    
    public String getPassportid1() {
        return this.passportid1;
    }

    
    public void setPassportid1(String passportid1) {
        this.passportid1 = passportid1;
    }

    
    public String getOwnerName2() {
        return this.ownerName2;
    }

    
    public void setOwnerName2(String ownerName2) {
        this.ownerName2 = ownerName2;
    }

    
    public Long getGender2() {
        return this.gender2;
    }

    
    public void setGender2(Long gender2) {
        this.gender2 = gender2;
    }

    
    public String getPassportid2() {
        return this.passportid2;
    }

    
    public void setPassportid2(String passportid2) {
        this.passportid2 = passportid2;
    }
	
	
}
