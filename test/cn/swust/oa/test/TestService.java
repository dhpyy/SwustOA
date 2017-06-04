package cn.swust.oa.test;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.swust.oa.dao.UserDao;
import cn.swust.oa.domain.User;

@Service
@Transactional
public class TestService {
	
	@Resource
	SessionFactory sessionFactory;
	
	@Resource
	UserDao userDao;
	
	// 测试事务
	public void saveTwoUsers() {
		Session session = sessionFactory.getCurrentSession();
		session.save(new User());
		int a = 1/0;
		session.save(new User());
	}
	
	// 测试save方法
	public void save(User u) {
		userDao.save(u);
	}
	
	// 测试delete方法
	public void deleteById(Long id) {
		userDao.deleteById(id);
	}

	// 测试find方法
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	public User findById(Long id) {
		User user = userDao.findById(id);
		user.setName("yy");						// model缓存，在事务提交前的持久化状态会与  数据库表同步
		return user;
	}

	//　测试update方法
	public void update(User u) {
		userDao.update(u);
	}

	public List<User> findByIds(Long[] ids) {
		return userDao.findByIds(ids);
	}
	
}
