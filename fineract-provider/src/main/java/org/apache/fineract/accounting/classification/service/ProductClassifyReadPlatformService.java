package org.apache.fineract.accounting.classification.service;

import java.util.Date;
import java.util.List;

import org.apache.fineract.accounting.classification.data.LoanArriaClassifyData;
import org.apache.fineract.accounting.classification.data.LoanLastValueAccForMoveData;
import org.apache.fineract.accounting.classification.data.LoanSubTypeData;
import org.apache.fineract.accounting.classification.data.ProductClassifyMappingData;
import org.apache.fineract.accounting.classification.data.ProductSubTypeMappingData;

public interface ProductClassifyReadPlatformService {
	List<ProductClassifyMappingData> retrieveProductClassifyList(final Long ProductId, final Integer Agging);

	List<LoanArriaClassifyData> retrieveLoanArriaClassifyDataByLoanId(Long LoanId);

	List<LoanLastValueAccForMoveData> retrieveLoanLastValueAccForMoveDataByLoanIdAndAccId(Long LoanId, Long AccId);

	List<LoanArriaClassifyData> retrieveLoanArriaClassifyDataByLoanId(Long LoanId, Date AccrDate);

	List<LoanLastValueAccForMoveData> retrieveLoanLastValueAccForMoveDataByLoanIdForFirstAcc(Long LoanId, Long ProductId, Long AccId);

	int isFirstAccraul(Long LoanId);

	List<ProductSubTypeMappingData> retrieveProductSubTypeMappingList(Long ProductId, Integer Agging);

	List<LoanArriaClassifyData> retrieveLoanArriaByDate(Date AccrDate);

	int retrieveProductSubTypeByLoanId(Long LoanId);

	List<ProductSubTypeMappingData> retrieveProductSubTypeMappingListForChangeType(Long loan_subtype_status_id, Long product_id);

	List<LoanArriaClassifyData> retrieveLoanArriaDataByLoanIdAndDate(Long LoanId, Date TranDate);

	List<LoanSubTypeData> retrieveSubTypeByLoanIdAndDate(Long LoanId, Date TranDate);

	int getLoanSubTypeStatus(Long LoanId);
	
	ProductSubTypeMappingData retrieveProductSubtypeMappingDataByProductId(Long productId, int loanSubTypeStatus);
}
