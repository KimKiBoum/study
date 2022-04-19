## @Bean과 @Component 차이점



### @Bean

```java
//ObjectMapper Class에 @Component 선언 불가능 -> ObjectMapper의 인스턴스를 생성하는 메소드 만든 후 해당 메소드에 @Bean 선언
@Bean
public Objectmapper objectMapper() {return new ObjectMapper();}
//RestTemplate Class에 @Component 선언 불가능 -> RestTemplate 인스턴스를 생성하는 메소드 만든 후 해당 메소드에 @Bean 선언
@Bean
public RestTemplate restTemplate() {return new RestTemplate();}
```

- 개발자가 컨트롤 불가능한 외부 라이브러리들을 Bean으로 등록하고 싶은 경우 사용

<img src = "https://github.com/KimKiBoum/study/blob/main/Spring/image/@Bean.png?raw=true">

-> @Target이 METHOD로 지정되어 있지만, TYPE은 없음

------

### @Component

```java
@Component
public Class TemplateEngine {}
```

- 개발자가 생성한 클래스에 Bean으로 등록하고 싶은 경우 사용

<img src="https://github.com/KimKiBoum/study/blob/main/Spring/image/@Component.png?raw=true">

-> @Target이 TYPE으로 지정되어 Class 위에서만 선언가능
