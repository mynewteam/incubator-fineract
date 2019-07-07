package org.apache.fineract.portfolio.inward.domain;

import org.springframework.data.jpa.repository.JpaRepository;

abstract interface InwardRepository extends JpaRepository<Inward, Long>{

	
}
