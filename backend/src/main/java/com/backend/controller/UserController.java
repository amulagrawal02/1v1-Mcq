package com.backend.controller;

import com.backend.jwt.JwtHelper;
import com.backend.jwt.JwtRequest;
import com.backend.jwt.JwtResponse;
import com.backend.model.UserModel;
import com.backend.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	 
    @Autowired
    public UserController(UserService userService, JwtHelper helper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.helper = helper;
        this.authenticationManager = authenticationManager;
        System.out.println("UserController called");
    }

    private final UserService userService;
    private final JwtHelper helper;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/create")
    public ResponseEntity<String> register(@RequestBody UserModel userModel) {
        System.out.println(userModel);
        System.out.println("create controller called");
        try {
        	userService.addUser(userModel);
        	return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch(Exception e)
        {
        	System.out.println("Error while creating a user"  + e);
        	return new ResponseEntity<>("Failed", HttpStatus.EXPECTATION_FAILED);
        }
		
        
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        System.out.println("request while login: " + request);

        this.doAuthentication(request.getUsername(), request.getPassword());

        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        
        System.out.print("Details:  " + userDetails);
        
//
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.Builder.newInstance()
                .setJwtToken(token)
                .setUsername(userDetails.getUsername())
                .build();


       // JwtResponse response = new JwtResponse();
      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthentication(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credentials Invalid !!");
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
    	
    	System.out.println("Logout called");
        // Clear the security context
        SecurityContextHolder.clearContext();
        
        // You can also invalidate the session if you are using session-based authentication
        // HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // request.getSession().invalidate();

        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }
}
