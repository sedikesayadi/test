package banqueImpl.controller;

import banqueImpl.dao.Account;
import banqueImpl.dao.Operation;
import banqueImpl.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
        return ResponseEntity.ok(accountService.updateAccount(id, accountDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Operation> withdraw(@PathVariable Long accountId, @RequestBody Double amount) {
        return ResponseEntity.ok(accountService.withdraw(accountId, amount));
    }

    @PostMapping("/{fromAccountId}/transfer/{toAccountId}")
    public ResponseEntity<Operation> transfer(@PathVariable Long fromAccountId, @PathVariable Long toAccountId, @RequestBody Double amount) {
        return ResponseEntity.ok(accountService.transfer(fromAccountId, toAccountId, amount));
    }
}
