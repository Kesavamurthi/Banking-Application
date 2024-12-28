package com.example.bank.bank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bank.bank.entity.Users;
import com.example.bank.bank.entity.accounts;
import com.example.bank.bank.model.Withdraw;
import com.example.bank.bank.service.AccountsService;
import com.example.bank.bank.service.UsersService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class BankController {

    static double balance;
    static long accnum;
    static long accountNumber;

    private AccountsService accountsService;
    private UsersService usersService;

    public BankController(AccountsService accountsService, UsersService theusersService) {
        this.accountsService = accountsService;
        this.usersService = theusersService;
    }

    @GetMapping("/")
    public String showHomePage(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        accounts account = accountsService.findAccountByUsername(username);
        model.addAttribute("account", account);
        return "accounts/home";
    }
    
    @GetMapping("/list")
    public String accountList(Model model) {
        List<accounts> accounts = accountsService.findAll();
        model.addAttribute("accounts", accounts);
        return "accounts/list";
    }

    @GetMapping("/showform")
    public String showform(Model model) {
        accounts account = new accounts();
        model.addAttribute("accounts", account);
        return "accounts/create-form";
    }

    @GetMapping("/showformforupdate{accountId}")
    public String showformforupdate(@RequestParam int accountId,Model model) {
        accounts foundAcc = accountsService.findById(accountId);
        accnum = foundAcc.getAccountNumber();
        balance = foundAcc.getBalance();
        model.addAttribute("accounts", foundAcc);
        return "accounts/create-form";
    }
    

    @PostMapping("/processform")
    public String processForm(@ModelAttribute("accounts") accounts account) {
        accounts accToFindLastAccNum = accountsService.findLastAccountNumber();
        long lastAccnum = 0L;
        if(account.getId() == 0){
            if(accToFindLastAccNum == null){
                accountNumber=201400;
            }else{
                lastAccnum = accToFindLastAccNum.getAccountNumber();
                accountNumber = lastAccnum + 1;
            }
            account.setBalance(5000);
            account.setAccountNumber(accountNumber);
        }
        else{
            account.setBalance(balance);
            account.setAccountNumber(accnum);
        }
        Users user = new Users();
        user.setUsername(account.getFirstName());
        user.setPassword("{noop}"+account.getFirstName());
        user.setEnabled(1);
        usersService.save(user);
        accountsService.save(account);
        return "redirect:/list";
    }

    @GetMapping("/deleteaccount{accountId}")
    public String deleteAcc(@RequestParam int accountId){
        accountsService.deleteAccount(accountId);
        return "redirect:/list";
    }
    
}
