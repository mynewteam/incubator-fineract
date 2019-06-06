package org.apache.fineract.accounting.exchange.serialization;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.fineract.accounting.exchange.api.ExchangeJsonInputParams;
import org.apache.fineract.accounting.exchange.command.ExchangeCommand;
import org.apache.fineract.accounting.spotrate.api.SpotRateJsonInputParams;
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
public class ExchangeSerialization extends AbstractFromApiJsonDeserializer<ExchangeCommand>
{
	private final FromJsonHelper fromApiJsonHelper;

    @Autowired
    public ExchangeSerialization(final FromJsonHelper fromApiJsonfromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonfromApiJsonHelper;
    }

    @Override
    public ExchangeCommand commandFromApiJson(final String json) {
        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        final Set<String> supportedParameters = ExchangeJsonInputParams.getAllValues();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);

        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final JsonObject topLevelJsonElement = element.getAsJsonObject();
        final Locale locale = this.fromApiJsonHelper.extractLocaleParameter(topLevelJsonElement);
        
        final String buycurrencyCode = this.fromApiJsonHelper.extractStringNamed(ExchangeJsonInputParams.BUYCURRENCY_CODE.getValue(), element);
        final String sellcurrencyCode = this.fromApiJsonHelper.extractStringNamed(ExchangeJsonInputParams.SELLCURRENCY_CODE.getValue(), element);
        final BigDecimal sellamount = this.fromApiJsonHelper.extractBigDecimalNamed(ExchangeJsonInputParams.SELLAMOUNT.getValue(), element, locale);
        final BigDecimal buyamount = this.fromApiJsonHelper.extractBigDecimalNamed(ExchangeJsonInputParams.BUYAMOUNT.getValue(), element, locale);
        final BigDecimal spotRate = this.fromApiJsonHelper.extractBigDecimalNamed(SpotRateJsonInputParams.SPOTRATE.getValue(), element, locale);
        final LocalDate transactionDate = this.fromApiJsonHelper.extractLocalDateNamed(SpotRateJsonInputParams.TRANSACTION_DATE.getValue(), element);
        final Long officeId = this.fromApiJsonHelper.extractLongNamed(SpotRateJsonInputParams.OFFICE_ID.getValue(), element);
        return new ExchangeCommand(officeId, buycurrencyCode, sellcurrencyCode, buyamount, sellamount, spotRate, transactionDate);
    }
}
