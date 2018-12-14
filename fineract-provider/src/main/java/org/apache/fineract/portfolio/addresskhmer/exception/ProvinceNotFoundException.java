package org.apache.fineract.portfolio.addresskhmer.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class ProvinceNotFoundException extends AbstractPlatformResourceNotFoundException {

    public ProvinceNotFoundException(final Long id) {
        super("error.msg.address.province.id.invalid", "Province with identifier "+id+" does not exist", id);
    }

}
