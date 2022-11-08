# 9. 애플리케이션 조립하기

## 1. 조립까지 신경 써야 하는 이유

- 코드의 의존성은 항상 안쪽으로,
  -  도메인 코드 방향으로 향해야 안전함.
- 설정 컴포넌트 (configuration component)가 필요
  - 아키텍처에 중립적
  - 인스턴스 생성을 위해 모든 클래스의 의존성을 가짐

<br>

  <img width="800" src="https://user-images.githubusercontent.com/89288109/200202108-884dc0e4-d6f2-47c2-a9a8-4f71e3f13515.png">

<br>

### 설정 컴포넌트의 역할
- 웹 어댑터 인스턴스 생성
- HTTP 요청이 실제로 웹 어댑터로 전달되도록 보장
- 유스케이스 인스턴스 생성
- 웹 어댑터에 유스케이스 인스턴스 제공
- 영속성 어댑터 인스턴스 생성
- 유스케이스에 영속성 어댑터 인스턴스 제공
- 영속성 어댑터가 실제로 데이터베이스에 접근할 수 있도록 보장

> 이러한 작업은 단일 책임 원칙을 위반하는게 맞다.  
애플리케이션의 모든 부품을 알고 있기 때문.

<br>

## 2. 평범한 코드로 조립하기

```java
package com.book.cleanarchitecture.buckpal;

import com.book.cleanarchitecture.buckpal.account.adapter.in.web.SendMoneyController;
import com.book.cleanarchitecture.buckpal.account.adapter.out.persistence.AccountPersistenceAdapter;
import com.book.cleanarchitecture.buckpal.account.application.port.in.SendMoneyUseCase;
import com.book.cleanarchitecture.buckpal.account.application.service.SendMoneyService;

public class Application {
    public static void main(String[] args) {
        AccountRepository accountRepository = new AccountRepository();
        ActivityRepository activityRepository = new ActivityRepository();

        AccountPersistenceAdapter accountPersistenceAdapter = new AccountPersistenceAdapter(accountRepository, activityRepository);

        SendMoneyUseCase sendMoneyUseCase = new SendMoneyService(
                accountPersistenceAdapter,
                accountPersistenceAdapter
        );

        SendMoneyController sendMoneyController = new SendMoneyController(sendMoneyUseCase);
        
        startProcessingWebRequests(sendMoneyController);
    }
}
```

- 설정 컴포넌트의 간략한 예시
  - main 메서드 안에서 모든 클래스의 인스턴스를 생성 후 연결한 것을 볼 수 있다.
- 애플리케이션이 완전해질 경우 동일한 코드를 끊임 없이 생성해야 함.
- 이러한 구성은 모든 클래스 public 이어야 인스턴스 생성이 가능함. => 최악
- package-private (default) 접근 제한자를 유지하며 자바의 의존성 주입 프레임워크인 스프링을 사용할 수도 있다.

<br>

## 3. 스프링의 클래스패스 스캐닝으로 조립하기
- 스프링 프레임워크를 이용해 조립한 애플리케이션 결과물을 애플리케이션 컨텍스트라고 부름.
  - 구성 객체는 bean.
- 스프링은 클래스패스 스캐닝으로 클래스패스에서 접근 가능한 모든 클래스를 확인해서 `@Component` 애너테이션이 붙은 클래스를 찾는다.

<br>

**스프링 인식 용 커스텀 애노테이션**
```java
package com.book.cleanarchitecture.buckpal.shared;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface UseCase {

    @AliasFor(annotation = Component.class)
    String value() default "";
}
```
- 스프링이 인식할 수 있는 커스텀 애노테이션도 생성 가능하다.
### 단점 
1. 스프링 프레임워크에 매우 의존적임.
2. 흑마법이 일어날 가능성.
    - 당장 Bean으로 등록하지 않기를 바라는 클래스를 막지 못함.
    - 악의적으로 애플리케이션 컨텍스트를 조작하여 추적하기 어려운 에러를 일으킬 우려가 있다.

<br>

## 4. 스프링의 자바 컨피그로 조립하기
- `@Configuration` 을 통해 스캐닝을 가지고 찾아야 하는 설정 클래스임을 표시.
- 모든 Bean을 찾아오는건 아니기 때문에, 마법이 일어날 일은 적다.
- 설정 클래스와 같은 패키지에 넣어놓지 않는 경우에 public으로 해야 한다. -> **단점**
- 패키지를 모듈 경계로 사용하고 각 패키지 안에 전용 클래스를 만들 수 있지만, 하위 패키지를 사용할 순 없다.

<br>

## 5. 유지보수가 가능한 소프트웨어를 만드는 데 어떻게 도움이 되는지?
- 스프링 프레임워크를 이용하여 편리하게 애플리케이션 조립 가능.
- 코드의 규모가 커질 경우 투명성이 낮아짐.
  - 어떤 Bean이 애플리케이션 컨텍스트에 올라오는지 정확히 파악 불가.
- 독립적인 테스트가 불가.