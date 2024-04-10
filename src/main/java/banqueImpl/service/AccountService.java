package banqueImpl.service;

import banqueImpl.dao.Account;
import banqueImpl.dao.Operation;
import banqueImpl.exception.ResourceNotFoundException;
import banqueImpl.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account account = getAccount(id);
        account.setBalance(accountDetails.getBalance());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        Account account = getAccount(id);
        accountRepository.delete(account);
    }

    public Operation withdraw(Long accountId, Double amount) {
        Account account = getAccount(accountId);
        if (account.getBalance() < amount) {
            throw new RuntimeException("Not enough balance");
        }
        Operation operation = new Operation();
        operation.setAmount(-amount);
        operation.setDate(LocalDateTime.now());
        operation.setAccount(account);
        account.setBalance(account.getBalance() - amount);
        account.getOperations().add(operation);
        accountRepository.save(account);
        return operation;
    }

    public Operation transfer(Long fromAccountId, Long toAccountId, Double amount) {
        Account fromAccount = getAccount(fromAccountId);
        Account toAccount = getAccount(toAccountId);
        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Not enough balance");
        }
        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setDate(LocalDateTime.now());
        operation.setAccount(toAccount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        toAccount.getOperations().add(operation);
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        return operation;
    }
}

