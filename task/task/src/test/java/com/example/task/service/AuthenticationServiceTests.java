package com.example.task.service;

import com.example.task.entity.BioId;
import com.example.task.entity.MPin;
import com.example.task.entity.User;
import com.example.task.repository.BioIdRepository;
import com.example.task.repository.MPinRepository;
import com.example.task.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MPinRepository mPinRepository;

    @Mock
    private BioIdRepository bioIdRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    //             AuthenticateByUsernameAndPassword



    @Test
    public void testAuthenticateByUsernameAndPassword_UserNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        ResponseEntity<String> response = authenticationService.authenticateByUsernameAndPassword(new User("xyz", "xyz"));
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    public void testAuthenticateByUsernameAndPassword_InvalidCredentials() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User("abcd", "abc1")));
        ResponseEntity<String> response = authenticationService.authenticateByUsernameAndPassword(new User("abcd", "abc"));
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    public void testAuthenticateByUsernameAndPassword_ValidCredentials() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User("abcd", "abc1")));
        when(jwtService.generateToken(anyString())).thenReturn("mockedToken");
        ResponseEntity<String> response = authenticationService.authenticateByUsernameAndPassword(new User("abcd", "abc1"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mockedToken", response.getBody());
    }



    //           AuthenticateByMPin



    @Test
    public void testAuthenticateByMPin_UserNotFound(){
        when(mPinRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        ResponseEntity<String> response = authenticationService.authenticateByMPin(new MPin("xyz", 123));
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    public void testAuthenticateByMPin_InvalidCredentials(){
        when(mPinRepository.findByUsername(anyString())).thenReturn(Optional.of(new MPin("abcd", 1234)));
        ResponseEntity<String> response = authenticationService.authenticateByMPin(new MPin("abcd", 123));
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Invalid Credentials", response.getBody());
    }

    @Test
    public void testAuthenticateByMPin_ValidCredentials(){
        when(mPinRepository.findByUsername(anyString())).thenReturn(Optional.of(new MPin("abcd", 1234)));
        when(jwtService.generateToken(anyString())).thenReturn("mockedToken");
        ResponseEntity<String> response = authenticationService.authenticateByMPin(new MPin("abcd", 1234));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mockedToken", response.getBody());
    }


    //       AuthenticateByBioID


    @Test
    public void testAuthenticateByBioId_ValidCredentials() {
        when(bioIdRepository.findByUsername(anyString())).thenReturn(Optional.of(new BioId("abcd", 1234)));
        when(jwtService.generateToken(anyString())).thenReturn("randomUDID");
        ResponseEntity<String> response = authenticationService.authenticateByBioId(new BioId("abcd", 1234));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationService.randomUDID, response.getBody());
    }

    @Test
    public void testAuthenticateByBioId_UserNotFound(){
        when(bioIdRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        ResponseEntity<String> response = authenticationService.authenticateByBioId(new BioId("abcd", 1234));
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }



    //           AuthenticateByRandomUDID



    @Test
    public void testAuthenticateByRandomUDID_ValidCredentials(){
        when(bioIdRepository.findByUsername(anyString())).thenReturn(Optional.of(new BioId("abcd", "va6vUVjmhbUo4lRNqH8YmA==")));
        when(jwtService.generateToken(anyString())).thenReturn("token");
        ResponseEntity<String> response = authenticationService.authenticateByRandomUDID(new BioId("abcd", "va6vUVjmhbUo4lRNqH8YmA=="));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody());
    }

    @Test
    public void testAuthenticateByRandomUDID_UserNotFound(){
        when(bioIdRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        ResponseEntity<String> response = authenticationService.authenticateByRandomUDID(new BioId("abcd", "va6vUVjmhbUo4lRNqH8YmA=="));
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }



    //             Forgot password



    @Test
    void testForgotPasswordValidUserAndPassword() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        ResponseEntity<String> response = authenticationService.forgotPassword("abcd", "abc1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password changed successfully", response.getBody());
    }

    @Test
    void testForgotPasswordInvalidUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        ResponseEntity<String> response = authenticationService.forgotPassword("abc", "abc1");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Enter valid details..", response.getBody());
    }

    @Test
    void testForgotPasswordDuplicatePasswordInHistory() {
        User user = new User();
        List<String> passwordHistory = new ArrayList<>(Arrays.asList("abc3", "abc", "abc1"));
        user.setPasswordHistory(passwordHistory);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        ResponseEntity<String> response = authenticationService.forgotPassword("abcd", "abc");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("New password can't be same as last 3 passwords!!", response.getBody());
    }
}

