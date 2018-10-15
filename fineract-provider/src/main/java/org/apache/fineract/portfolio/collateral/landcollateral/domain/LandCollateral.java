package org.apache.fineract.portfolio.collateral.landcollateral.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateral;
import org.joda.time.LocalDate;

@Entity
@Table(name = "m_land_collateral")
public class LandCollateral extends AbstractPersistableCustom<Long> {

	@ManyToOne(optional = false)
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
}
