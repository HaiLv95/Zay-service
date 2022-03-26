package com.zayshop.controller.Admin;

import com.zayshop.entity.Account;
import com.zayshop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/account")
public class AccountController {


    @Autowired
    AccountService accountService;
    @Autowired
    PasswordEncoder passwordEncoder;



    @GetMapping("")
    public ResponseEntity getAllAccount() {
        return ResponseEntity.ok(accountService.findAllAccount());
    }

    @GetMapping("/{username}")
    public ResponseEntity getAccountByUsername(@PathVariable String username) {
        Optional<Account> account = accountService.findAccountByUsername(username);
        if (account.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(accountService.findAccountByUsername(username).get());
        }
    }

    @PostMapping("/add")
    public ResponseEntity saveAccount(@RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return ResponseEntity.ok(accountService.saveAccount(account));
    }

    @PutMapping("/update")
    public ResponseEntity updateAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateAccount(account));
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity deleteAccount(@PathVariable Optional<String> username) {
        if (username.isEmpty()) return ResponseEntity.badRequest().build();
        Optional<Account> account = accountService.findAccountByUsername(username.get());
        if (account.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            account.get().setActivated(false);
            accountService.deleteAccount(account.get());
            return ResponseEntity.ok().build();
        }
    }
}
