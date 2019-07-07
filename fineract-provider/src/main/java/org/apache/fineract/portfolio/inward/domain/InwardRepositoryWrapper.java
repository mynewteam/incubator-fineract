package org.apache.fineract.portfolio.inward.domain;

import org.apache.fineract.portfolio.outward.domain.Outward;
import org.apache.fineract.portfolio.outward.exception.ChequeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InwardRepositoryWrapper {

	private final InwardRepository repository;

	@Autowired
	public InwardRepositoryWrapper(final InwardRepository repository) {
		this.repository = repository;
	}
	
	public void Save(final Inward inward) {
		this.repository.save(inward);
	}
	
	public void saveAndFlush(final Inward Inward) {
		this.repository.saveAndFlush(Inward);
	}
	
	public void delete(final Inward inward) {
		this.repository.delete(inward);
	}
	
	public void flush() {
		this.repository.flush();
	}
	
	public Inward findOneWithNotFoundDetection(Long inwardId) {
		return this.findOneWithNotFoundDetection(inwardId, false);
	}
	
//	@Transactional
//	(readOnly = true)
	public Inward findOneWithNotFoundDetection(Long inwardId, boolean loadLazyCollection) {
		final Inward Inward = this.repository.findOne(inwardId);
		if (Inward == null) {
			throw new ChequeNotFoundException(inwardId);
		}
		
		return Inward;
	}
	
}
