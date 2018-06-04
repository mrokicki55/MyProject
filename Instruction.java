package com.mytest;



import com.sun.istack.internal.NotNull;

/*
 * Here we could use lombok but I didn't want to
 * add any dependecies.
 */

import java.time.LocalDate;

public class Instruction {
    private final String entity;
    private final InstrType instrType;
    private final Double agreedFx;
    private final String currency;
    private final LocalDate instructionDate;
    private LocalDate settlementDate;
    private final Integer units;
    private final Double pricePerUnit;


    public Instruction (@NotNull final String entity,
                        @NotNull final InstrType instrType,
                        @NotNull final Double agreedFx,
                        @NotNull final String currency,
                        @NotNull final LocalDate instructionDate,
                        @NotNull final LocalDate settlementDate,
                        @NotNull final Integer units,
                        @NotNull final Double pricePerUnit) {

        this.entity = entity;
        this.instrType = instrType;
        this.agreedFx = agreedFx;
        this.currency = currency;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.units = units;
        this.pricePerUnit = pricePerUnit;

    }

    public String getEntity() {
        return entity;
    }

    public InstrType instrType() {
        return instrType;
    }

    public Double getAgreedFx() {
        return agreedFx;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getInstructionDate() {
        return instructionDate;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(LocalDate newDate) {
        settlementDate = newDate;
    }
    public Integer getUnits() {
        return units;

    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public Double getUSDAmountOfTrade() {
        return pricePerUnit * units * agreedFx;
    }
}
