package utility;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import models.Address;
import models.User;
import services.UserService;

public class BeanProvider {
	private static AbstractApplicationContext context;
	private static final String path = "spring.xml";
	public static User getUserBean() {
		context = new ClassPathXmlApplicationContext(path);
		return context.getBean("user", User.class);
	}
	public static Address getAddressBean() {
		context = new ClassPathXmlApplicationContext(path);
		return context.getBean("address", Address.class);
	}
	public static UserService getUserService() {
		context = new ClassPathXmlApplicationContext(path);
		return context.getBean("service", UserService.class);
	}
}
