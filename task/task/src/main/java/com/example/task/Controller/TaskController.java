package com.example.task.Controller;

import com.example.task.entity.*;
import com.example.task.repository.AlbumClientRepository;
import com.example.task.service.AuthenticationService;
import com.example.task.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class TaskController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AlbumClientRepository albumClientRepository;

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

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam("user") String username, @RequestParam String newPassword){
        return authenticationService.forgotPassword(username, newPassword);
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

    @GetMapping("/restClient")
    public List<AlbumClient> AlbumClientDetails(){
        return authenticationService.AlbumClientDetails();
    }

}