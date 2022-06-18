RIPv1의 개념

- RIP은 v1과 v2가 있다.
- Distance Vector 라우팅 프로토콜이며, 라우팅 정보 전송을 위해서 UDP 520번을 사용한다.
- 설정이 간단하고 소규모 네트워크에 사용하기 좋다.(Hop Count 15까지만 지원)
- 대규모의 네트워크 보다는 소규모의 네트워크에 적합하다.
- 목적지로 가는 경로 중에서 라우터를 가장 적게 거치는 경로를 선택한다.


RIPv2의 개념 및 RIPv1과의 차이점

- RIPv2는 Classless 라우팅 프로토콜이며, 네트워크 정보와 함께 서브넷 마스크의 정보도 함께 전달한다.
- 라우팅 정보의 전달을 위하여 브로드캐스트 주소를 사용하지 않고 Multicast 주소인 224.0.0.9를 사용한다.
- RIPv2와 RIPV1의 차이점은 다음과 같다.
  - RIPv1은 Classful 이지만, RIPv2는 Classless 이다.
  - RIPv1은 VLSM 및 서브넷팅을 지원하지 않지만, RIPv2는 지원한다.
  - RIPv1는 255.255.255.255 브로드캐스트를 하고 인증을 지원하지 않지만, RIPv2 는 224.0.0.9 멀티캐스트를 사용하고 인증을 지원한다.
  
  
RIPv2를 활용한 Full-Routing

- RIPv1의 기본적인 설정 방법은 다음과 같다.
    Router>enable

    Router#conf t

    Router(config)#router rip

    → 라우팅 프로토콜로 RIP을 사용할 것을 선언.

    Router(config-network)#network 네트워크 주소

    → network 명령어로 라우터에 직접 연결되어 있는 네트워크 주소를 입력

- show ip int brief 사용하여 RIP 라우터 인터페이스의 설정이 정상적으로 되어 있는지 확인한다.
- RIPv2는 모든 설정 과정이 RIPv1과 같고, 단순히 Version 2 명령어만 추구하면 된다.
- Passive-Interface로 설정된 네트워크 구역으로는 라우팅 정보를 보내지 않는다.
