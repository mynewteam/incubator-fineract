package org.apache.fineract.portfolio.outward.service;

import java.util.List;

import org.apache.fineract.portfolio.outward.data.CheckType;
import org.apache.fineract.portfolio.outward.data.OutwardData;
import org.apache.fineract.portfolio.outward.data.DebitorName;

public interface OutwardReadPlatformService {

	List<CheckType> retrieveTemplate();

	List<OutwardData> retrieveAllOutward();

	List<OutwardData> retrieveOneOutward(String chequeNo);

	List<DebitorName> retrieveDebitorName();
}
