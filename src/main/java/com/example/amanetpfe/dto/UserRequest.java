package com.example.amanetpfe.dto;

import com.example.amanetpfe.Entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private String firstName;
    private String familyName;
    private String gender;
    private String address;
    private  String stateOfOrigin;
    private String email ;
    private String phoneNumber;
    private String Password;
    private Date birthDate;
    private String accountType;
    private Long CIN;
    private Role role;
    private String RIB;








}
