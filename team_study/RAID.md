## RAID

> (Redundant Array of Independent Disk)
> 여러 개의 하드 디스크에 일부 중복된 데이터를 나눠서 저장하는 기술

### RAID 목적

- 여러 개의 디스크 모듈을 하나의 대용량 디스크처럼 사용할 수 있기 위함
- 여러 개의 디스크 모듈에 데이터를 나눠 한 번에 쓰고 읽는 식으로 입출력 속도를 높이기 위함
- 여러 디스크를 모아 하나의 디스크로 만들고 하나 이상의 디스크에 장애가 나도 데이터가 사라지는 건 방지 가능
- 주요 단어
  - Mirroring: 데이터를 그대로 복제하는 것 -> 신뢰성 확보
  - Striping: 데이터를 여러 개의 디스크에 일정 크기의 바이트 or 섹터 단위로 나누어 저장 -> 데이터 읽기&쓰기 속도 증가

### 대표적인 RAID 구성

---

####  RAID 0

> Striped disk array without fault tolerance

<img src="/Users/kimkiboum/Library/Application Support/typora-user-images/image-20220920004444594.png" alt="image-20220920004444594" style="zoom:50%;" />

- 데이터의 빠른 입출력을 위해 데이터를 여러 디스크에 **분산 저장**
- 데이터 복구를 위한 추가 정보 기록 X -> **성능**적 측면 우수
- 어느 한 디스크에서 장애 발생 시 모든 데이터 손실
- RAID 0만으로 구성된 서버 or 스토리지는 거의 없음

****

#### RAID 1

> Mirroring and duplexing

<img src="/Users/kimkiboum/Library/Application Support/typora-user-images/image-20220920004735079.png" alt="image-20220920004735079" style="zoom:50%;" />

- Mirror 구성이나 Shadowing이라고도 불림
- 빠른 기록 속도 & 장애 복구 능력이 장점
- 최소 2개의 디스크로 구성
- 동일한 디스크 2개로 구성(디스크 장애를 대비한 백업용) -> **백업용 디스크**로 복구 가능
- 하나의 디스크 보단 약간 나은 성능을 보여줌
- 읽을 때의 속도에 비해 저장할 때 속도가 느림 -> 같은 내용을 두 번 저장
- **동일한 데이터 2번 저장** -> 복구 능력 높음 & 저장 용량 당 단가 높음

---

#### RAID 5

> Independent data disks with distributed parity blocks

<img src="/Users/kimkiboum/Library/Application Support/typora-user-images/image-20220920005527759.png" alt="image-20220920005527759" style="zoom:50%;" />

- **Parity** 정보를 모든 디스크에 나눠 기록

[^Parity]: 디스크 장애 시 데이터를 재 구축하는데 사용할 수 있는 사전에 계산된 값. 디스크 4개 블록 중 3개를 데이터 저장하는 데 쓰고 하나는 Parity 영역.

- 디스크 하나에 장애 발생시 Parity 영역을 통해 장애 난 디스크 데이터 복구
- 문제 발생시 컨트롤러가 정상 작동되고 있는 다른 디스크로부터 손실 된 데이터를 가져와 복구/재생
- Parity 담당하는 디스크는 병목현상 X -> Multi 프로세스 시스템과 같이 작은 데이터 기록이 수시로 발생할 경우 속도 빠름
- 읽기 작업의 경우 각 드라이브에서 Parity 정보 skip 필요 -> 약간 지연
- 작고 Random한 입출력이 많은 경우 더 나은 성능 제공
- 빠른 기록 속도가 필수가 아닐 경우 좋은 선택
- 일반적으로 5개(최소 3개)이상의 Disk 필요

---

#### RAID 6

> Independent data disks with two independent distributed parity schemas

<img src="/Users/kimkiboum/Library/Application Support/typora-user-images/image-20220920013524115.png" alt="image-20220920013524115" style="zoom:50%;" />

- RAID 5와 비슷
- 다른 디스크 값에 분포되어 있는 **2차 Parity 구성**을 포함 -> 장애 대비 능력 매우 높음
- 디스크가 최대 2개까지 장애가 발생해도 데이터 손실 X
- Parity가 2개 -> 장애가 난 디스크와의 동기화가 RAID 5에 비하여 느림
- DISK는 최소 4개 필요

---

#### RAID 1+0

> Mirroring + Striping

<img src="/Users/kimkiboum/Library/Application Support/typora-user-images/image-20220920013735114.png" alt="image-20220920013735114" style="zoom:50%;" />

- RAID 0(빠른 속도) + RAID 1(안정적 복구 기능) 합친 방식
- 최소 4개의 디스크로 구성
- **Mirroring + Striping**
  - 두 개 이상의 디스크에 나눠 저장(Striping)
  - 같은 형태로 다른 하드디스크에 동일하게 저장(Mirroring)
- 4개의 디스크로 구성
  - 쓰기 속도: 2개의 디스크로 Striping 할 때와 같음
  - 읽기 속도: 4개의 디스크로 나눠 읽어옴 -> 빠름
- Mirroring을 사용하여 장애 발생시 완벽 복구 가능(전체 용량의 50%만 사용 가능)



### 다른 RAID

---

#### RAID 2

> Hamming code ECC

- 오류 정정을 위한 해밍 코드 사용하는 방식
- 최근 디스크 드라이브는 기본적으로 에러 검출 기능 탑재 -> 사용 X



#### RAID 3

> Parallel transfer with parity

- 하나의 디스크를 Parity 정보를 위해 사용하고 나머지 디스크에 데이터 균등 저장



#### RAID 4

> Independent data disks with shared parity disk

- 패러티 정보를 독립된 디스크에 저장
- 병목 현상 발생시 성능 저하 발생 가능

