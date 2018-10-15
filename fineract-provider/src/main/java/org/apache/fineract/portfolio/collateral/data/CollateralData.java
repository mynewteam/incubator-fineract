/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.portfolio.collateral.data;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
import org.apache.fineract.portfolio.collateral.landcollateral.data.LandCollateralData;

/**
 * Immutable data object for Collateral data.
 */
public class CollateralData {

    private final Long id;
    private final CodeValueData type;
    private final BigDecimal value;
    private final String description;
    @SuppressWarnings("unused")
    private final Collection<CodeValueData> allowedCollateralTypes;
    private final CurrencyData currency;
    private final LandCollateralData landCollateralData;
   

    public static CollateralData instance(final Long id, final CodeValueData type, final BigDecimal value, final String description,
            final CurrencyData currencyData, final LandCollateralData landCollateralData) {
        return new CollateralData(id, type, value, description, currencyData, landCollateralData);
    }

    public static CollateralData template(final Collection<CodeValueData> codeValues) {
        return new CollateralData(null, null, null, null, null, codeValues, null);
    }

    private CollateralData(final Long id, final CodeValueData type, final BigDecimal value, final String description,
            final CurrencyData currencyData, LandCollateralData landCollateralData) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.description = description;
        this.currency = currencyData;
        this.allowedCollateralTypes = null;
        this.landCollateralData = landCollateralData;
    }

    private CollateralData(final Long id, final CodeValueData type, final BigDecimal value, final String description,
            final CurrencyData currencyData, final Collection<CodeValueData> allowedCollateralTypes, final LandCollateralData landCollateralData) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.description = description;
        this.currency = currencyData;
        this.allowedCollateralTypes = allowedCollateralTypes;
        this.landCollateralData = landCollateralData;
    }

    public CollateralData template(final CollateralData collateralData, final Collection<CodeValueData> codeValues) {
        return new CollateralData(collateralData.id, collateralData.type, collateralData.value, collateralData.description,
                collateralData.currency, codeValues, null);
    }
}