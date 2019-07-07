package org.apache.fineract.portfolio.outward.domain;

import org.springframework.data.jpa.repository.JpaRepository;

abstract interface OutwardRepository extends JpaRepository<Outward, Long>{

	
}
