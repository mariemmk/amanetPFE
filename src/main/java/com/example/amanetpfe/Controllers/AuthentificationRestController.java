package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.Services.Classes.UserServiceImpl;
import com.example.amanetpfe.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Tag(name = "authentication endpoints")
public class AuthentificationRestController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    @Operation(description = "login service")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            if (user.getTotpSecret() == null) {
                String otpSecret = OTPUtils.generateSecret();
                user.setTotpSecret(otpSecret);
                userService.updateUser(user);
            }

            int otpCode = OTPUtils.getTOTPCode(user.getTotpSecret());

            String emailBody = String.format("Your code for logging in is: %d", otpCode);
            userService.sendEmail(user.getEmail(), "Your OTP Code", emailBody);

            Map<String, String> response = new HashMap<>();
            response.put("message", " login code has been sent to your email.");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }



    @PostMapping("/validate-otp")
    @Operation(description = "validate OTP")
    public ResponseEntity<?> validateOTP(@RequestParam String email, @RequestParam int otpCode) {
        User user = userService.findByEmail(email);

        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid email.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        boolean isValid = OTPUtils.validateTOTP(otpCode, user.getTotpSecret());
        if (isValid) {
            String accessToken = jwtTokenUtil.generateAccessToken(user);
            return ResponseEntity.ok(new AuthResponse(accessToken, user));
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid OTP");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

    }

    @PostMapping("/getCurrentUser")
    public User getCurrentUser(@RequestBody AuthResponse token) {
        return jwtTokenFilter.getCurrentUser(token.getAccessToken());
    }
}
