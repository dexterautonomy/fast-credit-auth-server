package com.hingebridge.devops.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hingebridge.devops.exceptions.AuthException;
import com.hingebridge.devops.models.User;
import com.hingebridge.devops.repositories.UserRepo;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);

		if (!user.isPresent()) {
			throw new AuthException("Username does not exist");
		}

		return new UserDetailsClass(user.get());
	}
}
