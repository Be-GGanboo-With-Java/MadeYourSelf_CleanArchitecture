# CH 6. `영속성 어댑터` 구현하기

## 1. 의존성 역전
<img width="800" src="https://user-images.githubusercontent.com/89288109/198863975-78747a72-dc5e-4f7a-9700-ef7534ae9718.png">

- 서비스가 영속성 기능을 사용하기 위해 포트 인터페이스를 호출
- 육각형 아키텍처에서 영속성 어댑터는 주로 아웃고잉 어댑터  
=> 앱에서 호출하기 때문, 반대의 경우는 없다.
- 포트는 앱과 영속성 사이의 간접적 계층
  - 영속성 문제에 신경쓰지 않고 도메인 코드 개발 목적
  - 영속성 계층에 코드 의존성을 없앰
  - 이럴 경우, 영속성 코드를 변경하더라도 코어 코드에 영향이 없음
- 런타임에도 여전히 앱이 영속성 코드에 의존하고 있다. 
- 인터페이스 계약을 만족하는 한 영속성 코드 수정은 문제가 없다.

<br>

## 2. 영속성 어댑터의 책임
1. 입력을 받는다.
    - 주로 도메인 엔티티, DB 연산 전용 객체
2. 입력을 데이터베이스 포맷으로 매핑한다.
    - JPA Entity 객체 매핑
    - JPA를 제외한 기술도 고려
    - 영속성 어댑터의 입력 모델은 애플리케이션 코어에 있으므로, 영속성 어댑터의 변경은 아무런 영향을 가하지 않는다.
3. 입력을 데이터베이스로 보낸다.
4. 데이터베이스 출력을 애플리케이션 포맷으로 매핑한다.
    - 출력 모델 또한 코어에 위치한다.
5. 출력을 반환한다.

<br>

## 3. 포트 인터페이스 나누기
<img width="800" src="https://user-images.githubusercontent.com/89288109/198864360-b52cbc1a-5f44-437c-a11a-5631eedd64ea.png">

- 하나의 Repository에 모든 데이터베이스 연산을 담아 놓는 게 일반적인 모델
  - 넓은 포트 인터페이스를 가짐.
  - SRP (Single Responsiblity Principle) 위배
  - 테스트가 어렵고 불필요한 의존을 가짐
- ISP (Interface Segregation Principle) 적용이 시급하다.

<br>

<img width="800" src="https://user-images.githubusercontent.com/89288109/198864445-40897f43-c3d3-4986-8e19-e683c9da439d.png">

- 포트를 책임에 맞게 나눈 모습
- 가독성과 책임의 표현이 개선되었다.
- 테스트 시 모킹이 간편해졌다.
- 플러그 앤드 플레이가 가능해졌다.
  - 서비스 코드를 필요한 포트에 **꽂기** 매우 가능.

<br>

## 4. 영속성 어댑터 나누기
<img width="800" src="https://user-images.githubusercontent.com/89288109/198864556-9a6d99a1-977a-47f4-a63f-e91f59756c53.png">

- 어댑터를 나눔으로써 도메인 클래스(애그리거트)마다 필요한 하나의 어댑터를 구현.
- 도메인 경계를 따라 자연스레 나누어짐.
- 성능을 위한 SQL을 쓰기 위한 포트를 넣기 위해 더 쪼갤 수도 있다.
- 추후 여러 개의 바운디드 컨텍스트의 영속성 요구사항을 분리하기 위한 좋은 토대가 된다.

<br>

<img width="800" src="https://user-images.githubusercontent.com/89288109/198864649-c74876aa-7aaf-4357-b651-54baaa88b798.png">

- 그림을 보면 도메인 별 바운디드 컨텍스트는 각자의 영속성 어댑터를 가진다.
  - 바운디드 컨텍스트 = 경계
- 경계끼리는 철저하게 분리되어있다.
- 다른 맥락을 필요로 할 경우 인커밍 포트를 통해 접근하는 방법밖엔 없음.

<br>

## 5. 스프링 데이터 JPA 예제

<img width="800" src="https://user-images.githubusercontent.com/89288109/198864895-89ccd44a-0520-4e67-b005-d3a4b4c92c48.png">

- 불변성을 띈 도메인 모델을 생성할 수 있는 유효한 상태의 Account 엔티티 클래스
- 팩토리 메서드를 제공

<br>

<img width="800" src="https://user-images.githubusercontent.com/89288109/198864918-920bd066-20dd-4816-abd1-ac613c2928dd.png">

- 영속성 어댑터를 위한 AccountEntity를 추가 정의

<br>

<img width="800" src="https://user-images.githubusercontent.com/89288109/198864954-6d693c05-c81d-4a43-b6eb-f2401d4c4d6a.png">

- 영속성 어댑터에서 사용할 ActivityEntity 클래스 정의
- JPA가 마냥 좋진 않다. 
  - 관계 매핑 애노테이션을 사용하면 부수효과가 생길 수 있음.
- 조금 더 간단한 ORM도 있음.

<br>

<img width="800" src="https://user-images.githubusercontent.com/89288109/198865027-7a6b7e4b-28dc-4575-aa56-b7adf4f97fdd.png">

- ActivityRepository 인터페이스
- 스프링은 구현체를 자동으로 생성.

<br>

<img width="800" src="https://user-images.githubusercontent.com/89288109/198865067-fedd3403-d9cf-4ab4-807f-8b101cb20e5e.png">
<img width="800" src="https://user-images.githubusercontent.com/89288109/198865087-63ea627f-75af-4f39-9ac1-569a2b86d351.png">

- 영속성 어댑터
- LoadAccountPort & UpdateAccountStatePort 2개의 포트 구현
- Account 데이터를 DB에서 조회  
=> 특정 시간 범위의 Activity 조회  
=> 시간 범위의 잔고를 구하고 시간 범위 전의 입, 출금 Activity를 DB에서 조회  
=> Account Entity로 변경할 경우 잔고를 계산한다.

<br>

<img width="800" src="https://user-images.githubusercontent.com/89288109/198865182-67376b94-e136-40f1-8ba2-0d0a454512f4.png">

- Domain 엔티티 & 영속성 엔티티가 쌍으로 존재
- JPA 특성상 기보본 생성자를 필요로 하기 때문에 매핑 진행.
- 풍부한 도메인 모델을 생성하고 싶을 경우 도메인, 영속성 모델을 매핑하는 것이 좋다. 
  - 영속성 측면과의 타협을 하고 싶지 않을 경우.

<br>

## 6. 데이터베이스 트랜잭션은 어떻게 해야 할까?
- 트랜잭션은 하나의 유스케이스에 대해 일어나는 모든 쓰기 작업에 걸쳐야 함.
  - 실패 시 모두 롤백 필요

- 영속성 어댑터의 경우 유스케이스를 모르기 때문에 책임을 가지기 부적합.
- @Transactional 애노테이션으로 오염을 원치 않을 경우 AspectJ의 위빙을 통해 해결하는 방법도 고려할 수 있다.

<br>

## 7. 유지보수 가능한 소프트웨어를 만드는 데 어떻게 도움이 될까?
- 도메인 코드에 플로그인처럼 동작하는 영속성 어댑터는 도메인이 영속성에 분리되어 풍부한 도메인 모델이 가능하다.
- 좁은 포트 인터페이스는 포트마다 다른 방식으로 구현 가능하기 때문에 유연하다.
- 포트의 인터페이스만 지킨다면 영속성 기술 선택에 자유로워진다.