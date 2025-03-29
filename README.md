# ProjectBPM
[깃허브에 올린 사이트](https://projectbpm.koyeb.app) <br>
[현재 제작중인 사이트](http://projectBPM.kro.kr)

## 총 코드 수
```
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
XML                             15              0              0           1987
HTML                            11             89              5           1425
Java                            38            221              9           1373
Bourne Shell                     1             26             48            185
DOS Batch                        1             15              0            134
Maven                            1              5              2             84
CSS                              1              9              2             64
Markdown                         3             10              0             42
Properties                       3              5             20             23
-------------------------------------------------------------------------------
SUM:                            74            380             86           5317
-------------------------------------------------------------------------------
```

## 📂프로젝트 구조
```
📦main
 ┣ 📂java
 ┃ ┗ 📂kr
 ┃ ┃ ┗ 📂kro
 ┃ ┃ ┃ ┗ 📂projectbpm
 ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CategoryController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜EmailController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜HomeController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜LoginController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜RegisterController.java
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
 ┃ ┃ ┃ ┃ ┃ ┣ 📜EncodeService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜EncodeServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜UserService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜UserServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜ViewService.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ViewServiceImpl.java
 ┃ ┃ ┃ ┃ ┣ 📜GlobalExceptionHandler.java
 ┃ ┃ ┃ ┃ ┣ 📜GlobalInterceptor.java
 ┃ ┃ ┃ ┃ ┣ 📜ProjectBpmApplication.java
 ┃ ┃ ┃ ┃ ┗ 📜WebConfig.java
 ┗ 📂resources
 ┃ ┣ 📂static
 ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┗ 📜board.css
 ┃ ┃ ┗ 📂images
 ┃ ┃ ┃ ┣ 📜favicon.ico
 ┃ ┃ ┃ ┣ 📜image.png
 ┃ ┃ ┃ ┣ 📜logo.png
 ┃ ┃ ┃ ┗ 📜searchicon.png
 ┃ ┣ 📂templates
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