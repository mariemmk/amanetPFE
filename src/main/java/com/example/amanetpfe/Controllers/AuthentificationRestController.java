package com.example.amanetpfe.Controllers;

import com.example.amanetpfe.Entities.User;
import com.example.amanetpfe.utils.AuthRequest;
import com.example.amanetpfe.utils.AuthResponse;
import com.example.amanetpfe.utils.JwtTokenFilter;
import com.example.amanetpfe.utils.JwtTokenUtil;
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
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    @Operation(description = "login service")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String accesToken = jwtTokenUtil.generateAccessToken(user);
            AuthResponse authResponse = new AuthResponse(accesToken,user);
            return ResponseEntity.ok().body(authResponse);
        }catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PostMapping("/getCurrentUser")
    public User getCurrentUser(@RequestBody AuthResponse token){
        return jwtTokenFilter.getCurrentUser(token.getAccessToken());
    }
}
