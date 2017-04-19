package cn.swust.oa.service;

import cn.swust.oa.base.DaoSupport;
import cn.swust.oa.domain.User;

public interface UserService extends DaoSupport<User> {

	User findByNameAndPassword(String loginName, String password);
	
}
