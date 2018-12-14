package org.apache.fineract.portfolio.addresskhmer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.addresskhmer.data.ProvinceKhmerData;

@Entity
@Table(name = "tbl_province")
public class ProvinceKhmer {
    
    @Column(name = "id")
    private Integer Id;
        
    @Column(name = "ProDescription")
    private String ProDescription;
    
    @Column(name = "Des_In_Khmer")
    private String Des_In_Khmer;
    
    
    public ProvinceKhmerData toData() {
        return ProvinceKhmerData.instance(this.Id, this.ProDescription, this.Des_In_Khmer, null);
    }
    
    
}
