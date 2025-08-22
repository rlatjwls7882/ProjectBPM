# ProjectBPM
Spring Boot 기반의 게시판 플랫폼으로 로그인, 게시글 작성, 카테고리 분류, Markdown 파싱 기능을 제공합니다.

## 만든 이유
웹 서비스 구조를 기획하고, 배포와 운영 역량 강화를 위해 제작하였습니다.

## 성과
![](https://github.com/user-attachments/assets/adf9f58c-3f63-4c49-9b4e-9fecad312a20)

## 기술 스택
| 구분       | 내용                                                                   |
|------------|------------------------------------------------------------------------|
| Backend    | Java 17, Spring Boot, Spring Security, Spring Data JPA                 |
| Frontend   | Thymeleaf, HTML5, CSS3, JavaScript                                     |
| Database   | MySQL                                                                  |
| Infra      | Ubuntu 22.04 (Hostinger VPS), Nginx, ZeroSSL                           |
| Build Tool | Maven                                                                  |
| Security   | BCrypt(비밀번호), AES-256(개인정보), HTTPS(SSL), 세션 및 쿠키 보안       |

## DevOps & 배포 환경
- **무중단 배포**: `Systemd` + `Nginx reverse proxy`를 이용한 무중단 배포 구성
- **HTTPS 적용**: ZeroSSL + `.well-known/acme-challenge` → `keystore.p12` 인증서 등록
- **도메인 설정**: `projectbpm.kro.kr` (내도메인.한국 사용)
- **검색엔진 최적화**: `robots.txt`, `sitemap.xml`, `favicon.ico`

### 보안 처리 구조
- 비밀번호는 `BCrypt` 해시 함수로 저장
- 개인정보는 `AES-256`으로 암호화 후 DB 저장
- HTTPS 환경에서 데이터 송수신 암호화

### 환경 설정 (`application.properties`)
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
## openssl pkcs12 -export -inkey private.key -in certificate.crt -certfile ca_bundle.crt -out keystore.p12 -name keystore
server.port=443
server.ssl.enabled=true
server.ssl.key-store=classpath:cert/keystore.p12
server.ssl.key-store-password=${SERVER_SSL_KEY_STORE_PASSWORD}
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=keystore
```

## 프로젝트 구조
<details>
<summary>펼쳐보기</summary>
 
```
📦main
 ┣ 📂java
 ┃ ┗ 📂kr
 ┃ ┃ ┗ 📂kro
 ┃ ┃ ┃ ┗ 📂projectbpm
 ┃ ┃ ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂crypto
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Aes256Utils.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GlobalExceptionHandler.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂intercepter
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GlobalInterceptor.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MaskingUtils.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SessionUtils.java
 ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜HomeController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜LoginController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜RegisterController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜SitemapController.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜UserController.java
 ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Board.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Category.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜User.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜View.java
 ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryDto.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDto.java
 ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜UserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ViewRepository.java
 ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜UserService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜UserServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ViewService.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ViewServiceImpl.java
 ┃ ┃ ┃ ┃ ┗ 📜ProjectBpmApplication.java
 ┗ 📂resources
 ┃ ┣ 📂cert
 ┃ ┃ ┗ 📜keystore.p12
 ┃ ┣ 📂static
 ┃ ┃ ┣ 📂.well-known
 ┃ ┃ ┃ ┗ 📂acme-challenge
 ┃ ┃ ┃ ┃ ┗ 📜web.config
 ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┣ 📜board.css
 ┃ ┃ ┃ ┗ 📜error.css
 ┃ ┃ ┣ 📂images
 ┃ ┃ ┃ ┣ 📜image.png
 ┃ ┃ ┃ ┣ 📜logo.png
 ┃ ┃ ┃ ┗ 📜searchicon.png
 ┃ ┃ ┣ 📜favicon.ico
 ┃ ┃ ┗ 📜robots.txt
 ┃ ┣ 📂templates
 ┃ ┃ ┣ 📂error
 ┃ ┃ ┃ ┣ 📜404.html
 ┃ ┃ ┃ ┗ 📜500.html
 ┃ ┃ ┣ 📂fragments
 ┃ ┃ ┃ ┣ 📜header.html
 ┃ ┃ ┃ ┣ 📜markdown.html
 ┃ ┃ ┃ ┗ 📜nav.html
 ┃ ┃ ┗ 📂views
 ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┃ ┣ 📜read.html
 ┃ ┃ ┃ ┃ ┗ 📜write.html
 ┃ ┃ ┃ ┣ 📂login
 ┃ ┃ ┃ ┃ ┣ 📜changePassword.html
 ┃ ┃ ┃ ┃ ┗ 📜loginForm.html
 ┃ ┃ ┃ ┣ 📂register
 ┃ ┃ ┃ ┃ ┣ 📜registerForm.html
 ┃ ┃ ┃ ┃ ┗ 📜terms.html
 ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┗ 📜userPage.html
 ┃ ┃ ┃ ┗ 📜home.html
 ┃ ┗ 📜application.properties
```
</details>

## 코드 통계 *(by cloc)*
```
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
XML                             17              7              6           2207
HTML                            13            103             16           1589
Java                            40            231            640           1518
Bourne Shell                     1             26             48            185
DOS Batch                        1             15              0            134
CSS                              2             14              2            100
Maven                            1              5              2             97
Properties                       4              8             25             37
Text                             2              1              0              8
-------------------------------------------------------------------------------
SUM:                            81            410            739           5875
-------------------------------------------------------------------------------
```

## 배포 링크
- [정식 서비스](https://projectbpm.kro.kr)
- HTTPS: ZeroSSL + Nginx SSL 적용
- Google Search Console 등록 완료
