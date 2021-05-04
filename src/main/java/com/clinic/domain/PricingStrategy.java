package com.clinic.domain;

import java.math.BigDecimal;

public enum PricingStrategy {

    GROUPON(new BigDecimal(0.5)),
    FRIENDS(new BigDecimal(0.8)),
    NORMAL(new BigDecimal(1));

    private BigDecimal discount;

    PricingStrategy(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
