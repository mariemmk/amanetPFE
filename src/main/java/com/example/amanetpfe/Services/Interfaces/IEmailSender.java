package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.dto.EmailDetails;

public interface IEmailSender {
    void sendEmailAlert(EmailDetails emailDetails);
}
