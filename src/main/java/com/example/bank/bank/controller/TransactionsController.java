package com.example.bank.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bank.bank.entity.accounts;
import com.example.bank.bank.model.AccountHolderBalance;
import com.example.bank.bank.model.Deposit;
import com.example.bank.bank.model.Withdraw;
import com.example.bank.bank.service.AccountsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/transactions")
public class TransactionsController {

    private AccountsService accountsService;

    public TransactionsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/")
    public String showTransactionsPage() {
        return "accounts/transactions";
    }
    

    @GetMapping("/showformwithdrawal")
    public String withdrawMoney(Model model) {
        Withdraw withdraw = new Withdraw();
        model.addAttribute("withdraw", withdraw);
        return "accounts/withdraw-form";
    }

    @GetMapping("/showformdeposit")
    public String deppositMoney(Model model) {
        Deposit withdraw = new Deposit();
        model.addAttribute("deposit", withdraw);
        return "accounts/deposit-form";
    }

    @PostMapping("/withdraw")
    public String withdraw(@ModelAttribute("withdraw") Withdraw withdraw,Model model){
        long accountNum = withdraw.getAccountNumber();
        accounts foundacc = accountsService.findAccountByAccountNumber(accountNum);
        double accbalance = foundacc.getBalance();
        double newbalance = accbalance - withdraw.getAmount();
        foundacc.setBalance(newbalance);
        AccountHolderBalance balancemodel = new AccountHolderBalance();
        balancemodel.setFirstName(foundacc.getFirstName());
        balancemodel.setLastName(foundacc.getLastName());
        balancemodel.setAccountNumber(accountNum);
        balancemodel.setBalance(newbalance);
        accountsService.save(foundacc);
        model.addAttribute("accountStatus", balancemodel);
        return "accounts/balance";
    }
    

    @PostMapping("/deposit")
    public String deposit(@ModelAttribute("deposit") Deposit deposit,Model model){
        long accountNum = deposit.getAccountNumber();
        accounts foundacc = accountsService.findAccountByAccountNumber(accountNum);
        double accbalance = foundacc.getBalance();
        double newbalance = deposit.getAmount() + accbalance;
        foundacc.setBalance(newbalance);
        AccountHolderBalance balancemodel = new AccountHolderBalance();
        balancemodel.setFirstName(foundacc.getFirstName());
        balancemodel.setLastName(foundacc.getLastName());
        balancemodel.setAccountNumber(accountNum);
        balancemodel.setBalance(newbalance);
        accountsService.save(foundacc);
        model.addAttribute("accountStatus", balancemodel);
        return "accounts/balance";
    }
    
}
