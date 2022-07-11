package com.techelevator.tenmo.model;


import java.math.BigDecimal;

public class TransferCredentials {

    /* THESE ARE USER IDS!!!! */
    private long fromId;
    private long toId;
    private BigDecimal amount;

    public TransferCredentials(long fromId, long toId, BigDecimal amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
