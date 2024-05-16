package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.ChangePasswordRequest;
import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Services.Interfaces.IUserService;
import com.example.amanetpfe.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Tag(name = "user management")
public class UserRestController {
    @Autowired
    private IUserService userService;



    @GetMapping("/show")
    @Operation(description = "show all users")
    ResponseEntity<List<User>> getAllUsers(){
        List<User> result = userService.retrieveAllUsers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/show/{id}")
    @Operation(description = "show one user")
    User getOneUser(@PathVariable("id") Integer idUser){
        User result = userService.retrieveUser(idUser);
        return result;
    }



    @PutMapping("/edit/{id}")
    @Operation(description = "edit one user")
    User editUser(@PathVariable("id") Integer idUser, @RequestBody User user){
        return userService.updateUser(user);
    }

    @DeleteMapping("/remove/{id}")
    @Operation(description = "remove user by id")
    ResponseEntity<User> removeUser(@PathVariable("id") Integer idUser){
        userService.removeUser(idUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/resetCode")
    public void updateCodes() {
        userService.updateCodes();
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCodeByEmail(@RequestBody String email) {
        if(userService.sendVerificationCodeByEmail(email)){
            return ResponseEntity.ok("");
        }
        return ResponseEntity.ok("erreur");
    }

    @PostMapping("/changePassword")
    public boolean changePassword(@RequestBody ChangePasswordRequest request) {

        return userService.changePassword(request.getEmail(), request.getNewPass());
    }

    @PostMapping("/verify-code")
    public Boolean verifyVerificationCode(@RequestBody String email, @RequestParam String verificationCode) {
        return userService.isVerificationCodeValid(email, verificationCode);
    }

  /*  @PostMapping("/verify-code-Compte")
    public boolean verifyVerificationCodeverif(@RequestBody String email, @RequestParam String verificationCode) {
        return userService.isVerificationCodeValidVerif(email, verificationCode);
    }*/

   /* @PostMapping("/uploadProfilePicture/{idUser}")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable int idUser, @RequestParam("file") MultipartFile file) throws IOException {
        userService.uploadProfilePicture(idUser, file);
        return ResponseEntity.ok("Photo de profil téléchargée avec succès.");
    }*/

  /*  @PostMapping("/bannedAccount/{id}")
    public ResponseEntity<String> banUser(@PathVariable("id") Integer idUser) {
        User bannedUser = userService.banUser(idUser);
        if (bannedUser != null) {
            return ResponseEntity.ok("User banned successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }*/

    @PostMapping("/verifyOldPassword/{id}")
    public boolean verifyOldPassword(@PathVariable("id") Integer idUser ,@RequestBody String oldPassword){
        return userService.checkOldPassword(oldPassword, idUser);
    }

    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request){
        return userService.balanceEnquiry(request);
    }

    @GetMapping("nameEnquiry")
    public String nameEnquiry( @RequestBody  EnquiryRequest request){
        return userService.nameEnquiry(request);
    }


    @PostMapping("creationAccount")
    public  BankResponse creationAccount(@RequestBody UserRequest userRequest){
        return userService.creationAccount(userRequest);


    }

    @PostMapping ("credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return  userService.creditAccount(request);
    }

    @PostMapping ("debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
        return  userService.debitAccount(request);
    }

    @PostMapping("transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return  userService.transfer(request);
    }


    @GetMapping("/identiteBancaire")
    public String afficheIdentiteBancaire(@PathVariable("id") Integer userId) {
        User user = new User(); // Créer un objet User avec l'ID fourni
        user.setIdUser(userId);

        // Appeler la méthode afficheIdentiteBancaire de AccountService
        return userService.afficheIdentiteBancair(user);
    }
}
