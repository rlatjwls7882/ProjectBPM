# 🧠 ProjectBPM - 게시판 중심 웹 애플리케이션

Spring Boot 기반의 게시판 플랫폼으로 사용자 인증, 게시글 CRUD, 카테고리 분류, 조회수 통계, Markdown 미리보기 등 실용 기능을 제공합니다.

## 🚀 주요 기능

- 사용자 로그인 / 회원가입 / 로그아웃
- 이메일 인증, 비밀번호 암호화
- 게시글 작성, 수정, 삭제
- 카테고리 분류 및 목록 필터링
- 쿠키 기반 조회수 기록 (중복 방지)
- Markdown 기반 글 작성 및 실시간 미리보기
- 사용자 전용 페이지

## 🧱 기술 스택

| 구분       | 내용                                               |
|------------|----------------------------------------------------|
| Backend    | Spring Boot, Java 17, JPA                          |
| Frontend   | Thymeleaf, HTML5, CSS3, JavaScript                 |
| Database   | MySQL                                              |
| Build Tool | Maven                                              |
| Infra      | Ubuntu 22.04 (Hostinger VPS), Nginx, Let's Encrypt |

## 🔐 DevOps & 배포 환경

- **무중단 배포**: `Systemd` + `Nginx reverse proxy`를 이용한 무중단 배포 구성
- **HTTPS 적용**: Let's Encrypt + `.well-known/acme-challenge` → `keystore.p12` 인증서 등록
- **도메인 설정**: `projectbpm.kro.kr` (내도메인.한국 사용)
- **검색 최적화**: `robots.txt`, `sitemap.xml` 구성

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
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EncodeService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EncodeServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ViewService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ViewServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GlobalExceptionHandler.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜GlobalInterceptor.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ProjectBpmApplication.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜WebConfig.java
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
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserServiceTest.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ProjectBpmApplicationTests.java
```
</details>

## 📊 코드 통계
```
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
XML                             17              4              6           2244
HTML                            13            108             12           1585
Java                            39            220            106           1412
Bourne Shell                     1             26             48            185
DOS Batch                        1             15              0            134
CSS                              2             14              2            100
Maven                            1              5              2             84
Properties                       4              7             24             36
Text                             2              1              0              8
-------------------------------------------------------------------------------
SUM:                            80            400            200           5788
-------------------------------------------------------------------------------
```

## 📎 배포 링크
- 정식 서비스: [https://projectBPM.kro.kr](https://projectBPM.kro.kr)