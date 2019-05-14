package org.apache.fineract.accounting.spotrate.serialization;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.fineract.accounting.spotrate.api.SpotRateJsonInputParams;
import org.apache.fineract.accounting.spotrate.command.SpotRateCommand;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.serialization.AbstractFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@Component
public class SpotRateSerialization extends AbstractFromApiJsonDeserializer<SpotRateCommand>
{
	private final FromJsonHelper fromApiJsonHelper;

    @Autowired
    public SpotRateSerialization(final FromJsonHelper fromApiJsonfromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonfromApiJsonHelper;
    }

    @Override
    public SpotRateCommand commandFromApiJson(final String json) {
        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        final Set<String> supportedParameters = SpotRateJsonInputParams.getAllValues();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);

        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final JsonObject topLevelJsonElement = element.getAsJsonObject();
        final Locale locale = this.fromApiJsonHelper.extractLocaleParameter(topLevelJsonElement);
        
        final String currency_code = this.fromApiJsonHelper.extractStringNamed(SpotRateJsonInputParams.CURRENCY_CODE.getValue(), element);
        final BigDecimal buyingRate = this.fromApiJsonHelper.extractBigDecimalNamed(SpotRateJsonInputParams.BUYING_RATE.getValue(), element, locale);
        final BigDecimal sellingRate = this.fromApiJsonHelper.extractBigDecimalNamed(SpotRateJsonInputParams.SELLING_RATE.getValue(), element, locale);
        final BigDecimal spotRate = this.fromApiJsonHelper.extractBigDecimalNamed(SpotRateJsonInputParams.SPOTRATE.getValue(), element, locale);
        final LocalDate transactionDate = this.fromApiJsonHelper.extractLocalDateNamed(
        		SpotRateJsonInputParams.TRANSACTION_DATE.getValue(), element);
        return new SpotRateCommand(currency_code, buyingRate, sellingRate, spotRate, transactionDate);
    }
}
