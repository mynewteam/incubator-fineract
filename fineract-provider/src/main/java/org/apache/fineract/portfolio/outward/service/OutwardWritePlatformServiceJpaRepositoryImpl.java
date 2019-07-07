package org.apache.fineract.portfolio.outward.service;

import java.util.Map;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.outward.data.OutwardData;
import org.apache.fineract.portfolio.outward.domain.Outward;
import org.apache.fineract.portfolio.outward.domain.OutwardRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutwardWritePlatformServiceJpaRepositoryImpl implements OutwardWritePlatformService{
	
	private OutwardRepositoryWrapper outwardRepository;

	@Autowired
	public OutwardWritePlatformServiceJpaRepositoryImpl(OutwardRepositoryWrapper outwardRepository) {
		
		this.outwardRepository = outwardRepository;
	}
	
	

	@Override
	public void createOutward(OutwardData data) {
		
		Outward newOutward = new Outward(data.getDateTime(), data.getDepositAccountNo(), data.getName(), 
				data.getChequeNo(), data.getChequeType(), data.getFromAccountNo(), data.getCurrency(), data.getAmount(), data.getDebitorName(), data.getStatus());
		
		this.outwardRepository.Save(newOutward);
	}
	

	@Override
	public void updateOutward(Long outwardId, OutwardData data) {
//		Outward newOutward = new Outward(data.getDate(), data.getAccountNo(), data.getName(), 
//				data.getChequeNo(), data.getChequeType(), data.getFromAccount(), data.getCurrency(), data.getAmount(), data.getStatus());
		
		final Outward outwardForUpdate = this.outwardRepository.findOneWithNotFoundDetection(outwardId);
		outwardForUpdate.update(data);
		this.outwardRepository.saveAndFlush(outwardForUpdate);
		
	}
	
	@Override
	public void deleteOutward(Long outwardId) {
//		Outward deleteOutward = new Outward(data.getDate(), data.getAccountNo(), data.getName(), 
//				data.getChequeNo(), data.getChequeType(), data.getFromAccount(), data.getCurrency(), data.getAmount(), data.getStatus());
		final Outward outward = this.outwardRepository.findOneWithNotFoundDetection(outwardId);
		this.outwardRepository.deleteOutward(outward);
		this.outwardRepository.flush();
	}

	
	
	
}


