package com.quinnandrews.rest.webservices.expensetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * <p>Represents a party that has Items for sale and the location where Transactions are made.
 *
 * @author Quinn Andrews
 *
 */
@Entity
@Table(name = "merchant")
@ApiModel(description = "Represents a party that has Items for sale and the location where Transactions are made.")
public class Merchant {

    @Id
    @Column(name = "merchant_id")
    @GeneratedValue
    @ApiModelProperty(notes = "Merchant.id")
    private Long id;

    @Column(name = "merchant_name", length = 32, unique = true, nullable = false)
    @NotBlank(message = "Merchant.name cannot be null or blank.")
    @Size(max = 32, message = "Merchant.name cannot be more than 32 characters long.")
    @ApiModelProperty(notes = "Merchant.name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ApiModelProperty(notes = "Merchant.transactions")
    private List<Transaction> transactions;

    public Merchant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merchant merchant = (Merchant) o;
        return Objects.equals(getId(), merchant.getId()) &&
                Objects.equals(getName(), merchant.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
