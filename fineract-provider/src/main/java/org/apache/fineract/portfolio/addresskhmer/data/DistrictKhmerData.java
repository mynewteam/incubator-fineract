package org.apache.fineract.portfolio.addresskhmer.data;


public class DistrictKhmerData {

    private Integer DistrictID;
    private String disDescription;
    private String Des_In_Khmer;
    private Integer tbl_province_id;
    
    public DistrictKhmerData(
            Integer districtID, 
            String disDescription, 
            String des_In_Khmer,
            Integer tbl_province_id) {
       
        this.DistrictID = districtID;
        this.disDescription = disDescription;
        this.Des_In_Khmer = des_In_Khmer;
        this.tbl_province_id = tbl_province_id;
    }
    
    public DistrictKhmerData instance (
            final Integer districtID, 
            final String disDescription, 
            final String des_In_Khmer,
            final Integer tbl_province_id) {

       return new DistrictKhmerData(
               districtID,
               disDescription,
               des_In_Khmer,
               tbl_province_id);
    }
}