package com.quinnandrews.rest.webservices.expensetracker.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>Represents a line item within a Transaction. Describes what was purchased, how much and at what cost.
 *
 * @author Quinn Andrews
 *
 */
@Entity
@Table(name = "transaction_item")
@ApiModel(description = "Represents a line item within a Transaction. Describes what was purchased, " +
        "how much and at what cost.")
public class TransactionItem {

    @Id
    @Column(name = "transaction_item_id")
    @GeneratedValue
    @ApiModelProperty(notes = "User.id")
    private Long id;

    @Column(name = "transaction_item_price", nullable = false)
    @NotNull(message = "TransactionItem.price cannot be null.")
    @Positive(message = "TransactionItem.price cannot be less than or equal to 0.")
    @DecimalMax(value = "2", message = "TransactionItem.price cannot have more than 2 decimals.")
    @ApiModelProperty(notes = "User.id")
    private BigDecimal price;

    @Column(name = "transaction_item_quantity", nullable = false)
    @NotNull(message = "TransactionItem.quantity cannot be null.")
    @Positive(message = "TransactionItem.quantity cannot be less than or equal to 0.")
    @DecimalMax(value = "2", message = "TransactionItem.quantity cannot have more than 2 decimals.")
    @ApiModelProperty(notes = "User.id")
    private BigDecimal quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id")
    @ApiModelProperty(notes = "User.id")
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measure_id")
    @ApiModelProperty(notes = "User.id")
    private Measure measure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    @ApiModelProperty(notes = "User.id")
    private Item items;

    public TransactionItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public Item getItems() {
        return items;
    }

    public void setItems(Item items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionItem that = (TransactionItem) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getQuantity());
    }

    @Override
    public String toString() {
        return "TransactionItem{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

}
