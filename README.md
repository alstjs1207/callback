# Callback

---

## Introduction

---
`application.yml`파일의 `channel`에 있는 정보를 이용하여 구동 시 채널 생성

---
## 발행(Pub)

### site 별 발행
- path `POST` `/callback/publish/{site}`


## 공통 모듈

### 구독 시작
- path `PUT` `/callback/subscribe/start/{key}`

- Parameters
  - key: topic

- Example

```
/callback/subscribe/start/ping
```

### 구독 종료
- path `PUT` `/callback/subscribe/stop/{key}`

- Parameters
  - key: topic

- Example

```
/callback/subscribe/stop/ping
```

### 모든 채널 조회
- path `GET` `/callback/channels`

- Response

### 채널 생성
- path `PUT` `/callback/channel/{key}`

- Parameters
    - key: topic

- Example

```
/callback/channel/ping
```



## How to run

---
gradle 빌드
```
$ sudo ./gradlew build
```

빌드 완료 후 실행
```
$ cd build/libs
$ java -jar callback-0.0.1-SNAPSHOT.jar
```

`application.properties` 파일 설정 상 port는 `9090` 으로 설정 함.


