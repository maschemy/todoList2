# 📝 TodoList2

> 유저 인증 기반의 일정 관리 시스템 RESTful API 서버

<br>

## 📌 프로젝트 소개

로그인한 유저가 일정을 작성하고, 다른 유저의 일정에 댓글을 남길 수 있는 일정 관리 시스템입니다.  
Cookie/Session 기반 인증을 통해 본인의 일정과 댓글만 수정 및 삭제할 수 있습니다.

<br>

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot |
| ORM | Spring Data JPA |
| Database | MySQL |
| 인증 | Cookie / Session |
| 암호화 | BCrypt |
| 빌드 | Gradle |

<br>

## 📁 프로젝트 구조

```
src/main/java/com/todolist2
├── controller
│   ├── UserController.java
│   ├── ScheduleController.java
│   └── CommentController.java
├── service
│   ├── UserService.java
│   ├── ScheduleService.java
│   └── CommentService.java
├── repository
│   ├── UserRepository.java
│   ├── ScheduleRepository.java
│   └── CommentRepository.java
├── entity
│   ├── BaseEntity.java
│   ├── User.java
│   ├── Schedule.java
│   └── Comment.java
├── dto
│   ├── userDto
│   ├── scheduleDto
│   └── commentdto
├── exception
│   ├── CustomException.java
│   ├── NotFoundException.java
│   ├── UnauthorizedException.java
│   ├── ForbiddenException.java
│   └── GlobalExceptionHandler.java
└── TodoList2Application.java
```

<br>

## 🗂 ERD

```
┌──────────────────────┐         ┌──────────────────────────┐
│        users         │         │        schedules          │
├──────────────────────┤         ├──────────────────────────┤
│ id (PK)              │ 1     N │ id (PK)                  │
│ user_name            │────────►│ user_id (FK)             │
│ email (UNIQUE)       │         │ title                    │
│ password             │         │ contents                 │
│ created_at           │         │ created_at               │
│ modified_at          │         │ modified_at              │
└──────────────────────┘         └──────────────────────────┘
         │                                   │
         │ 1                               1 │
         │               ┌───────────────────┘
         │               │
         │             N │
         │  ┌────────────▼─────────────────┐
         │  │          comments            │
         │  ├──────────────────────────────┤
         └─►│ id (PK)                      │
          N │ user_id (FK)                 │
            │ schedule_id (FK)             │
            │ comment_contents             │
            │ created_at                   │
            │ modified_at                  │
            └──────────────────────────────┘
```

- `users` 1 : N `schedules`
- `users` 1 : N `comments`
- `schedules` 1 : N `comments`

<br>

## 📋 API 명세서

### 👤 User API

<details>
<summary>회원가입</summary>

---

- **POST** `/users/signup`
- 인증 불필요

**Request Body**
```json
{
  "userName": "홍길동",
  "email": "hong@email.com",
  "password": "password123"
}
```

**Response** `201 Created`
```json
{
  "id": 1,
  "userName": "홍길동",
  "email": "hong@email.com",
  "createdAt": "2024-01-01T00:00:00",
  "modifiedAt": "2024-01-01T00:00:00"
}
```

---

</details>

<details>
<summary>로그인</summary>

---

- **POST** `/users/login`
- 인증 불필요

**Request Body**
```json
{
  "email": "hong@email.com",
  "password": "password123"
}
```

**Response** `200 OK`
> 응답 바디 없음, 세션 쿠키 발급

---

</details>

<details>
<summary>로그아웃</summary>

---

- **POST** `/users/logout`
- 인증 불필요

**Response** `200 OK`
```
로그아웃 성공
```

---

</details>

<details>
<summary>전체 유저 조회</summary>

---

- **GET** `/users`
- 인증 불필요

**Response** `200 OK`
```json
[
  {
    "id": 1,
    "username": "홍길동",
    "createdAt": "2024-01-01T00:00:00",
    "modifiedAt": "2024-01-01T00:00:00"
  }
]
```

---

</details>

<details>
<summary>단건 유저 조회</summary>

---

- **GET** `/users/{userId}`
- 인증 불필요

**Response** `200 OK`
```json
{
  "id": 1,
  "username": "홍길동",
  "createdAt": "2024-01-01T00:00:00",
  "modifiedAt": "2024-01-01T00:00:00"
}
```

---

</details>

<details>
<summary>유저 수정 🔒</summary>

---

- **PATCH** `/users/{userId}`
- 🔒 인증 필요 (본인만 가능)

**Request Body** (변경할 필드만 전송 가능)
```json
{
  "userName": "김철수",
  "email": "kim@email.com"
}
```

**Response** `200 OK`
```json
{
  "id": 1,
  "userName": "김철수",
  "email": "kim@email.com",
  "createdAt": "2024-01-01T00:00:00",
  "modifiedAt": "2024-01-02T00:00:00"
}
```

---

</details>

<details>
<summary>유저 삭제 </summary>

---

- **DELETE** `/users/{userId}`
- 🔒 인증 필요 (본인만 가능)

**Response** `204 No Content`

---

</details>

<br>

### 📅 Schedule API

<details>
<summary>일정 생성 🔒</summary>

---

- **POST** `/schedules`
- 🔒 인증 필요

**Request Body**
```json
{
  "title": "오늘의 할일",
  "contents": "운동하기"
}
```

**Response** `201 Created`
```json
{
  "userName": "홍길동",
  "title": "오늘의 할일",
  "contents": "운동하기",
  "createdAt": "2024-01-01T00:00:00",
  "modifiedAt": "2024-01-01T00:00:00"
}
```

---

</details>

<details>
<summary>전체 일정 조회</summary>

---

- **GET** `/schedules`
- 인증 불필요

**Query Parameter**

| 파라미터 | 필수 여부 | 설명 |
|---------|---------|------|
| userName | 선택 | 특정 유저의 일정만 필터링 |

**Response** `200 OK`
```json
[
  {
    "id": 1,
    "userName": "홍길동",
    "title": "오늘의 할일",
    "contents": "운동하기",
    "createdAt": "2024-01-01T00:00:00",
    "modifiedAt": "2024-01-01T00:00:00"
  }
]
```

---

</details>

<details>
<summary>일정 페이징 조회</summary>

---

- **GET** `/schedules/page`
- 인증 불필요
- 수정일 기준 내림차순 정렬

**Query Parameter**

| 파라미터 | 필수 여부 | 기본값 | 설명 |
|---------|---------|-------|------|
| page | 선택 | 0 | 페이지 번호 (0부터 시작) |
| size | 선택 | 10 | 페이지 크기 |

**Response** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "userName": "홍길동",
      "title": "오늘의 할일",
      "contents": "운동하기",
      "commentCount": 3,
      "createdAt": "2024-01-01T00:00:00",
      "modifiedAt": "2024-01-01T00:00:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 10,
  "number": 0
}
```

---

</details>

<details>
<summary>단건 일정 조회</summary>

---

- **GET** `/schedules/{scheduleId}`
- 인증 불필요

**Response** `200 OK`
```json
{
  "id": 1,
  "userName": "홍길동",
  "title": "오늘의 할일",
  "contents": "운동하기",
  "createdAt": "2024-01-01T00:00:00",
  "modifiedAt": "2024-01-01T00:00:00"
}
```

---

</details>

<details>
<summary>일정 수정 </summary>

---

- **PATCH** `/schedules/{scheduleId}`
- 🔒 인증 필요 (본인만 가능)

**Request Body** (변경할 필드만 전송 가능)
```json
{
  "title": "수정된 제목",
  "contents": "수정된 내용"
}
```

**Response** `200 OK`
```json
{
  "id": 1,
  "userName": "홍길동",
  "title": "수정된 제목",
  "contents": "수정된 내용",
  "createdAt": "2024-01-01T00:00:00",
  "modifiedAt": "2024-01-02T00:00:00"
}
```

---

</details>

<details>
<summary>일정 삭제 🔒</summary>

---

- **DELETE** `/schedules/{scheduleId}`
- 🔒 인증 필요 (본인만 가능)

**Response** `204 No Content`

---

</details>

<br>

### 💬 Comment API

<details>
<summary>댓글 생성 </summary>

---

- **POST** `/schedules/{scheduleId}/comments`
- 🔒 인증 필요

**Request Body**
```json
{
  "commentContents": "좋은 일정이네요!"
}
```

**Response** `201 Created`
```json
{
  "id": 1,
  "userName": "홍길동",
  "commentContents": "좋은 일정이네요!",
  "createdAt": "2024-01-01T00:00:00",
  "modifiedAt": "2024-01-01T00:00:00"
}
```

---

</details>

<details>
<summary>댓글 전체 조회</summary>

---

- **GET** `/schedules/{scheduleId}/comments`
- 인증 불필요

**Response** `200 OK`
```json
[
  {
    "id": 1,
    "userName": "홍길동",
    "commentContents": "좋은 일정이네요!",
    "createdAt": "2024-01-01T00:00:00",
    "modifiedAt": "2024-01-01T00:00:00"
  }
]
```

---

</details>

<details>
<summary>댓글 삭제 </summary>

---

- **DELETE** `/schedules/{scheduleId}/comments/{commentId}`
- 🔒 인증 필요 (본인만 가능)

**Response** `204 No Content`

---

</details>

<br>

## ⚠️ 예외처리

| HTTP 상태코드 | 상황 |
|-------------|------|
| 400 Bad Request | Validation 실패 (입력값 오류) |
| 401 Unauthorized | 로그인하지 않은 상태에서 인증 필요 API 호출, 비밀번호 불일치 |
| 403 Forbidden | 본인이 아닌 리소스 수정/삭제 시도 |
| 404 Not Found | 존재하지 않는 유저, 일정, 댓글 조회 |

<br>

## ✅ Validation

| 필드 | 조건 |
|------|------|
| userName | 2글자 이상 6글자 이하, 공백 불가 |
| email | 이메일 형식 |
| password | 8글자 이상, 공백 불가 |
| title | 1글자 이상 10글자 이하, 공백 불가 |
| contents | 200글자 이내 |
