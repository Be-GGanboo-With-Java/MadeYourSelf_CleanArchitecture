---

title: ( 만들면서 배우는 클린 아키텍처 ) CHAPTER 4. 유스케이스 확인하기
layout: post
description: 네트워크 프로그래밍 개념 중간 점검
redirect_from:

- /2022/10/31/

---

# CHAPTER 04. 코드 구성하기

---

### 전체 코드

[CHAPTER 4. 전체 코드 확인하기](https://github.com/Be-GGanboo-With-Java/MadeYourself_CleanArchitecture/tree/main/examplecode/src/main/java/io/reflectoring/buckpal)

---

## 풍부한 도메인 모델 vs 빈약한 도메인 모델

풍부한 도메인 모델은 애플리케이션의 코어에 있는 엔티티에서 가능한 많은 도메인 로직이 구현된다.

엔티티들은 상태를 변경하는 메서드를 제공하고, 비즈니스 규치엑 맞는 유효한 변경만을 허용한다. --> DDD 철학을 따른다.

빈약한 도메인 모델은 엔티티 자체가 굉장히 얇고 엔티티 상태를 표현하는 필드와 이 값에 대한 Getter/Setter 밖에 없다. --> 보통 우리가 사용하는 JPA Entity의 모습이다.


### 풍부한 도메인 모델의 유스케이스 구현부

[CHAPTER 4. 풍부한 도메인 모델의 유스케이스 구현부](https://github.com/Be-GGanboo-With-Java/MadeYourself_CleanArchitecture/tree/main/examplecode/src/main/java/io/reflectoring/buckpal/account/adapter/in/web)

풍부한 도메인 모델의 유스케이스는 도메인 모델의 진입점으로 동작한다. (p.48)

이어서 유스케이스는 사용자의 의도만을 표현하면서 이 의도의 실제 작업을 수행하는 도메인 엔티티 메서드 호출로 변환다.

즉, Controller 역할을 하는 Adapter 가 아닐까 하는 추측이 있다. (이건 좀 이야기 해봐야할듯 유스케이스가 뭔지 정확히 이해가 안되어서 뭔말인지 모르겠다.)


### 빈약한 도메인 모델의 유스케이스 구현부

빈약한 도메인 모델의 유스케이스는 유스케이스 클래스 자체에 있다.

도메인 로직이 유스케이스 클래스에 구현돼 있다는 것이다.

엔티티의 상태 변경, 데이터베이스 접근을 담당하는 아웃고잉 포트에 엔티티에 전달할 책임 역시 유스케이스 클래스에 있다.

> 실제로 우리는 보편적으로 Controller -> Service or Repository -> DB 의 흐름으로 위에서 이야기하는 흐름을 수행한다고 본다.