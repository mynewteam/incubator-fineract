package org.apache.fineract.portfolio.addresskhmer.data;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;

public class CountryKhmerData {
    
    private String countryCode;

    private String Des_In_Khmer;

    private String Country;
    
    
    
    public CountryKhmerData(String countryCode, String des_In_Khmer, String country) {
        this.countryCode = countryCode;
        this.Des_In_Khmer = des_In_Khmer;
        this.Country = country;
    }




    public static CountryKhmerData instance(
            final String countryCode,
            final String Des_In_Khmer,
            final String Country) {
        
        return new CountryKhmerData(
                countryCode,
                Des_In_Khmer,
                Country);
    }
    
}