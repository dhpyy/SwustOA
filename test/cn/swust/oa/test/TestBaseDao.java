package cn.swust.oa.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.swust.oa.domain.User;


public class TestBaseDao {
	
	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	TestService testService = (TestService) ac.getBean("testService");
		
	@Test
	public void testFindAll() throws Exception {
		List<User> users = testService.findAll();
		for(User element : users) {
			System.out.println(element.getLoginName());
		}
	}
	
	@Test
	public void testDelete() throws Exception {
		testService.deleteById(4l);
	}
	
	@Test
	public void testSave() throws Exception {
		User u = new User();
		u.setLoginName("YY");
		u.setPassword("YY");
		testService.save(u);
	}
	
	
	@Test
	public void testUpdate() throws Exception {
		User u = new User();
		u.setId(5l);
		u.setLoginName("dhp");
		u.setPassword("dhp");
		testService.update(u);
	}
	
	@Test
	public void testFindByIds() throws Exception {
		Long[] ids = {5l, 7l};
		List<User> users = testService.findByIds(ids);
		for(User element : users) {
			System.out.println(element.getLoginName());
		}
	}
}
