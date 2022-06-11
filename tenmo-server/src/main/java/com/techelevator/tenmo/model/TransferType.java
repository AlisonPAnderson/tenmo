package com.techelevator.tenmo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="transfer_type")
public class TransferType {

    @Id
    @Column(name = "transfer_type_id")
    @JoinColumn(name="transfer_type_id")
    private long transferTypeId;

    @Column(name="transfer_type_desc")
    private String transferType;


}
