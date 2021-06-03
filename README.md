# Callback


## iamport callback api

---

### Introducion

---
`application.yml`파일의 `channel`에 있는 정보를 이용하여 구동 시 채널 생성


### iamport 발행
- path `POST` `/callback/imp/publish`

- Parameters

|Parameters|Name|Description|
|------|---|---|
|imp_uid|||
|merchant_uid|||
|status|상태|테스트 : ready|

- Example

```aidl
{
    "imp_uid":"imp_1234567890",
    "merchant_id":"merchant_1234567890",
    "status":"ready"
}
```


### iamport 구독 시작
- path `PUT` `/callback/imp/subscribe/start/{key}`

- Parameters
  - key: topic

- Example

```aidl
bus:0:pg:imp
```

### iamport 구독 종료
- path `PUT` `/callback/imp/subscribe/stop/{key}`

- Parameters
  - key: topic

- Example

```aidl
bus:0:pg:imp
```

### 모든 채널 조회
- path `GET` `/callback/imp/channels`

- Response

### 채널 생성
- path `PUT` `/callback/imp/channel/{key}`

- Parameters
    - key: topic

- Example

```aidl
bus:0:pg:imp
```

## Test 진행

### Redis 접속

```aidl
$ docker exec -it v2_redis_1 /bin/sh

# redis 접속
$ redis-cli

127.0.0.1:6379>
```

구독 하기
```aidl
$ subscribe bus:o:pg:imp

1) "subscribe"
2) "bus:0:pg:imp"
3) (integer) 1
```

### worker 기동

jobs.js 확인
```aidl
iamport: { task: 'iamport', triggers: [{ bus: 'pg:imp' }], queue: 0 }
```
worker 기동
```aidl
$ cd v2/worker
$ npm run serve
```

이후 test case - `iamport 발행` 작업 진행

## How to run

---
gradle 빌드
```aidl
$ sudo ./gradlew build
```

빌드 완료 후 실행
```aidl
$ cd build/libs
$ java -jar callback-0.0.1-SNAPSHOT.jar
```

`application.properties` 파일 설정 상 port는 `9090` 으로 설정 함.





