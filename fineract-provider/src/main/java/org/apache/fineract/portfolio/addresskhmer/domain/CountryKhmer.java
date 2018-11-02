package org.apache.fineract.portfolio.addresskhmer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "m_country")
public class CountryKhmer extends AbstractPersistableCustom<Long> {

    @Column(name = "country_code")
    private String country_code;

    @Column(name = "description_khmer")
    private String description_khmer;

    @Column(name = "description")
    private String description;

    public CountryKhmer(String country_code, String description_khmer, String description) {
        this.country_code = country_code;
        this.description_khmer = description_khmer;
        this.description = description;
    }

    public CountryKhmer() {
    }

    public String getCountry_code() {
        return this.country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getDescription_khmer() {
        return this.description_khmer;
    }

    public void setDescription_khmer(String description_khmer) {
        this.description_khmer = description_khmer;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
