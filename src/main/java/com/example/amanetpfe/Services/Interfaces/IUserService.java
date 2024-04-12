package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    List<User> retrieveAllUsers();

    User addUser(User user);

    User retrieveUser(Integer idUser);

    void removeUser(Integer idUser);

    public void updateCodes();

    public boolean sendVerificationCodeByEmail(String email) ;

    public boolean isVerificationCodeValid(String email, String verificationCode);

    public boolean changePassword(String email, String newPassword) ;

    public boolean isVerificationCodeValidVerif(String email, String verificationCode) ;

    User updateUser(User user);

    public boolean checkOldPassword(String password, Integer idUser);

    void uploadProfilePicture(int idUser, MultipartFile file) throws IOException;

    User banUser(Integer idUser);
}
