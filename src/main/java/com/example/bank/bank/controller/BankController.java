package com.example.bank.bank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bank.bank.entity.accounts;
import com.example.bank.bank.model.Withdraw;
import com.example.bank.bank.service.AccountsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/accounts")
public class BankController {

    static double balance;
    static long accnum;
    static long accountNumber;

    private AccountsService accountsService;

    public BankController(AccountsService accountsService) {
        this.accountsService = accountsService;
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
        accountsService.save(account);
        return "redirect:/accounts/list";
    }

    @GetMapping("/deleteaccount{accountId}")
    public String deleteAcc(@RequestParam int accountId){
        accountsService.deleteAccount(accountId);
        return "redirect:/accounts/list";
    }
    
}
