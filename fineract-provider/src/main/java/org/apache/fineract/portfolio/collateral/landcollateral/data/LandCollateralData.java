package org.apache.fineract.portfolio.collateral.landcollateral.data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

import org.apache.fineract.portfolio.collateral.landcollateral.domain.CollateralStatus;
import org.joda.time.LocalDate;

public class LandCollateralData {

    private final Long id;
    private final Date dateIssue;
    private final String size;
    private final BigDecimal oldPrice;
    private final BigDecimal price;
    private final String province;
    private final Integer numberOfCopy;
    private final Integer status;
    private final String detailLocation;
    private final String ownerName1;
    private final Integer gender1;
    private final String passportid1;
    private final String ownerName2;
    private final Integer gender2;
    private final String passportid2;

    private LandCollateralData(final Long id, final String name, final Long collateralId, final Date dateIssue,
            final String natural, final String size, final BigDecimal oldPrice, final BigDecimal price,
            final String province, final Integer numberOfCopy, final Integer status, final String detailLocation,
            final String ownerName1, final Integer gender1, final String passportid1, final String ownerName2,
            final Integer gender2, final String passportid2) {

        this.id = id;
        this.dateIssue = dateIssue;
        this.size = size;
        this.oldPrice = oldPrice;
        this.price = price;
        this.province = province;
        this.numberOfCopy = numberOfCopy;
        this.status = status;
        this.detailLocation = detailLocation;
        this.ownerName1 = ownerName1;
        this.gender1 = gender1;
        this.passportid1 = passportid1;
        this.ownerName2 = ownerName2;
        this.gender2 = gender2;
        this.passportid2 = passportid2;
    }

    public static LandCollateralData instance(final Long id, final String name, final Long collateralId,
            final Date dateIssue, final String natural, final String size, final BigDecimal oldPrice,
            final BigDecimal price, final String province, final Integer numberOfCopy, final Integer status,
            final String detailLocation, final String ownerName1, final Integer gender1, final String passportid1,
            final String ownerName2, final Integer gender2, final String passportid2) {
        return new LandCollateralData(id, name, collateralId, dateIssue, natural, size, oldPrice, price, province,
                numberOfCopy, status, detailLocation, ownerName1, gender1, passportid1, ownerName2, gender2,
                passportid2);

    }
}
