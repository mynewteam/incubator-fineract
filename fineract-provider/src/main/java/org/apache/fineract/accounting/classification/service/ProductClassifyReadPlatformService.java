package org.apache.fineract.accounting.classification.service;

import java.util.List;

import org.apache.fineract.accounting.classification.data.ProductClassifyMappingData;


public interface ProductClassifyReadPlatformService {
	List<ProductClassifyMappingData> retrieveProductClassifyList(final Long ProductId, final Integer Agging);
}
