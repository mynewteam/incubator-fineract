package org.apache.fineract.accounting.spotrate.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpotRateRepository extends JpaRepository<SpotRate, Long>, JpaSpecificationExecutor<SpotRate> 
{
	// no added behaviour
}
