# T1F3 - 이게 무슨 대기야❓

Java 기반 콘솔 애플리케이션으로, 식당 예약 대기 시스템을 시뮬레이션합니다.  
손님은 전화번호와 인원수를 기반으로 식당에 예약을 하고, 식당은 좌석 상황에 따라 예약을 수락하거나 입장시킬 수 있습니다.

---

## 📁 프로젝트 구조

```
waitingSystem/
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.waiting.system
│   │   │       ├── exception
│   │   │       │   ├── InvalidPhoneNumberException.java
│   │   │       │   └── ReservationNotFoundException.java
│   │   │       ├── model
│   │   │       │   ├── Customer.java
│   │   │       │   ├── Reservation.java
│   │   │       │   └── Restaurant.java
│   │   │       ├── service
│   │   │       │   └── ReservationManager.java
│   │   │       ├── util
│   │   │       │   └── RestaurantLogger.java
│   │   │       └── Main.java
│   │   └── resources
│   │       └── logback.xml
│   └── test
│       └── java
```

---

## 🛠️ Windows에서 Maven 설치

1. [공식 Apache Maven 다운로드 페이지](https://maven.apache.org/download.cgi)에서 `.zip` 파일 다운로드
2. 압축 해제 (예: `C:\tools\apache-maven-3.9.6`)
3. 환경 변수 설정:
   - 시스템 변수 → 새로 만들기  
     `MAVEN_HOME = C:\tools\apache-maven-3.9.6`
   - `Path` 편집 → `C:\tools\apache-maven-3.9.6\bin` 추가
4. 커맨드 프롬프트 새로 열기
5. 아래 명령어로 설치 확인:

```bash
mvn -v
```

---

## ⚙️ 프로젝트 빌드 및 실행

### 🔹 JAR 파일 생성

```bash
cd waitingSystem
mvn clean package
```

- 결과 파일: `target/waiting-system.jar`

### 🔹 JAR 실행

```bash
java -jar target/waiting-system.jar
```

---

## 🧩 클래스 다이어그램

```
+---------------------+        +---------------------+        +---------------------+
|     Customer        |        |     Reservation     |        |     Restaurant      |
+---------------------+        +---------------------+        +---------------------+
| - id: int           |        | - id: int           |        | - id: int           |
| - phoneNum: String  |        | - restaurantId: int |        | - name: String      |
| - memberNum: int    |        | - customerId: int   |        | - description: Str  |
| - reservation: Res  |        | - memberNum: int    |        | - seatCount: int    |
+---------------------+        | - status: enum      |        | - waitingList: Queue|
| +makeReservation()  |        | - createdAt: Date   |        +---------------------+
| +cancelReservation()|        +---------------------+        | +receiveReservation()|
| +enterRestaurant()  |        | +cancel()           |        | +acceptReservation() |
+---------------------+        | +markAsEntered()    |        | +acceptReservationByG|
                               | +isEligible()       |        | +notifyAvailable()   |
                               +---------------------+        +---------------------+

                   +-----------------------------------------+
                   |        ReservationManager               |
                   +-----------------------------------------+
                   | - reservations: List<Reservation>       |
                   | - restaurants: Map<Integer, Restaurant> |
                   | - customers: Map<Integer, Customer>     |
                   +-----------------------------------------+
                   | +registerReservation(...)               |
                   | +cancelReservation(...)                 |
                   | +acceptNextTeam(...)                    |
                   | +getWaitingCount(...)                   |
                   +-----------------------------------------+

                   +---------------------------+
                   |   RestaurantLogger        |
                   +---------------------------+
                   | +logReservationReceived() |
                   | +logReservationAccepted() |
                   | +logReservationCancelled()|
                   | +logReservationEntered()  |
                   +---------------------------+
```

---

## 🔚 기타

- 로그 설정은 `src/main/resources/logback.xml`에서 설정 가능
- 테스트 코드는 `src/test/java` 경로에 작성
- SLF4J + Logback 기반 로깅 사용
