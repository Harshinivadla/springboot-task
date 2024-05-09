package com.example.task.Controller;

import com.example.task.entity.BioId;
import com.example.task.entity.Contact;
import com.example.task.entity.MPin;
import com.example.task.entity.User;
import com.example.task.service.AuthenticationService;
import com.example.task.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class TaskController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public Contact contactDetails() {
        return new Contact(1, "Harshini", "6303647033");
    }

    @PostMapping("/password")
    public List<User> addUserPassword(@RequestBody User user){
        return authenticationService.addUserPassword(user);
    }

    @PostMapping("/mpin")
    public List<MPin> addUserMPin(@RequestBody MPin mPin){
        return authenticationService.addUserMPin(mPin);
    }

    @PostMapping("/bioId")
    public List<BioId> addUserBioId(@RequestBody BioId bioId){
        return authenticationService.addUserBioId(bioId);
    }

    @PostMapping("/authenticate/username-password")
    public ResponseEntity<String> authenticateByUsernameAndPassword(@RequestBody User user) {
        return authenticationService.authenticateByUsernameAndPassword(user);
    }


    @PostMapping("/authenticate/m-pin")
    public ResponseEntity<String> authenticateByMPin(@RequestBody MPin mPin) {
        return authenticationService.authenticateByMPin(mPin);
    }

    @PostMapping("/authenticate/bio-id")
    public ResponseEntity<String> authenticateByBioId(@RequestBody BioId bioId) {
        return authenticationService.authenticateByBioId(bioId);
    }

    @PostMapping("/authenticate/randomUDID")
    public ResponseEntity<String> authenticateByRandomUDID(@RequestBody BioId bioId){
        return authenticationService.authenticateByRandomUDID(bioId);
    }
}








//package com.example.task.Controller;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.example.task.repository.ContactRepository;
//import com.example.task.repository.BioIdRepository;
//import com.example.task.repository.MPinRepository;
//import com.example.task.repository.UserRepository;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;
//import com.example.task.service.JwtService;
//import org.springframework.http.HttpStatus;
//import com.example.task.entity.Contact;
//import com.example.task.entity.BioId;
//import com.example.task.entity.MPin;
//import com.example.task.entity.User;
//import java.security.SecureRandom;
//import lombok.extern.slf4j.Slf4j;
//import java.util.Optional;
//import java.util.Base64;
//
//@RestController
//@RequestMapping("/contact")
//@Slf4j
//public class TaskController {
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private ContactRepository contactRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BioIdRepository bioIdRepository;
//
//    @Autowired
//    private MPinRepository mPinRepository;
//
//    public String randomUDID = generateRandomUDID();
//
//    @GetMapping
//    public Contact contactDetails() {
//        return new Contact(1, "Harshini", "6303647033");
//    }
//
//    @PostMapping("/authenticate/username-password")
//    public ResponseEntity<String> authenticateAndGetToken(@RequestBody User user) {
//        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
//        if (userOptional.isEmpty()) {
//            log.info("User not found in the database");
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
//        }
//        User foundUser = userOptional.get();
//        if (!foundUser.getPassword().equals(user.getPassword())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
//        }
//        String token = jwtService.generateToken(foundUser.getUsername());
//        return ResponseEntity.ok(token);
//    }
//
//    @PostMapping("/authenticate/m-pin")
//    public ResponseEntity<String> MPin_authenticate(@RequestBody MPin mPin){
//        Optional<MPin> mPinOptional = mPinRepository.findByUsername(mPin.getUsername());
//        if (mPinOptional.isEmpty()){
//            log.info("User not found in the database");
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
//        }
//        MPin foundUser = mPinOptional.get();
//        if (!foundUser.getMpin().equals(mPin.getMpin())){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Credentials");
//        }
//        String token = jwtService.generateToken(foundUser.getUsername());
//        return ResponseEntity.ok(token);
//    }
//
//    public static String generateRandomUDID(){
//        SecureRandom secureRandom = new SecureRandom();
//        byte[] randomBytes = new byte[16];
//        secureRandom.nextBytes(randomBytes);
//        return Base64.getEncoder().encodeToString(randomBytes);
//    }
//
//    @PostMapping("/authenticate/bio-id")
//    public ResponseEntity<String> BPin_authenticate(@RequestBody BioId bioId){
//        Optional<BioId> bioIdOptional = bioIdRepository.findByUsername(bioId.getUsername());
//        if (bioIdOptional.isEmpty()){
//            log.info("User not found in the database");
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
//        }
//        BioId foundUser = bioIdOptional.get();
//        if (!foundUser.getBpin().equals(bioId.getBpin())){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Credentials");
//        }
//        BioId udid1 = new BioId(foundUser.getUsername(), foundUser.getBpin(), randomUDID);
//        bioIdRepository.save(udid1);
//        return ResponseEntity.ok(randomUDID);
//    }
//
//    @PostMapping("/authenticate/randomUDID")
//    public ResponseEntity<String> RandomUDID(@RequestBody BioId bioId){
//        Optional<BioId> bioIdOptional = bioIdRepository.findByUsername(bioId.getUsername());
//        if (bioIdOptional.isEmpty()){
//            log.info("User not found in the database");
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
//        }
//        BioId foundUser = bioIdOptional.get();
//        if (!foundUser.getUDID().equals(bioId.getUDID())){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Credentials");
//        }
//        String token = jwtService.generateToken(foundUser.getUsername());
//        return ResponseEntity.ok(token);
//    }
//}