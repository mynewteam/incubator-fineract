package org.apache.fineract.accounting.spotrate.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.accounting.spotrate.data.SpotRateData;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.apache.fineract.accounting.spotrate.service.SpotRateReadPlatformService;

@Path("/spotrate")
@Component
@Scope("singleton")
public class SpotRateApiResource {
	
	private static final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList("id", "currency_code", "buying_rate", "selling_rate",
            "spot_rate", "transaction_date"));
	
	private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final DefaultToApiJsonSerializer<SpotRateData> toApiJsonSerializer;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    private final SpotRateReadPlatformService spotRateReadPlatformService;
    
    @Autowired
    public SpotRateApiResource(ApiRequestParameterHelper apiRequestParameterHelper,
    		final DefaultToApiJsonSerializer<SpotRateData> toApiJsonSerializer,
    		final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
    		SpotRateReadPlatformService spotRateReadPlatformService) {
    	this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        this.spotRateReadPlatformService = spotRateReadPlatformService;
    }
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String createSpotrate(final String jsonRequestBody) {

        final CommandWrapper commandRequest = new CommandWrapperBuilder().createSpotRate().withJson(jsonRequestBody).build();

        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

        return this.toApiJsonSerializer.serialize(result);
    }
    
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retreiveSpotRate(@Context final UriInfo uriInfo) {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date Date = new Date();
    	String transactionDate;
    	transactionDate = dateFormat.format(Date);
    	final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
    	
        List<SpotRateData> spotrateDate = this.spotRateReadPlatformService.retrieveTodaySpotRate(transactionDate);


        return this.toApiJsonSerializer.serialize(settings, spotrateDate, RESPONSE_DATA_PARAMETERS);
    }
}
