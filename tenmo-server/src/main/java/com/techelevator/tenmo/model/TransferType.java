package com.techelevator.tenmo.model;

import org.springframework.beans.factory.annotation.Autowired;


/*@Entity
@Table(name="transfer_type")*/
public enum TransferType {
    request, send;

    @Autowired
    TransferType() {
    }

    //<editor-fold desc="Old code joined Type to each transfer">
/*    @Id
    @Column(name = "transfer_type_id")
    @JoinColumn(name="transfer_type_id")
    private long transferTypeId;

    @Column(name="transfer_type_desc")
    private String transferTypeDescription;

    @JsonIgnore
    @OneToMany(mappedBy = "transferType")
    private List<Transfer> transfers;



    TransferType(long transferTypeId, String transferTypeDescription, List<Transfer> transfers) {
        this.transferTypeId = transferTypeId;
        this.transferTypeDescription = transferTypeDescription;
        this.transfers = transfers;
    }

    TransferType(long transferTypeId, String transferTypeDescription) {
        this.transferTypeId = transferTypeId;
        this.transferTypeDescription = transferTypeDescription;
    }


    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferTypeDescription() {
        return transferTypeDescription;
    }

    public void setTransferTypeDescription(String transferTypeDescription) {
        this.transferTypeDescription = transferTypeDescription;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }*/
    //</editor-fold>

}
