package org.apache.fineract.portfolio.addresskhmer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_village")
public class VillageKhmer {
   
    @Column(name = "VillageID")
    private String VillageID;
    
    @Column(name = "villDescription")
    private String villDescription;
    
    @Column(name = "Des_In_Khmer")
    private String Des_In_Khmer;
}