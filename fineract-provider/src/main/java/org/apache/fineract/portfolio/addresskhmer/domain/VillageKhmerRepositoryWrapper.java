package org.apache.fineract.portfolio.addresskhmer.domain;

import org.apache.fineract.portfolio.addresskhmer.exception.VillageKhmerNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VillageKhmerRepositoryWrapper {
    
    private final VillageKhmerRepository repository;

    @Autowired
    public VillageKhmerRepositoryWrapper(VillageKhmerRepository repository) {
        this.repository = repository;
    }
    
    
    public VillageKhmer findOneWithNotFoundDetection(final Long id) {
        final VillageKhmer villageKhmer = this.repository.findOne(id);
        if(villageKhmer == null) {
            throw new VillageKhmerNotfoundException(id);
        }
        return villageKhmer;
    }
}
