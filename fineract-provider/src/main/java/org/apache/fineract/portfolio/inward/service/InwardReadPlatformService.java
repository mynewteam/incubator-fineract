package org.apache.fineract.portfolio.inward.service;

import java.util.Date;
import java.util.List;

import org.apache.fineract.portfolio.inward.data.InwardData;
import org.apache.fineract.portfolio.inward.data.NostroAccountData;
import org.apache.fineract.portfolio.inward.data.NostroBalanceAndCurrencyData;

public interface InwardReadPlatformService {

	List<InwardData> retrieveOneInward(String chequeNo);
	List<InwardData> retrieveAllInward();
	List<NostroAccountData> retrieveNostro();
	List<NostroBalanceAndCurrencyData> retrieveOneNostro(Long nostroId);
}
