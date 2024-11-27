package com.backend.service;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.backend.model.UserModel;
import com.backend.repository.UserRepository;

@Service
public class UserService  implements UserDetailsService{

	private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StringEncryptor stringEncryptor;
    
    
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, StringEncryptor stringEncryptor)
    {
    	this.passwordEncoder = passwordEncoder;
    	this.userRepository = userRepository;
    	this.stringEncryptor = stringEncryptor;
    }
    

    public void addUser(UserModel userModel) {
    	
    	System.out.println(userModel);
    	
    	
        userModel.setUserPassword(passwordEncoder.encode(userModel.getUserPassword()));
        //userModel.set
    	userRepository.save(userModel);
        return;
    }

    public String getEncodedPassword(String password) {
        return stringEncryptor.encrypt(password);
    }

	//@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 UserModel user = userRepository.findByUsername(username);
		    if (user == null) {
		        throw new UsernameNotFoundException("User not found with username: " + username);
		    }
		    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getUserPassword(), new ArrayList<>());
	}
}
