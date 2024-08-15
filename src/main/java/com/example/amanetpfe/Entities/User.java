package com.example.amanetpfe.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    Integer idUser;

    @Column(name = "firstname")
    String firstName;

    @Column(name = "familyName")
    String familyName;

    @Column(name = "photo")
    String photo;

    @Column(name = "Gender")
    String gender;

    @Column(name = "phoneNumber")
    String phoneNumber;

    @Column(name = "stateOfOrigin")
    String stateOfOrigin;

    @Column(name = "status")
    String status;

    @Column(name = "cin", unique = true)
    Long cin;

    @Column(name = "password")
    String password;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "birthDate")
    Date birthDate;

    @CreationTimestamp
    @Column(name = "createdAt")
    Date createdAt;

    @UpdateTimestamp
    @Column(name = "modifiedAt")
    Date lastModifiedAt;

    @Column(name = "lastPassword")
    String lastPassword;

    @Column(name = "lastDateChangePassword")
    Date lastDateChangePassword;

    @Column(name = "address")
    String address;

    @Column(name = "codeVerif")
    String codeVerif;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Credit> credits;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Income> incomes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private BankAccount bankAccount;
}
