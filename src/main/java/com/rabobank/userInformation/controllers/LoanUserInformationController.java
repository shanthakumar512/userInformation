/**
 * 
 */
package com.rabobank.userinformation.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.userinformation.exceptions.LoanUserNotFoundException;
import com.rabobank.userinformation.exceptions.UserDetailsAlreadyExistForEmailIDException;
import com.rabobank.userinformation.model.LoanUser;
import com.rabobank.userinformation.request.LoanUserRequest;
import com.rabobank.userinformation.services.LoanUsersService;

/**
 * @author Shanthakumar
 *
 */

@RestController
@RequestMapping("/loanUser")
public class LoanUserInformationController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoanUserInformationController.class);
	
	@Autowired
	LoanUsersService  loanUsersService;
	
	@PostMapping("/addLoanUser")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<LoanUser>> addLoanUser(@Valid @RequestBody LoanUserRequest loanUserRequest) throws UserDetailsAlreadyExistForEmailIDException{		
		logger.info("Add user information request with User Name {} User Email {} ", loanUserRequest.getUserFirstname(),loanUserRequest.getUserEmail());
	
		loanUsersService.addLoanUser(loanUserRequest);
		logger.info("Loan user information Added successfullly"); 
		List<LoanUser> loanUsers= loanUsersService.findAllUsers();
		logger.info("Total users added {}", loanUsers.size());
		return  new ResponseEntity<>(loanUsers, HttpStatus.OK);
	}
	
	@GetMapping("/getLoanUsers")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<LoanUser>> getLoanUser(){		
		logger.info("Entered request for find all Loan users");
		return new ResponseEntity<> (loanUsersService.findAllUsers(), HttpStatus.OK);
	}
	
	@GetMapping("/getLoanUserByFirstName/{userFirstName}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<LoanUser> getLoanUserByFirstName(@PathVariable String userFirstName) throws LoanUserNotFoundException{	
		logger.info("Search request for Loan with firstName :{} ", userFirstName);
		return new ResponseEntity<> (loanUsersService.findByFirstName(userFirstName), HttpStatus.OK);
	}
	
	@GetMapping("/getLoanUserByLastName/{userLastName}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<LoanUser> getLoanUserByLastName(@PathVariable String userLastName) throws LoanUserNotFoundException{		
		logger.info("Search request for Loan with Last Name :{} ", userLastName);
		return new ResponseEntity<> (loanUsersService.findByLastName(userLastName), HttpStatus.OK);
	}
}