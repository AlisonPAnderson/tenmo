package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ACCOUNT_ID")
    private long id;

    @Column(name="USER_ID")
    private long userId;

    private BigDecimal balance;

// TODO user in constructor ? (account not showing when log in
    public Account( long id, long userId, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", insertable = false, updatable = false)
    private User user;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
//    @Autowired
//    public Account(int id, double balance) {
//        this.id = id;
//        this.balance = balance;
//    }




}
