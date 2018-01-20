package com.chd.test.hessian.api;

import java.util.List;
import java.util.Map;

import com.chd.test.hessian.domain.User;

public interface BaseAPI {

	String getVersion();
	
	User getUser();
	
	List<User> getUsers();
	
	boolean deleteUser(User user);
	
	Integer saveUser(User user);
	
	Integer updateUser(User user);

	Map<String, User> getMap();
	
}
