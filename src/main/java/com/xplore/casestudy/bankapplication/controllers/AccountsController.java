package com.xplore.casestudy.bankapplication.controllers;

import com.xplore.casestudy.bankapplication.models.Account;
import com.xplore.casestudy.bankapplication.models.AccountStatus;
import com.xplore.casestudy.bankapplication.models.Customer;
import com.xplore.casestudy.bankapplication.models.Transaction;
import com.xplore.casestudy.bankapplication.services.AccountService;
import com.xplore.casestudy.bankapplication.services.CustomerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Controller
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService accountService;
    private final CustomerService customerService;

    public AccountsController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }


    //Create Accounts

    @GetMapping("/create_account")
    public String createAccountPage(Model model) {
        Account holder = new Account();
        List<Customer> customerList = this.customerService.getAllCustomer();
        model.addAttribute("account", holder);
        model.addAttribute("customerList", customerList);
        return "executive/create_account";
    }

    @PostMapping(path = "/create_account", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String createAccount(Account account, Model model) {
        Account holder = new Account();
        List<Customer> customerList = this.customerService.getAllCustomer();
        model.addAttribute("account", holder);
        model.addAttribute("customerList", customerList);

        try {
            this.accountService.createAccounts(account);
            model.addAttribute("message", "Account Created Successfully!!");
        } catch (Exception e) {
            model.addAttribute("message", "An error has occurred!!");
        }
        return "redirect:/accounts/create_account";
    }

    //delete account
    @GetMapping("/delete_account")
    public String deleteAccountPage(Model model) {
        Long reqBody = 1L;
        model.addAttribute("reqBody", reqBody);
        List<Long> accountList = this.accountService.getAccountIds();
        model.addAttribute("accountList", accountList);
        return "executive/delete_account";
    }

    @PostMapping(path = "/delete_account", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String deleteAccount(@RequestParam Map<String, String> id, Model model) {
        if (id == null) {
            model.addAttribute("message", "An error has occurred!!");
            return "redirect:/accounts/delete_account";
        }

        try {
            this.accountService.deleteAccountById(id.get("id"));
            model.addAttribute("message", "Account deletion completed!!");
        } catch (Exception e) {
            model.addAttribute("message", "An error has occurred!!");
        }
        return "redirect:/accounts/delete_account";
    }

    //account status

    @GetMapping("/account_status")
    public String accountStatusPage(Model model) {
        List<AccountStatus> accountStatuses = this.accountService.getAllAccountStatus();
        model.addAttribute("statusList", accountStatuses);
        return "executive/account_status";
    }


    //cashier controllers

    //search account
    @GetMapping("/search_account")
    public String searchAccountPage(Model model) {
        List<Long> accounts = this.accountService.getAccountIds();
        model.addAttribute("accountIds", accounts);
        return "cashier/search_account";
    }

    @PostMapping(path = "/search_account", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String searchAccountRedirect(@RequestParam Map<String, String> param, Model model) {
        Account account = null;
        if (param.containsKey("accountId")) {
            account = this.accountService.getAccountById(param.get("accountId"));
        } else if (param.containsKey("customerId")) {
            account = this.accountService.getAccountByCustomerId(param.get("accountId"));
        }
        if (account == null)
            throw new RuntimeException();
        return "redirect:/accounts/deposit_money/" + account.getAccountId();
    }

    //deposit money
    @GetMapping(path = "/deposit_money/{id}")
    public String depositMoneyPage(@PathVariable("id") String accountId, Model model) {
        Account account = this.accountService.getAccountById(accountId);
        if (account == null)
            throw new RuntimeException();

        model.addAttribute("account", account);
        model.addAttribute("accountId", account.getAccountId());
        return "cashier/deposit_money";
    }

    @PostMapping(path = "/deposit_money/{id}")
    public String depositMoney(@PathVariable("id") String accountId,
                               @RequestParam Map<String, String> params, Model model) {
        Account account = this.accountService.getAccountById(accountId);
        if (account == null) {
            model.addAttribute("message", "Account not found!!");
            return "redirect:/accounts/deposit_money/" + accountId;
        }
        if (params.containsKey("amount")) {
            int amount = Integer.parseInt(params.get("amount"));
            if (amount < 0) {
                model.addAttribute("message", "Bad Request");
                return "redirect:/accounts/withdraw_money/" + accountId;
            }
            this.accountService.depositMoney(account, amount);
        } else {
            model.addAttribute("message", "Bad Request!!");
            return "redirect:/accounts/deposit_money/" + accountId;
        }
        model.addAttribute("message", "Money successfully deposited!!");

        return "redirect:/accounts/deposit_money/" + accountId;
    }


    //withdraw money
    @GetMapping(path = "/withdraw_money/{id}")
    public String withdrawMoneyPage(@PathVariable("id") String accountId, Model model) {
        Account account = this.accountService.getAccountById(accountId);
        if (account == null)
            throw new RuntimeException();

        model.addAttribute("account", account);
        model.addAttribute("accountId", account.getAccountId());
        return "cashier/withdraw_money";
    }

    @PostMapping(path = "/withdraw_money/{id}", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String withdrawMoney(@PathVariable("id") String accountId,
                                @RequestParam Map<String, String> params, Model model) {
        System.out.println("inside withdraw");
        Account account = this.accountService.getAccountById(accountId);
        if (account == null) {
            model.addAttribute("message", "Account not found!!");
            return "redirect:/accounts/withdraw_money/" + accountId;
        }
        if (params.containsKey("amount")) {
            int amount = Integer.parseInt(params.get("amount"));
            if (amount < 0) {
                model.addAttribute("message", "Bad Request");
                return "redirect:/accounts/withdraw_money/" + accountId;
            }
            if (account.getAccountBalance() - amount < 0) {
                model.addAttribute("message", "Not enough balance!!");
                return "redirect:/accounts/withdraw_money/" + accountId;
            }
            this.accountService.withdrawMoney(account, amount);
        } else {
            model.addAttribute("message", "Bad Request!!");
            return "redirect:/accounts/withdraw_money/" + accountId;
        }
        model.addAttribute("message", "Money successfully withdrawn!!");

        return "redirect:/accounts/withdraw_money/" + accountId;
    }

    //transfer money
    @GetMapping("/transfer_money/{id}")
    public String transferMoneyPage(@PathVariable("id") String accountId,
                                    @RequestParam Map<String, String> params, Model model) {
        Account account = this.accountService.getAccountById(accountId);
        if (account == null)
            throw new RuntimeException();
        List<Long> accountIds = this.accountService.getAccountIds();
        model.addAttribute("accountIds", accountIds);
        model.addAttribute("account", account);
        model.addAttribute("accountId", account.getAccountId());

        return "cashier/transfer_money";
    }

    @PostMapping(path = "/transfer_money/{id}", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String transferMoney(@PathVariable("id") String accountId,
                                @RequestParam Map<String, String> params, Model model) {
        Account account = this.accountService.getAccountById(accountId);
        Account target = null;
        if (params.containsKey("targetAccountId")) {
            String targetId = params.get("targetAccountId");
            if (targetId.equals(accountId)) {
                model.addAttribute("message", "Bad Request!!");
                return "redirect:/accounts/transfer_money/" + accountId;
            }
            target = this.accountService.getAccountById(targetId);
        } else {
            model.addAttribute("message", "Bad Request!!");
            return "redirect:/accounts/transfer_money/" + accountId;
        }
        if (account == null || target == null) {
            model.addAttribute("message", "Account not found!!");
            return "redirect:/accounts/transfer_money/" + accountId;
        }
        if (params.containsKey("amount")) {
            int amount = Integer.parseInt(params.get("amount"));
            if (amount < 0) {
                model.addAttribute("message", "Bad Request");
                return "redirect:/accounts/transfer_money/" + accountId;
            }
            if (account.getAccountBalance() - amount < 0) {
                model.addAttribute("message", "Not enough balance!!");
                return "redirect:/accounts/transfer_money/" + accountId;
            }
            this.accountService.withdrawMoney(account, amount);
            this.accountService.depositMoney(target, amount);
        } else {
            model.addAttribute("message", "Bad Request!!");
            return "redirect:/accounts/transfer_money/" + accountId;
        }
        model.addAttribute("message", "Money successfully transferred!!");
        return "redirect:/accounts/transfer_money/" + accountId;
    }


    /**
     *
     */
    @GetMapping("/statement_by_transaction_cnt/{id}")
    public String statementByNTransactionPage(@PathVariable("id") String id, Model model) {
        model.addAttribute("accountId", id);
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        model.addAttribute("list", list);
        return "cashier/statement_by_transaction";
    }

    @GetMapping("/statement_by_transaction_cnt/{id}/{number}")
    public String statementByNTransaction(@PathVariable("id") String id,
                                          @PathVariable("number") int number, Model model) {
        List<Transaction> transactions = this.accountService.getNTransactionById(id);
        if (transactions == null || transactions.isEmpty())
            return "";
        int to = Integer.min(transactions.size(), number);
        model.addAttribute("transactionList", transactions.subList(0, number));
        return "fragment::n-transaction-table";
    }

    //statement by date

    @GetMapping("/statement_by_transaction_date/{id}")
    public String statementByDatePage(@PathVariable("id") String id, Model model) {
        model.addAttribute("accountId", id);
        return "cashier/statement_by_date";
    }

    @GetMapping(path = "/statement_by_transaction_date_table/{id}")
    public String statementByDate(@PathVariable("id") String id,
                                  @RequestParam("start")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                          LocalDate start,
                                  @RequestParam("end")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                          LocalDate ending,
                                  Model model) {
        System.out.println(start+" "+ending);
        List<Transaction> transactions = this.accountService
                .getAllBetweenStartAndEnd(start, ending);
        if (transactions == null || transactions.isEmpty())
            return "";
        model.addAttribute("transactionList", transactions);
        return "fragment::n-transaction-table";
    }

}
