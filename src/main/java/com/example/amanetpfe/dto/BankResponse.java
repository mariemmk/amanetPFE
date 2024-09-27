package com.example.amanetpfe.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankResponse {
    private String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo;
    private AccountRequest accountRequest;

}
