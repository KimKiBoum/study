## DI(Dependency Injection)

- 객체를 직접 생성하는 게 아닌 외부에서 생성한 후 주입 시켜주는 방식
- 모듈 간의 결합도 ↓
- 유연성 ↑

<img src = "https://github.com/KimKiBoum/study/blob/main/Spring/image/DI.png?raw=ture">

1. A 객체가 B와 C객체를 New 생성자를 통해 직접 생성

2. 외부에서 생성 된 객체를 setter()를 통해 사용 -> 의존성 주입 예시

   -> A 객체에서 B,C객체 사용할 때 A객체에서 직접 생성이 아닌 외부에서 생성된 B,C를 주입해 setter or 생성자를 통해 사용하는 방식

- 스프링에서의 객체 = Bean

- 프로젝트가 실행될 때 사용자가 Bean으로 관리하는 객체들의 생성, 소멸에 관련된 작업 자동 수행

  -> 객체가 생성되는 곳 = Bean 컨테이너

## IoC(Inversion of Control)

- 제어의 역전이라는 의미
- 메소드나 객체의 호출 작업을 개발자가 아닌 외부에서 결정 되는 것
- 가독성 ↑, 유지보수 편리