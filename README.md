# 🧠 ProjectBPM - 게시판 중심 웹 애플리케이션

Spring Boot 기반의 게시판 플랫폼으로 사용자 인증, 게시글 CRUD, 카테고리 분류, 조회수 통계, Markdown 미리보기 등 실용 기능을 제공합니다.

## ✨ 만든 이유

웹 서비스 구조 전반을 직접 기획하고, 배포와 운영까지 풀스택 역량 강화를 위해 제작하였습니다. 실사용 가능한 게시판 기반 웹 플랫폼의 구성과 배포 경험을 목적으로 합니다.

## 🚀 주요 기능

- 사용자 로그인 / 회원가입 / 로그아웃
- 이메일 인증 (SMTP 연동)
- 비밀번호는 BCrypt, 기타 개인정보는 AES-256 기반 암호화
- 게시글 작성, 수정, 삭제 (Markdown 지원)
- 카테고리 분류 및 필터링
- 쿠키 기반 중복 방지 조회수 기록
- Markdown 기반 글 작성 및 실시간 미리보기
- 사용자 전용 페이지
- SEO 최적화: robots.txt / sitemap.xml / favicon 등 포함

## 🧱 기술 스택

| 구분       | 내용                                                                   |
|------------|------------------------------------------------------------------------|
| Backend    | Java 17, Spring Boot, Spring Security, Spring Data JPA                 |
| Frontend   | Thymeleaf, HTML5, CSS3, JavaScript                                     |
| Database   | MySQL                                                                  |
| Infra      | Ubuntu 22.04 (Hostinger VPS), Nginx, Let's Encrypt                     |
| Build Tool | Maven                                                                  |
| Security   | BCrypt(비밀번호), AES-256(개인정보), HTTPS(SSL), 세션 및 쿠키 보안       |

## 🔐 DevOps & 배포 환경

- **무중단 배포**: `Systemd` + `Nginx reverse proxy`를 이용한 무중단 배포 구성
- **HTTPS 적용**: Let's Encrypt + `.well-known/acme-challenge` → `keystore.p12` 인증서 등록
- **도메인 설정**: `projectbpm.kro.kr` (내도메인.한국 사용)
- **검색엔진 최적화**: `robots.txt`, `sitemap.xml`, `favicon.ico` 포함

### 🔒 보안 처리 구조

- 비밀번호는 `BCrypt` 해시 함수로 저장 (역방향 복호화 불가능)
- 이메일 등 민감 정보는 `AES-256`으로 암호화 후 DB 저장
- HTTPS 환경에서 모든 데이터 송수신 암호화

### ⚙️ 환경 설정 (`application.properties`)

```
spring.application.name=projectBPM

# DB(MYSQL)
spring.config.import=optional:file:.env[.properties]
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA and Hibernate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update

# table engine and DB encoding
spring.jpa.properties.hibernate.dialect.storage_engine=innodb
spring.jpa.properties.hibernate.connection.characterEncoding=utf8mb4
spring.jpa.properties.hibernate.connection.useUnicode=true
spring.jpa.properties.hibernate.connection.collation=utf8mb4_unicode_ci

# Ignore JSESSIONID to url when using redirect
server.servlet.session.tracking-modes=cookie

# session timeout
server.servlet.session.timeout=7d

# EMAIL
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.starttls.enable=true

# encode
crypto.aes.key=${AES_KEY}

# ssl
# openssl pkcs12 -export -inkey private.key -in certificate.crt -certfile ca_bundle.crt -out keystore.p12 -name keystore
server.port=443
server.ssl.enabled=true
server.ssl.key-store=classpath:cert/keystore.p12
server.ssl.key-store-password=${SERVER_SSL_KEY_STORE_PASSWORD}
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=keystore
```

## 📂 프로젝트 구조
<details>
<summary>펼쳐보기</summary>
 
```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂kr
 ┃ ┃ ┃ ┗ 📂kro
 ┃ ┃ ┃ ┃ ┗ 📂projectbpm
 ┃ ┃ ┃ ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂crypto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Aes256Utils.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GlobalExceptionHandler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂intercepter
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GlobalInterceptor.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MaskingUtils.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SessionUtils.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HomeController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜LoginController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RegisterController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SitemapController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Board.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Category.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜User.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜View.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ViewRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ViewService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ViewServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ProjectBpmApplication.java
 ┃ ┗ 📂resources
 ┃ ┃ ┣ 📂cert
 ┃ ┃ ┃ ┗ 📜keystore.p12
 ┃ ┃ ┣ 📂static
 ┃ ┃ ┃ ┣ 📂.well-known
 ┃ ┃ ┃ ┃ ┗ 📂acme-challenge
 ┃ ┃ ┃ ┃ ┃ ┗ 📜web.config
 ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┃ ┣ 📜board.css
 ┃ ┃ ┃ ┃ ┗ 📜error.css
 ┃ ┃ ┃ ┣ 📂images
 ┃ ┃ ┃ ┃ ┣ 📜image.png
 ┃ ┃ ┃ ┃ ┣ 📜logo.png
 ┃ ┃ ┃ ┃ ┗ 📜searchicon.png
 ┃ ┃ ┃ ┣ 📜favicon.ico
 ┃ ┃ ┃ ┗ 📜robots.txt
 ┃ ┃ ┣ 📂templates
 ┃ ┃ ┃ ┣ 📂error
 ┃ ┃ ┃ ┃ ┣ 📜404.html
 ┃ ┃ ┃ ┃ ┗ 📜500.html
 ┃ ┃ ┃ ┣ 📂fragments
 ┃ ┃ ┃ ┃ ┣ 📜header.html
 ┃ ┃ ┃ ┃ ┣ 📜markdown.html
 ┃ ┃ ┃ ┃ ┗ 📜nav.html
 ┃ ┃ ┃ ┗ 📂views
 ┃ ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┃ ┃ ┣ 📜read.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜write.html
 ┃ ┃ ┃ ┃ ┣ 📂login
 ┃ ┃ ┃ ┃ ┃ ┣ 📜changePassword.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜loginForm.html
 ┃ ┃ ┃ ┃ ┣ 📂register
 ┃ ┃ ┃ ┃ ┃ ┣ 📜registerForm.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜terms.html
 ┃ ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┃ ┗ 📜userPage.html
 ┃ ┃ ┃ ┃ ┗ 📜home.html
 ┃ ┃ ┗ 📜application.properties
 ┗ 📂test
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂kr
 ┃ ┃ ┃ ┗ 📂kro
 ┃ ┃ ┃ ┃ ┗ 📂projectbpm
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardRepositoryTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRepositoryTest.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ProjectBpmApplicationTests.java
```
</details>

## 📊 코드 통계 *(by cloc)*
```
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
XML                             17              7              6           2138
HTML                            13            108             12           1586
Java                            40            232            119           1517
Bourne Shell                     1             26             48            185
DOS Batch                        1             15              0            134
CSS                              2             14              2            100
Maven                            1              5              2             97
Properties                       4              8             25             37
Text                             2              1              0              8
-------------------------------------------------------------------------------
SUM:                            81            416            214           5802
-------------------------------------------------------------------------------
```

## 📎 배포 링크
- [정식 서비스](https://projectbpm.kro.kr)
- HTTPS: Let's Encrypt + Nginx SSL 적용
- Google Search Console 등록 완료