# SQLD 연습 서비스

SQLD(SQL 개발자) 자격증 취득을 준비하는 사용자를 위해 문제 풀이 환경과 효율적인 오답 관리 시스템을 제공하는 웹 서비스입니다.

## 1. 프로젝트 개요

* **소개:** 데이터 기반의 분석을 통해 취약점을 보완하는 SQLD 학습 플랫폼

## 2. 주요 기능

* **시험 응시:** 50문항 랜덤 구성 및 마크다운 기반의 지문/SQL 코드/테이블 렌더링
* **자동 오답 북마크:** 문제를 틀릴 경우 시스템이 자동으로 해당 문제를 북마크에 추가하여 별도의 오답 정리 시간을 단축
* **통합 오답노트:** 사용자가 직접 북마크한 문제와 오답으로 자동 추가된 문제를 다시 풀어보고, 상세 해설 및 정답 확인 기능 제공
* **취약 분야 리포트:** 오답 데이터를 태그별(예: 조인, 서브쿼리, 윈도우 함수 등)로 집계하여 시각화된 리포트 제공
* **실시간 타이머:** 웹 사이트를 나갔다 들어와도 유지되는 실제 시험 시간 타이머 기능

## 3. 기술 스택

### Backend & Frontend

* **Language:** Java 21
* **Framework:** Spring Boot 4.0.1
* **View:** Thymeleaf (Server-Side Rendering)
* **ORM:** Spring Data JPA
* **Security:** Spring Security, JWT

### Database

* **Main DB:** MySQL 8.4.8
* **Cache:** Redis

## 4. 데이터베이스 설계

* **ERD:** (추후 이미지 업로드 예정)

| 테이블명 | 설명 | 주요 컬럼 |
| --- | --- | --- |
| **users** | 회원 정보 | `id`, `email`, `password`, `nickname`, `role` |
| **problems** | SQLD 문제 데이터 | `id`, `category`, `content`, `sql_code`, `answer`, `explanation` |
| **exams** | 사용자의 시험 응시 기록 | `id`, `user_id`, `score`, `taken_at`, `duration` |
| **exam_details** | 시험별 개별 문항 응시 결과 | `id`, `exam_id`, `problem_id`, `user_answer`, `is_correct` |
| **bookmarks** | **오답 및 관심 문제 통합 관리** | `id`, `user_id`, `problem_id`, `is_auto_added(오답여부)`, `created_at` |

## 5. 프로젝트 구조

```text
src/main/java/com/sqld/practice/
├── domain/            # Entity 및 Domain 로직
├── controller/        # 웹 요청 처리 및 API 엔드포인트
├── service/           # 비즈니스 로직 및 트랜잭션 관리
├── repository/        # DB 접근 인터페이스
└── global/            # 공통 설정 (Security, Exception 등)

```

## 6. 컨벤션 (Conventions)

프로젝트의 일관성을 위해 아래 규칙들을 준수합니다.

* **Git Commit:** [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) 가이드를 따릅니다.
* **Code Style:** [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)를 기반으로 작성합니다.
* **Branch Strategy:** [Git Flow](https://nvie.com/posts/a-successful-git-branching-model/) 전략을 단순화하여 사용합니다. (Main - Develop - Feature)

## 7. 라이선스

* 본 프로젝트는 **MIT License**를 따릅니다.

## 8. 연락처 및 피드백

* **Email:** ypungkyu0317@gmail.com
* **Issues:** [GitHub Issues](https://www.google.com/search?q=https://github.com/%EC%9C%A0%EC%A0%80ID/sqld-practice/issues)를 통해 버그나 개선 사항을 남겨주세요.
