package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransferDTO {

    @JsonProperty("fromId")
    private long fromID;
    @JsonProperty("toId")
    private long toID;
    @JsonProperty("amount")
    private BigDecimal amount;

    public long getFromID() {
        return fromID;
    }

    public void setFromID(long fromID) {
        this.fromID = fromID;
    }

    public long getToID() {
        return toID;
    }

    public void setToID(long toID) {
        this.toID = toID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
