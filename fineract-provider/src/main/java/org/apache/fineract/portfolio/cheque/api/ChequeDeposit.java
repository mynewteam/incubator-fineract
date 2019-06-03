package org.apache.fineract.portfolio.cheque.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.ToApiJsonSerializer;
import org.apache.fineract.portfolio.cheque.data.ChequeDepositToSummaryCollectionData;
import org.apache.fineract.portfolio.cheque.data.ChequeSummaryCollectionData;
import org.apache.fineract.portfolio.cheque.service.ChequeDetailReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/chepuedeposit")
@Component
@Scope("singleton")
public class ChequeDeposit {
	
	private final ToApiJsonSerializer<ChequeSummaryCollectionData> chequeSummaryCollectionData;
	private final ToApiJsonSerializer<ChequeDepositToSummaryCollectionData> chequeDepositToSummaryCollectionData;
	private final ApiRequestParameterHelper apiRequestParameterHelper;
	private final ChequeDetailReadPlatformService chequeDetailReadPlatformService;
	
	@Autowired
	public ChequeDeposit(ToApiJsonSerializer<ChequeSummaryCollectionData> chequeSummaryCollectionData,final ApiRequestParameterHelper apiRequestParameterHelper
			,final ChequeDetailReadPlatformService chequeDetailReadPlatformService,ToApiJsonSerializer<ChequeDepositToSummaryCollectionData>chequeDepositToSummaryCollectionData) {
		
		this.chequeSummaryCollectionData = chequeSummaryCollectionData;
		this.apiRequestParameterHelper = apiRequestParameterHelper;
		this.chequeDetailReadPlatformService=chequeDetailReadPlatformService;
		this.chequeDepositToSummaryCollectionData=chequeDepositToSummaryCollectionData;
	}
	
	@GET()
	@Path("{accountsaving}/chepuefromaccout")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String retrievedeposit(@PathParam("accountsaving") final String accountsaving,
			@Context final UriInfo uriInfo){
		
		
		final ChequeSummaryCollectionData ChequeAccount= this.chequeDetailReadPlatformService.retrieveClientAccountDetails(accountsaving);
		final Set<String> CLIENT_ACCOUNTS_DATA_PARAMETERS = new HashSet<>(
				Arrays.asList("savingsAccounts"));
		// logger.debug("damn_4");
		final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper
				.process(uriInfo.getQueryParameters());
		// logger.debug("damn_ " +
		// this.clientAccountSummaryToApiJsonSerializer.serialize(settings,
		// clientAccount,CLIENT_ACCOUNTS_DATA_PARAMETERS));
		return this.chequeSummaryCollectionData.serialize(settings, ChequeAccount,
				CLIENT_ACCOUNTS_DATA_PARAMETERS);
	}
	@GET()
	@Path("{accountsaving}/chepuedepositto")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String retrievedepositto(@PathParam("accountsaving") final String accountsaving,
			@Context final UriInfo uriInfo){
		
		
		final ChequeDepositToSummaryCollectionData ChequeAccount= this.chequeDetailReadPlatformService.retrieveClientAccountdepositto(accountsaving);
		final Set<String> CLIENT_ACCOUNTS_DATA_PARAMETERS = new HashSet<>(
				Arrays.asList("savingsAccounts"));
		// logger.debug("damn_4");
		final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper
				.process(uriInfo.getQueryParameters());
		// logger.debug("damn_ " +
		// this.clientAccountSummaryToApiJsonSerializer.serialize(settings,
		// clientAccount,CLIENT_ACCOUNTS_DATA_PARAMETERS));
		return this.chequeDepositToSummaryCollectionData.serialize(settings, ChequeAccount,
				CLIENT_ACCOUNTS_DATA_PARAMETERS);
	}
	

}
