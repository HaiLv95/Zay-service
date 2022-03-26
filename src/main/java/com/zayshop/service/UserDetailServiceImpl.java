package com.zayshop.service;

import com.zayshop.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Kiem tra user co ton  tai khong
        Optional<Account> account = accountService.findAccountByUsername(username);
        if (account.isEmpty()) throw new UsernameNotFoundException(username);
        List<GrantedAuthority> authorityList =new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(account.get().getRole().toUpperCase()));
        User user =new User(account.get().getUsername(), account.get().getPassword(), authorityList);
        return user;
    }
}
