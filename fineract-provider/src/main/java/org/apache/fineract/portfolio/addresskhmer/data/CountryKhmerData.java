package org.apache.fineract.portfolio.addresskhmer.data;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;

public class CountryKhmerData {
    
    private Integer Id;
    
    private Integer CountryID;

    private String Des_In_Khmer;

    private String Country;
    
    public CountryKhmerData(Integer Id, Integer CountryID, String des_In_Khmer, String country) {
        this.Id = Id;
        this.CountryID = CountryID;
        this.Des_In_Khmer = des_In_Khmer;
        this.Country = country;
    }

    public static CountryKhmerData instance(
            final Integer Id,
            final Integer CountryID,
            final String Des_In_Khmer,
            final String Country
            ) {
        
        return new CountryKhmerData(
                Id,
                CountryID,
                Des_In_Khmer,
                Country);
    }
}