package org.apache.fineract.portfolio.collateral.zland.serialization;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.serialization.AbstractFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.collateral.zland.api.LandCollateralApiConstrants.LAND_COLLATERAL_JSON_INPUT_PARAMS;
import org.apache.fineract.portfolio.collateral.zland.command.LandCollateralCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.joda.time.LocalDate;

@Component
public class LandCommandFromApiJsonDeserializer extends AbstractFromApiJsonDeserializer<LandCollateralCommand> {

    private final FromJsonHelper fromApiJsonhelper;

    @Autowired
    public LandCommandFromApiJsonDeserializer(final FromJsonHelper fromJsonHelper) {
        this.fromApiJsonhelper = fromJsonHelper;
    }

    @Override
    public LandCollateralCommand commandFromApiJson(final String json) {
        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        final Set<String> supportedParameters = LAND_COLLATERAL_JSON_INPUT_PARAMS.getAllValues();
        supportedParameters.add("locale");
        supportedParameters.add("dateFormat");
        this.fromApiJsonhelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);

        final JsonElement element = this.fromApiJsonhelper.parse(json);
        final JsonObject topLevelJsonElement = element.getAsJsonObject();
        final Locale locale = this.fromApiJsonhelper.extractLocaleParameter(topLevelJsonElement);
        
        final LocalDate dateIssue = this.fromApiJsonhelper.extractLocalDateNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.DATE_ISSUE.getValue(), element);
        final String size = this.fromApiJsonhelper.extractStringNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.SIZE.getValue(), element);
        final BigDecimal oldPrice = this.fromApiJsonhelper.extractBigDecimalNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.OLD_PRICE.getValue(), element, locale);
        final Integer numberOfCopy = this.fromApiJsonhelper.extractIntegerNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.NUMBER_OF_COPY.getValue(), element, locale);
        final Long status = this.fromApiJsonhelper.extractLongNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.STATUS.getValue(), element);
        final String detailLocation = this.fromApiJsonhelper.extractStringNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.DETAIL_LOCATION.getValue(), element);
        final String ownerName1 = this.fromApiJsonhelper.extractStringNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.OWNER_NAME_1.getValue(), element);
        final Long gender1 = this.fromApiJsonhelper.extractLongNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.GENER_1.getValue(), element);
        final String passportId1 = this.fromApiJsonhelper.extractStringNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.PASSPORT_ID_1.getValue(), element);
        final String ownerName2 = this.fromApiJsonhelper.extractStringNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.OWNER_NAME_2.getValue(), element);
        final Long gender2 = this.fromApiJsonhelper.extractLongNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.GENDER_2.getValue(), element);
        final String passportId2 = this.fromApiJsonhelper.extractStringNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.PASSPORT_ID_2.getValue(), element);
        
        
        
        final Long collateralId = this.fromApiJsonhelper.extractLongNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.COLLATERAL_ID.getValue(), element);
        final Long provinceId = this.fromApiJsonhelper.extractLongNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.PROVINCE_ID.getValue(), element);
        final Long collateralNameId = this.fromApiJsonhelper.extractLongNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.COLLATERAL_NAME.getValue(), element);
        final Long collateralNatureId = this.fromApiJsonhelper.extractLongNamed(LAND_COLLATERAL_JSON_INPUT_PARAMS.COLLATERAL_NATURE.getValue(), element);
        
        
        return new LandCollateralCommand( dateIssue, size, oldPrice, numberOfCopy, status, detailLocation, ownerName1, gender1, passportId1, ownerName2, gender2, passportId2, collateralId, provinceId, collateralNameId, collateralNatureId);
    }

}
