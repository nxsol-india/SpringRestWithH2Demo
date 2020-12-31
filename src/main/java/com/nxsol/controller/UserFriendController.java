package com.nxsol.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.nxsol.entity.User;
import com.nxsol.entity.UserFriend;
import com.nxsol.entity.UserFriendPK;
import com.nxsol.model.ErrorDetails;
import com.nxsol.model.UserFriendRequest;
import com.nxsol.service.UserFriendService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("userFriend")
public class UserFriendController {
	
	private static final Logger logger = LogManager.getLogger(UserFriendController.class);
	
	@Autowired
	private UserFriendService userFriendService;
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
	  logger.error("---error :: - "+ex.getMessage());
	  ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	      request.getDescription(false));
	  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiOperation(value = "Find My Friends by user_id", notes = "Find My Friends by user_id")
	@ApiImplicitParam(name = "id", value = "user_id", required = true, dataType = "Long", paramType = "path")
	@GetMapping("/{id}")
	public List<UserFriend> findMyFriend(@PathVariable Long id) throws Exception {
		logger.info("---findMyFriend :: - ");
		return userFriendService.findAll(id);
	}
	
	@ApiOperation(value = "Search Suggetion friend by user_id", notes = "Search Suggetion friend by user_id")
	@ApiImplicitParam(name = "id", value = "user_id", required = true, dataType = "Long", paramType = "path")
	@GetMapping("/suggetion/friend/{id}")
	public List<User> suggestionFriend(@PathVariable Long id) throws Exception {
		logger.info("---suggestionFriend :: - ");
		return userFriendService.suggestionFriend(id);
	}
	
	
	@ApiOperation(value = "save friend", notes = "save friend")
	@ApiParam(name = "user", value = "user", required = true)
	@PostMapping()
	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	public ResponseEntity<?> create(@RequestBody UserFriendRequest userFriendRequest) throws Exception {
		logger.info("---create :: - ");
		UserFriend entity = userFriendService.save(new UserFriend(
					new UserFriendPK(userFriendRequest.getUserId(), userFriendRequest.getFriendId().get(0)),
					User.builder().id(userFriendRequest.getUserId()).build(),
					User.builder().id(userFriendRequest.getFriendId().get(0)).build()
					));
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Reject friend", notes = "Reject friend")
	@ApiParam(name = "user", value = "user", required = true)
	@DeleteMapping("reject")
	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	public ResponseEntity<?> reject(@RequestBody UserFriendRequest userFriendRequest) throws Exception {
		userFriendService.deleteUserFriend(userFriendRequest);
		return new ResponseEntity<>(userFriendRequest, HttpStatus.OK);
	}
	
}
