package com.quinnandrews.rest.webservices.expensetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>Represents an exchange of Items for money between Merchants and Users.
 *
 * @author Quinn Andrews
 *
 */
@Entity
@Table(name = "transaction")
@ApiModel(description = "Represents an exchange of Items for money between Merchants and Users.")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue
    @ApiModelProperty(notes = "User.id")
    private Long id;

    @Column(name = "transaction_date", nullable = false)
    @NotNull(message = "Transaction.date cannot be null.")
    @PastOrPresent(message = "Transaction.date cannot be a date in the future.")
    @ApiModelProperty(notes = "User.id")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ApiModelProperty(notes = "User.id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "merchant_id")
    @ApiModelProperty(notes = "User.id")
    private Merchant merchant;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ApiModelProperty(notes = "User.transactions")
    private List<TransactionItem> transactionItems;

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public List<TransactionItem> getTransactionItems() {
        return transactionItems;
    }

    public void setTransactionItems(List<TransactionItem> transactionItems) {
        this.transactionItems = transactionItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }

}
