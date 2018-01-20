package com.chd.test.hessian.test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.chd.test.hessian.api.BaseAPI;
import com.chd.test.hessian.domain.User;

public class MainTest {

	public static void main(String[] args) throws Exception {
		HessianProxyFactory proxy = new HessianProxyFactory();
		BaseAPI api = (BaseAPI)proxy.create(BaseAPI.class, "http://localhost:9090/test/api");
		User user = api.getUser();
		String version = api.getVersion();
		System.out.println(user.getUsername() + " " + user.getPassword());
		System.out.println(version);
	}
}
