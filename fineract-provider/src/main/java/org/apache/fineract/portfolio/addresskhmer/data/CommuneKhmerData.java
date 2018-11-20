package org.apache.fineract.portfolio.addresskhmer.data;


public class CommuneKhmerData {
    private Integer id;
    private Integer CommuneID;
    private String comDescription;
    private String Des_In_Khmer;
    private Integer tbl_district_id;
    
    public CommuneKhmerData(
            Integer id,
            Integer communeID, 
            String comDescription, 
            String des_In_Khmer,
            Integer tbl_district_id) {
        this.id = id;
        this.CommuneID = communeID;
        this.comDescription = comDescription;
        this.Des_In_Khmer = des_In_Khmer;
        this.tbl_district_id = tbl_district_id;
    }
    
    public CommuneKhmerData instance(
            final Integer id,
            final Integer communeID,
            final String comDescription,
            final String des_In_Khmer,
            final Integer tbl_district_id) {
       return new CommuneKhmerData( id, communeID, comDescription, des_In_Khmer, tbl_district_id);
    }

    
    public Integer getId() {
        return this.id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getCommuneID() {
        return this.CommuneID;
    }

    
    public void setCommuneID(Integer communeID) {
        this.CommuneID = communeID;
    }

    
    public String getComDescription() {
        return this.comDescription;
    }

    
    public void setComDescription(String comDescription) {
        this.comDescription = comDescription;
    }

    
    public String getDes_In_Khmer() {
        return this.Des_In_Khmer;
    }

    
    public void setDes_In_Khmer(String des_In_Khmer) {
        this.Des_In_Khmer = des_In_Khmer;
    }

    
    public Integer getTbl_district_id() {
        return this.tbl_district_id;
    }

    
    public void setTbl_district_id(Integer tbl_district_id) {
        this.tbl_district_id = tbl_district_id;
    }
    
}
