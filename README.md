# 📌 쇼핑몰 BackEnd 프로젝트

RESTful API를 제공하는 쇼핑몰 백엔드 서비스
SpringBoot를 통해 BackEnd 서비스를 제작하는 것을 목표
다양한 backend 기술들을 배우고 적용해보는 연습용 프로젝트

### [프론트엔드 코드](https://github.com/asa9874/shoppingmall-frontend)

## 📌 기술 스택

- **언어**: Java
- **프레임워크**: Spring Boot
- **데이터베이스**: H2, Redis
- **ORM**: JPA
- **API 문서화**: Swagger

# 🔍관련 작성 블로그 글
- [쇼핑몰 상품 조회기능 만들기](https://asa9874.tistory.com/661)
- [쇼핑몰 회원가입 기능 제작](https://asa9874.tistory.com/662)
- [JWT와 로그인,로그아웃 기능 제작](https://asa9874.tistory.com/663)
- [쇼핑몰 상품 엔드포인트제작,예외처리, 로그설정](https://asa9874.tistory.com/665)
- [역할 접근권한설정, 상품 소유 인증](https://asa9874.tistory.com/666)
- [@PreAuthorize 사용설정](https://asa9874.tistory.com/667)
- [쇼핑몰 Entity 추가, 엔티티 어노테이션 상세 사용법](https://asa9874.tistory.com/668)
- [Redis 추가, Redis로 JWT 블랙리스트 구현](https://asa9874.tistory.com/669)
- [Redis 로 Spring Cache 추가](https://asa9874.tistory.com/670)
- [Rate Limiting 기능, AOP 적용,어노테이션 만들기](https://asa9874.tistory.com/671)
- [redis로 실시간 인기 제품](https://asa9874.tistory.com/672)
- [이미지 업로드 기능만들기](https://asa9874.tistory.com/673)
- [redis + 스케쥴링으로 조회수 관리](https://asa9874.tistory.com/674)
- [redis 예외 필터, redis 연결상태 확인 이벤트 리스너, 추가](https://asa9874.tistory.com/675)
- [log aspect, API 표준 Response 제작](https://asa9874.tistory.com/677)
- [Actuator + Prometheus + Grafana 로 모니터링](https://asa9874.tistory.com/678)
- [WebSocket 사용하기](https://asa9874.tistory.com/679)
- [JWT + Oauth2 추가하기](https://asa9874.tistory.com/680)
- [WebFlux기반으로 외부 API 받기](https://asa9874.tistory.com/681)
- [이미지 리사이징 하기](https://asa9874.tistory.com/683)
- [Maven -> Gradle 마이그레이션하기](https://asa9874.tistory.com/689)



## 📍 서버 실행

```sh
mvn spring-boot:run
```

## 📍 API 엔드포인트

```
자세한 부분은 Swagger-UI 에서 확인가능
http://localhost:8080/swagger-ui.html
```

### 성공 응답 예시
``` 
{
  "message": "Success",
  "data": {
    "id": 1,
    "name": "Product A",
    "price": 100
  },
  "status": 200,
  "error": null
}
```

## 📌 인증 방식

JWT (JSON Web Token)를 사용하여 인증을 처리하고,
`Authorization` 헤더에 포함시켜야 합니다.

### JWT 토큰 발급

```http
POST /auth/login
Content-Type: application/json
{
  "username": "testuser",
  "password": "password"
}
```

### JWT 토큰을 이용한 인증

```http
GET /test/protected
Authorization: Bearer {JWT 토큰}
```

## 📌 빌드 및 배포 명령어

### 빌드

```sh
mvn clean install
```

### 빌드 후 배포

```sh
java -jar target/빌드된_이름.jar
```
