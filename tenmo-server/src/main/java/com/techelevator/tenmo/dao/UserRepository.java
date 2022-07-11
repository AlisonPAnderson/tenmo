package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/* Alison started by converting the JDBC Dao to JPA repositories.
So we have access to all JPA embedded methods. */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findById(long id);
    List<User> findAll();
    User findByAccountId(long accountId);

}
