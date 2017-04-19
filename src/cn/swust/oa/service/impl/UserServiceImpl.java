package cn.swust.oa.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import cn.swust.oa.base.DaoSupportImpl;
import cn.swust.oa.domain.User;
import cn.swust.oa.service.UserService;

@Service
public class UserServiceImpl extends DaoSupportImpl<User> implements UserService {
	public User findByNameAndPassword(String loginName, String password) {
		String md5Digest = DigestUtils.md5Hex(password);
		return (User) getSession().createQuery(// 
				 "FROM User u WHERE u.loginName = ? AND u.password = ?")//
				 .setParameter(0, loginName)//
				 .setParameter(1, md5Digest)//
				 .uniqueResult();
	}
		
}
