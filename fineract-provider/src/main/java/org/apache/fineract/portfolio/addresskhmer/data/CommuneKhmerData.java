package org.apache.fineract.portfolio.addresskhmer.data;


public class CommuneKhmerData {
    
    private Integer CommuneID;
    private String comDescription;
    private String Des_In_Khmer;
    private Integer tbl_district_id;
    
    public CommuneKhmerData(
            Integer communeID, 
            String comDescription, 
            String des_In_Khmer,
            Integer tbl_district_id) {
        
        this.CommuneID = communeID;
        this.comDescription = comDescription;
        this.Des_In_Khmer = des_In_Khmer;
        this.tbl_district_id = tbl_district_id;
    }
    
    public CommuneKhmerData instance(
            final Integer communeID,
            final String comDescription,
            final String des_In_Khmer,
            final Integer tbl_district_id) {
       return new CommuneKhmerData(communeID, comDescription, des_In_Khmer, tbl_district_id);
    }
    
}
