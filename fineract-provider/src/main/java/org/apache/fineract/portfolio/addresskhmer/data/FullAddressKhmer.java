package org.apache.fineract.portfolio.addresskhmer.data;

import java.util.Collection;

import org.apache.fineract.portfolio.addresskhmer.domain.CountryKhmer;

public class FullAddressKhmer {
    Collection<CountryKhmerData> countryKhmer;
    Collection<ProvinceKhmerData>provinceKhmer;
    Collection<DistrictKhmerData>districtKhmer;
    Collection<CommuneKhmerData>communeKhmer;
    Collection<VillageKhmerData>villageKhmer;
    public FullAddressKhmer(Collection<CountryKhmerData> countryKhmer, Collection<ProvinceKhmerData> provinceKhmer,
            Collection<DistrictKhmerData> districtKhmer, Collection<CommuneKhmerData> communeKhmer,
            Collection<VillageKhmerData> villageKhmer) {
        this.countryKhmer = countryKhmer;
        this.provinceKhmer = provinceKhmer;
        this.districtKhmer = districtKhmer;
        this.communeKhmer = communeKhmer;
        this.villageKhmer = villageKhmer;
    }
    
    public static FullAddressKhmer template(
            final Collection<CountryKhmerData> countryKhmer,
            final Collection<ProvinceKhmerData>province,
            final Collection<DistrictKhmerData>district,
            final Collection<CommuneKhmerData>commune,
            final Collection<VillageKhmerData>village
            ) {
        return new FullAddressKhmer(countryKhmer, province, district, commune, village);
    }
}
