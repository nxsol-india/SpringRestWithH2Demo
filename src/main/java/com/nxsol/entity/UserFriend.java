package com.nxsol.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "USER_FRIEND")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFriend {

	@EmbeddedId
    private UserFriendPK id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_id")
    private User user;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("friend_id")
    private User friend;

	public UserFriendPK getId() {
		return id;
	}

	public void setId(UserFriendPK id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}

	public UserFriend() {
	}
	
	public UserFriend(UserFriendPK id) {
		super();
		this.id = id;
	}
	
	public UserFriend(UserFriendPK id, User user, User friend) {
		super();
		this.id = id;
		this.user = user;
		this.friend = friend;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserFriend other = (UserFriend) obj;
		if (friend == null) {
			if (other.friend != null)
				return false;
		} else if (!friend.equals(other.friend))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((friend == null) ? 0 : friend.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	public UserFriend(User user, User friend) {
		super();
		this.user = user;
		this.friend = friend;
	}
    
}
