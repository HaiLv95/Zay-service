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
@CrossOrigin(origins = "http://localhost:8080/")
public class LoginController {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    UserDetailServiceImpl userDetailService;
    @PostMapping(value = "/logon")
    public JwtResponse postLogin(@RequestBody JwtRequest jwtRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
            e.printStackTrace();
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
