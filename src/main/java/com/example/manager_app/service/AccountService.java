package com.example.manager_app.service;

import com.example.manager_app.dto.AccountRespone;
import com.example.manager_app.model.Account;
import com.example.manager_app.repository.AccountRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    public void fetchAccount(){
        RestTemplate restTemplate=new RestTemplate();
        String apiUrl="http://localhost:8081/api/Account/accounts";
        ResponseEntity<Account[]>responseEntity=restTemplate.getForEntity(apiUrl,Account[].class);
        Account[] accounts=responseEntity.getBody();
        for (Account account:accounts) {
            accountRepository.save(account);
        }
    }
    public List<Account> readAccounts() {
        ObjectMapper objectMapper = new ObjectMapper();
        // chuyển đổi kiểu dữ liệu mà objectmapper chuyển đổi từ json thành list đối tượng
        TypeReference<List<Account>> typeReference = new TypeReference<List<Account>>() {};
        //
        InputStream inputStream = TypeReference.class.getResourceAsStream("/account.json");

        try {
            //đọc ữ liệu từ file
            List<Account> accounts = objectMapper.readValue(inputStream, typeReference);
            return accounts;
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc tệp JSON: " + e.getMessage());
            return null;
        }
    }
    public List<AccountRespone>getAll(){
        List<Account>listAccounts=accountRepository.findAll();
        List<AccountRespone>list=new ArrayList<>();
        for (Account item:listAccounts) {
            AccountRespone accountRespone=new AccountRespone();
            accountRespone.setId(item.getId());
            accountRespone.setUsername(item.fullName());
            accountRespone.setEmail(item.getEmail());
            list.add(accountRespone);
        }
        return list;
    }
}
