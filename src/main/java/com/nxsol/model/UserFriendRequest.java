package com.nxsol.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFriendRequest {
	
	private Long userId;
	private List<Long> friendId;

}
