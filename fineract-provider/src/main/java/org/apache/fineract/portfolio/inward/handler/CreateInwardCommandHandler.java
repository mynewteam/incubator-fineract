package org.apache.fineract.portfolio.inward.handler;

import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.inward.service.InwardWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CommandType(entity = "INWARD", action = "CREATE")
public class CreateInwardCommandHandler implements NewCommandSourceHandler {
	
	private final InwardWritePlatformService inwardWritePlatFormService;

	@Autowired
	public CreateInwardCommandHandler(InwardWritePlatformService inwardWritePlatFormService) {
	
		this.inwardWritePlatFormService = inwardWritePlatFormService;
	}
	

	@Override
	public CommandProcessingResult processCommand(final JsonCommand command) {
		return null;
	}
	
}
