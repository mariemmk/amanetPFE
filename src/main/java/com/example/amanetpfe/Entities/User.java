package com.example.amanetpfe.Entities;


import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Entity
@Table(name = "User")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    Integer idUser;
    @Column(name = "userApplicationId", unique = true)
    Long userApplicationId ;

    @Column(name = "name")
    String name;

    @Column(name = "photo")
    String photo;

    @Column(name = "familyName")
    String familyName;

    @Column(name = "phoneNumber")
    Long phoneNumber ;

    @Column(name = "cin", unique = true)
    Long cin ;

    @Column(name = "password")
    String password;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "birthDate")
    Date birthDate;

    @Column(name = "isVerified")
    Boolean isVerified;

    @Column(name = "isBanned")
    Boolean isBanned;

  /*  @Column(name = "isPremium")
    Boolean isPremium;

    @Column(name = "dateStartPremium")
    Date dateStartPremium;*/

    @Column(name = "createdAt")
    Date createdAt;

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

}
