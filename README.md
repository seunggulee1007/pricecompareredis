
# Price Compare Redis

Redis 기반 캐싱을 활용한 Spring Boot 가격 비교 애플리케이션입니다.  
외부 API에서 상품 정보를 가져오고, Redis를 통해 가격 정보를 캐싱하여 효율적인 가격 비교 서비스를 제공합니다.

## 🧩 기술 스택

- Java 17
- Spring Boot 3.x
- Spring Data Redis
- Redis (local or remote)
- Maven

## 📦 주요 기능

- 🔄 외부 상품 가격 조회 API 연동
- 🧠 Redis 기반 캐시 처리
- 📉 동일 상품에 대해 최저가 제공
- 📁 JSON 직렬화/역직렬화 통한 데이터 저장
- 💡 TTL(Time To Live) 설정을 통한 자동 만료 처리

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/pricecompare/
│   │   ├── config/        # Redis 설정
│   │   ├── controller/    # REST API 컨트롤러
│   │   ├── dto/           # 응답/요청 DTO
│   │   ├── model/         # Redis 저장용 도메인
│   │   ├── service/       # 핵심 비즈니스 로직
│   │   └── PriceCompareRedisApplication.java
│   └── resources/
│       └── application.yml
```

## 🚀 실행 방법

### 1. Redis 설치

```bash
# MacOS (Homebrew)
brew install redis
brew services start redis
```

또는 Docker:

```bash
docker run --name redis -p 6379:6379 -d redis
```

### 2. 애플리케이션 실행

```bash
# 프로젝트 루트에서 실행
./mvnw spring-boot:run
```

### 3. API 테스트

#### 상품 가격 조회 요청

```http
GET /api/compare?productName=아이폰15
```

**Response**
```json
{
  "productName": "아이폰15",
  "minPrice": 1230000,
  "shopName": "Naver",
  "cached": false
}
```

## 🛠 설정

`application.yml` 예시:

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```

## 🧪 테스트

```bash
./mvnw test
```

## 📌 참고

- Redis 캐시는 특정 키 기반으로 저장되며, TTL 설정으로 일정 시간 이후 자동 제거됩니다.
- 외부 API에서 받은 상품 가격은 DTO로 변환되어 Redis에 저장됩니다.

## 👨‍💻 개발자

- [seunggulee1007](https://github.com/seunggulee1007)
