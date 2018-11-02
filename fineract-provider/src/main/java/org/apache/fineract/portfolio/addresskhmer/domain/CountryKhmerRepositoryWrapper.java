package org.apache.fineract.portfolio.addresskhmer.domain;

import org.apache.fineract.portfolio.addresskhmer.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class CountryKhmerRepositoryWrapper {
    
    private final CountryKhmerRepository repository;

    @Autowired
    public CountryKhmerRepositoryWrapper(CountryKhmerRepository repository) {
        this.repository = repository;
    }
    
    public CountryKhmer findOneWithNotfoundDetection(final Long id) {
        final CountryKhmer coundefinition = this.repository.findOne(id);
        if(coundefinition == null) {
            throw new CountryNotFoundException(id);
        }
        return coundefinition;
    }
    
    

}
