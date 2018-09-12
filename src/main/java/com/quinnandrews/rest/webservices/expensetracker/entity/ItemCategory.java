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
 * <p>Represents a group of Items.
 *
 * @author Quinn Andrews
 *
 */
@Entity
@Table(name = "item_category")
@ApiModel(description = "Represents a group of Items.")
public class ItemCategory {

    @Id
    @Column(name = "item_category")
    @GeneratedValue
    @ApiModelProperty(notes = "User.id")
    private Long id;

    @Column(name = "item_category_name", length = 32, unique = true, nullable = false)
    @NotBlank(message = "ItemCategory.name cannot be null or blank.")
    @Size(max = 32, message = "ItemCategory.name cannot be more than 32 characters long.")
    @ApiModelProperty(notes = "User.id")
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ApiModelProperty(notes = "ItemCategory.items")
    private List<Item> items;

    public ItemCategory() {
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCategory that = (ItemCategory) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "ItemCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
