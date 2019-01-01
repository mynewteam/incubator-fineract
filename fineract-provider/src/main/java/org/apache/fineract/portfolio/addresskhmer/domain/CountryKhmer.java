package org.apache.fineract.portfolio.addresskhmer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "tbl_country")
public class CountryKhmer {
    
    @Column(name = "id")
    private Integer Id;
    
    @Column(name = "Country")
    private String Country;
    
    @Column(name = "Des_In_Khmer")
    private String Des_In_Khmer;
    
    @Column(name = "countryCode")
    private String countryCode;

}
