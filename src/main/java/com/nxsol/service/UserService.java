package com.nxsol.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nxsol.entity.User;

public interface UserService extends IService<User>,IFinder<User>{
	
	Page<User> search(Pageable pageable, String searchText) throws Exception;

	User loadUserByUsername_(String username);
}
