# 토이프로젝트: 게시판 구현 (목표: 정용석 학원 홈페이지 Open)
<hr />

## 사용 기술

#### Backend
- Java (Build Tool: Gradle)
- Springboot
- JPA

#### Infra
- AWS EC2
- AWS RDS (w. MariaDB)
- AWS S3

#### CI/CD
- Travis CI
- AWS CodeDeploy

## [전체 구조]
`Springboot`를 통한 `Backend API` 서버이며 자동화 배포를 위하여 `git repository` master branch에 push가 되면
정의된 `.travis.yml` 정보를 통해 `Travis CI`에서 빌드가 되고 빌드 성공 시 `AWS S3 Bucket`에 빌드 파일이 zip 형태로 업로드 됨.

`AWS Codedeploy` 서비스를 통해 `AWS EC2` 서버로 빌드 파일을 가져와 실행
(서버에 기 배포되어 있을 경우 프로세스 종료 후 재 실행)

[API 서버 URL]   
EC2: https://ec2-3-39-57-119.ap-northeast-2.compute.amazonaws.com:8443
Domain 설정 : https://projects.yongcademy.kro.kr:8443/

[Frontend]   
https://homepage-nextjs-eight.vercel.app/ (Chrome에서 setCookie 동작하지 않아 로그인 되지 않음)
https://github.com/GyuYeonHan/homepage-nextjs (Frontend Git Repository 주소)

관리자 계정 제공 : admin / admin

## REST API 정의
1. 유저
    - 유저 전체 조회 - GET /api/user
    - 유저 조회 - GET /api/user/{userId}
    - 유저 생성 - POST /api/user
  
- 인증
   - 로그인 - POST /api/auth/login
   - 로그아웃 - POST /api/auth/logout
   - 세션확인 - GET /api/auth/session 

- 게시글
   - 전체 게시글 조회 - GET /api/post
   - 전체 공지글 조회 - GET /api/post/announcement
   - 전체 질문글 조회 - GET /api/post/question
   - 공지글 생성 - POST /api/post/announcement
   - 질문글 생성 - POST /api/post/question
   - 게시글 조회 - GET /api/post/{postId}
   - 게시글 수정 - PUT /api/post/{postId}
   - 게시글 삭제 - DELETE /api/post/{postId}

- 댓글
   - 댓글 생성 - GET /api/comment/post/{postId}
   - 댓글 삭제 - GET /api/post/{commentId}
   
- 알림
   - 유저 별 알림 전체 조회 - GET /api/notice/{userId}/all
   - 유저 별 읽지 않은 알림 조회 - GET /api/notice/{userId}/unread
   - 알림 읽은 상태로 변경 - PATCH /api/notice/{noticeId}