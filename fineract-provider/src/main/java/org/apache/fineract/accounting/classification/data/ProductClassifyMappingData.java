package org.apache.fineract.accounting.classification.data;

public class ProductClassifyMappingData {
	private String Description;
	private Integer Min_Aging;
	private Integer Max_Aging;
	private Integer AccDrId;
	private Integer AccCrId;
	private Integer Type;
	private Integer ClassAcc;
	private Long ProductMappingId;
	public ProductClassifyMappingData() {
		super();
	}
	public ProductClassifyMappingData(String description, Integer min_Aging, Integer max_Aging, Integer accDrId,
			Integer accCrId, Integer type, Integer classAcc, Long productMappingId) {
		super();
		Description = description;
		Min_Aging = min_Aging;
		Max_Aging = max_Aging;
		AccDrId = accDrId;
		AccCrId = accCrId;
		Type = type;
		ClassAcc = classAcc;
		ProductMappingId = productMappingId;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public Integer getMin_Aging() {
		return Min_Aging;
	}
	public void setMin_Aging(Integer min_Aging) {
		Min_Aging = min_Aging;
	}
	public Integer getMax_Aging() {
		return Max_Aging;
	}
	public void setMax_Aging(Integer max_Aging) {
		Max_Aging = max_Aging;
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
	public Integer getClassAcc() {
		return ClassAcc;
	}
	public void setClassAcc(Integer classAcc) {
		ClassAcc = classAcc;
	}
	public Long getProductMappingId() {
		return ProductMappingId;
	}
	public void setProductMappingId(Long productMappingId) {
		ProductMappingId = productMappingId;
	}
	
}
