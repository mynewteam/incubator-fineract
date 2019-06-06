package org.apache.fineract.accounting.exchange.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.joda.time.LocalDate;
import java.util.List;

import org.apache.fineract.accounting.exchange.api.ExchangeJsonInputParams;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;

public class ExchangeCommand
{

    private final Long officeId;
    private final String buycurrencyCode;
    private final String sellcurrencyCode;
    private final BigDecimal sellamount;
    private final BigDecimal buyamount;
    private final BigDecimal spotRate;
    private final LocalDate transactionDate;
    
    public ExchangeCommand(final Long officeId, final String buycurrencyCode, final String sellcurrencyCode, 
    		final BigDecimal buyamount, final BigDecimal sellamount, final BigDecimal spotRate, final LocalDate transactionDate) {
        this.officeId = officeId;
        this.buycurrencyCode = buycurrencyCode;
        this.sellcurrencyCode = sellcurrencyCode;
        this.buyamount = buyamount;
        this.sellamount = sellamount;
        this.spotRate = spotRate;
        this.transactionDate = transactionDate;
    }

	public void validateForCreate() {

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();

        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("SpotRate");

        baseDataValidator.reset().parameter(ExchangeJsonInputParams.OFFICE_ID.getValue()).value(this.officeId).notBlank();

        baseDataValidator.reset().parameter(ExchangeJsonInputParams.BUYCURRENCY_CODE.getValue()).value(this.buycurrencyCode).notBlank();

        baseDataValidator.reset().parameter(ExchangeJsonInputParams.SELLCURRENCY_CODE.getValue()).value(this.sellcurrencyCode).notBlank();
        
        baseDataValidator.reset().parameter(ExchangeJsonInputParams.BUYAMOUNT.getValue()).value(this.buyamount).notBlank();

        baseDataValidator.reset().parameter(ExchangeJsonInputParams.SELLAMOUNT.getValue()).value(this.sellamount).notBlank();

        baseDataValidator.reset().parameter(ExchangeJsonInputParams.SPOTRATE.getValue()).value(this.spotRate).notNull().positiveAmount();

        baseDataValidator.reset().parameter(ExchangeJsonInputParams.TRANSACTION_DATE.getValue()).value(this.transactionDate).notBlank();

		if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
                "Validation errors exist.", dataValidationErrors); }
    }
}
