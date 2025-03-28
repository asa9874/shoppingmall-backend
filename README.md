# 📌 쇼핑몰 BackEnd 프로젝트

RESTful API를 제공하는 쇼핑몰 백엔드 서비스
SpringBoot를 통해 BackEnd 서비스를 제작하는 것을 목표

### [프론트엔드 코드](https://github.com/asa9874/shoppingmall-frontend)

## 📌 기술 스택

- **언어**: Java
- **프레임워크**: Spring Boot
- **데이터베이스**: H2, Redis
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
---

### ❓ **질문(Question) API**
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|--------------|------|
| `/question/` | GET | 질문 목록 조회 | ❌ | - |
| `/question/{questionId}` | GET | 특정 질문 조회 | ❌ | - |
| `/question/{questionId}/answer` | GET | 특정 질문의 답변 조회 | ❌ | - |
| `/question/` | POST | 질문 생성 | ✅ | - |
| `/question/{questionId}` | PUT | 질문 수정 | ✅ | 작성자 |
| `/question/{questionId}` | DELETE | 질문 삭제 | ✅ | 작성자 |

---

### 💬 **답변(Answer) API**
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|--------------|------|
| `/answer/` | GET | 답변 목록 조회 | ❌ | - |
| `/answer/{id}` | GET | 특정 답변 조회 | ❌ | - |
| `/answer/{questionId}` | POST | 특정 질문에 답변 작성 | ✅ | - |
| `/answer/{id}` | PUT | 답변 수정 | ✅ | 작성자 |
| `/answer/{id}` | DELETE | 답변 삭제 | ✅ | 작성자 |

---

### 👤 **맴버(Member) API**
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|--------------|------|
| `/member/register` | POST | 회원 가입 | ❌ | - |
| `/member/me` | GET | 내 계정 정보 | ✅ | - |
| `/member/my-info` | GET | 내 상세 정보 조회 | ✅ | - |
| `/member/{memberId}` | GET | 특정 회원 조회 | ✅ | 본인 |
| `/member/{memberId}` | PUT | 회원 정보 수정 | ✅ | 본인 |
| `/member/{memberId}` | DELETE | 회원 삭제 | ✅ | 본인 |

---

### 🛍 **구매자(Customer) API**
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|--------------|------|
| `/customer/{memberId}/orders` | GET | 구매 내역 조회 | ✅ | CUSTOMER |
| `/customer/{memberId}/orders` | POST | 주문 생성 | ✅ | CUSTOMER |
| `/customer/{memberId}/orders/{productId}` | POST | 특정 상품 주문 | ✅ | CUSTOMER |
| `/customer/{memberId}/orders/{orderId}` | GET | 특정 주문 조회 | ✅ | CUSTOMER |
| `/customer/{memberId}/cart` | GET | 장바구니 조회 | ✅ | CUSTOMER |
| `/customer/{memberId}/cart/{productId}` | POST | 장바구니에 상품 추가 | ✅ | CUSTOMER |
| `/customer/{memberId}/cart/{cartItemId}` | DELETE | 장바구니에서 상품 삭제 | ✅ | CUSTOMER |
| `/customer/{memberId}/cart/clear` | DELETE | 장바구니 비우기 | ✅ | CUSTOMER |

---

### 🏬 **상품(Product) API**
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|--------------|------|
| `/product/` | GET | 상품 목록 조회 | ❌ | - |
| `/product/{productId}` | GET | 특정 상품 조회 | ❌ | - |
| `/product/{productId}/reviews` | GET | 특정 상품의 리뷰 조회 | ❌ | - |
| `/product/search` | GET | 상품 검색 | ❌ | - |

---

### 🔑 **인증(Auth) API**
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|--------------|------|
| `/auth/login` | POST | 로그인 (JWT 발급) | ❌ | - |
| `/auth/refresh-token` | POST | JWT 토큰 갱신 | ✅ | 본인 |
| `/auth/reset-password` | POST | 비밀번호 재설정 | ✅ | 본인 |
| `/auth/logout` | POST | 로그아웃 | ✅ | 본인 |

---

### 🛒 **판매자(Seller) API**
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|--------------|------|
| `/seller/{memberId}/products` | GET | 판매 상품 목록 조회 | ❌ | - |
| `/seller/{memberId}/product/create` | POST | 상품 등록 | ✅ | SELLER |
| `/seller/{memberId}/product/{productId}` | PUT | 상품 수정 | ✅ | SELLER |
| `/seller/{memberId}/product/{productId}` | DELETE | 상품 삭제 | ✅ | SELLER |

---

### ⭐ **리뷰(Review) API**
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|--------------|------|
| `/review/` | POST | 리뷰 작성 | ✅ | CUSTOMER |
| `/review/{reviewId}` | GET | 리뷰 조회 | ❌ | - |
| `/review/{reviewId}` | PUT | 리뷰 수정 | ✅ | CUSTOMER (작성자) |
| `/review/{reviewId}` | DELETE | 리뷰 삭제 | ✅ | CUSTOMER (작성자) |

---

### 📲 **알림(Notification) API**
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|--------------|------|
| `/notification/` | GET | 알림 목록 조회 | ✅ | - |
| `/notification/{notificationId}` | GET | 특정 알림 조회 | ✅ | - |
| `/notification/{notificationId}` | PUT | 알림 업데이트 | ✅ | - |
| `/notification/{notificationId}` | DELETE | 알림 삭제 | ✅ | - |
| `/notification` | POST | 알림 생성 | ✅ | - |

---

### 👮‍♂️ **관리자(Admin) API**
| 엔드포인트 | 메서드 | 설명 | 인증 필요 여부 | ROLE |
|------------|--------|------|--------------|------|
| `/admin/unban/member/{memberId}` | POST | 회원의 차단 해제 | ✅ | ADMIN |
| `/admin/ban/member/{memberId}` | POST | 회원 차단 | ✅ | ADMIN |
| `/admin/users` | GET | 전체 사용자 목록 조회 | ✅ | ADMIN |
| `/admin/test` | GET | 관리자용 테스트 | ✅ | ADMIN |
| `/admin/ban/members` | GET | 차단된 회원 목록 조회 | ✅ | ADMIN |

---


---

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
