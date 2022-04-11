package com.zayshop.controller.Admin;

import com.zayshop.dto.AccountDTO;
import com.zayshop.entity.Account;
import com.zayshop.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
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
        AccountDTO dto = new AccountDTO();
        if (account.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            BeanUtils.copyProperties(account.get(), dto);
            return ResponseEntity.ok(dto);
        }
    }

    @PostMapping("/add")
    public ResponseEntity saveAccount(@RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return ResponseEntity.ok(accountService.saveAccount(account));
    }

    @PutMapping("/update")
    public ResponseEntity updateAccount(@RequestBody AccountDTO dto) {
        Account account = new Account();
        Optional<Account> checkAcc = accountService.findAccountByUsername(dto.getUsername());
        if (checkAcc.isEmpty()) return ResponseEntity.badRequest().build();
        BeanUtils.copyProperties(dto, account);
        account.setPassword(checkAcc.get().getPassword());
        accountService.updateAccount(account);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity deleteAccount(@PathVariable Optional<String> username) {
        if (username.isEmpty()) return ResponseEntity.badRequest().build();
        Optional<Account> account = accountService.findAccountByUsername(username.get());
        if (account.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            account.get().setActivated(!account.get().getActivated());
            Account accountDelete = accountService.deleteAccount(account.get());
            return ResponseEntity.ok(accountDelete);
        }
    }
}
