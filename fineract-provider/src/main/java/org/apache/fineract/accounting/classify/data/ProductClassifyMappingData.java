package org.apache.fineract.accounting.classify.data;

public class ProductClassifyMappingData {
	
	private String description;
	private Integer minAging;
	private Integer maxAging;
	private Integer accDrId;
	private Integer accCrId;
	private Integer type;
	private Integer classAcc;
	private Long productMappingId;

	public ProductClassifyMappingData() {
		super();
	}

	public ProductClassifyMappingData(String description, Integer minAging, Integer maxAging, Integer accDrId,
			Integer accCrId, Integer type, Integer classAcc, Long productMappingId) {
		
		super();
		
		this.description = description;
		this.minAging = minAging;
		this.maxAging = maxAging;
		this.accDrId = accDrId;
		this.accCrId = accCrId;
		this.type = type;
		this.classAcc = classAcc;
		this.productMappingId = productMappingId;
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMinAging() {
		return minAging;
	}

	public void setMinAging(Integer minAging) {
		this.minAging = minAging;
	}

	public Integer getMaxAging() {
		return maxAging;
	}

	public void setMaxAging(Integer maxAging) {
		this.maxAging = maxAging;
	}

	public Integer getAccDrId() {
		return accDrId;
	}

	public void setAccDrId(Integer accDrId) {
		this.accDrId = accDrId;
	}

	public Integer getAccCrId() {
		return accCrId;
	}

	public void setAccCrId(Integer accCrId) {
		this.accCrId = accCrId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getClassAcc() {
		return classAcc;
	}

	public void setClassAcc(Integer classAcc) {
		this.classAcc = classAcc;
	}

	public Long getProductMappingId() {
		return productMappingId;
	}

	public void setProductMappingId(Long productMappingId) {
		this.productMappingId = productMappingId;
	}

}
