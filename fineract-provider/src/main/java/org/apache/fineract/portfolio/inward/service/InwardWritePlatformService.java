package org.apache.fineract.portfolio.inward.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.inward.data.InwardData;
import org.apache.fineract.portfolio.outward.data.OutwardData;

public interface InwardWritePlatformService {

	void createInward(InwardData inwardData);
	
	void deleteInward(Long inwardId);

	void updateInward(Long inwardId, InwardData data);

}
