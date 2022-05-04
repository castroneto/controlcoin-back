package coltrolcoin.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;

import coltrolcoin.models.JwtResponse;
import coltrolcoin.models.User;
import coltrolcoin.models.dtos.LoginRequestDTO;
import coltrolcoin.models.dtos.SignupRequestDTO;
import coltrolcoin.repository.UserRepository;
import coltrolcoin.utils.EncryptUtil;
import coltrolcoin.utils.JwtTokenUtil;

@RestController
@Validated
public class Autentication {
	private final UserRepository repository;
	private JwtTokenUtil tokenUtil;
	private EncryptUtil encryptUtil;
	
	  Autentication(UserRepository repository, JwtTokenUtil tokenUtil, EncryptUtil encryptUtil) {
		    this.repository = repository;
		    this.tokenUtil = tokenUtil;
		    this.encryptUtil = encryptUtil;
	  }
	  
	  
	  
	  @RequestMapping(value = "/login", method = RequestMethod.POST)
	  ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
		  try {
			  Optional<User> user = this.repository.findByEmail(request.email);
			  if(user.isPresent()) {
				 				  
				 boolean isValid = this.encryptUtil.compare(request.password, user.get().getPassword());
				 if(isValid) {
					 String token = this.tokenUtil.generateToken(user.get());
					 return ResponseEntity.ok(new JwtResponse(token, this.tokenUtil));
				 } else {
					 return ResponseEntity.status(401).body("Password does Not match");
				 }
			  } else {
				 return ResponseEntity.status(401).body("User Not Found");
			  }
		  } catch (Error error) {
			return ResponseEntity.ok(error.getMessage());
		  }
	  }
	  
	  @RequestMapping(value = "/signup", method = RequestMethod.POST)
	  ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDTO request) {
		  try {
			  	Optional<User> user = this.repository.findByEmail(request.email);
			  	
			  	
			  	if(user.isPresent()) {
			  		return ResponseEntity.status(401).body("User Already Present");
			  	}
			  	
				User newUser = new User();
				newUser.setUsername(request.getUsername());
				newUser.setPassword(this.encryptUtil.encode(request.getPassword()));
				newUser.setEmail(request.getEmail());
				this.repository.save(newUser);
				
				String token = this.tokenUtil.generateToken(newUser);
	
				return ResponseEntity.ok(new JwtResponse(token, this.tokenUtil));
		  } catch (Error error) {
			return ResponseEntity.ok(error.getMessage());
		  }
	  }
	  
				

}
