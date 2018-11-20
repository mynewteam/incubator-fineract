package org.apache.fineract.portfolio.addresskhmer.data;


public class DistrictKhmerData {
    private Integer id;
    private Integer DistrictID;
    private String disDescription;
    private String Des_In_Khmer;
    private long tbl_province_id;
    
    public DistrictKhmerData(
            Integer id,
            Integer districtID, 
            String disDescription, 
            String des_In_Khmer,
            Integer tbl_province_id) {
       this.id = id;
        this.DistrictID = districtID;
        this.disDescription = disDescription;
        this.Des_In_Khmer = des_In_Khmer;
        this.tbl_province_id = tbl_province_id;
    }
    
    public DistrictKhmerData instance (
            final Integer id,
            final Integer districtID, 
            final String disDescription, 
            final String des_In_Khmer,
            final Integer tbl_province_id) {

       return new DistrictKhmerData(
               id,
               districtID,
               disDescription,
               des_In_Khmer,
               tbl_province_id);
    }

    
    public Integer getId() {
        return this.id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getDistrictID() {
        return this.DistrictID;
    }

    
    public void setDistrictID(Integer districtID) {
        this.DistrictID = districtID;
    }

    
    public String getDisDescription() {
        return this.disDescription;
    }

    
    public void setDisDescription(String disDescription) {
        this.disDescription = disDescription;
    }

    
    public String getDes_In_Khmer() {
        return this.Des_In_Khmer;
    }

    
    public void setDes_In_Khmer(String des_In_Khmer) {
        this.Des_In_Khmer = des_In_Khmer;
    }

    
    public long getTbl_province_id() {
        return this.tbl_province_id;
    }

    
    public void setTbl_province_id(long tbl_province_id) {
        this.tbl_province_id = tbl_province_id;
    }
    
    
}