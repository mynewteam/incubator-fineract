package org.apache.fineract.portfolio.outward.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.outward.data.OutwardData;
import org.apache.fineract.portfolio.outward.domain.Outward;

public interface OutwardWritePlatformService {

	void createOutward(OutwardData outwardData);
	
	void deleteOutward(Long outwardId);

	void updateOutward(Long outwardId, OutwardData data);

}
