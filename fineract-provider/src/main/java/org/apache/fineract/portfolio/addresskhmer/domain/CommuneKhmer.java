package org.apache.fineract.portfolio.addresskhmer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_commune")
public class CommuneKhmer {
    
    @Column(name = "CommuneID")
    private Integer CommuneID;

    @Column(name = "comDescription")
    private String comDescription;
    
    @Column(name = "Des_In_Khmer")
    private Integer Des_In_Khmer;
    
    
}
