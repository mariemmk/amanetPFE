package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.dto.EmailDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSender implements com.example.amanetpfe.Services.Interfaces.IEmailSender {
    @Autowired
    private MailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Value("${spring.mail.username}")
    private  String senderEmail;


    @Override
    public void sendEmailAlert(EmailDetails emailDetails){
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMessageBody());
            mailMessage.setSubject(emailDetails.getSubject());

            mailSender.send(mailMessage);
            System.out.println("mail sent successfuly");

        }
        catch (MailException e){
            throw  new RuntimeException(e);
        }

    }



}
