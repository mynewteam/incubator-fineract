package org.apache.fineract.portfolio.addresskhmer.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class VillageKhmerNotfoundException extends AbstractPlatformResourceNotFoundException {
    public VillageKhmerNotfoundException(final Long id) {
        super("error.msg.address.village.id.invalid", " Village with identifier "+id+" does not exist", id);
    }
}
