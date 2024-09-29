package com.collegedirectory.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.collegedirectory.dto.AuthenticationResponse;
import com.collegedirectory.dto.LoginRequest;
import com.collegedirectory.dto.Role;
import com.collegedirectory.dto.SignUpRequest;
import com.collegedirectory.exception.InvalidCredentialsException;
import com.collegedirectory.services.CustomUserDetailsService;
import com.collegedirectory.services.UserService;
import com.collegedirectory.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for successful login
    @Test
    public void testAuthenticateUser_Success() throws Exception {
        // Given
        String username = "testuser";
        String password = "password";
        
        LoginRequest loginRequest = new LoginRequest(username, password);
        
        // Mock UserDetails with the expected username and encoded password
        UserDetails userDetails = User.withUsername("testuser")
                .password("password") // Set the password here
                .roles("STUDENT") // You can set roles as needed
                .build();

        // Mock the authentication manager to return a valid authentication token
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities()));
        
        // Mock user details service to return the UserDetails
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        
        // Mock JWT token generation
        when(jwtUtil.generateToken(userDetails)).thenReturn("fake-jwt-token");

        // When
        ResultActions result = mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest)));

        // Then
        result.andExpect(status().isOk())
              .andExpect(jsonPath("$.jwtToken").value("fake-jwt-token"));
    }



    // Test for login with invalid credentials
    @Test
    public void testAuthenticateUser_InvalidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest("testuser", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        ResultActions result = mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest)));

        result.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Invalid username or password."));
    }


    // Test for successful signup
    @Test
    public void testSignUp_Success() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("newUser", "user@example.com", "password123", "John Doe", Role.STUDENT);

        // Mocking that the username and email don't already exist
        when(userService.existsByUsername("newUser")).thenReturn(false);
        when(userService.existsByEmail("user@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        ResultActions result = mockMvc.perform(post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signUpRequest)));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User registered successfully!"));
    }


    // Test for signup with existing username
    @Test
    public void testSignUp_UsernameAlreadyExists() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("existingUser", "user@example.com", "password123", "John Doe", Role.STUDENT);

        // Mocking that the username already exists
        when(userService.existsByUsername("existingUser")).thenReturn(true);

        ResultActions result = mockMvc.perform(post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signUpRequest)));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Error: Username is already taken!"));
    }

//    // Test for signup with existing email
//    @Test
//    public void testSignUp_EmailAlreadyExists() throws Exception {
//        SignUpRequest signUpRequest = new SignUpRequest("testuser", "password", "user@example.com", "Test User",  Role.STUDENT);
//
//        when(userService.existsByUsername("testuser")).thenReturn(false);
//        when(userService.existsByEmail("test@example.com")).thenReturn(true);
//
//        ResultActions result = mockMvc.perform(post("/api/auth/signup")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(signUpRequest)));
//
//        result.andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").value("Error: Email is already in use!"));
//    }
}

