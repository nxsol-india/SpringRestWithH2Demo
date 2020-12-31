package com.nxsol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nxsol.entity.UserFriend;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long>,JpaSpecificationExecutor<UserFriend>{
	
	@Query("select u From UserFriend u where u.user.id like ?1")
	List<UserFriend> findAll(Long userId);
	
	@Query(value = "SELECT uf from UserFriend uf WHERE uf.user.id = ?1 AND uf.friend.id = ?2")
	UserFriend findByUserIdFriendId(Long userId, Long friendId);
	
	@Modifying
	@Query("DELETE from UserFriend uf where uf.id.user_id = ?1 AND uf.id.friend_id in (?2)")
	void deleteUsersWithIds(Long userId, List<Long> friendids);
	
	@Modifying
	@Query(value = "DELETE FROM UserFriend uf WHERE uf.id.user_id = ?1")
	Integer deleteAllUserRelatedFriend(Long userId);


}
