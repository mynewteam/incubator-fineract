package org.apache.fineract.accounting.classification.data;

public class ProductClassifyMappingData {
	private String Description;
	private Integer Agging;
	private Integer AccDrId;
	private Integer AccCrId;
	private Integer Type;
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


	public ProductClassifyMappingData(String description, Integer agging, Integer accDrId, Integer accCrId,
			Integer type, Long productMappingId) {
		Description = description;
		Agging = agging;
		AccDrId = accDrId;
		AccCrId = accCrId;
		Type = type;
		ProductMappingId = productMappingId;
	}

	
}
