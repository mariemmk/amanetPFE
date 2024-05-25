package com.example.amanetpfe.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    String email ;
    String newPass ;
}
