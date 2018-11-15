package org.apache.fineract.portfolio.addresskhmer.data;


public class DistrictKhmerData {
    private Integer id;
    private Integer DistrictID;
    private String disDescription;
    private String Des_In_Khmer;
    private Integer tbl_province_id;
    
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
}