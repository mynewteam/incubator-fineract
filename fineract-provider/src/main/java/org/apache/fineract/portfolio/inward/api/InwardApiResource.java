package org.apache.fineract.portfolio.inward.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.ToApiJsonSerializer;
import org.apache.fineract.portfolio.inward.data.InwardData;
import org.apache.fineract.portfolio.inward.data.NostroAccountData;
import org.apache.fineract.portfolio.inward.data.NostroBalanceAndCurrencyData;
import org.apache.fineract.portfolio.inward.domain.InwardRepositoryWrapper;
import org.apache.fineract.portfolio.inward.service.InwardReadPlatformService;
import org.apache.fineract.portfolio.inward.service.InwardWritePlatformServiceJpaRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Path("/inward")
@Component
@Scope("singleton")
public class InwardApiResource {

	private final InwardRepositoryWrapper inwardRepositoryWrapper;
	private final ToApiJsonSerializer<InwardData> toApiJsonSerializer;
	private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
	private final InwardReadPlatformService inwardReadPlatformService;
	
	private final static Logger logger =  LoggerFactory.getLogger(InwardRepositoryWrapper.class);
	
	@Autowired
	public InwardApiResource(InwardRepositoryWrapper inwardRepositoryWrapper,
			ToApiJsonSerializer<InwardData> toApiJsonSerializer,
			PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
			InwardReadPlatformService inwardReadPlatformService) {
		super();
		this.inwardRepositoryWrapper = inwardRepositoryWrapper;
		this.toApiJsonSerializer = toApiJsonSerializer;
		this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
		this.inwardReadPlatformService = inwardReadPlatformService;
	}

	
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String RetrieveAllInward() {
		List<InwardData> allInward = this.inwardReadPlatformService.retrieveAllInward();
		return this.toApiJsonSerializer.serialize(allInward);
	}
	
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("nostro")
	public String RetrieveNostroAccount() {
		List<NostroAccountData> allNostro = this.inwardReadPlatformService.retrieveNostro();
		return this.toApiJsonSerializer.serialize(allNostro);
	}
	
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("{nostroId}")
	public String RetrieveOneNostroAccount(@PathParam("nostroId") Long nostroId) {
		List<NostroBalanceAndCurrencyData> oneNostro = this.inwardReadPlatformService.retrieveOneNostro(nostroId);
		return this.toApiJsonSerializer.serialize(oneNostro);
	}
	
	
	

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response Create(final InwardData inwardData) {
		//logger.debug(this.toApiJsonSerializer.serialize(inwardData));
		
		InwardWritePlatformServiceJpaRepositoryImpl inwardImpl = new InwardWritePlatformServiceJpaRepositoryImpl(this.inwardRepositoryWrapper);
		inwardImpl.createInward(inwardData);
		return Response.status(200).build();
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("{inwardId}")
	public Response Update(@PathParam("inwardId") Long inwardId, InwardData inwardData) {
		
		InwardWritePlatformServiceJpaRepositoryImpl inwardImpl = new InwardWritePlatformServiceJpaRepositoryImpl(this.inwardRepositoryWrapper);
		inwardImpl.updateInward(inwardId, inwardData);
		return Response.status(200).build();
		
	}
	
	@DELETE
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("{inwardId}")
	public Response Delete(@PathParam("inwardId") Long inwardId) {
		InwardWritePlatformServiceJpaRepositoryImpl inwardImpl = new InwardWritePlatformServiceJpaRepositoryImpl(this.inwardRepositoryWrapper);
		inwardImpl.deleteInward(inwardId);
		return Response.status(200).build();
	}
	
	
}
