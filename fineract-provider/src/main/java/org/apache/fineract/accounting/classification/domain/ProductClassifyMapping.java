package org.apache.fineract.accounting.classification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name="acc_product_classify_mapping")
public class ProductClassifyMapping extends AbstractPersistableCustom<Long>{

	@Column(name="description")
	private String Description;
	
	@Column(name="agging")
	private Integer Agging;
	
	@Column(name="acc_dr_id" , nullable = false)
	private Integer AccDrId;
	
	@Column(name="acc_cr_id", nullable = false)
	private Integer AccCrId;
	
	@Column(name="type")
	private Integer Type;
	
	@ManyToOne
	@JoinColumn(name="acc_product_mapping_id")
	private Long ProductMappingId;

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Integer getAgging() {
		return Agging;
	}

	public void setAgging(Integer agging) {
		Agging = agging;
	}

	public Integer getAccDrId() {
		return AccDrId;
	}

	public void setAccDrId(Integer accDrId) {
		AccDrId = accDrId;
	}

	public Integer getAccCrId() {
		return AccCrId;
	}

	public void setAccCrId(Integer accCrId) {
		AccCrId = accCrId;
	}

	public Integer getType() {
		return Type;
	}

	public void setType(Integer type) {
		Type = type;
	}

	public Long getProductMappingId() {
		return ProductMappingId;
	}

	public void setProductMappingId(Long productMappingId) {
		ProductMappingId = productMappingId;
	}
	
	
}
