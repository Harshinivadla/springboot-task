package com.example.task.service;

import com.example.task.repository.BioIdRepository;
import com.example.task.repository.MPinRepository;
import com.example.task.repository.UserRepository;
import com.example.task.entity.BioId;
import com.example.task.entity.MPin;
import com.example.task.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Optional;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MPinRepository mPinRepository;

    @Autowired
    private BioIdRepository bioIdRepository;

    @Autowired
    private JwtService jwtService;

    public List<User> addUserPassword(User user){
        return Collections.singletonList(userRepository.save(user));
    }

    public List<MPin> addUserMPin(MPin mPin){
        return Collections.singletonList(mPinRepository.save(mPin));
    }

    public List<BioId> addUserBioId(BioId bioId){
        return Collections.singletonList(bioIdRepository.save(bioId));
    }

    public ResponseEntity<String> authenticateByUsernameAndPassword(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isEmpty()) {
            log.info("User not found in the database");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
        }
        User foundUser = userOptional.get();
        if (!foundUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
        }
        String token = jwtService.generateToken(foundUser.getUsername());
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<String> authenticateByMPin(MPin mPin) {
        Optional<MPin> mPinOptional = mPinRepository.findByUsername(mPin.getUsername());
        if (mPinOptional.isEmpty()) {
            log.info("User not found in the database");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
        }
        MPin foundUser = mPinOptional.get();
        if (!foundUser.getMpin().equals(mPin.getMpin())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Credentials");
        }
        String token = jwtService.generateToken(foundUser.getUsername());
        return ResponseEntity.ok(token);
    }

    public static String generateRandomUDID(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    public String randomUDID = generateRandomUDID();

    public ResponseEntity<String> authenticateByBioId(BioId bioId) {
        Optional<BioId> bioIdOptional = bioIdRepository.findByUsername(bioId.getUsername());
        if (bioIdOptional.isEmpty()) {
            log.info("User not found in the database");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
        }
        BioId foundUser = bioIdOptional.get();
        if (!foundUser.getBpin().equals(bioId.getBpin())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Credentials");
        }
        BioId udid1 = new BioId(foundUser.getUsername(), foundUser.getBpin(), randomUDID);
        bioIdRepository.save(udid1);
        return ResponseEntity.ok(randomUDID);
    }

    public ResponseEntity<String> authenticateByRandomUDID(BioId bioId) {
        Optional<BioId> bioIdOptional = bioIdRepository.findByUsername(bioId.getUsername());
        if (bioIdOptional.isEmpty()){
            log.info("User not found in the database");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
        }
        BioId foundUser = bioIdOptional.get();
        if (!foundUser.getUDID().equals(bioId.getUDID())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Credentials");
        }
        String token = jwtService.generateToken(foundUser.getUsername());
        return ResponseEntity.ok(token);
    }
}