package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="transfer_status")
public class TransferStatus {

    @Id
    @Column(name = "transfer_status_id")
    @JoinColumn(name="transfer_status_id")
    @JsonProperty("transferStatusId")
    private long transferStatusId;

    @Column(name="transfer_status_desc")
    @JsonProperty("transferStatus")
    private String transferStatus;


}
