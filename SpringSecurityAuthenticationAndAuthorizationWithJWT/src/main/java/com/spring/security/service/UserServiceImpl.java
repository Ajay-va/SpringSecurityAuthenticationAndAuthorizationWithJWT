package com.spring.security.service;

import com.spring.security.entities.User;
import com.spring.security.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      Optional<User> user= userRepository.findByUsername(username);

      LOGGER.info("user deatails from "+LOGGER.getName()+"{}",user.get().toString());
      if(user.isPresent()){

          LOGGER.info("user is present {}",user.get().getUsername());
          var userData=user.get();
         return org.springframework.security.core.userdetails.User.builder()
                  .username(userData.getUsername())
                  .password(userData.getPassword())
                  .roles(getRoles(userData))
                  .build();
      }else {
          LOGGER.info("invalid user "+LOGGER.getName()+user.get().getUsername());
          throw new UsernameNotFoundException("User not identified...!!!!");
      }
    }
    private String[] getRoles(User userData) {
        if(userData.getRoles()==null){
           return new String[]{"USER"};
        }else {
           return  userData.getRoles().split(",");
        }
    }
}
