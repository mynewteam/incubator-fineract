package org.apache.fineract.portfolio.outward.api;

import java.io.*;
import java.security.Security;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


import org.apache.commons.lang.StringUtils;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.exception.UnrecognizedQueryParamException;
import org.apache.fineract.infrastructure.core.serialization.ToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.outward.data.CheckType;
import org.apache.fineract.portfolio.outward.data.DebitorName;
import org.apache.fineract.portfolio.outward.data.OutwardData;
import org.apache.fineract.portfolio.outward.domain.Outward;
import org.apache.fineract.portfolio.outward.domain.OutwardRepositoryWrapper;
import org.apache.fineract.portfolio.outward.service.OutwardReadPlatformService;
import org.apache.fineract.portfolio.outward.service.OutwardWritePlatformServiceJpaRepositoryImpl;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import com.sun.jersey.api.spring.Autowire;

import retrofit.http.PATCH;



@Path("/outward")
@Component
@Scope("singleton")
public class OutwardApiResource {
	
	private final OutwardRepositoryWrapper outwardRepositoryWrapper;;
	private final ToApiJsonSerializer<OutwardData> toApiJsonSerializer;
	private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
	private final OutwardReadPlatformService outwardReadPlatformServices;
	
	private final static Logger logger =  LoggerFactory.getLogger(OutwardRepositoryWrapper.class);
	
	
	
	@Autowired
	public OutwardApiResource(OutwardRepositoryWrapper outwardRepositoryWrapper,
			ToApiJsonSerializer<OutwardData> toApiJsonSerializer,
			PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
			OutwardReadPlatformService outwardReadPlatformServices) {
		super();
		this.outwardRepositoryWrapper = outwardRepositoryWrapper;
		this.toApiJsonSerializer = toApiJsonSerializer;
		this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
		this.outwardReadPlatformServices = outwardReadPlatformServices;
	}
	
	
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("{chequeNo}")
	public String RetrieveOneOutward(@PathParam("chequeNo") String chequeNo) {
		List<OutwardData> oneOutward = this.outwardReadPlatformServices.retrieveOneOutward(chequeNo);
		return this.toApiJsonSerializer.serialize(oneOutward);
	}
	 
	
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("template")

	public String RetrieveTemplate() {
		List<CheckType> ch = this.outwardReadPlatformServices.retrieveTemplate();
		return this.toApiJsonSerializer.serialize(ch);
	}

	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("debitor_name")

	public String retrieveDebitorName() {
		List<DebitorName> dn = this.outwardReadPlatformServices.retrieveDebitorName();
		return this.toApiJsonSerializer.serialize(dn);
	}

	


	
	
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String RetrieveAllOutward() {
		
		List<OutwardData> outwardData = this.outwardReadPlatformServices.retrieveAllOutward();
		return this.toApiJsonSerializer.serialize(outwardData);
	}
	
	
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response Create(final OutwardData outwardData) {
		logger.debug(this.toApiJsonSerializer.serialize(outwardData));
	
		
		OutwardWritePlatformServiceJpaRepositoryImpl outwardImpl = new OutwardWritePlatformServiceJpaRepositoryImpl(this.outwardRepositoryWrapper);
		outwardImpl.createOutward(outwardData);
		return Response.status(200).build();
		
	}
	
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("{outwardId}")
	public Response Update(@PathParam("outwardId") Long outwardId, OutwardData outwardData) {
		
		OutwardWritePlatformServiceJpaRepositoryImpl outwardImpl = new OutwardWritePlatformServiceJpaRepositoryImpl(this.outwardRepositoryWrapper);
		outwardImpl.updateOutward(outwardId, outwardData);
		return Response.status(200).build();
	}
	
	
	@DELETE
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("{outwardId}")
	public Response Delete(@PathParam("outwardId") Long outwardId) {
		OutwardWritePlatformServiceJpaRepositoryImpl outwardImpl = new OutwardWritePlatformServiceJpaRepositoryImpl(this.outwardRepositoryWrapper);
		outwardImpl.deleteOutward(outwardId);
		return Response.status(200).build();
	}
	
	///Testing
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("test")
	public String justTest() {
		return "Hello world";
	}
	
	
}


