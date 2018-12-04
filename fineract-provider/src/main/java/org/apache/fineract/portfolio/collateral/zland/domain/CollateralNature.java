package org.apache.fineract.portfolio.collateral.zland.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name="m_loan_collateral_nature")
public class CollateralNature extends AbstractPersistableCustom<Long> {
    @Column(name="name")
    private String name;
}
