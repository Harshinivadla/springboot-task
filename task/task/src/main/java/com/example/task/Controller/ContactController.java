package com.example.task.Controller;

import com.example.task.entity.BioId;
import com.example.task.entity.Contact;
import com.example.task.entity.MPin;
import com.example.task.entity.User;
import com.example.task.repository.BioIdRepository;
import com.example.task.repository.ContactRepository;
import com.example.task.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;
import java.util.Base64;

@RestController
@RequestMapping("/contact")
public class ContactController {

@Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private ContactRepository repository;

    @Autowired
   private BioIdRepository bioIdRepository;

    public String randomUDID = generateRandomUDID();


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

        if (mpin.equals(mpin) && mpin!= null) {
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

    public static String generateRandomUDID(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }


    @PostMapping("/authenticate/bio-id")
    public ResponseEntity<String> BPin_authenticate(@RequestBody BioId bioId){
        String username = bioId.getUsername();
        Integer bpin = bioId.getBpin();
//        String randomUDID = generateRandomUDID();
        if (username != null && randomUDID != null){
            BioId udid1 = new BioId();
            udid1.setUsername(username);
            udid1.setBpin(bpin);
            udid1.setUDID(randomUDID);
            bioIdRepository.save(udid1);
            return ResponseEntity.ok(randomUDID);
        }else{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, randomUDID));
            if (authentication.isAuthenticated()){
                BioId udid1 = new BioId();
                udid1.setUsername(username);
                udid1.setBpin(bpin);
                udid1.setUDID(randomUDID);
                bioIdRepository.save(udid1);
               // String token = jwtService.generateToken(username);
                return ResponseEntity.ok(randomUDID);
            }
        }
        return ResponseEntity.badRequest().body("Invalid Credentials");
    }

    @PostMapping("/authenticate/randomUDID")
    public ResponseEntity<String> RandomUDID(@RequestBody BioId bioId){
        String username = bioId.getUsername();
//        String UDID = randomUDID;
        if (username != null && randomUDID != null){
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok(token);
        }else {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, randomUDID));
            if (authentication.isAuthenticated()){
                String token = jwtService.generateToken(username);
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.badRequest().body("Invalid Credentials");
    }
}