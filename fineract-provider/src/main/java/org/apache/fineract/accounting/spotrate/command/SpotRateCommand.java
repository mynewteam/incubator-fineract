package org.apache.fineract.accounting.spotrate.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.fineract.accounting.spotrate.api.SpotRateJsonInputParams;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.joda.time.LocalDate;

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

        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("mSpotRate");
		

        baseDataValidator.reset().parameter(SpotRateJsonInputParams.CURRENCY_CODE.getValue()).value(this.currency_code).notBlank();

        baseDataValidator.reset().parameter(SpotRateJsonInputParams.BUYING_RATE.getValue()).value(this.buyingRate).notNull().positiveAmount();

        baseDataValidator.reset().parameter(SpotRateJsonInputParams.SELLING_RATE.getValue()).value(this.sellingRate).notNull().positiveAmount();

        baseDataValidator.reset().parameter(SpotRateJsonInputParams.SPOTRATE.getValue()).value(this.spotRate).notNull().positiveAmount();

        baseDataValidator.reset().parameter(SpotRateJsonInputParams.TRANSACTION_DATE.getValue()).value(this.transactionDate).notBlank();

		if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
                "Validation errors exist.", dataValidationErrors); }
    }

//    public void validateForUpdate() {
//        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
//
//        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("GLAccount");
//
//        baseDataValidator.reset().parameter(GLAccountJsonInputParams.NAME.getValue()).value(this.name).ignoreIfNull().notBlank()
//                .notExceedingLengthOf(200);
//
//        baseDataValidator.reset().parameter(GLAccountJsonInputParams.GL_CODE.getValue()).ignoreIfNull().value(this.glCode).notBlank()
//                .notExceedingLengthOf(45);
//
//        baseDataValidator.reset().parameter(GLAccountJsonInputParams.PARENT_ID.getValue()).value(this.parentId).ignoreIfNull()
//                .integerGreaterThanZero();
//
//        baseDataValidator.reset().parameter(GLAccountJsonInputParams.TYPE.getValue()).value(this.type).ignoreIfNull()
//                .inMinMaxRange(GLAccountType.getMinValue(), GLAccountType.getMaxValue());
//        baseDataValidator.reset().parameter(GLAccountJsonInputParams.USAGE.getValue()).value(this.usage).ignoreIfNull()
//                .inMinMaxRange(GLAccountUsage.getMinValue(), GLAccountUsage.getMaxValue());
//
//        baseDataValidator.reset().parameter(GLAccountJsonInputParams.DESCRIPTION.getValue()).value(this.description).ignoreIfNull()
//                .notBlank().notExceedingLengthOf(500);
//
//        baseDataValidator.reset().parameter(GLAccountJsonInputParams.DISABLED.getValue()).value(this.disabled).ignoreIfNull();
//
//        baseDataValidator.reset().anyOfNotNull(this.name, this.glCode, this.parentId, this.type, this.description, this.disabled);
//
//        if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
//                "Validation errors exist.", dataValidationErrors); }
//        baseDataValidator.reset().parameter(GLAccountJsonInputParams.TAGID.getValue()).value(this.tagId).ignoreIfNull()
//                .longGreaterThanZero();
//    }
}
