package org.apache.fineract.portfolio.collateral.zland.api;

import java.util.HashSet;
import java.util.Set;

import org.apache.fineract.portfolio.collateral.api.CollateralApiConstants.COLLATERAL_JSON_INPUT_PARAMS;

public class LandCollateralApiConstrants {

    public static final String LAND_COLLATERAL_CODE_NAME = "LandCollateral";

    // request parameter
    // public static final String COLLATERAL_ID = "id";
    // public static final String DATE_ISSUE = "dateIssue";
    // public static final String SIZE = "size";
    // public static final String OLD_PRICE = "oldPrice";
    // public static final String NUMBER_OF_COPY = "NumberOfCopy";
    // public static final String DETAIL_LOCATION = "detailLocation";
    // public static final String OWNER_NAME_1 = "ownerName1";
    // public static final String GENER_1 = "gender1";
    // public static final String PASSPORT_ID_1 = "passportId1";
    // public static final String OWNER_NAME_2 = "ownerName2";
    // public static final String GENDER_2 = "gender2";
    // public static final String PASSPORT_ID_2 = "passportId2";
    // public static final String PROVINCE_ID = "provinceId";
    // public static final String COLLATERAL_NAME = "collateralNameId";
    // public static final String COLLATERAL_NATURE = "collateralNatureId";

    public static enum LAND_COLLATERAL_JSON_INPUT_PARAMS {
        COLLATERAL_ID("id"), 
        DATE_ISSUE("dateIssue"), 
        SIZE("size"), 
        OLD_PRICE("oldPrice"), 
        NUMBER_OF_COPY("NumberOfCopy"), 
        STATUS("status"), 
        DETAIL_LOCATION("detailLocation"), 
        OWNER_NAME_1("ownerName1"), 
        GENER_1("gender1"), 
        PASSPORT_ID_1("passportId1"), 
        OWNER_NAME_2("ownerName2"), 
        GENDER_2("gender2"), 
        PASSPORT_ID_2("passportId2"), 
        PROVINCE_ID("provinceId"), 
        COLLATERAL_NAME("collateralNameId"), 
        COLLATERAL_NATURE("collateralNatureId");

        private final String value;

        private LAND_COLLATERAL_JSON_INPUT_PARAMS(final String value) {
            this.value = value;
        }

        private static final Set<String> values = new HashSet<>();
        static {
            for (final LAND_COLLATERAL_JSON_INPUT_PARAMS type : LAND_COLLATERAL_JSON_INPUT_PARAMS.values()) {
                values.add(type.value);
            }
        }

        public static Set<String> getAllValues() {
            return values;
        }

        @Override
        public String toString() {
            return name().toString().replaceAll("_", " ");
        }

        public String getValue() {
            return this.value;
        }

    }
}
