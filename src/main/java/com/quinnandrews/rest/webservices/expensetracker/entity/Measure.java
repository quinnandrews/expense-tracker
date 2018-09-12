package com.quinnandrews.rest.webservices.expensetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * <p>Represents the manner in which the price of an Item is measured during a Transaction (e.g. lbs, oz, etc.).
 *
 * @author Quinn Andrews
 *
 */
@Entity
@Table(name = "measure")
@ApiModel(description = "Represents the manner in which the price of an Item is measured during a " +
        "Transaction (e.g. lbs, oz, etc.).")
public class Measure {

    @Id
    @Column(name = "measure_id")
    @GeneratedValue
    @ApiModelProperty(notes = "User.id")
    private Long id;

    @Column(name = "measure_name", length = 32, unique = true, nullable = false)
    @NotNull(message = "Measure.name cannot be null.")
    @Size(max = 32, message = "Measure.name cannot be more than 32 characters long.")
    @ApiModelProperty(notes = "User.id")
    private String name;

    @Column(name = "measure_symbol", length = 3, unique = true, nullable = false)
    @NotBlank(message = "Measure.symbol cannot be null or blank.")
    @Size(max = 3, message = "Measure.symbol cannot be more than 3 characters long.")
    @ApiModelProperty(notes = "User.id")
    private String symbol;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ApiModelProperty(notes = "User.transactions")
    private List<TransactionItem> transactionItems;

    public Measure() {
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
        Measure measure = (Measure) o;
        return Objects.equals(getId(), measure.getId()) &&
                Objects.equals(getName(), measure.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }

}
