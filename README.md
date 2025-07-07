# 🧠 ProjectBPM
## 🔗 배포 링크
* [정식 배포 사이트](http://projectBPM.kro.kr)

## 📌 개요
ProjectBPM은 게시판 기능 중심의 웹 애플리케이션입니다.

✔ 사용자 인증, 게시물 CRUD, 카테고리 분류, 조회수 통계 기능 포함  
✔ Spring Boot, Thymeleaf, JPA, MySQL로 제작  
✔ Koyeb, 내도메인.한국 (`projectbpm.kro.kr`) 배포 환경 구성

## 🧱 기술 스택
| Category       | Tech Stack                                  |
| -------------- | ------------------------------------------- |
| **Backend**    | Spring Boot, JPA, Java 17                   |
| **Frontend**   | HTML5, CSS3, Thymeleaf, JavaScript          |
| **Database**   | MySQL                                       |
| **Build Tool** | Maven                                       |
| **Deployment** | hostinger, 내도메인.한국 (`projectbpm.kro.kr`)   |

## 📂프로젝트 구조
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
 ┃ ┃ ┃ ┃ ┃ ┣ 📜SecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜WebConfig.java
 ┃ ┗ 📂resources
 ┃ ┃ ┣ 📂cert
 ┃ ┃ ┃ ┗ 📜keystore.p12
 ┃ ┃ ┣ 📂static
 ┃ ┃ ┃ ┣ 📂.well-known
 ┃ ┃ ┃ ┃ ┗ 📂acme-challenge
 ┃ ┃ ┃ ┃ ┃ ┗ 📜web.config
 ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┃ ┗ 📜board.css
 ┃ ┃ ┃ ┣ 📂images
 ┃ ┃ ┃ ┃ ┣ 📜image.png
 ┃ ┃ ┃ ┃ ┣ 📜logo.png
 ┃ ┃ ┃ ┃ ┗ 📜searchicon.png
 ┃ ┃ ┃ ┣ 📜favicon.ico
 ┃ ┃ ┃ ┗ 📜robots.txt
 ┃ ┃ ┣ 📂templates
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
```
</details>

## 🚀 주요 기능
* 사용자 회원가입 / 로그인 / 로그아웃
* 이메일 인증 및 비밀번호 암호화
* 게시글 작성, 수정, 삭제, 카테고리 분류
* 조회수 기록 (쿠키 기반 중복 방지)
* Markdown 기반 게시글 작성 + 미리보기
* 사용자 전용 페이지 및 정보 수정

## 🧪 테스트 및 배포
* hostinger를 통한 무중단 배포
* HTTPS 적용 (Let's Encrypt / keystore.p12)
* robots.txt 및 sitemap.xml 구성
* .well-known/acme-challenge로 도메인 인증 처리

## 📊 코드 통계
```
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
XML                             16              1              6           2045
HTML                            11             95              6           1520
Java                            40            235             41           1485
Bourne Shell                     1             26             48            185
Markdown                         2             15              0            146
DOS Batch                        1             15              0            134
Maven                            1              6              2             84
CSS                              1              9              2             64
Properties                       3              6             22             29
Text                             1              1              0              4
-------------------------------------------------------------------------------
SUM:                            77            409            127           5696
-------------------------------------------------------------------------------
```
