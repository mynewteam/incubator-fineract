package org.apache.fineract.portfolio.addresskhmer.data;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;

public class CountryKhmerData {
    
    private String country_code;

    private String description_khmer;

    private String description;
    
    
    
    public CountryKhmerData(String country_code, String description_khmer, String description) {
        super();
        this.country_code = country_code;
        this.description_khmer = description_khmer;
        this.description = description;
    }



    public static CountryKhmerData instance(
            final String countryCode,
            final String descriptionKhmer,
            final String description) {
        
        return new CountryKhmerData(
                countryCode,
                descriptionKhmer,
                description);
    }
    
}