package org.apache.fineract.portfolio.outward.data;

public class CheckType {

	private Long id;
	private String type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public CheckType(Long id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	
}
