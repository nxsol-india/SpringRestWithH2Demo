package com.nxsol.controller;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.nxsol.entity.User;
import com.nxsol.model.ErrorDetails;
import com.nxsol.service.UserFriendService;
import com.nxsol.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("user")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserFriendService userFriendService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
	  logger.error("---error :: - "+ex.getMessage());
	  ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	      request.getDescription(false));
	  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiOperation(value = "get all user maching search", notes = "get all user maching search")
	@GetMapping("/search/")
	public Page<User> search(Pageable pageable, @RequestParam(value = "searchText", required=false) String searchText) throws Exception {
		logger.info("---search :: - ");
		return userService.search(pageable, searchText);
	}
	
	@ApiOperation(value = "get user model by id", notes = "get user model by id")
	@ApiImplicitParam(name = "id", value = "user id", required = true, dataType = "Long", paramType = "path")
	@GetMapping("/{id}")
	public User read(@PathVariable Long id) throws Exception {
		logger.info("---read :: - ");
		return userService.findById(id).orElse(null);
	}
	
	@ApiOperation(value = "get all ", notes = "get all ")
	@GetMapping()
	public Page<User> readAll(Pageable pageable) throws Exception {
		logger.info("---readAll :: - ");
		return userService.findAll(pageable);
	}
	
	@ApiOperation(value = "save user", notes = "save user")
	@ApiParam(name = "user", value = "user", required = true)
	@PostMapping()
	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	public ResponseEntity<?> create(@RequestBody User user) throws Exception {
		logger.info("---create :: - ");
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		userService.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@ApiOperation(value = "update user", notes = "update user")
	@ApiParam(name = "user", value = "user update", required = true)
	@PutMapping("/{id}")
	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) throws Exception {
		logger.info("---update :: - ");
		User entity = userService.findById(id).orElse(null);
		if (entity == null)
			return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
		user.setId(entity.getId());
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		userService.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "delete user", notes = "delete user")
	@ApiParam(name = "id", value = "Long", required = true)
	@DeleteMapping("/{id}")
	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
		logger.info("---delete :: - ");
		User entity = userService.findById(id).orElse(null);
		if (entity == null)
			return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
		
		if(userFriendService.deleteAllUserRelatedFriend(id) >= 1)
			userService.delete(id);
		
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
}
