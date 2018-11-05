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
package org.apache.fineract.portfolio.addresskhmer.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.portfolio.addresskhmer.data.CountryKhmerData;
import org.apache.fineract.portfolio.addresskhmer.domain.CountryKhmer;
import org.apache.fineract.portfolio.charge.data.ChargeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressKhmerReadPlatformServiceImp implements AddressKhmerRreadPlatformService {
    
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public AddressKhmerReadPlatformServiceImp(final RoutingDataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public CountryKhmerData retrieveCountry(long id) {

        return null;
    }

    @Override
    @Cacheable(value = "charges", key = "T(org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil).getTenant().getTenantIdentifier().concat('ch')")
    public Collection<CountryKhmerData> retrieveAllCountry() {
        
        final CountryMapper mp = new CountryMapper();
        
        String sql = "select " + mp.CountrySchema() + " order by countryCode ";
        
        return this.jdbcTemplate.query(sql, mp, new Object[] {});
    }

    private static final class CountryMapper implements RowMapper<CountryKhmerData> {
        public String CountrySchema() {
            return " CountryID, Country, Des_In_Khmer, countryCode"
        + " from tbl_country";
        }

        @Override
        public CountryKhmerData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
                throws SQLException {
            String countryCode = rs.getString("countryCode");
            String descriptionKhmer = rs.getString("Des_In_Khmer");
            String description = rs.getString("Country");
           
            return CountryKhmerData.instance(countryCode, descriptionKhmer, description);
        }
    }
}