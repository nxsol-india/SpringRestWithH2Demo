package com.nxsol.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nxsol.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{

	@Query("select u From User u where u.firstname like ?1 or u.lastname like ?1 or u.username like ?1")
	Page<User> search(Pageable pageable, String searchText);

	@Query("select u From User u where u.username like ?1")
	User loadUserByUsername(String username);

	@Query(
			  value = "SELECT * FROM USER u "
			  		+ " WHERE u.id not in (SELECT uf.FRIEND_ID FROM USER_FRIEND uf where uf.USER_ID = (:id)) "
			  		+ " and u.id not in (:id) and u.role != 'ADMIN' ", 
			  nativeQuery = true)
	List<User> suggestionFriend(@Param("id") Long id);
}
