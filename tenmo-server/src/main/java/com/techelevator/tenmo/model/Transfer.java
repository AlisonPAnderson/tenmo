package com.techelevator.tenmo.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private long transferId;

    @Column(name="transfer_type_id")
    @JoinColumn(name = "transfer_type_id", insertable = false, updatable = false)
    private long transferTypeId;

    @Column(name="transfer_status_id")
    @JoinColumn(name = "transfer_status_id", insertable = false, updatable = false)
    private long transferStatusId;

    @Column(name = "account_from")
    @JoinColumn(name = "account_id")
    private long accountFrom;

    @Column(name = "account_to")
    @JoinColumn(name = "account_id")
    private long accountTo;

    @Column(name="amount")
    private BigDecimal amount;


    public Transfer(long transferId, long transferTypeId, long transferStatusId, long accountFrom, long accountTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

}
