package com.zayshop.controller.User;

import com.zayshop.component.JwtUtility;
import com.zayshop.dto.JwtRequest;
import com.zayshop.dto.JwtResponse;
import com.zayshop.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    UserDetailServiceImpl userDetailService;


    @PostMapping("/login")
    public JwtResponse loginResponse(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails
                = userDetailService.loadUserByUsername(jwtRequest.getUsername());
        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
    }
    @GetMapping("/403")
    @ResponseBody
    public String loginError(){
        return "<h1>User not accept</h1>";
    }

}
