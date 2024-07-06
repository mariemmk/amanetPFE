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
    private String otherName;
    private String gender;
    private String address;
    private  String stateOfOrigin;
    private String email ;
    private Long phoneNumber;
    private Long alternativePhoneNumber;
    private String Password;
    private Date birthDate;
    private String accountType;
    private Long CIN;
    private Role role;
    private String RIB;
  /*  //employe
    private String Profession;
    private String NomDeEmployeur;
    //reserver au professionnels
    private String NomCommercial;
    private String SecteurActivite;
**/







}
