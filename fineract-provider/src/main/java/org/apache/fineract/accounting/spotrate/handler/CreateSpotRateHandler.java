package org.apache.fineract.accounting.spotrate.handler;

import org.apache.fineract.accounting.spotrate.service.SpotRateWritePlatformService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CommandType(entity = "SPOTRATE", action = "CREATE")
public class CreateSpotRateHandler implements NewCommandSourceHandler
{
	private final SpotRateWritePlatformService writePlatformService;

    @Autowired
    public CreateSpotRateHandler(final SpotRateWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }

    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {

        return this.writePlatformService.createSpotRate(command);
    }
}
