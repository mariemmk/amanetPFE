package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.dto.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {

    List<User> retrieveAllUsers();

    BankResponse creationAccount(UserRequest userRequest);

    User createUserFromRequest(UserRequest userRequest);

    void createAndSaveAccountRequest(User user, String accountType);

    void sendEmail(String recipient, String subject, String body);

    void updateCodes();

    BankResponse approveAccountRequest(Integer idRequest);

    String afficheIdentiteBancair(Integer idUser);

    User retrieveUser(Integer idUser);

    void removeUser(Integer idUser);

    User updateUser(User user);

    boolean sendVerificationCodeByEmail(String email);

    boolean isVerificationCodeValid(String email, String verificationCode);

    boolean changePassword(String email, String newPassword);


    boolean isVerificationCodeValidVerif(String email, String verificationCode);

    boolean checkOldPassword(String password, Integer idUser);

    User banUser(Integer idUser);

    List<AccountRequest> getAllAccountRequests();

    Map<String, Long> getAccountRequestCountByStatus();


    User findByEmail(String email);

    User updateContactDetails(Integer idUser, String phoneNumber, String address);
}
