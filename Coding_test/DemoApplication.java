package Coding_test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");

        // class로 Bean 가져오기
        DemoService service = ctx.getBean(DemoService.class);
        System.out.println("1 " + service.repository.name);

        // 이름으로 Bean 가져오기
        DemoService service2 = (DemoService) ctx.getBean("demoService");
        System.out.println("2 " + service.repository.name);

        // Bean 이름 출력하기
        String[] beanNames = ctx.getBeanDefinitionNames();
        for (String name: beanNames) {
            System.out.println(name);
        }
    }
}