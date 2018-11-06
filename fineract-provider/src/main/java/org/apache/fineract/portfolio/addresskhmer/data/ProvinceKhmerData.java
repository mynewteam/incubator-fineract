package org.apache.fineract.portfolio.addresskhmer.data;


public class ProvinceKhmerData {
    private Integer ProvinceID;
    private String provinceCode;
    private String ProDescription;
    private String Des_In_Khmer;
    private Integer tbl_country_id;
    
    public ProvinceKhmerData(
            Integer provinceID, 
            String provinceCode, 
            String proDescription, 
            String des_In_Khmer,
            Integer tbl_country_id) {
    
        this.ProvinceID = provinceID;
        this.provinceCode = provinceCode;
        this.ProDescription = proDescription;
        this.Des_In_Khmer = des_In_Khmer;
        this.tbl_country_id = tbl_country_id;
    }

    public ProvinceKhmerData instance(
            final Integer provinceID, 
            final String provinceCode,
            final String proDescription,
            final String des_In_Khmer,
            final Integer tbl_country_id) {
        
        return new ProvinceKhmerData(
                provinceID,
                provinceCode,
                proDescription,
                des_In_Khmer, 
                tbl_country_id);
    }
    
   
    
}
