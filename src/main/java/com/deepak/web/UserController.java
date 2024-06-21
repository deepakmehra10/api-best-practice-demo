package com.deepak.web;

import com.deepak.model.User;
import com.deepak.service.JwtService;
import com.deepak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/auth/login")
    public String loginUser(@RequestBody User user) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user
                .getUserName(), user.getPassword()));

        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(user.getUserName());
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @PostMapping("/auth/register")
    public String registerUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
