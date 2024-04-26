package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.AccountRespone;
import com.example.manager_app.model.Account;
import com.example.manager_app.repository.AccountRepository;
import com.example.manager_app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/Account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @GetMapping("/fetch")
    public ResponseEntity<?> fetchAccounts() {
        accountService.fetchAccount();
        return ResponseEntity.ok("Data fetched and saved successfully!");
    }
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = accountService.readAccounts();
        return ResponseEntity.ok(accounts);
    }
    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        List<AccountRespone>list=accountService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<Account> list=accountRepository.findById(id);
        return ResponseEntity.ok(list);
    }
    @DeleteMapping("/deleteall")
    public String deleteAllAccounts() {
        try {
            accountRepository.deleteAll();
            return "All accounts have been deleted successfully.";
        } catch (Exception e) {
            return "An error occurred while deleting accounts: " + e.getMessage();
        }
    }
}
