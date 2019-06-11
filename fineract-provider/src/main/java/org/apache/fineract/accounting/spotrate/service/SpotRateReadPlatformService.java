package org.apache.fineract.accounting.spotrate.service;


import java.util.List;

import org.apache.fineract.accounting.spotrate.data.SpotRateData;

public interface SpotRateReadPlatformService
{
	List<SpotRateData> retrieveTodaySpotRate(String transactionDate);
	Boolean CheckSpotrate(String transactionDate, String currencyCode);
}
