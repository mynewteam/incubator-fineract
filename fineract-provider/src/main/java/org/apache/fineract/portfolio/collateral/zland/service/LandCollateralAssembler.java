package org.apache.fineract.portfolio.collateral.zland.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.codes.domain.CodeValueRepositoryWrapper;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.addresskhmer.domain.ProvinceKhmer;
import org.apache.fineract.portfolio.addresskhmer.domain.ProvinceKhmerRepository;
import org.apache.fineract.portfolio.collateral.zland.domain.LandCollateral;
import org.apache.fineract.portfolio.collateral.zland.domain.LandCollateralRepository;
import org.apache.fineract.portfolio.collateral.zland.exception.LandNotFoundException;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepositoryWrapper;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class LandCollateralAssembler {
    private final FromJsonHelper fromApiJsonHelper;
    private final CodeValueRepositoryWrapper codeValueRepository;
    private final LandCollateralRepository landCollateralRepository;
    private final ProvinceKhmerRepository provinceKhmerRepository;
    private final LoanRepositoryWrapper loanRepository;
    
    @Autowired
    public LandCollateralAssembler(FromJsonHelper fromApiJsonHelper, CodeValueRepositoryWrapper codeValueRepository,
            LandCollateralRepository landCollateralRepository, ProvinceKhmerRepository provinceKhmer, LoanRepositoryWrapper loanRepository) {
        this.fromApiJsonHelper = fromApiJsonHelper;
        this.codeValueRepository = codeValueRepository;
        this.landCollateralRepository = landCollateralRepository;
        this.provinceKhmerRepository = provinceKhmer;
        this.loanRepository = loanRepository;
    }
    
    public Set<LandCollateral> fromParsedJson (final JsonElement element){
        final Set<LandCollateral> landCollateralItem  = new HashSet<>();
        if(element.isJsonObject()) {
            final JsonObject topLevelJsonElement = element.getAsJsonObject();
            if(topLevelJsonElement.has("landCollateral") && topLevelJsonElement.get("landCollateral").isJsonArray()) {
                final JsonArray array = topLevelJsonElement.get("landCollateral").getAsJsonArray();
                final Locale local = this.fromApiJsonHelper.extractLocaleParameter(topLevelJsonElement);
                for(int i = 0; i < array.size(); i++) {
                    final JsonObject landItemElement = array.get(i).getAsJsonObject();
                    
                    
                    final Long id = this.fromApiJsonHelper.extractLongNamed("id", landItemElement);
//                    final Loan loan = this.loanRepository.findOneWithNotFoundDetection(this.fromApiJsonHelper.extractLongNamed("", landItemElement));
                    final ProvinceKhmer provinceKhmer =this.provinceKhmerRepository.findOne(this.fromApiJsonHelper.extractLongNamed("provinceId", landItemElement));
                    final CodeValue collateralName = this.codeValueRepository.findOneWithNotFoundDetection(this.fromApiJsonHelper.extractLongNamed("collateralNameId", landItemElement));
                    final CodeValue collateralNature = this.codeValueRepository.findOneWithNotFoundDetection(this.fromApiJsonHelper.extractLongNamed("collateralNatureId", landItemElement));
                    final LocalDate dateIssue = this.fromApiJsonHelper.extractLocalDateNamed("dateIssue", landItemElement);
                    final String size = this.fromApiJsonHelper.extractStringNamed("size", landItemElement);
                    final BigDecimal oldPrice = this.fromApiJsonHelper.extractBigDecimalWithLocaleNamed("oldPrice", landItemElement);
                    final Integer numberOfCopy = this.fromApiJsonHelper.extractIntegerWithLocaleNamed("NumberOfCopy", landItemElement);
                    final CodeValue status = this.codeValueRepository.findOneWithNotFoundDetection(this.fromApiJsonHelper.extractLongNamed("status", landItemElement));
                    final String detailLocation = this.fromApiJsonHelper.extractStringNamed("detailLocation", landItemElement);
                    final String ownerName1 = this.fromApiJsonHelper.extractStringNamed("ownerName1", landItemElement);
                    final CodeValue gender1 = this.codeValueRepository.findOneWithNotFoundDetection(this.fromApiJsonHelper.extractLongNamed("gender1", landItemElement));
                    final String passportId1 = this.fromApiJsonHelper.extractStringNamed("passportId1", landItemElement);
                    final String ownerName2 = this.fromApiJsonHelper.extractStringNamed("ownerName2", landItemElement);
                    final CodeValue gender2 = this.codeValueRepository.findOneWithNotFoundDetection(this.fromApiJsonHelper.extractLongNamed("gender2", landItemElement));
                    final String passportId2 = this.fromApiJsonHelper.extractStringNamed("passportId2", landItemElement);
                    
                    
                    landCollateralItem.add(LandCollateral.from(provinceKhmer, collateralName, collateralNature, dateIssue, size, oldPrice, numberOfCopy, status, detailLocation, ownerName1, gender1, passportId1, ownerName2, gender2, passportId2));
                    
                    
                    if(id == null ) {
                        landCollateralItem.add(LandCollateral.from(provinceKhmer, collateralName, collateralNature, dateIssue, size, oldPrice, numberOfCopy, status, detailLocation, ownerName1, gender1, passportId1, ownerName2, gender2, passportId2));
                    }else {
                        final LandCollateral land = this.landCollateralRepository.findOne(id);
                        if(land == null) {throw new LandNotFoundException(id);}
                        land.assembleFrom(provinceKhmer, collateralName, collateralNature, dateIssue, size, oldPrice, numberOfCopy, status, detailLocation, ownerName1, gender1, passportId1, ownerName2, gender2, passportId2);
                        landCollateralItem.add(land);
                    }
                }
            }else {
             // no collaterals passed, use existing ones against loan
            }
        }
        return landCollateralItem;
    }
}
