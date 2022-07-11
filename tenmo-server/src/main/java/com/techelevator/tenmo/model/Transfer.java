package com.techelevator.tenmo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;

/* Lombok annotations replace getters, setters, constructors */
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

    /* We changed TransferType and TransferStatus
    to enum classes for readability
    * this way we didn't have to join two more tables,
    * and assigning types and statuses is more clear */

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type")
    private TransferType transferType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_status")
    private TransferStatus transferStatus;

    @Column(name = "account_from")
    @JsonProperty("accountIdFrom")
    private long accountIdFrom;

    @Column(name = "account_to")
    @JsonProperty("accountIdTo")
    private long accountIdTo;

    @Column(name="amount")
    private BigDecimal amount;


    //<editor-fold desc="Old code joined Type and Status tables to each transfer">
/*    @Column(name="transfer_type_id")
    private long transferTypeId;

    @Column(name="transfer_status_id")
    @JsonProperty("transferStatusId")
    private long transferStatusId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="transfer_status_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TransferStatus transferStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="transfer_type_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TransferType transferType;*/
    //</editor-fold>

    /* Joined two accounts to each transfer
    * sending account joins */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_from", referencedColumnName = "account_id",
            insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "balance"})
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_to", referencedColumnName = "account_id",
            insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "balance"})
    private Account toAccount;

    /* setting insertable and updatable to false -
    the Account entity updates Accounts, not the Transfer entity */


    //<editor-fold desc="Type and Status Getters & Setters">
    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }
    //</editor-fold>

    public Transfer(long transferId, TransferType transferType, TransferStatus transferStatus, long accountIdFrom, long accountIdTo, BigDecimal amount, Account fromAccount, Account toAccount) {
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }
}
