package com.aninfo;

import com.aninfo.model.Account;
import com.aninfo.service.AccountService;
import com.aninfo.model.Transactions;
import com.aninfo.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionsService transactionService;
	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/accounts")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/accounts")
	public Collection<Account> getAccounts() {
		return accountService.getAccounts();
	}

	@GetMapping("/accounts/{cbu}")
	public ResponseEntity<Account> getAccount(@PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		return ResponseEntity.of(accountOptional);
	}

	@PutMapping("/accounts/{cbu}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);

		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		account.setCbu(cbu);
		accountService.save(account);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/accounts/{cbu}")
	public void deleteAccount(@PathVariable Long cbu) {
		accountService.deleteById(cbu);
	}
/*
	@PutMapping("/accounts/{cbu}/withdraw")
	public Account withdraw(@PathVariable Long cbu, @RequestParam Double sum) {
		return accountService.withdraw(cbu, sum);
	}

	@PutMapping("/accounts/{cbu}/deposit")
	public Account deposit(@PathVariable Long cbu, @RequestParam Double sum) {
		return accountService.deposit(cbu, sum);
	}
*/
	/********************************/
	@PostMapping("/transactions")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Transactions> createTransaction(@RequestBody Transactions transaction) {
		Optional<Account> accountOptional = accountService.findById(transaction.getCbuTransacition());
		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}//me traigo el validador del upgrade de cuenta
		//me traigo el deposit y withraw aca:
		if("deposit".equals(transaction.getTransactionType())){
			accountService.deposit(transaction.getCbuTransacition(), transaction.getAmount());
		}else{
			accountService.withdraw(transaction.getCbuTransacition(), transaction.getAmount());
		}
		transactionService.createTransaction(transaction);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/transactions")
	public Collection<Transactions> getTransactions() {
		return transactionService.getTransactions();
	}

	@GetMapping("/transactions/byCBU/{cbu}")
	public Collection<Transactions> getTransactionsByCBU(@PathVariable long cbu) {
		return transactionService.getTransactionsByCbu(cbu);
	}

	@GetMapping("/transactions/byID/{id}")
	public ResponseEntity<Transactions> getTransactionsById(@PathVariable long id) {
		Optional<Transactions> transactionsOptional = transactionService.findById(id);
		return ResponseEntity.of(transactionsOptional);
	}
	@DeleteMapping("/transactions/{id}")
	public void deleteTransaction(@PathVariable Long id){
		transactionService.deleteById(id);
	}



	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}


}
