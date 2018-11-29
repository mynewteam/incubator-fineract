package org.apache.fineract.portfolio.addresskhmer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "tbl_village")
public class VillageKhmer  {
   
    @Column(name = "id")
    private Long Id;
    
    @Column(name = "villDescription")
    private String villDescription;
    
    @Column(name = "Des_In_Khmer")
    private String Des_In_Khmer;

    
    
    
    public Long getId() {
        return this.Id;
    }


    
    public void setId(Long id) {
        this.Id = id;
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
    
    
}