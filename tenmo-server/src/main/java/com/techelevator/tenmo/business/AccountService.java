package com.techelevator.tenmo.business;

import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.dao.UserRepository;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;


   public Account findByUserId (long id) {
        return accountRepository.findByUserId(id);
    }

    List<User> findAllUsers() {
       return userRepository.findAll();
    }


    public BigDecimal getBalance(long id) {
        return accountRepository.findByUserId(id).getBalance();
    }

    public BigDecimal addToBalance(long userId, BigDecimal amount) {
       return getBalance(userId).add(amount);
    }

    public BigDecimal subtractFromBalance(long userId, BigDecimal amount) {
       if (getBalance(userId).compareTo(amount) < 0) {
           //TODO add NSF exception
       }
       return getBalance(userId).subtract(amount);
    }

    public void updateBalance(long userId, BigDecimal newBalance) {
       Account account = accountRepository.findByUserId(userId);
       account.setBalance(newBalance);

    }



//    public BigDecimal setBalance(long id, TransferDTO transferDetails) {
//       Account account = accountRepository.findByUserId(id);
//
//       BigDecimal startingBalance = account.getBalance();
//       BigDecimal amount = transferDetails.getAmount();
//
//       BigDecimal newBalance = startingBalance.add(amount);
//       account.setBalance(newBalance);
//       return accountRepository.findByUserId(id).getBalance();
//    }


}
