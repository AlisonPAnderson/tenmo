package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {


    @Query(value = "SELECT transfer_id, transfer_type, transfer_status, account_from, account_to, amount " +
            "FROM transfer " +
            "WHERE account_from = :account_id " +
            "OR account_to = :account_id" ,
            nativeQuery = true)
    List<Transfer> findAllByAccountId(@Param("account_id") long accountId);

   Transfer findById(long id);

   @Query(value = "select * from transfer t " +
           "JOIN account a ON t.account_from = a.account_id " +
           "WHERE transfer_status_id = :transferStatusId AND a.account_id = :accountId",
           nativeQuery = true)
   List<Transfer> findAllByAccountFromAndTransferStatusId(long accountId, long transferStatusId);

    //<editor-fold desc="May not need - was for viewing outstanding requests">
    /* @Query(value = "select * from transfer t " +
            "JOIN account a ON t.account_to = a.account_id " +
            "WHERE transfer_status_id = :transferStatusId AND a.account_id = :accountId ",
            nativeQuery = true)
    List<Transfer> findAllByAccountToAndTransferStatusId(long accountId, long transferStatusId); */
    //</editor-fold>

    List<Transfer> findAllByAccountIdFrom(long accountIdFrom);

    List<Transfer> findAllByAccountIdTo(long accountIdTo);
}
