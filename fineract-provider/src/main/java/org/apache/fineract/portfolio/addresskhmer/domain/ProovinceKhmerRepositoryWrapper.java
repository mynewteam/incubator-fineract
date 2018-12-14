package org.apache.fineract.portfolio.addresskhmer.domain;

import org.apache.fineract.portfolio.addresskhmer.exception.ProvinceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProovinceKhmerRepositoryWrapper  {

    private final ProvinceKhmerRepository repository;

    @Autowired
    public ProovinceKhmerRepositoryWrapper(ProvinceKhmerRepository repository) {
        this.repository = repository;
    }
    
    public ProvinceKhmer findOneWithNotFoundDetection(final Long id) {
        final ProvinceKhmer provinceKhmer = this.repository.findOne(id);
        if(provinceKhmer == null) {
            throw new ProvinceNotFoundException(id);
        }
        return provinceKhmer;
    }    
}
