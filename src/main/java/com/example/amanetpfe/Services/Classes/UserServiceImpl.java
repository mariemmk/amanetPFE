package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Services.Interfaces.IUserService;



import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userRepository ;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailSender emailSender;

    @Override
    public List<User> retrieveAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        boolean test = isMailExisit(user);
        if (!isMailExisit(user)) {
            user.setUserApplicationId(generateUniqueId());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setLastPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setLastDateChangePassword(user.getCreatedAt());
            return this.userRepository.save(user);
        }
        return null;
    }

    @Override
    public User retrieveUser(Integer idUser) {
        return this.userRepository.findById(idUser).orElse(null);
    }

    @Override
    public void removeUser(Integer idUser) {
        this.userRepository.deleteById(idUser);
    }

    @Override
    public User updateUser(User user) {
        User u = this.userRepository.findById(user.getIdUser()).orElse(null);
        if (u != null) {
            return this.userRepository.save(user);
        }
        return null;
    }

    private Long generateUniqueId() {
        long timestamp = System.currentTimeMillis();

        Random random = new Random();
        int randomNum = random.nextInt(90000) + 10000;

        String uniqueIdStr = String.valueOf(timestamp) + String.valueOf(randomNum);

        uniqueIdStr = uniqueIdStr.substring(uniqueIdStr.length() - 15);

        Long uniqueId = Long.parseLong(uniqueIdStr);

        return uniqueId;
    }

    private boolean isMailExisit(User user){
        Optional<User> obj = this.userRepository.findByEmail(user.getEmail());
        if(obj.isEmpty()){
            return false;
        }
        return true;
    }



    public void updateCodes() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            String newCode = generateNewCode();
            user.setCodeVerif(newCode);
            userRepository.save(user);
        }
    }

    @Override
    public boolean sendVerificationCodeByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return false;
        }
        String verificationCode = generateNewCode();
        user.setCodeVerif(verificationCode);
        userRepository.save(user);
        String userEmail = user.getEmail();
        String emailSubject = "Code de  Verification";
        String emailText = "Votre code de verification : "+verificationCode;
        emailSender.sendEmail(userEmail, emailSubject, emailText);
        return true;
    }

    @Override
    public boolean isVerificationCodeValid(String email, String verificationCode) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return false; // L'utilisateur n'existe pas
        }

        String storedVerificationCode = user.getCodeVerif();

        if (storedVerificationCode == null || storedVerificationCode.isEmpty()) {
            return false; // Aucun code de vérification n'est enregistré pour cet utilisateur
        }

        boolean isValid = storedVerificationCode.equals(verificationCode);

        return isValid;

    }

    @Override
    public boolean changePassword(String email, String newPassword) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return false;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        if (!encodedNewPassword.equals(user.getLastPassword())) {
            user.setLastPassword(user.getPassword());
            user.setPassword(encodedNewPassword);
            user.setLastDateChangePassword(new Date());
            user.setLastModifiedAt(new Date());
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean isVerificationCodeValidVerif(String email, String verificationCode) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return false; // L'utilisateur n'existe pas
        }

        String storedVerificationCode = user.getCodeVerif();

        if (storedVerificationCode == null || storedVerificationCode.isEmpty()) {
            return false; // Aucun code de vérification n'est enregistré pour cet utilisateur
        }

        boolean isValid = storedVerificationCode.equals(verificationCode);
        user.setIsVerified(true);
        userRepository.save(user);


        return isValid;

    }


    public String generateNewCode() {
        int codeLength = 6;
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder code = new StringBuilder(codeLength);
        Random random = new Random();

        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }

    @Override
    public void uploadProfilePicture(int idUser, MultipartFile file) throws IOException {
        // Vérifier si l'utilisateur existe
        User user = userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        // Définir l'emplacement de stockage des fichiers téléchargés (ex. dossier "uploads" dans le répertoire de ressources)
        String uploadDirectory = "src/main/resources/uploads";

        // Créer le dossier s'il n'existe pas
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Nom du fichier téléchargé
        String fileName = user.getIdUser() + "_" + file.getOriginalFilename();

        // Chemin complet du fichier sur le serveur
        Path filePath = Paths.get(uploadDirectory, fileName);

        // Copier le fichier téléchargé vers le serveur
        FileCopyUtils.copy(file.getBytes(), filePath.toFile());

        // Mettre à jour le chemin de la photo de profil dans l'entité User
        user.setPhoto("/uploads/" + fileName);

        // Enregistrer les modifications dans la base de données
        userRepository.save(user);
    }

    @Override
    public User banUser(Integer idUser) {
        Optional<User> userOptional = userRepository.findById(idUser);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsBanned(true);
            return userRepository.save(user);
        } else {
            // Handle the case where the user with the given ID doesn't exist
            return null;
        }
    }
    public boolean checkOldPassword(String password, Integer idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return true;
            }
        }
        return false;
    }


}
