#### Bean이란

- Spring IoC 컨테이너가 관리하는 자바 객체

  > <img src = "https://github.com/KimKiBoum/study/blob/main/Spring/image/IoC_container.jpeg?raw=ture">
  >
  > IoC(Inversion of Control) : 제어의 역전
  >
  > - 객체에서 직접 생성하지 않고 외부에서 생성한 후 주입 시켜주는 방식
  >
  > IoC Container( = BeanFactory) : 오브젝트의 생성과 관계설정, 사용, 제거 등 작업을 대신해 줌

- Spring에서 보통 Singleton으로 존재

- - 사용 이유
    - 자주 사용하는 객체를 singleton으로 만들어놓고 어디서든 호출 가능


------

#### Spring Bean을 Spring IoC Container에 등록하는 방법

------

- ##### Component Scanning

  - @ComponentScan : 어느 지점부터 Component를 찾으라고 알려주는 역할

    @ComponentScan 포함 어노테이션 예시 : **@SpringBootApplication**

  - @Component : 실제로 찾아서 등록할 Class를 의미

    @Component 포함 어노테이션 예시 : **@Controller**, **@Repository**, **@Service**

```java
@Controller // @Controller에는 @Component 포함 -> HelloController = Bean
public class HelloController {
  //Http Get method의 /hello 경로로 요청이 들어올 때 처리할 Method를 아래와 같이 @GetMapping 사용해 사용 가능
  @GetMapping("hello")
  public String hello(Model model) {
    model.addAttribute("data", "This is data!!");
    return "hello";
  }
}
```

- ------

  ##### Bean Configuration File에 직접 Bean 등록하는 방법

  - Java Class에 설정

  ```java
@Configuration //Spring Project에서 Configuration 역할 하는 클래스로 지정
public class HelloConfiguration {
  @Bean //해당 File 하위에 Bean으로 등록
  public HelloController sampleController() {
    return new SampleController;
  }
}
  ```

  -> @Bean을 이용해 직접 Bean 정의 시 @Component 사용 X

		- @Bean : 개발자가 컨트롤 불가능한 외부 라이브러리들을 Bean으로 등록하고 싶은 경우 사용
		- @Component : 개발자가 생성한 클래스에 Bean으로 등록하고 싶은 경우 사용

------

  - ##### XML 파일에 설정

    - XML 방식으로 Bean 정의 시 필요한 속성

      - class(필수) : 정규화된 자바 class 이름
      - id : bean의 고유 식별자
      - scope : 객체의 범위 (singleton, prototype 등)
      - constructor-arg : 생성 시 생성자에 전달할 인수
      - property : 생성시 bean setter에 전달할 인수
      - Init-method, destroy-method

    - 기본적인 양식

      ```xml
      <!-- A simple bean definition -->
      <bean id="..." class="..."></bean>
      
      <!-- A bean definition with scope -->
      <bean id="..." class="..." scope="singleton"></bean>
      
      <!-- A bean definition with property -->
      <bean id="..." class="...">
        <property name="message" value="Hello World!"/>
      </bean>
      
      <!-- A bean definition with initialization method -->
      <bean id="..." class="..." init-method="..."></bean>
      ```

    - 예시

      ```xml
      <!-- application.xml -->
      <?xml version="1.0" encoding="UTF-8" ?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      
          <bean id="demoService" class="com.example.demo.DemoService">
              <property name="repository" ref="demoRepository"/>
              <property name="count" value="42"/>
          </bean>
          <bean id="demoRepository" class="com.example.demo.DemoRepository"/>
      </beans>
      ```

      ```java
      package com.example.demo;
      
      public class DemoRepository {
          public String name = "DemoRepository!";
      }
      ```

      ```java
      package com.example.demo;
      
      public class DemoService {
          DemoRepository repository;
          int count;
      
          public void setRepository(DemoRepository demoRepository) {
              this.repository = demoRepository;
          }
          public void setCount(int count) {
              this.count = count;
          }
      }
      ```

      ```java
      package com.example.demo;
      
      import org.springframework.context.ApplicationContext;
      import org.springframework.context.support.ClassPathXmlApplicationContext;
      
      public class DemoApplication {
      
          public static void main(String[] args) {
              ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
      
              // class로 Bean 가져오기
              DemoService service = ctx.getBean(DemoService.class);
              System.out.println("1 " + service.repository.name); // 출력 : 1 DemoRepository!
            	System.out.println("1 " + service.repository.count); // 출력 : 1 0
      
              // 이름으로 Bean 가져오기
              DemoService service2 = (DemoService) ctx.getBean("demoService");
              System.out.println("2 " + service.repository.name); // 출력 : 2 DemoRepository!
            	System.out.println("2 " + service.repository.name); // 출력 : 2 
      
              // Bean 이름 출력하기 / 출력 : demoService\n demoRepository
              String[] beanNames = ctx.getBeanDefinitionNames();
              for (String name: beanNames) {
                  System.out.println(name);
              }
          }
      }
      ```

------

  #### Bean Scope

  - Spring은 기본적으로 모든 Bean을 Singleton으로 생성하여 관리

  - Singleton Bean은 Spring Container에서 한 번 생성 후 Container가 사라질 때 Bean도 제거

  - 생성된 하나의 Instance는 Single Beans Cache에 저장, 해당 Bean에 대한 요청과 참조가 있으면 캐시된 객체 반환

  - 싱글턴 -> 하나만 생성 -> 동일한 객체 참조

  - Scope가 명시적으로 지정되지 않은 Bean -> Singleton

  - Default = singleton인 이유 : 대규모 트래픽 처리를 위해

    - 스프링은 대규모 엔터프라이즈 환경에서 요청을 처리할 수 있도록 고안

      -> 계층적 구조로 분리(Controller, Service, Repository 등)

    - 매번 클라이언트 요청 시 로직을 처리하는 빈을 생성 -> 부하가 걸려 감당 X

      -> bean을 싱글턴으로 관리해 1개의 요청이 들어올 경우 여러 쓰레드가 빈을 공유해 처리

#### Singleton 

- 하나의 Bean 정의에 대해서 Spring IoC Container 내에 단 하나의 객체만 존재

- xml 설정

  ```xml
  <bean id="..." scope="singleton"></bean>
  ```

- annotation 설정

  ```java
  @Scope("singleton")
  public class SingletonExample{}
  ```

- 싱글턴 패턴에 무상태가 필요한 이유

  - 상태가 있을 경우 -> 하나의 객체가 갖는 상태를 여러 클라이언트가 공유

  ```java
  public class StatefulService {
  
      private int price; // 상태를 유지하는 필드
  
      public void order(String name, int price){
          System.out.println("name = " + name + " price = " + price);
          this.price = price; // 문제 발생
      }
  
      public int getPrice() {
          return price;
      }
  }
  ```

  ```java
  @Test
  void statefulServiceSingleton(){
  	ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
  	StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
  	StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);
  
  	//ThreadA: A사용자 10000원 주문
  	statefulService1.order("userA", 10000);
  	//ThreadB: B사용자 20000원 주문
  	statefulService2.order("userB", 20000);
  
  	//ThreadA: 사용자A 주문 금액 조회
  	int price = statefulService1.getPrice();
    //ThreadB에서 pirce = 20000이 되어 price = 20000 출력
  	System.out.println("price = " + price);
  
    //getPrice = 20000
  	Assertions.assertThat(statefulService1.getPrice()).isEqualTo(10000); // 에러발생
  }
  
  static class TestConfig {
  	@Bean
  	public StatefulService statefulService() {
  		return new StatefulService();
  	}
  }
  ```

  

------

#### Prototype

- 모든 요청에서 새로운 객체를 생성하는 것을 의미

  -> 의존성 관계의 bean에 주입 될 때 새로운 객체가 생성되어 주입

  -> Garbage Collection에 의해 bean 제거

- xml 설정

  ```xml
  <bean id="..." class="..." scope="prototype"></bean>
  ```

- annotation

  ```java
  @Scope("prototype")
  public class PrototypeExample{}
  ```

- **이용방법**

  ```java
  public class PrototypeTest {
      @Test
      void prototypeBeanFind() {
          AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
  
          System.out.println("find prototypeBean1");
          PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
          System.out.println("find prototypeBean2");
          PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
  
          System.out.println("prototypeBean1 = " + prototypeBean1);
          System.out.println("prototypeBean2 = " + prototypeBean2);
          assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
  
          ac.close();
      }
  
      @Scope("prototype")
      static class PrototypeBean {
          @PostConstruct
          public void init() {
              System.out.println("SingletonBean.init");
          }
  
          @PreDestroy
          public void destroy() {
              System.out.println("SingletonBean.destroy");
          }
      }
  }
  ```

  - **문제점**

    싱글턴 bean 내부에 프로토타입 bean 사용 시 의존관계 주입 시점에 생성된 뒤 계속 재활용 됨

  - **프로토 타입 빈 사용시 생기는 문제점 해결**

    - Object Provider 인터페이스 사용
      - 컨테이너에 요청하면 bean을 찾아주는 기능(Dependency Lookup) 제공
      - ObjectFactory와 다른점(비슷한 기능)
      - ObjectFactory : 기능 단순, 별도 라이브러리 X, 스프링에 의존
      - ObjectProvider : ObjectFactory 상속, 옵션, 스트림 처리 등 편의 기능이 많고, 별도 라이브러리 필요 X, 스프링에 의존

  ```java
  @Scope("singleton") 
  static class SingletonBean { 
    @Autowired 
    private ObjectProvider<PrototypeBean> provider; 
    public int logic() { 
      PrototypeBean prototypeBean = provider.getObject(); 
      System.out.println("사용할 prototypeBean 정보!: "+prototypeBean); 
      prototypeBean.addCount(); 
      return prototypeBean.getCount(); 
    } 
    @PostConstruct 
    void init() { 
      System.out.println("PrototypeBean.init"+this); 
    }
    @PreDestroy 
    void preDestroy() { 
      System.out.println("PrototypeBean.preDestroy"+this); 
    } 
  }
  ```

  - JAVA 표준 기술 JPR-330 사용하기

  ```java
  dependencies {
  	...
  
  	implementation 'javax.inject:javax.inject:1'
  	
      ...
  }
  라이브러리를 무사히 가져왔다면 코드를 아래와 같이 수정한다.
  
      @Scope("singleton")
      static class SingletonBean {
  
          @Autowired
          private Provider<PrototypeBean> provider;
          
          public int logic() {
              PrototypeBean prototypeBean = provider.get();
              System.out.println("사용할 prototypeBean 정보!: "+prototypeBean);
  
              prototypeBean.addCount();
              return prototypeBean.getCount();
          }
  
          @PostConstruct
          void init() {
              System.out.println("PrototypeBean.init"+this);
          }
  
          @PreDestroy
          void preDestroy() {
              System.out.println("PrototypeBean.preDestroy"+this);
          }
  
      }
  ```


------

#### 프로토 타입과 싱글턴 타입 비교 예시

- PetOwner Class

```java
package com.spring;

public class PetOwner {
    String userName;
    public AnimalType animal;

    public PerOwner(AnimalType animal) { this.animal = animal; }
    
    public String getUserName() {
        System.out.println("Person name is " + , userName);
        return userName;
    }
    public void setUserName(String userName) { this.userName = userName; }
    
    public void play() { animal.sound(); }
}
```

- MainApp Class

```java
package com.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/spring/beans/bean.xml");

        // getBean()을 통해 bean의 주소값을 가져온다.  
        PetOwner person1 = (PetOwner) context.getBean("petOwner");
        person1.setUserName("Alice");
        person1.getUserName();

        PetOwner person2 = (PetOwner) context.getBean("petOwner");
        person2.getUserName();

        context.close();
    } 
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.2.xsd">

    <bean id="dog" class="com.spring.Dog">
        <property name="myName" value="poodle"></property>
    </bean>

    <bean id="cat" class="com.spring.Cat">
        <property name="myName" value="bella"></property>
    </bean>

    <bean id="petOwner" class="com.spring.PetOwner" scope="singleton">
        <constructor-arg name="animal" ref="dog"></constructor-arg>
    </bean>
</beans>
```

**결과**

- Scope="singleton"인 경우
  - Person name is Alice
  - Person name is Alice
- Scope="prototype"인 경우
  - Person name is Alice
  - Person name is null

------

#### 그 외의 Bean scope

| Scope          | Description                                                  |
| -------------- | ------------------------------------------------------------ |
| request        | 하나의 Bean 정의에 대해서 하나의 HTTP_request의 생명주기 안에 단 하나의 객체만 존재. -> 각각의 HTTP request는 자신만의 객체를 가짐(Web-aware Spring ApplicationContext 안에서만 유효) |
| session        | 하나의 Bean 정의에 대해서 하나의 HTTP Session의 생명주기 안에 단 하나의 객체만 존재(Web-aware Spring ApplicationContext 안에서만 유효) |
| global session | 하나의 Bean 정의에 대해서 하나의 global HTTP Session의 생명주기 안에 단 하나의 객체만 존재 일반적으로 portlet context 안에서 유효((Web-aware Spring ApplicationContext 안에서만 유효)) |

**request, session, global sssion의 Scope는 일반 Spring Application이 아닌 Spring MVC Web Application 에서만 사용**

