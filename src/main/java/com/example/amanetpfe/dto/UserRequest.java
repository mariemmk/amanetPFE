package com.example.amanetpfe.dto;

import com.example.amanetpfe.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Date birthdate;
    private Long CIN;
    private Role role;
    private String RIB;






}
