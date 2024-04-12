package com.example.amanetpfe.Entities;

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
