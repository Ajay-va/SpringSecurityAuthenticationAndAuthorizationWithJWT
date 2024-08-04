package com.spring.security.controller;

import com.spring.security.entities.LoginForm;
import com.spring.security.entities.User;
import com.spring.security.repositories.UserRepository;
import com.spring.security.service.JWTService;
import com.spring.security.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContentController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/home")
    public String home(){
        return "Welcome to Home Page...!!!";
    }


    @GetMapping("/admin/home")
    public String handleAdminHome(){
        return "Welcome to ADMIN Page...!!!";
    }

    @GetMapping("/user/home")
    public String handleUserHOme(){
        return "Welcome to User Page...!!!";
    }

    @PostMapping("/user/save")
    public User saveUser(@RequestBody User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User userData=userRepository.save(user);
        return userData;
    }

    @PostMapping("/authenticate")
    public String authentication(@RequestBody LoginForm loginForm){

      Authentication authenticate =  authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(loginForm.username(),loginForm.password()));

      if(authenticate.isAuthenticated()){
       return    jwtService.generateKey(userService.loadUserByUsername(loginForm.username()));
      }else {
          throw new UsernameNotFoundException("Invalid Credentials for the User");
      }
    }


}