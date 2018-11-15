package org.apache.fineract.portfolio.addresskhmer.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.addresskhmer.data.CommuneKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.CountryKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.DistrictKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.ProvinceKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.VillageKhmerData;
import org.apache.fineract.portfolio.addresskhmer.domain.CommuneKhmer;
import org.apache.fineract.portfolio.addresskhmer.domain.CountryKhmer;
import org.apache.fineract.portfolio.addresskhmer.service.AddressKhmerRreadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/address")
@Component
@Scope("singleton")
public class ShitApiResource {

    private final DefaultToApiJsonSerializer<CountryKhmer> toApiJsonSerializer;
    private final AddressKhmerRreadPlatformService readPlatformService;

    @Autowired
    public ShitApiResource(final DefaultToApiJsonSerializer<CountryKhmer> toApiJsonSerializer,
            final AddressKhmerRreadPlatformService readPlatformService
    ) {
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.readPlatformService = readPlatformService;
    }

    @GET
    @Path("/country")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String Country() {

        final Collection<CountryKhmerData> countrykh = this.readPlatformService.retrieveAllCountry();
         return this.toApiJsonSerializer.serialize(countrykh);
    }
    
    @GET
    @Path("/country/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String Country(@PathParam("id") final long id) {

        final CountryKhmerData countrykh = this.readPlatformService.retrieveCountry(id);
         return this.toApiJsonSerializer.serialize(countrykh);
    }
    
    @GET
    @Path("/province")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String Province() {

        final Collection<ProvinceKhmerData> ProvinceKH = this.readPlatformService.RetrieveAllProvince();
         return this.toApiJsonSerializer.serialize(ProvinceKH);
    }
    
    @GET
    @Path("/province/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String Profince(@PathParam("id") final long id) {

        final ProvinceKhmerData ProvinceKH = this.readPlatformService.retrieveProvince(id);
         return this.toApiJsonSerializer.serialize(ProvinceKH);
    }
    
    
    @GET
    @Path("/province/country/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String ProvinceByCountry(@PathParam("id") final long id) {

        final Collection<ProvinceKhmerData> ProvinceKH = this.readPlatformService.retrieveProvinceByCountryId(id);
         return this.toApiJsonSerializer.serialize(ProvinceKH);
    }
    
    @GET
    @Path("/district")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String District() {

        final Collection<DistrictKhmerData> DistrictKH = this.readPlatformService.retrieveAllDistrict();
         return this.toApiJsonSerializer.serialize(DistrictKH);
    }
    
    @GET
    @Path("/district/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String District(@PathParam("id") final long id) {

        final DistrictKhmerData DistrictKH = this.readPlatformService.retriveDistrict(id);
         return this.toApiJsonSerializer.serialize(DistrictKH);
    }
    
    @GET
    @Path("/district/province/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String DistrictByProvinceID(@PathParam("id") final long id) {

        final Collection<DistrictKhmerData> DistrictKH = this.readPlatformService.retrieveDistrictByProvinceID(id);
         return this.toApiJsonSerializer.serialize(DistrictKH);
    }
    
    
    @GET
    @Path("/commune")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String Commune() {

        final Collection<CommuneKhmerData> communeKhmerDatas = this.readPlatformService.retrieveAllCommune();
         return this.toApiJsonSerializer.serialize(communeKhmerDatas);
    }
    
    @GET
    @Path("/commune/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String Commune(@PathParam("id") final long id) {

        final CommuneKhmerData communeKhmerData = this.readPlatformService.retrieveCommune(id);
         return this.toApiJsonSerializer.serialize(communeKhmerData);
    }
    
    @GET
    @Path("/commune/district/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String CommuneByDistrict(@PathParam("id") final long id) {

        final Collection<CommuneKhmerData> communeKhmerData = this.readPlatformService.retrieveCommuneByDistrictID(id);
         return this.toApiJsonSerializer.serialize(communeKhmerData);
    }
    
    @GET
    @Path("/village")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String Village() {

        final Collection<VillageKhmerData> villageKhmerDatas = this.readPlatformService.retrieveAllVillage();
         return this.toApiJsonSerializer.serialize(villageKhmerDatas);
    }
    
    @GET
    @Path("/village/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String Village(@PathParam("id") final long id) {

        final VillageKhmerData villageKhmerData = this.readPlatformService.retrieveVillage(id);
         return this.toApiJsonSerializer.serialize(villageKhmerData);
    }
    
    @GET
    @Path("/village/commune/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String VillageByCommune(@PathParam("id") final long id) {

        final Collection<VillageKhmerData> villageKhmerData = this.readPlatformService.retrieveVillageByCommune(id);
         return this.toApiJsonSerializer.serialize(villageKhmerData);
    }
}
