package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query(value = "SELECT " +
            "transfer_id, " +
            "transfer_type_id, " +
            "transfer_status_id, " +
            "account_from, " +
            "account_to, " +
            "amount " +
            "FROM transfer " +
            "WHERE account_from = :account_id " +
            "OR account_to = :account_id" ,
            nativeQuery = true)
    List<Transfer> findAllByAccountId(@Param("account_id") long accountId);

    @Query(value = "SELECT " +
            "transfer_id, " +
            "transfer_type_id, " +
            "transfer.transfer_status_id, " +
            "account_from, " +
            "account_to, " +
            "amount " +
            "FROM transfer " +
            "JOIN transfer_status " +
            "ON transfer.transfer_status_id = transfer_status.transfer_status_id " +
            "WHERE transfer.transfer_status_id = :status " +
            "AND (account_from = :accountid " +
            "OR account_to = :accountid)" ,
            nativeQuery = true)
    List<Transfer> findAllByStatusAndUser(@Param("status") long transferStatusId,
                                          @Param("accountid") long accountId);

}
