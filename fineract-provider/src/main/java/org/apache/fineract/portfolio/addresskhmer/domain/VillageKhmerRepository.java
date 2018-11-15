package org.apache.fineract.portfolio.addresskhmer.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VillageKhmerRepository extends JpaRepository<VillageKhmer, Long>, JpaSpecificationExecutor<VillageKhmer>{

}
