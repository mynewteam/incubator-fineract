package org.apache.fineract.accounting.spotrate.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;

import org.apache.fineract.accounting.spotrate.data.SpotRateData;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/spotrate")
@Component
@Scope("singleton")
public class SpotRateApiResource {
	
	private final static Logger Logger = LoggerFactory.getLogger(SpotRateApiResource.class);
    private final DefaultToApiJsonSerializer<SpotRateData> toApiJsonSerializer;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    @Autowired
    public SpotRateApiResource(final DefaultToApiJsonSerializer<SpotRateData> toApiJsonSerializer,
    		final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
    }
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String createGLAccount(final String jsonRequestBody) {

        final CommandWrapper commandRequest = new CommandWrapperBuilder().createSpotRate().withJson(jsonRequestBody).build();

        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        Logger.info(jsonRequestBody);
        return this.toApiJsonSerializer.serialize(result);
    }

    
    
}
