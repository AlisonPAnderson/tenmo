package com.techelevator.tenmo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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

    @Column(name="balance")
    private BigDecimal balance;

    public Account( long id, long userId, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    /* JsonIgnore annotation prevents recursion by not also showing user information when we pull an account */
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", insertable = false, updatable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    private List<Transfer> receivedTransferList;

    @JsonIgnore
    @OneToMany(mappedBy = "fromAccount")
    private List<Transfer> sentTransferList;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }

}
