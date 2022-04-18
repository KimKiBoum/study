## Spring Bean Life Cycle (빈 생명주기 관리)

<img src="https://github.com/KimKiBoum/study/blob/main/Spring/image/Spring Bean Life Cycle.jpeg" width=700>

- 애플리케이션 시작 시점에 필요한 연결을 미리 해두고, 애플리케이션 종료 시점에 연결을 모두 종료하는 작업을 진행하려면, 객체의 초기화와 종료 작업이 필요

  -> Spring Bean도 위와 같은 원리로 초기화 작업 종료 작업 나눠서 진행(간단히 객체 생성 -> 의존 관계 주입)

  -> 의존관계 주입이 다 끝난 뒤 필요한 데이터를 사용할 수 있는 준비 완료

------

### Spring 의존관계 주입 과정

> 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료

- Spring Boot에서 Component-Scan으로 Bean 등록 시작

<img src="https://github.com/KimKiBoum/study/blob/main/Spring/image/Component Scan.png" width=700>

- Bean으로 등록할 수 있는 어노테이션, 설정파일을 읽어 IoC 컨테이너에 Bean으로 등록

<img src="https://github.com/KimKiBoum/study/blob/main/Spring/image/IoC Container Bean 등록 과정.png" width=700>

- 의존 관계 주입 전 객체의 생성이 일어남

  - 생성자 주입 : 객체의 생성, 의존관계 주입이 동시에 일어남

    ```java
    public class Car{
      private Tire tire;
      
      public Car(Tire tire) {
        this.tire = tire;
      }
    }
    ```

    - 의존성 주입 시 new 연산 사용

      -> Car 클래스에 존재하는 Tire 클래스와 의존관계 존재 X -> 객체 생성 불가 -> 생성자 주입 = 객체 생성, 의존관계 주입 동시

  - Setter, Field 주입 : 객체의 생성 -> 의존관계 주입으로 라이프 사이클 나누어져 있음

    ```java
    public class Car{
      private Tire tire;
      
      public setTire(Tire tire) {
        this.tire = tire;
      }
    }
    ```

    - Car 객체를 만들 때 의존 관계 필요 X

      -> 객체 생성 후 의존 관계 주입으로 Bean Life Cycle이 진행

- 초기화 콜백 : 빈이 생성되고 빈의 의존관계 주입이 완료된 후 호출

- 소멸전 콜백 : 빈이 소멸되기 직전에 호출

> 객체의 생성과 초기화 분리 필요 이유
>
> - 생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임 가짐
>
> - 초기화는 이렇게 생성된 값을 활용해 외부 커넥션 연결 등 무거운 동작 수행
>
>   -> 생성자 안에서 무거운 작업을 함께 하는 것 보단 객체를 생성하는 부분과 초기화 하는 부분을 명확히 나누는 것이 유지보수 관점에서 좋음

### Spring Bean Life Cycle 관리방법

- **인터페이스 구현**

  ```java
  @Component
  public class TestComponent implements InitializingBean, DisposableBean {
  
      @Override
      public void afterPropertiesSet() throws Exception {  // 의존관계 주입이 끝나면 호출해주는 콜백
          System.out.println("초기화 콜백 테스트");
      }
  
      @Override
      public void destroy() throws Exception {            
          System.out.println("소멸전 콜백 테스트");
      }
  }
  ```

  - IntializingBean -> afterPropertiesSet 메소드로 초기화 지원(의존관계 주입 끝난 후 초기화 진행)

  - DisposableBean -> destroy 메소드로 소멸 지원(Bean 종료 전에 마무리 작업)

  - 단점

    - IntializingBean, DisposableBean -> 스프링 전용 인터페이스로 해당 코드가 스프링 전용 인터페이스에 의존
    - 초기화, 소멸 메소드의 이름 변경 X
    - 내가 코드 수정이 불가능한 외부 라이브러리에 적용 X

    -> 스프링 초창기에 나온 방법으로 지금은 거의 사용 X

- **@PostConstruct, @PreDestroy**

  ```java
  @Component
  public class TestComponent {
  
      @PostConstruct
      public void initTest() {
          System.out.println("초기화");
      }
  
      @PreDestroy
      public void destoryTest() {
          System.out.println("종료");
      }
  }
  ```

  - 최신 스프링에서 가장 권장하는 방법
  - Annotation 하나만 붙이면 됨 -> 편리성 ↑
  - 스프링에 종속적인 기술이 아닌 자바 표준(JSR-250) -> 스프링이 아닌 다른 컨테이너에서도 동작
  - 단점
    - 외부 라이브러리에는 적용 X -> 외부 라이브러리 초기화, 종료 필요시 @Bean 기능 이용
    - scope가 prototype의 경우 destroy 이벤트 처리가 불가능
