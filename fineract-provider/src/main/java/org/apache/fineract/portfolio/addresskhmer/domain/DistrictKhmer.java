package org.apache.fineract.portfolio.addresskhmer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "tbl_district")
public class DistrictKhmer extends AbstractPersistableCustom<Long> {
    
    @Column(name = "DistrictID")
    private Integer DistrictID;
    
    @Column(name = "disDescription")
    private String disDescription;
    
    @Column(name = "Des_In_Khmer")
    private String Des_In_Khmer;
    
}
