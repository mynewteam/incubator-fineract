package org.apache.fineract.portfolio.addresskhmer.data;


public class VillageKhmerData {
    private Integer id;
    private String villDescription;
    private String Des_In_Khmer;
    private Long tbl_commune_id;
    
    public VillageKhmerData(Integer id, String villDescription, String des_In_Khmer, long tbl_commune_id) {
        this.id=id;
        this.villDescription = villDescription;
        this.Des_In_Khmer = des_In_Khmer;
        this.tbl_commune_id = tbl_commune_id;
    }
    
    public VillageKhmerData instance(
            final Integer id,
            final String villDescription,
            final String des_In_Khmer,
            final Integer tbl_commune_id) {
       return new VillageKhmerData(id, villDescription, des_In_Khmer, tbl_commune_id);
    }

    
    public Integer getId() {
        return this.id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getVillDescription() {
        return this.villDescription;
    }

    
    public void setVillDescription(String villDescription) {
        this.villDescription = villDescription;
    }

    
    public String getDes_In_Khmer() {
        return this.Des_In_Khmer;
    }

    
    public void setDes_In_Khmer(String des_In_Khmer) {
        this.Des_In_Khmer = des_In_Khmer;
    }

    
    public long getTbl_commune_id() {
        return this.tbl_commune_id;
    }

    
    public void setTbl_commune_id(long tbl_commune_id) {
        this.tbl_commune_id = tbl_commune_id;
    }
    
    
}