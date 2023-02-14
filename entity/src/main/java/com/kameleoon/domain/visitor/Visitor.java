package com.kameleoon.domain.visitor;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.kameleoon.domain.base.AbstractEntity;
import com.kameleoon.domain.quote.Quote;
import com.kameleoon.domain.role.Role;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "visitors", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class Visitor extends AbstractEntity {
    @NotNull(groups = VisitorValidation.class)
    @NotEmpty
    @Length(min = 5, max = 50, groups = VisitorValidation.class)
    @Column(nullable = false)
    private String name;
    @NotNull(groups = VisitorValidation.class)
    @NotEmpty
    @Length(min = 5, max = 50, groups = VisitorValidation.class)
    @Column(nullable = false)
    private String username;
    @NotNull(groups = VisitorValidation.class)
    @NotEmpty
    @Length(min = 5, max = 50, groups = VisitorValidation.class)
    @Column(nullable = false)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Column(nullable = false)
    private String password;
    @Type(type = "yes_no")
    private boolean admin;
    private Set<Role> roles;
    private Set<Quote> quotesCreated;

    public Visitor() {
        super();
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany
    @JoinTable(name = "visitors_role",
            joinColumns = @JoinColumn(name = "visitors_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(targetEntity = Quote.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Quote> getQuotesCreated() {
        return this.quotesCreated;
    }

    public void setQuotesCreated(Set<Quote> quotesCreated) {
        this.quotesCreated = quotesCreated;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void addQuoteCreated(Quote quote) {
        quotesCreated.add(quote);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        if (!Objects.equals(this.id, visitor.id)) return false;

        return Objects.equals(this.name, visitor.name)
                && Objects.equals(this.username, visitor.username)
                && Objects.equals(this.email, visitor.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, username, email);
    }

    @Override
    public String toString() {
        return String.format("Visitor\n\t[name='%s',username=%s, email='%s']", this.name, this.username, this.email);
    }

    public interface VisitorValidation {
    }

}
