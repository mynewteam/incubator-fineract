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
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.portfolio.addresskhmer.data.CommuneKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.CountryKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.DistrictKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.ProvinceKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.VillageKhmerData;
import org.apache.fineract.portfolio.addresskhmer.domain.CountryKhmer;
import org.apache.fineract.portfolio.charge.data.ChargeData;
import org.apache.fineract.portfolio.charge.domain.ChargeAppliesTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AddressKhmerReadPlatformServiceImp implements AddressKhmerRreadPlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public AddressKhmerReadPlatformServiceImp(final RoutingDataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
    }

    @Override
    public CountryKhmerData retrieveCountry(long id) {
        final CountryMapper mp = new CountryMapper();
        String sql = " select " + mp.CountrySchema() + " where id = ? ";
        return this.jdbcTemplate.queryForObject(sql, new Object[] {id}, mp);
    }

    @Override
    @Cacheable(value = "charges", key = "T(org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil).getTenant().getTenantIdentifier().concat('ch')")
    public Collection<CountryKhmerData> retrieveAllCountry() {

        final CountryMapper mp = new CountryMapper();

        String sql = "select " + mp.CountrySchema() + " order by countryCode ";

        return this.jdbcTemplate.query(sql, mp, new Object[] {});
    }

    @Override
    public ProvinceKhmerData retrieveProvince(long id) {
        final ProvinceMapper mp = new ProvinceMapper();
        String sql = " select " + mp.ProvinceSchema() + " where id = ? ";
        return this.jdbcTemplate.queryForObject(sql, new Object[] {id}, mp);
    }

    @Override
    @Cacheable(value = "charges", key = "T(org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil).getTenant().getTenantIdentifier().concat('ch')")
    public Collection<ProvinceKhmerData> RetrieveAllProvince() {
        final ProvinceMapper mp = new ProvinceMapper();
        String sql = "select " + mp.ProvinceSchema() + " order by ProvinceID";
        return this.jdbcTemplate.query(sql, mp, new Object[] {});
    }

    @Override
    public DistrictKhmerData retriveDistrict(long id) {
        final DistrictMapper mp = new DistrictMapper();
        String sql = " select " + mp.DistrictSchema() + " where id = ? ";
        return this.jdbcTemplate.queryForObject(sql, new Object[] {id}, mp);
    }

    @Override
    @Cacheable(value = "charges", key = "T(org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil).getTenant().getTenantIdentifier().concat('ch')")
    public Collection<DistrictKhmerData> retrieveAllDistrict() {
        final DistrictMapper mp = new DistrictMapper();
        String sql = "select " + mp.DistrictSchema() + " order by DistrictID";
        return this.jdbcTemplate.query(sql, mp, new Object[] {});
    }

    @Override
    public CommuneKhmerData retrieveCommune(long id) {
        final CommuneMapper mp = new CommuneMapper();
        String sql = " select " + mp.CommuneSchema() + " where id = ? ";
        return this.jdbcTemplate.queryForObject(sql, new Object[] {id}, mp);
    }

    @Override
    @Cacheable(value = "charges", key = "T(org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil).getTenant().getTenantIdentifier().concat('ch')")
    public Collection<CommuneKhmerData> retrieveAllCommune() {
        final CommuneMapper mp = new CommuneMapper();
        String sql = "select " + mp.CommuneSchema();
        return this.jdbcTemplate.query(sql, mp, new Object[] {});
    }

    @Override
    public VillageKhmerData retrieveVillage(long id) {
        final VillageMapper mp = new VillageMapper();
        String sql = " select " + mp.VillageSchema() + " where id = ? ";
        return this.jdbcTemplate.queryForObject(sql, new Object[] {id}, mp);
    }

    @Override
    @Cacheable(value = "charges", key = "T(org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil).getTenant().getTenantIdentifier().concat('ch')")
    public Collection<VillageKhmerData> retrieveAllVillage() {
        final VillageMapper mp = new VillageMapper();
        String sql = "select " + mp.VillageSchema() + " order by villageID";
        return this.jdbcTemplate.query(sql, mp, new Object[] {});
    }

    private static final class CountryMapper implements RowMapper<CountryKhmerData> {

        public String CountrySchema() {
            return " CountryID, Country, Des_In_Khmer, countryCode " + " from tbl_country";
        }

        @Override
        public CountryKhmerData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            Integer countryID = rs.getInt("CountryID");
            String descriptionKhmer = rs.getString("Des_In_Khmer");
            String description = rs.getString("Country");

            return CountryKhmerData.instance(countryID, descriptionKhmer, description);
        }
    }

    private static final class ProvinceMapper implements RowMapper<ProvinceKhmerData> {

        public String ProvinceSchema() {
            return " ProvinceID, ProvinceCode, ProDescription, Des_In_Khmer, tbl_country_id " + " from tbl_province ";
        }

        @Override
        public ProvinceKhmerData mapRow(ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            Integer provinceID = rs.getInt("ProvinceID");
            String provinceCode = rs.getString("ProvinceCode");
            String proDescription = rs.getString("ProDescription");
            String des_In_Khmer = rs.getString("Des_In_Khmer");
            Integer tbl_country_id = rs.getInt("tbl_country_id");
            return new ProvinceKhmerData(provinceID, provinceCode, proDescription, des_In_Khmer, tbl_country_id);
        }
    }

    private static final class DistrictMapper implements RowMapper<DistrictKhmerData> {

        public String DistrictSchema() {
            return " DistrictID, disDescription, Des_In_Khmer, tbl_province_id " + " from tbl_district ";
        }

        @Override
        public DistrictKhmerData mapRow(ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            Integer districtID = rs.getInt("DistrictID");
            String disDescription = rs.getString("disDescription");
            String des_In_Khmer = rs.getString("Des_In_Khmer");
            Integer tbl_province_id = rs.getInt("tbl_province_id");
            return new DistrictKhmerData(districtID, disDescription, des_In_Khmer, tbl_province_id);
        }
    }

    private static final class CommuneMapper implements RowMapper<CommuneKhmerData> {

        public String CommuneSchema() {
            return " CommuneID, comDescription, Des_In_Khmer, tbl_district_id " + " from tbl_commune ";
        }

        @Override
        public CommuneKhmerData mapRow(ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            Integer communeID = rs.getInt("CommuneID");
            String comDescription = rs.getString("comDescription");
            String des_In_Khmer = rs.getString("Des_In_Khmer");
            Integer tbl_district_id = rs.getInt("tbl_district_id");
            return new CommuneKhmerData(communeID, comDescription, des_In_Khmer, tbl_district_id);
        }
    }

    private static final class VillageMapper implements RowMapper<VillageKhmerData> {

        public String VillageSchema() {
            return " villageID, villDescription, Des_In_Khmer, tbl_commune_id " + " from tbl_village ";
        }

        @Override
        public VillageKhmerData mapRow(ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            Integer villageID = rs.getInt("villageID");
            String villDescription = rs.getString("villDescription");
            String des_In_Khmer = rs.getString("Des_In_Khmer");
            Integer tbl_commune_id = rs.getInt("tbl_commune_id");
            return new VillageKhmerData(villageID, villDescription, des_In_Khmer, tbl_commune_id);
        }
    }
}