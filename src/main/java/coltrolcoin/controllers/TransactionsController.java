package coltrolcoin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import coltrolcoin.models.Transactions;
import coltrolcoin.models.User;
import coltrolcoin.models.dtos.CreateTransactionDTO;
import coltrolcoin.repository.TransactionsRepository;
import coltrolcoin.repository.UserRepository;

@RestController
@Validated
public class TransactionsController {
	
	private final UserRepository userRepository;
	private final TransactionsRepository transactionsRepository;
	
	TransactionsController(UserRepository userRepository, TransactionsRepository transactionsRepositor) {
		this.userRepository = userRepository;
		this.transactionsRepository = transactionsRepositor;
	}
	
	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	ResponseEntity<?> CreateTransactions(@Valid @RequestBody CreateTransactionDTO request) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<Transactions> transactions = new ArrayList<>();
			Transactions transaction = new Transactions();
			
			transaction.setEntry(request.entry);
			transaction.setDescription(request.description);
			transaction.setValue(request.value);
			
			transactions.add(transaction);

			user.setTransactions(transactions);
			
			this.userRepository.save(user);
			
			return ResponseEntity.status(200).body(transactions);
			
		} catch(Error error) {
			return ResponseEntity.ok(error.getMessage());
		}
	
	}

	
	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	ResponseEntity<?> ListTransactions() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Transactions> transactions = user.getTransactions();
		
		return ResponseEntity.ok(transactions);
	}
	
	
	@RequestMapping(value = "/transactions/{id}", method = RequestMethod.DELETE)
	ResponseEntity<?> DeleteTransactions(@PathVariable(required=true,name="id") Number id) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Optional<Transactions> transaction = this.transactionsRepository.findTransactionByUserId(id, user.getId());
			
			if(transaction.isEmpty()) {
				return ResponseEntity.status(401).body(new Error("Permission denied"));   
			}
			
			this.transactionsRepository.deleteById(transaction.get().getId());
			
			return ResponseEntity.ok("Deletado Com Sucesso");
		} catch(Error error) {
			return ResponseEntity.ok(error.getMessage());
		}
	}
	
	@RequestMapping(value = "/transactions/resume", method = RequestMethod.GET)
	ResponseEntity<?> resumeTransactions() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    Map<String, Object> response = new HashMap<>();
		Optional<Float> input = this.transactionsRepository.findAndSumTransactionByUserId(true, user.getId());
		
		
		
		Optional<Float> output = this.transactionsRepository.findAndSumTransactionByUserId(false, user.getId());


		
	    response.put("input", input.orElse(0F));
	    
	    response.put("output", output.orElse(0F));
	    response.put("total", input.orElse(0F) - output.orElse(0F));
	    		
		return ResponseEntity.ok(response);
	}
	
}
