package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Services.Interfaces.IUserService;
import com.example.amanetpfe.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Tag(name = "user management")
public class UserRestController {

    private final IUserService userService;

    @Autowired
    public UserRestController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/show")
    @Operation(description = "show all users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> result = userService.retrieveAllUsers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/show/{id}")
    @Operation(description = "show one user")
    public ResponseEntity<User> getOneUser(@PathVariable("id") Integer idUser) {
        User result = userService.retrieveUser(idUser);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/edit/{idUser}")
    @Operation(description = "edit one user")
    public ResponseEntity<User> editUser(@PathVariable("idUser") Integer idUser, @RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/remove/{idUser}")
    @Operation(description = "remove user by id")
    public ResponseEntity<Void> removeUser(@PathVariable("idUser") Integer idUser) {
        userService.removeUser(idUser);
        return new ResponseEntity<>(HttpStatus.OK);
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


    @PostMapping("/verifyOldPassword/{idUser}")
    public ResponseEntity<Boolean> verifyOldPassword( @RequestBody String oldPassword,@PathVariable("idUser") Integer idUser) {
        boolean passwordValid = userService.checkOldPassword(oldPassword,idUser );
        return ResponseEntity.ok(passwordValid);
    }
    @PostMapping("/verify-code-Compte")
    public boolean verifyVerificationCodeverif(@RequestBody String email, @RequestParam String verificationCode) {
        return userService.isVerificationCodeValidVerif(email, verificationCode);
    }


    @PostMapping("/creationAccount")
    public ResponseEntity<BankResponse> creationAccount(@RequestBody UserRequest userRequest) {
        BankResponse response = userService.creationAccount(userRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/approve-request/{idRequest}")
    public ResponseEntity<BankResponse> approveAccountRequest(@PathVariable Integer idRequest) {
        BankResponse response = userService.approveAccountRequest(idRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/REFUSE-request/{idRequest}")
    public ResponseEntity<BankResponse> DeclineAccountRequest(@PathVariable Integer idRequest) {
        BankResponse response = userService.declineAccountRequest(idRequest);
        return ResponseEntity.ok(response);
    }

   /* @PostMapping("/credit")
    public ResponseEntity<BankResponse> creditAccount(@RequestBody CreditDebitRequest request) {
        BankResponse response = userService.creditAccount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/debit")e
    public ResponseEntity<BankResponse> debitAccount(@RequestBody CreditDebitRequest request) {
        BankResponse response = userService.debitAccount(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<BankResponse> transfer(@RequestBody TransferRequest request) {
        BankResponse response = userService.transfer(request);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/budget/{idUser}")
    public ResponseEntity<Map<String, BigDecimal>> getBudget(@PathVariable Integer idUser) {
        Map<String, BigDecimal> budget = userService.calculateBudget(idUser);
        return ResponseEntity.ok(budget);
    }*/
   @GetMapping("/identiteBancaire/{idUser}")
   public ResponseEntity<String> afficheIdentiteBancaire(@PathVariable("idUser") Integer idUser) {
       String userBankDetails = userService.afficheIdentiteBancair(idUser);
       if (userBankDetails.equals("Utilisateur non trouvé")) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       return ResponseEntity.ok(userBankDetails);
   }



    @GetMapping("accountRequests")
    public List<AccountRequest> getAllAccountRequests() {
        return userService.getAllAccountRequests();
    }



    @GetMapping("/resetCode")
    public void updateCodes() {
        userService.updateCodes();
    }

    @PutMapping("/editContactDetails/{idUser}")
    @Operation(description = "Edit user's phone number and address")
    public ResponseEntity<User> updateContactDetails(@PathVariable("idUser") Integer idUser,
                                                     @RequestBody Map<String, String> updates) {
        String newPhoneNumber = updates.get("phoneNumber");
        String newAddress = updates.get("address");

        User updatedUser = userService.updateContactDetails(idUser, newPhoneNumber, newAddress);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/getuserbyemail")
    public User getUserByEmail(@RequestParam ("email") String email){
         return userService.findByEmail(email);

    }
}
