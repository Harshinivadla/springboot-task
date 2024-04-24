package com.example.task.Controller;

import com.example.task.entity.Contact;
import com.example.task.entity.MPin;
import com.example.task.entity.User;
import com.example.task.repository.ContactRepository;
import com.example.task.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

@Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private ContactRepository repository;

    @GetMapping
    public Contact contactDetails() {
        Contact contact1 = new Contact(1, "Harshini", "6303647033");
        return contact1;
    }

    @PostMapping("/authenticate/username-password")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody User user) {

        String username = user.getUsername();
        String password = user.getPassword();

        if (password.equals(password)) {
            if (username != null && username.equals(username)) {
                String token = jwtService.generateToken(username);
                return ResponseEntity.ok(token);
            }
        } else {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(username);
                return ResponseEntity.ok(token);
            }
        }

        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @PostMapping("/authenticate/m-pin")
    public ResponseEntity<String> MPin_authenticate(@RequestBody MPin mPin) {
        String username = mPin.getUsername();
        Integer mpin = mPin.getMpin();

        if (mpin.equals(mpin)) {
            if (username != null && username.equals(username)) {
                String token = jwtService.generateToken(username);
                return ResponseEntity.ok(token);
            }
        } else {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, mpin));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(username);
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.badRequest().body("Invalid Credentials");
    }
}