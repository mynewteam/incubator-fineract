package org.apache.fineract.portfolio.addresskhmer.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class CountryNotFoundException  extends AbstractPlatformResourceNotFoundException{

    public CountryNotFoundException(final Long id) {
        super("error.msg.address.country.id.invalid", "Country with identifier "+id+" does not exist", id);
    }
}
