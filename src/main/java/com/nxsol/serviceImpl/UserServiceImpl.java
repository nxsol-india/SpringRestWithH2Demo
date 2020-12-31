package com.nxsol.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nxsol.entity.User;
import com.nxsol.repository.UserRepository;
import com.nxsol.service.BasicService;
import com.nxsol.service.UserService;

@Service
public class UserServiceImpl extends BasicService<User, UserRepository> implements UserService, UserDetailsService {

	public Page<User> search(Pageable pageable, String searchText) {
		String queriableText = new StringBuilder("%").append(searchText == null ? "" : searchText).append("%").toString();
		return repository.search(pageable, queriableText);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.loadUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
		List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorityList);
	}
	
	@Override
	public User loadUserByUsername_(String username)  {
		return repository.loadUserByUsername(username);
	}

}
