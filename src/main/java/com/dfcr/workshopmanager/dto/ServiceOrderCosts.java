package com.dfcr.workshopmanager.dto;

import java.math.BigDecimal;

public class ServiceOrderCosts {

    private BigDecimal labortTotal;
    private BigDecimal partsTotal;
    private BigDecimal total;

    public ServiceOrderCosts(BigDecimal labortTotal, BigDecimal partsTotal, BigDecimal total) {
        this.labortTotal = labortTotal;
        this.partsTotal = partsTotal;
        this.total = total;
    }

    public BigDecimal getLaborTotal() {
        return labortTotal;
    }

    public BigDecimal getPartsTotal() {
        return partsTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
