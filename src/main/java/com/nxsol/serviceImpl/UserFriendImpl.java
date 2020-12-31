package com.nxsol.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxsol.entity.User;
import com.nxsol.entity.UserFriend;
import com.nxsol.model.UserFriendRequest;
import com.nxsol.repository.UserFriendRepository;
import com.nxsol.repository.UserRepository;
import com.nxsol.service.BasicService;
import com.nxsol.service.UserFriendService;

@Service
public class UserFriendImpl extends BasicService<UserFriend, UserFriendRepository> implements UserFriendService {

	@Autowired
	private UserRepository userRepository;
	
	public List<UserFriend> findAll(Long userId){
		return repository.findAll(userId);
	}

	@Override
	public List<User> suggestionFriend(Long userId) {
		return userRepository.suggestionFriend(userId);
	}

	@Override
	public UserFriend findByUserIdAndFriendId(Long userId, Long friendId) {
		return repository.findByUserIdFriendId(userId, friendId);
	}

	@Override
	public void deleteUserFriend(UserFriendRequest userFriendRequest) {
		repository.deleteUsersWithIds(userFriendRequest.getUserId(), userFriendRequest.getFriendId());
	}

	@Override
	public int deleteAllUserRelatedFriend(Long userId) {
		return repository.deleteAllUserRelatedFriend(userId);
	}
}
