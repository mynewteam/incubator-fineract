package org.apache.fineract.portfolio.addresskhmer.data;


public class VillageKhmerData {
    private Integer id;
    private Integer VillageID;
    private String villDescription;
    private String Des_In_Khmer;
    private Integer tbl_commune_id;
    
    public VillageKhmerData(Integer id, Integer villageID, String villDescription, String des_In_Khmer, Integer tbl_commune_id) {
        this.id=id;
        this.VillageID = villageID;
        this.villDescription = villDescription;
        this.Des_In_Khmer = des_In_Khmer;
        this.tbl_commune_id = tbl_commune_id;
    }
    
    public VillageKhmerData instance(
            final Integer id,
            final Integer villageID, 
            final String villDescription,
            final String des_In_Khmer,
            final Integer tbl_commune_id) {
       return new VillageKhmerData(id, villageID, villDescription, des_In_Khmer, tbl_commune_id);
    }
}