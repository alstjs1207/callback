# Callback


## Test Case

### ImpApiControllerTest.kt

1. day1 callback iamport api
- path `POST` `/pg/imp`

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

### HttpApiTests.kt

