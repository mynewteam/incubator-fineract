package org.apache.fineract.accounting.spotrate.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.joda.time.LocalDate;
import java.util.List;

import org.apache.fineract.accounting.spotrate.api.SpotRateJsonInputParams;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;

public class SpotRateCommand
{

    private final String currency_code;
    private final BigDecimal buyingRate;
    private final BigDecimal sellingRate;
    private final BigDecimal spotRate;
    private final LocalDate transactionDate;
    
    public SpotRateCommand(final String currency_code, final BigDecimal buyingRate, final BigDecimal sellingRate, 
    		final BigDecimal spotRate, final LocalDate transactionDate) {
        this.currency_code = currency_code;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
        this.spotRate = spotRate;
        this.transactionDate = transactionDate;
    }

	public void validateForCreate() {

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();

        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("SpotRate");
		

        baseDataValidator.reset().parameter(SpotRateJsonInputParams.CURRENCY_CODE.getValue()).value(this.currency_code).notBlank();

        baseDataValidator.reset().parameter(SpotRateJsonInputParams.BUYING_RATE.getValue()).value(this.buyingRate).notNull().positiveAmount();

        baseDataValidator.reset().parameter(SpotRateJsonInputParams.SELLING_RATE.getValue()).value(this.sellingRate).notNull().positiveAmount();

        baseDataValidator.reset().parameter(SpotRateJsonInputParams.SPOTRATE.getValue()).value(this.spotRate).notNull().positiveAmount();

        baseDataValidator.reset().parameter(SpotRateJsonInputParams.TRANSACTION_DATE.getValue()).value(this.transactionDate).notBlank();

		if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
                "Validation errors exist.", dataValidationErrors); }
    }
}
