package org.apache.fineract.accounting.classify.service;
import java.util.Collection;

import org.apache.fineract.accounting.classify.data.GLAccountAmountData;
import org.apache.fineract.accounting.classify.data.LoanArrearClassifyData;
import org.apache.fineract.accounting.classify.data.LoanProductSubtypeMappingData;
import org.joda.time.LocalDate;

public interface  LoanSubtypeMappingReadPlatformService  {

//	Page<LoanProductSubtypeMappingData> retrieveAll(SearchParameters searchParameters);
//	LoanProductSubtypeMappingData retrieveLoanProductSubtypeMappingOne(Long productId, Integer age);
	LoanProductSubtypeMappingData retrieveLoanProductSubtypeMappingOne(Long id);
	Collection<LoanProductSubtypeMappingData> retrieveLoanProductSubtypeMappings(Long productId, Integer age);
	Collection<LoanProductSubtypeMappingData> retrieveLoanProductSubtypeMappingBySubtypeStatusId(Long productId, Long subtypeStatusId);
	Collection<LoanProductSubtypeMappingData> retrieveAllLoanProductSubtypeMappings();
	Collection<LoanArrearClassifyData> retrieveLoanArrearsClassifyData(LocalDate tilldate);

	Collection<GLAccountAmountData> retrieveLoanGLAccountAmountData(Long officeId, LocalDate tilldate, Long loanId,
			Long glAccountId);

			
	LoanProductSubtypeMappingData retrieveProductSubtypeMappingDataByProductId(Long productId);
}
