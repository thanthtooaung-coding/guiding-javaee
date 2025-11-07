package org.vinn.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    private BigDecimal balance;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_type")
    private String accountType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false) // Bi Directional Relationship
    private User user;

    @OneToMany(mappedBy = "sourceAccount")
    private Set<AccountTransaction> sourceTransactions;

    @OneToMany(mappedBy = "targetAccount")
    private Set<AccountTransaction> targetTransactions;

    public Account() {}

    public Account(String accountNumber, BigDecimal balance, User user, String accountName, String accountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.user = user;
        this.accountName = accountName;
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }
    public String getAccountType() {
        return accountType;
    }
}
