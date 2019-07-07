package org.apache.fineract.portfolio.outward.data;

public class DebitorName {
    private Long id;
    private String name;
    private String des;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

	public DebitorName(Long id, String name, String des) {
		this.id = id;
		this.name = name;
		this.des = des;
	}

    
}
