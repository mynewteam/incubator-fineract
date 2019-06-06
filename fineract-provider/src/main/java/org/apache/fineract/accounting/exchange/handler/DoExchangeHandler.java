package org.apache.fineract.accounting.exchange.handler;

import org.apache.fineract.accounting.exchange.service.ExchangeWritePlatformService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommandType(entity = "EXCHANGE", action = "CREATE")
public class DoExchangeHandler implements NewCommandSourceHandler
{
	private final ExchangeWritePlatformService writePlatformService;

    @Autowired
    public DoExchangeHandler(final ExchangeWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }

    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {

        return this.writePlatformService.doExchange(command);
    }
}
