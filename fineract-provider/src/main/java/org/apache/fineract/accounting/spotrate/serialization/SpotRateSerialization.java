package org.apache.fineract.accounting.spotrate.serialization;

import java.lang.reflect.Type;
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
        
        final String currency_code = this.fromApiJsonHelper.extractStringNamed(SpotRateJsonInputParams.CURRENCY_CODE.getValue(), element);
        final double buyingRate = this.fromApiJsonHelper.extractDoubleNamed(SpotRateJsonInputParams.BUYING_RATE.getValue(), element);
        final double sellingRate = this.fromApiJsonHelper.extractDoubleNamed(SpotRateJsonInputParams.SELLING_RATE.getValue(), element);
        final double spotRate = this.fromApiJsonHelper.extractDoubleNamed(SpotRateJsonInputParams.SPOTRATE.getValue(), element);
        final LocalDate transactionDate = this.fromApiJsonHelper.extractLocalDateNamed(
        		SpotRateJsonInputParams.TRANSACTION_DATE.getValue(), element);
        return new SpotRateCommand(currency_code, buyingRate, sellingRate, spotRate, transactionDate);
    }
}
