package org.apache.fineract.portfolio.addresskhmer.data;


public class ProvinceKhmerData {
    private Integer id;
    private String provinceCode;
    private String ProDescription;
    private String Des_In_Khmer;
    private Integer tbl_country_id;
    
    public ProvinceKhmerData(
            Integer id,
            String proDescription, 
            String des_In_Khmer,
            Integer tbl_country_id) {
        this.id = id;
        this.ProDescription = proDescription;
        this.Des_In_Khmer = des_In_Khmer;
        this.tbl_country_id = tbl_country_id;
    }

    public ProvinceKhmerData instance(
            final Integer id,
            final String proDescription,
            final String des_In_Khmer,
            final Integer tbl_country_id) {
        
        return new ProvinceKhmerData(
                id,
                proDescription,
                des_In_Khmer, 
                tbl_country_id);
    }

    
    public Integer getId() {
        return this.id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getProvinceCode() {
        return this.provinceCode;
    }

    
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    
    public String getProDescription() {
        return this.ProDescription;
    }

    
    public void setProDescription(String proDescription) {
        this.ProDescription = proDescription;
    }

    
    public String getDes_In_Khmer() {
        return this.Des_In_Khmer;
    }

    
    public void setDes_In_Khmer(String des_In_Khmer) {
        this.Des_In_Khmer = des_In_Khmer;
    }

    
    public long getTbl_country_id() {
        return this.tbl_country_id;
    }

    
    public void setTbl_country_id(Integer tbl_country_id) {
        this.tbl_country_id = tbl_country_id;
    }
    
   
    
}
