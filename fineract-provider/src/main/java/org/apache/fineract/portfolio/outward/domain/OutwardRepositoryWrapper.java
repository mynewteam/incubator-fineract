package org.apache.fineract.portfolio.outward.domain;

import org.apache.fineract.portfolio.outward.exception.ChequeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OutwardRepositoryWrapper {

	private final OutwardRepository repository;
	
	
	
	@Autowired
	public OutwardRepositoryWrapper(final OutwardRepository repository) {
		this.repository = repository;
	}
	
	public void Save(final Outward outward) {
		this.repository.save(outward);
	}
	
	public void saveAndFlush(final Outward outward) {
		this.repository.saveAndFlush(outward);
	}
	
	public void deleteOutward(final Outward outward) {
		this.repository.delete(outward);
	}
	
	public void flush() {
		this.repository.flush();
	}
	
	public Outward findOneWithNotFoundDetection(Long outwardId) {
		return this.findOneWithNotFoundDetection(outwardId, false);
	}
	
//	@Transactional
//	(readOnly = true)
	public Outward findOneWithNotFoundDetection(Long outwardId, boolean loadLazyCollection) {
		final Outward outward = this.repository.findOne(outwardId);
		if (outward == null) {
			throw new ChequeNotFoundException(outwardId);
		}
		
		return outward;
	}
	
	
}
