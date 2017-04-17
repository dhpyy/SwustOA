package cn.swust.oa.test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");  // Spring容器对象在监听器中创建中 
	                                                       // 运行JUnit不经过监听器，是单独的一个环境 需要手动创建 Spring容器对象
	// 测试Spring-Struts2
	@Test
	public void testAction() throws Exception {
		TestAction testAction = (TestAction)ac.getBean("testAction");
		System.out.println("---------" + testAction);
	}
	
	// 测试Spring-Hibernate:SessionFactory
	@Test
	public void testSessionFactory() throws Exception {
		SessionFactory sessionFactory = (SessionFactory)ac.getBean("sessionFactory");
		System.out.println("---------" + sessionFactory);
	}
	
	// 测试Spring-Hibernate:Transactional
	@Test
	public void testTransactional() throws Exception {
		TestService testService = (TestService)ac.getBean("testService");
		testService.saveTwoUsers();
	}
}
