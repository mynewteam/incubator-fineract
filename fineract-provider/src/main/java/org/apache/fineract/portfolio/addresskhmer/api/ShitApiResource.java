package org.apache.fineract.portfolio.addresskhmer.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.addresskhmer.data.CountryKhmerData;
import org.apache.fineract.portfolio.addresskhmer.domain.CountryKhmer;
import org.apache.fineract.portfolio.addresskhmer.service.AddressKhmerRreadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/helloworld")
@Component
@Scope("singleton")
public class ShitApiResource {

    private final DefaultToApiJsonSerializer<CountryKhmer> toApiJsonSerializer;
    private final AddressKhmerRreadPlatformService readPlatformService;
    // private final PlatformSecurityContext context;

    @Autowired
    public ShitApiResource(final DefaultToApiJsonSerializer<CountryKhmer> toApiJsonSerializer,
            final AddressKhmerRreadPlatformService readPlatformService
    // final PlatformSecurityContext context
    ) {
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.readPlatformService = readPlatformService;
        // this.context = context;
    }

    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveAllCountry() {

        final Collection<CountryKhmerData> countrykh = this.readPlatformService.retrieveAllCountry();
         return this.toApiJsonSerializer.serialize(countrykh);

//        return "hello world";
    }
}
