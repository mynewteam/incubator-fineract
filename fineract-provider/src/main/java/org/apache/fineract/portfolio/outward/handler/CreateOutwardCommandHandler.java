package org.apache.fineract.portfolio.outward.handler;

import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.outward.service.OutwardWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CommandType(entity = "OUTWARD", action = "CREATE")
public class CreateOutwardCommandHandler implements NewCommandSourceHandler {

	private final OutwardWritePlatformService outwardWritePlatformService;
	
	@Autowired
	public CreateOutwardCommandHandler(final OutwardWritePlatformService outwardWritePatformService) {
		this.outwardWritePlatformService = outwardWritePatformService;
	}

	@Override
	public CommandProcessingResult processCommand(JsonCommand command) {
		// TODO Auto-generated method stub
		return null;
	}

	
}