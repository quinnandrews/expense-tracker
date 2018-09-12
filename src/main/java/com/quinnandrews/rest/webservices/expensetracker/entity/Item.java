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
 * <p>Represents a thing that is purchased during a Transaction.
 *
 * @author Quinn Andrews
 *
 */
@Entity
@Table(name = "item")
@ApiModel(description = "Represents a thing that is purchased during a Transaction.")
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue
    @ApiModelProperty(notes = "User.id")
    private Long id;

    @Column(name = "item_name", length = 32, unique = true, nullable = false)
    @NotBlank(message = "Item.name cannot be null or blank.")
    @Size(max = 32, message = "Item.name cannot be more than 32 characters long.")
    @ApiModelProperty(notes = "User.id")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_category_id")
    @ApiModelProperty(notes = "User.id")
    private ItemCategory itemCategory;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ApiModelProperty(notes = "User.transactions")
    private List<TransactionItem> transactionItems;

    public Item() {
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

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
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
        Item item = (Item) o;
        return Objects.equals(getId(), item.getId()) &&
                Objects.equals(getName(), item.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
