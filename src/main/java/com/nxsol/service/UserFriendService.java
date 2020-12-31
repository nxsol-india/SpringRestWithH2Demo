package com.nxsol.service;

import java.util.List;

import com.nxsol.entity.User;
import com.nxsol.entity.UserFriend;
import com.nxsol.model.UserFriendRequest;

public interface UserFriendService  extends IService<UserFriend>,IFinder<UserFriend>{

	List<UserFriend> findAll(Long userId);

	List<User> suggestionFriend(Long userId);

	UserFriend findByUserIdAndFriendId(Long userId,Long friendId);

	void deleteUserFriend(UserFriendRequest userFriendRequest);

	int deleteAllUserRelatedFriend(Long userId);

}
