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

import java.util.Collection;

import org.apache.fineract.portfolio.addresskhmer.data.CommuneKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.CountryKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.DistrictKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.ProvinceKhmerData;
import org.apache.fineract.portfolio.addresskhmer.data.VillageKhmerData;
import org.apache.fineract.portfolio.addresskhmer.domain.VillageKhmer;

public interface AddressKhmerRreadPlatformService {

    CountryKhmerData retrieveCountry(long id);

    Collection<CountryKhmerData> retrieveAllCountry();
    
    
    
    
    ProvinceKhmerData retrieveProvince(long id);
    
    Collection<ProvinceKhmerData> retrieveProvinceByCountryId(Long id);
    
    Collection<ProvinceKhmerData> RetrieveAllProvince();
    
    
    
    
    
    
    
    DistrictKhmerData retriveDistrict(long id);
    
    Collection<DistrictKhmerData> retrieveDistrictByProvinceID(Long id);
    
    Collection<DistrictKhmerData> retrieveAllDistrict();
    
    
    
    
    
    CommuneKhmerData retrieveCommune(long id);
    
    Collection<CommuneKhmerData> retrieveCommuneByDistrictID(long id);
    
    Collection<CommuneKhmerData> retrieveAllCommune();
    
    
    
    
    
    
    VillageKhmerData retrieveVillage(long id);
    Collection<VillageKhmerData> retrieveVillageByCommune(Long id);
    Collection<VillageKhmerData> retrieveAllVillage();
    
}
