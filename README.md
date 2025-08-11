# T1F3 - 이게 무슨 대기야??

### Java Console Restaurant Waiting System

전화번호, 인원수, 식당 ID 기반으로 예약을 관리하고, 손님 퇴장 후 자동으로 대기 손님을 입장시키는 Java 콘솔 애플리케이션입니다.  
여러 식당의 예약 큐를 독립적으로 관리하며, 명령어 기반으로 동작합니다.

---

## ✅ 주요 기능

- 전화번호 기반 예약 및 예약 취소
- FIFO 대기열 방식 입장 처리
- 인원 수만큼 퇴장 시 자동 입장
- 명령어 기반 인터페이스
- 예약/취소/입장 로그 출력
- 콘솔 출력 버퍼링 처리
- 외부 Console 라이브러리 활용
- SLF4J 기반 로그 시스템 도입 (RestaurantLogger)

---

## 🗂️ 프로젝트 구조

```

📦 waitingSystem
┣ 📂 src
┃ ┣ 📂 com.waiting.system.controller    # CommandProcessor - 명령어 처리
┃ ┣ 📂 com.waiting.system.model        # Restaurant, Reservation - 도메인 모델
┃ ┣ 📂 com.waiting.system.service      # RestaurantManager, ReservationManager
┃ ┣ 📂 com.waiting.system.util         # RestaurantLogger
┃ ┣ 📂 com.waiting.system.view         # UIHandler
┃ ┗ 📜 Main.java                       # 진입점
┣ 📂 lib                               # 외부 콘솔 입력용 Console.jar
┣ 📜 pom.xml                           # Maven 프로젝트 설정
┗ 📜 README.md                         # 프로젝트 문서

```

---

## 💬 사용 가능한 명령어

| 명령어       | 형식                                               | 설명                                                  |
| ------------ | -------------------------------------------------- | ----------------------------------------------------- |
| 예약         | `reservation <전화번호> <인원수:int> <식당ID:int>` | 예약을 추가하고 대기번호를 반환합니다                 |
| 취소         | `cancel <전화번호> <식당ID:int>`                   | 전화번호에 해당하는 모든 예약을 취소합니다            |
| 퇴장 및 입장 | `quit <퇴장인원:int> <식당ID:int>`                 | 퇴장 인원 수만큼 입장 가능한 손님을 자동 입장시킵니다 |
| 도움말       | `help`                                             | 명령어 사용법을 출력합니다                            |

### 📌 예시

```bash
reservation 01012345678 3 1
cancel 01012345678 1
quit 4 1
help
```

---

### 🎥 실행 예시 (Console 시연)

![콘솔 시연]()

---

## ⚙️ 실행 방법

### 1. 의존성 설치 및 빌드

```bash
mvn clean package
```

> `lib/Console.jar`는 Maven 중앙 저장소에 존재하지 않는 외부 라이브러리이므로,  
> 아래 명령어를 통해 로컬 Maven 저장소에 수동 등록해야 합니다. (한 번만 실행하면 됩니다.)

```bash
mvn install:install-file -Dfile=lib/Console.jar -DgroupId=com.example -DartifactId=console -Dversion=1.0 -Dpackaging=jar
```

> 위 라이브러리는 pom.xml에서 다음과 같이 system scope로 참조됩니다:

```xml
<dependency>
  <groupId>com.example</groupId>
  <artifactId>console</artifactId>
  <version>1.0</version>
  <scope>system</scope>
  <systemPath>${project.basedir}/lib/Console.jar</systemPath>
</dependency>
```

### 2. 실행

```bash
java -jar target/waiting-system.jar
```

---

## 🔧 기술 스택

| 항목            | 내용                                       |
| --------------- | ------------------------------------------ |
| Language        | Java 17                                    |
| Build Tool      | Maven                                      |
| 외부 라이브러리 | Console.jar (콘솔 입력 지원)               |
| 로깅            | SLF4J, RestaurantLogger (자체 로그 시스템) |

---

## 🚀 향후 개선 예정

- 예약 현황 조회 명령어 추가 (`status`, `seat` 등)
- 동시성(멀티스레드) 환경 고려
- 데이터 저장 기능 추가 (파일 or DB)

---

## 👨‍💻 개발자

| <img alt="profile" src ="https://github.com/kswdot.png" width ="100px"> | <img alt="profile" src ="https://github.com/TaekkiMin.png" width ="100px"> | <img alt="profile" src ="https://github.com/yes2489.png" width ="100px"> | <img alt="profile" src ="https://github.com/JBL28.png" width ="100px"> |
| :---------------------------------------------------------------------: | :------------------------------------------------------------------------: | :----------------------------------------------------------------------: | :--------------------------------------------------------------------: |
|                                 김성은                                  |                                   민택기                                   |                                  양은서                                  |                                 이정복                                 |
|                   [kswdot](https://github.com/kswdot)                   |                 [TaekkiMin](https://github.com/TaekkiMin)                  |                  [yes2489](https://github.com/yes2489)                   |                   [JBL28](https://github.com/JBL28)                    |

- 프로젝트 목적: Java 콘솔 기반 입출력 및 로직 설계 실습
- 사용 기술: Java, Maven, SLF4J
