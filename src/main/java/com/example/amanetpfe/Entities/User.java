package com.example.amanetpfe.Entities;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.experimental.FieldDefaults;
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
    @Column(name = "otherName")
    String otherName;

    @Column(name = "photo")
    String photo;


    @Column(name = "Gender")
    String gender;

    @Column(name = "phoneNumber")
    Long phoneNumber;

    @Column(name = "alternativephoneNumber")
    Long alternativePhoneNumber;

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



   /* @Column(name = "isVerified")
    Boolean isVerified;*/

   /* @Column(name = "isBanned")
    Boolean isBanned;*/

    /*  @Column(name = "isPremium")
      Boolean isPremium;

      @Column(name = "dateStartPremium")
      Date dateStartPremium;*/
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

  /*  @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<Reclamation> reclamations;*/

    @Column(name = "accountNumber")
    private String accountNumber;

    @Column(name = "rib")
    private String rib;

    @Column(name = "accountBalance")
    private BigDecimal accountBalance;

    @Column(name = "accountType")
    String accountType;

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
    @JsonManagedReference
    List<Credit> credits;

    private BigDecimal income;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Expense> expenses;
}