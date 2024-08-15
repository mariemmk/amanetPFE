package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.BankAccount;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Repositories.BankAccountRepository;
import com.example.amanetpfe.Repositories.IUserRepository;
import com.example.amanetpfe.Repositories.accountRequestRepository;
import com.example.amanetpfe.Services.Interfaces.IUserService;
import com.example.amanetpfe.dto.*;
import com.example.amanetpfe.utils.AccountUtils;

import com.example.amanetpfe.utils.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final TransactionService transactionService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    private final BankAccountRepository bankAccountRepository;
    private final accountRequestRepository accountRequestRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, TransactionService transactionService,
                           PasswordEncoder passwordEncoder, EmailSender emailSender,
                           BankAccountRepository bankAccountRepository,
                           accountRequestRepository accountRequestRepository) {
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
        this.bankAccountRepository = bankAccountRepository;
        this.accountRequestRepository = accountRequestRepository;
    }

    @Override
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public BankResponse creationAccount(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User newUser = createUserFromRequest(userRequest);
        userRepository.save(newUser);
        createAndSaveAccountRequest(newUser, userRequest.getAccountType());
        sendEmail(newUser.getEmail(), "Account Creation Request", "Your account creation request has been submitted and is pending approval.");

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage("Account request submitted successfully.")
                .build();
    }
    @Override
    public User createUserFromRequest(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .familyName(userRequest.getFamilyName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .status("ACTIVE")
                .birthDate(userRequest.getBirthDate())
                .cin(userRequest.getCIN())
                .role(userRequest.getRole())
                .build();
    }

    @Override
    public void createAndSaveAccountRequest(User user, String accountType) {
        AccountRequest accountRequest = AccountRequest.builder()
                .accountType(accountType)
                .status("PENDING")
                .requestDate(new Date())
                .user(user)
                .build();
        accountRequestRepository.save(accountRequest);
    }

    @Override
    public void sendEmail(String recipient, String subject, String body) {
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(recipient)
                .subject(subject)
                .messageBody(body)
                .build();
        emailSender.sendEmailAlert(emailDetails);
    }

    @Override
    public BankResponse approveAccountRequest(Integer idRequest) {
        AccountRequest accountRequest = getAccountRequestById(idRequest);
        accountRequest.setStatus("APPROVED");
        accountRequest.setResponseDate(new Date());
        accountRequestRepository.save(accountRequest);

        BankAccount bankAccount = createAndSaveBankAccount(accountRequest);
        sendEmail(accountRequest.getUser().getEmail(), "Account Creation Approved",
                "Your account creation request has been approved. Your account number is: " + bankAccount.getAccountNumber());

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage("Account creation request approved successfully.")
                .build();
    }

    private AccountRequest getAccountRequestById(Integer requestId) {
        return accountRequestRepository.findById(requestId)
                .orElseThrow(() -> new UserNotFoundException("Request not found"));
    }

    private BankAccount createAndSaveBankAccount(AccountRequest accountRequest) {
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(AccountUtils.generateAccountNumber())
                .rib(AccountUtils.generateRIB())
                .accountBalance(BigDecimal.ZERO)
                .accountType(accountRequest.getAccountType())
                .user(accountRequest.getUser())
                .build();
        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

    @Override
    public String afficheIdentiteBancair(Integer idUser) {
        User u= userRepository.findById(idUser).orElse(null);
        String bic = "CFCTTNTT";
        if (u != null && u.getBankAccount() != null) {
            BankAccount bankAccount = u.getBankAccount(); // Assuming you want the first bank account
            return String.format("Nom: %s%nPrénom: %s%nSolde: %s%nDate de naissance: %s%nNature du Compte: %s%nRIB: %s%nCode BIC: %s",
                    u.getFamilyName(), u.getFirstName(), bankAccount.getAccountBalance(), u.getBirthDate(), bankAccount.getAccountType(), bankAccount.getRib(), bic);
        } else {
            return "Utilisateur non trouvé ou pas de compte bancaire";
        }
    }

    @Override
    public User retrieveUser(Integer idUser) {
        return userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void removeUser(Integer idUser) {
        userRepository.deleteById(idUser);
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getIdUser())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userRepository.save(existingUser);
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
        sendEmail(user.getEmail(), "Code de Verification", "Votre code de verification: " + verificationCode);
        return true;
    }

    @Override
    public boolean isVerificationCodeValid(String email, String verificationCode) {
        User user = userRepository.findUserByEmail(email);
        if (user == null || user.getCodeVerif() == null || user.getCodeVerif().isEmpty()) {
            return false;
        }
        return user.getCodeVerif().equals(verificationCode);
    }

    @Override
    public boolean changePassword(String email, String newPassword) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return false;
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        if (!encodedNewPassword.equals(user.getLastPassword())) {
            user.setLastPassword(user.getPassword());
            user.setPassword(encodedNewPassword);
            user.setLastDateChangePassword(new Date());
            user.setLastModifiedAt(new Date());
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
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


    @Override
    public List<AccountRequest> getAllAccountRequests() {
        return accountRequestRepository.findAll();
    }
    @Override
    public Map<String, Long> getAccountRequestCountByStatus() {
        List<Object[]> results = accountRequestRepository.countAccountRequestsByStatus();
        Map<String, Long> countByStatus = new HashMap<>();
        for (Object[] result : results) {
            String status = (String) result[0];
            Long count = (Long) result[1];
            countByStatus.put(status, count);
        }
        return countByStatus;
    }

}
