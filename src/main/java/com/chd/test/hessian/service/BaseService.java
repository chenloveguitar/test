package com.chd.test.hessian.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chd.test.hessian.api.BaseAPI;
import com.chd.test.hessian.domain.User;

public class BaseService implements BaseAPI{

	private static List<User> users = new ArrayList<User>(); 
	private Logger logger = LogManager.getLogger(getClass());
	
	@Override
	public String getVersion() {
		logger.info("log======>>>>>>:Method[getVersion]:params[]:return[1.0.0]");
		return "1.0.0";
	}

	@Override
	public User getUser() {
		logger.info("log======>>>>>>:Method[getUser]:params[]:return[chen,haidong]");
		return new User("chen","haidong");
	}

	@Override
	public List<User> getUsers() {
		logger.info("log======>>>>>>:Method[getUsers]:params[]:return["+users+"]");
		return users;
	}

	@Override
	public Integer saveUser(User user) {
		boolean add = users.add(user);
		logger.info("log======>>>>>>:Method[saveUser]:params["+(user)+"]:return["+(add ? 1 : -1)+"]");
		return add ? 1 : -1;
	}

	@Override
	public boolean deleteUser(User user) {
		boolean remove = users.remove(user);
		logger.info("log======>>>>>>:Method[deleteUser]:params["+(user)+"]:return["+(remove)+"]");
		return remove;
	}

	@Override
	public Integer updateUser(User user) {
		for (User u : users) {
			if(user.equals(u)) {
				u.setPassword(user.getPassword());
				u.setUsername(user.getUsername());
				logger.info("log======>>>>>>:Method[updateUser]:params["+(user)+"]:return["+(1)+"]");
				return 1;
			}
		}
		logger.info("log======>>>>>>:Method[updateUser]:params["+(user)+"]:return["+(-1)+"]");
		return -1;
	}

	@Override
	public Map<String, User> getMap() {
		User user = users.get(0);
		Map<String, User> map = new HashMap<String,User>();
		map.put("user", user);
		logger.info("log======>>>>>>:Method[getMap]:params[]:return["+(map)+"]");
		return map;
	}
}
