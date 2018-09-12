package com.quinnandrews.rest.webservices.expensetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * <p>Represents an owner of Transactions.
 *
 * @author Quinn Andrews
 *
 */
@Entity
@Table(name = "user")
@ApiModel(description = "Represents an owner of Transactions.")
public class User {

    /*
    @Column(name = "id")
    @SequenceGenerator(name = "seq", sequenceName = "schema.seq_tablename", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
     */

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    @ApiModelProperty(notes = "User.id")
    private Long id;

    @Column(name = "user_name", length = 32, unique = true, nullable=false)
    @NotBlank(message = "User.name cannot be null or blank.")
    @Size(max = 32, message = "User.name cannot be more than 32 characters long.")
    @ApiModelProperty(notes = "User.name")
    private String name;

    @Column(name = "user_email", length = 32, unique = true, nullable=false)
    @NotBlank(message = "User.email cannot be null or blank.")
    @Size(max = 32, message = "User.email cannot be more than 32 characters long.")
    @Email(message = "User.email must be a valid email address.")
    @ApiModelProperty(notes = "User.email")
    private String email;

    @Column(name = "user_password", length = 32, nullable=false)
    @NotBlank(message = "ItemCategory.name cannot be null or blank.")
    @Size(max = 32, message = "ItemCategory.name cannot be more than 32 characters long.")
    @JsonIgnore
    @ApiModelProperty(notes = "User.password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ApiModelProperty(notes = "User.transactions")
    private List<Transaction> transactions;

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
