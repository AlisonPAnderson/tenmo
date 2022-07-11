package com.techelevator.tenmo.model;

import org.springframework.beans.factory.annotation.Autowired;


/*@Entity
@Table(name="transfer_status")*/
public enum TransferStatus {
    pending,
    approved,
    rejected,
    invalid_transfer,
    invalid_amount,
    user_not_found,
    nsf,
    unauthorized
    ;

    /* !!! Added extra Transfer Statuses as an
    easy way to send info to client */

    @Autowired
    TransferStatus() {
    }

    public static boolean isValid(TransferStatus status) {
        return status == pending || status == approved || status == rejected || status == null;
    }

    //<editor-fold desc="Old Code joined Status to each transfer">
/*  @Id
    @Column(name = "transfer_status_id")
    @JoinColumn(name="transfer_status_id")
    @JsonProperty("transferStatusId")
    private long transferStatusId;

    @Column(name="transfer_status_desc")
    @JsonProperty("transferStatusDescription")
    private String transferStatusDescription;

    @JsonIgnore
    @OneToMany(mappedBy = "transferStatus")
    private List<Transfer> transfers;


     TransferStatus(long transferStatusId, String transferStatusDescription) {
        this.transferStatusId = transferStatusId;
        this.transferStatusDescription = transferStatusDescription;
    }

    TransferStatus(long transferStatusId, String transferStatusDescription, List<Transfer> transfers) {
        this.transferStatusId = transferStatusId;
        this.transferStatusDescription = transferStatusDescription;
        this.transfers = transfers;
    }

    public long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDescription() {
        return transferStatusDescription;
    }

    public void setTransferStatusDescription(String transferStatusDescription) {
        this.transferStatusDescription = transferStatusDescription;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }*/
    //</editor-fold>
}
