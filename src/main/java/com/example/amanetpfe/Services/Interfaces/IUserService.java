package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IUserService {
    List<User> retrieveAllUsers();

    String afficheIdentiteBancair(User user);

   // User addUser(User user);

    User retrieveUser(Integer idUser);

    void removeUser(Integer idUser);

    public void updateCodes();

    public boolean sendVerificationCodeByEmail(String email) ;

    public boolean isVerificationCodeValid(String email, String verificationCode);

    public boolean changePassword(String email, String newPassword) ;

    //public boolean isVerificationCodeValidVerif(String email, String verificationCode) ;

    User updateUser(User user);

    public boolean checkOldPassword(String password, Integer idUser);

   // void uploadProfilePicture(int idUser, MultipartFile file) throws IOException;

   // User banUser(Integer idUser);
    BankResponse balanceEnquiry(EnquiryRequest request);

    String nameEnquiry(EnquiryRequest request);


    BankResponse creationAccount(UserRequest userRequest);

    BankResponse creditAccount(CreditDebitRequest request);

    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse transfer(TransferRequest request);

    //balance enquiry , name enquiry , credit , debit , transfer
    Map<String, BigDecimal> calculateBudget(Integer idUser);
}
