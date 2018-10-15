package org.apache.fineract.portfolio.collateral.landcollateral.data;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.fineract.portfolio.collateral.landcollateral.domain.CollateralName;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.CollateralStatus;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.NaturalCollateral;
import org.apache.fineract.portfolio.collateral.landcollateral.domain.Province;
import org.joda.time.LocalDate;

public class LandCollateralData {
	private final Long id;
	private final Long collateralId;
	private final Collection<CollateralName> name;
	private final LocalDate dateIssue;
	private final Collection<NaturalCollateral> natural;
	private final String size;
	private final BigDecimal oldPrice;
	private final BigDecimal price;
	private final Collection<Province> province;
	private final Integer numberOfCopy;
	private final Collection<CollateralStatus> status;
	private final String detailLocation;
	private final String ownerName1;
	private final Long gender1;
	private final String passportid1;
	private final String ownerName2;
	private final Long gender2;
	private final String passportid2;
	
	public LandCollateralData(Long id, Collection<CollateralName> name, Long collateralId, LocalDate dateIssue,
			Collection<NaturalCollateral> natural, String size, BigDecimal oldPrice, BigDecimal price,
			Collection<Province> province, Integer numberOfCopy, Collection<CollateralStatus> status,
			String detailLocation, String ownerName1, Long gender1, String passportid1, String ownerName2, Long gender2,
			String passportid2) {
		super();
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

	public Long getId() {
		return id;
	}

	public Collection<CollateralName> getName() {
		return name;
	}

	public Long getCollateralId() {
		return collateralId;
	}

	public LocalDate getDateIssue() {
		return dateIssue;
	}

	public Collection<NaturalCollateral> getNatural() {
		return natural;
	}

	public String getSize() {
		return size;
	}

	public BigDecimal getOldPrice() {
		return oldPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Collection<Province> getProvince() {
		return province;
	}

	public Integer getNumberOfCopy() {
		return numberOfCopy;
	}

	public Collection<CollateralStatus> getStatus() {
		return status;
	}

	public String getDetailLocation() {
		return detailLocation;
	}

	public String getOwnerName1() {
		return ownerName1;
	}

	public Long getGender1() {
		return gender1;
	}

	public String getPassportid1() {
		return passportid1;
	}

	public String getOwnerName2() {
		return ownerName2;
	}

	public Long getGender2() {
		return gender2;
	}

	public String getPassportid2() {
		return passportid2;
	}
	
	
	
}
