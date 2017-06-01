package com.animus.delta;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {
	
	private final AccountRepository accountRepository;
	
	
	@Autowired
	AccountRestController(AccountRepository accountRepository){
		this.accountRepository = accountRepository;
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add( @RequestBody Account input) {
		System.out.println("input.username=" + input.username);
		System.out.println("input.password=" + input.password);
		Account result = accountRepository.save(new Account(input.username,input.password));
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

}
