package org.apache.fineract.portfolio.inward.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.portfolio.inward.data.InwardData;
import org.apache.fineract.portfolio.inward.domain.Inward;
import org.apache.fineract.portfolio.inward.domain.InwardRepositoryWrapper;
import org.apache.fineract.portfolio.outward.domain.Outward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InwardWritePlatformServiceJpaRepositoryImpl implements InwardWritePlatformService{

	private final InwardRepositoryWrapper inwardRepository;

	@Autowired
	public InwardWritePlatformServiceJpaRepositoryImpl(InwardRepositoryWrapper inwardRepository) {
	
		this.inwardRepository = inwardRepository;
	}
	
	
	@Override
	public void createInward(InwardData data) {
		Inward newInward = new Inward(data.getDateTime(), data.getNostroAccount(), data.getAmount(), data.getNostroBalance(), 
				data.getCurrency(), data.getFromAccountNo(), data.getChequeNo(), data.getAvailableBalance(), 
				data.getName());
		
		this.inwardRepository.Save(newInward);
		
	}
	
	@Override
	public void updateInward(Long inwardId, InwardData data) {
		final Inward inwardForUpdate = this.inwardRepository.findOneWithNotFoundDetection(inwardId);
		inwardForUpdate.update(data);
		this.inwardRepository.saveAndFlush(inwardForUpdate);
	}
	
	
	@Override
	public void deleteInward(Long inwardId) {
//		Outward deleteOutward = new Outward(data.getDate(), data.getAccountNo(), data.getName(), 
//				data.getChequeNo(), data.getChequeType(), data.getFromAccount(), data.getCurrency(), data.getAmount(), data.getStatus());
		final Inward inward = this.inwardRepository.findOneWithNotFoundDetection(inwardId);
		this.inwardRepository.delete(inward);
		this.inwardRepository.flush();
	}
}
