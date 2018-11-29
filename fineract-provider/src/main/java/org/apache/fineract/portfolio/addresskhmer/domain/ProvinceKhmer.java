package org.apache.fineract.portfolio.addresskhmer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "tbl_province")
public class ProvinceKhmer {
    
    @Column(name = "id")
    private Integer Id;
    
    @Column(name = "ProvinceCode")
    private String ProvinceCode;
    
    @Column(name = "ProDescription")
    private String ProDescription;
    
    @Column(name = "Des_In_Khmer")
    private String Des_In_Khmer;
    
    
    
    
    
}
