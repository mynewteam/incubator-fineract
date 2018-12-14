package org.apache.fineract.portfolio.collateral.zland.service;

import java.util.List;

import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.codes.domain.CodeValueRepositoryWrapper;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.addresskhmer.domain.ProovinceKhmerRepositoryWrapper;
import org.apache.fineract.portfolio.addresskhmer.domain.ProvinceKhmer;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateral;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateralRepository;
import org.apache.fineract.portfolio.collateral.domain.LoanCollateralRepositoryWrapper;
import org.apache.fineract.portfolio.collateral.serialization.CollateralCommandFromApiJsonDeserializer;
import org.apache.fineract.portfolio.collateral.zland.api.LandCollateralApiConstrants;
import org.apache.fineract.portfolio.collateral.zland.command.LandCollateralCommand;
import org.apache.fineract.portfolio.collateral.zland.data.LandCollateralData;
import org.apache.fineract.portfolio.collateral.zland.domain.LandCollateral;
import org.apache.fineract.portfolio.collateral.zland.serialization.LandCommandFromApiJsonDeserializer;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LandCollateralWritePlatformServiceImpl implements LandCollateralWritePlatformService {

    private final PlatformSecurityContext context;
    private final CodeValueRepositoryWrapper codeValueRepository;
    private final LandCommandFromApiJsonDeserializer landCommandFromApiJsonDeserializer;
    private final LoanRepositoryWrapper loanRepositoryWrapper;
    private final ProovinceKhmerRepositoryWrapper provinceWrapper;
   

    @Autowired
    public LandCollateralWritePlatformServiceImpl(PlatformSecurityContext context, CodeValueRepositoryWrapper codeValueRepository,
            LandCommandFromApiJsonDeserializer landCommandFromApiJsonDeserializer, LoanRepositoryWrapper loanRepositoryWrapper, ProovinceKhmerRepositoryWrapper provinceWrapper) {
        this.context = context;
        this.codeValueRepository = codeValueRepository;
        this.landCommandFromApiJsonDeserializer = landCommandFromApiJsonDeserializer;
        this.loanRepositoryWrapper = loanRepositoryWrapper;
        this.provinceWrapper = provinceWrapper;
    }

    @Transactional
    @Override
    public CommandProcessingResult addLandCollateral(final Long loanCollateralId, final JsonCommand command) {
        this.context.authenticatedUser();
        final LandCollateralCommand landCommand = this.landCommandFromApiJsonDeserializer.commandFromApiJson(command.json());
        landCommand.validateForCreateAndUpdate();
        
        try {
            final Loan loan = this.loanRepositoryWrapper.findOneWithNotFoundDetection(loanCollateralId, true);
            final ProvinceKhmer province = this.provinceWrapper.findOneWithNotFoundDetection(landCommand.getProvinceId());
           
            CodeValue gender1 = this.codeValueRepository.findOneWithNotFoundDetection(landCommand.getGender1());
            CodeValue gender2 = this.codeValueRepository.findOneWithNotFoundDetection(landCommand.getGender2());
            CodeValue status = this.codeValueRepository.findOneWithNotFoundDetection(landCommand.getStatus());
            CodeValue collateralName = this.codeValueRepository.findOneWithNotFoundDetection(landCommand.getCollateralNameId());
            CodeValue collateralNature = this.codeValueRepository.findOneWithNotFoundDetection(landCommand.getCollateralNatureId());
            
            final LandCollateral land = LandCollateral.fromJson(loan, province, collateralName, collateralNature, status, gender1, gender2, command);
//            this.landcollateral
        }catch(final DataIntegrityViolationException dve) {
//           throw new 
        }
        
        return null;
    }

    @Override
    public CommandProcessingResult updateLandCollateral(Long loanCollateralId, Long landId, JsonCommand command) {

        return null;
    }

    @Override
    public CommandProcessingResult deleteLandCollateral(Long loanCollateralId, Long landId, Long commandId) {

        return null;
    }

}
