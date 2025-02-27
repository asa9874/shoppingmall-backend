# 📌 쇼핑몰 BackEnd 프로젝트

RESTful API를 제공하는 쇼핑몰 백엔드 서비스
SpringBoot를 통해 BackEnd 서비스를 제작하는 것을 목표

### [프론트엔드 코드](https://github.com/asa9874/shoppingmall-frontend)

## 📌 기술 스택

- **언어**: Java
- **프레임워크**: Spring Boot
- **데이터베이스**: H2
- **ORM**: JPA
- **API 문서화**: Swagger

## 📍 서버 실행

```sh
mvn spring-boot:run
```

## 📍 API 엔드포인트
```
자세한 부분은 Swagger-UI 에서 확인가능 
http://localhost:8080/swagger-ui.html
```

### 🛠 맴버(Member) API
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|------|------|
| `/member/register` | POST | 회원 가입 | ❌ | - |
| `/member/me` | GET | 내 이름| ✅ | - |
| `/member/my-info` | GET | 내 정보 조회 | ✅ | - |
| `/member/update/{id}` | PUT | 회원 정보 수정 | ✅ | - |
| `/member/{id}` | GET | 특정 회원 조회 | ✅ | - |
| `/member/delete/{id}` | DELETE | 회원 삭제 | ✅ | - |

---
### 🛍 상품(Product) API
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|------|--------|
| `/product/` | GET | 상품 목록 조회 | ❌ | - |
| `/product/{productId}` | GET | 상품 조회 | ❌ | - |
| `/product/create` | POST | 상품 생성 | ✅ | SELLER |
| `/product/update` | PUT | 상품 업데이트 | ✅ | SELLER |
| `/product/delete/{productId}` | DELETE | 상품 삭제 | ✅ | SELLER |

---

### 🔑 인증(Auth) API
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|------|--------|
| `/auth/login` | POST | 로그인 (JWT 토큰 반환) | ❌ | - |
| `/auth/refresh-token` | POST | JWT 토큰 초기화 | ✅ | - |
| `/auth/reset-password` | POST | 비밀번호 초기화 | ✅ | - |

---
### 🧪 테스트(Test) API 
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|------|--------|
| `/test/public` | GET | 공용 테스트 API | ❌ | - |
| `/test/protected` | GET | 보호된 테스트 API | ✅ | - |
| `/test/seller` | GET | 판매자 전용 테스트 API | ✅ | SELLER |
| `/test/customer` | GET | 고객 전용 테스트 API | ✅ | CUSTOMER |
| `/test/admin` | GET | 관리자 전용 테스트 API | ✅ | ADMIN |


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