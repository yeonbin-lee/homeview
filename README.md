# 홈뷰 (homeview)
## 자취방 리뷰 서비스 
> Homeview는 거주지에 대한 리뷰를 확인하고, 리뷰를 남길 수 있는 **자취방 정보 공유 서비스**입니다.
> 
>**진행 기간**은 [2023.03. ~ 2023.07.] 입니다.

|March|April|May|June|July|
|------|---|---|------|---|
|아이디어 도출, 데모 설계, 웹 디자인|개발|개발|리팩토링 및 코드 최적화, 기능 추가|마무리|


## 0. Motivation & Contribution
* 부동산 정보를 얻기 쉽지 않음
* 실거주 리뷰를 기반으로 한 부동산 정보 공유 플랫폼을 만들고자 함


## 1. Database 
![image](https://github.com/user-attachments/assets/20fc5f2d-e708-4ad3-a435-124008065547)

## 2. Development Environment
* Java 17, Javascript
* Framework: SpringBoot(3.0.6), React
* Database: MySQL
* Server: AWS(EC2, RDS, S3)

## 3. Main Function
### - User Function
- [x] 회원가입 (이메일 인증 필요)
- [x] 로그인(Session)
- [x] 유저 정보 조회
- [x] 개인정보 수정
### - Review Function
- [x] 자취방 리뷰 작성/ 삭제 (AWS S3 이미지 저장)

### - Community Function
- [x] 포스팅 작성/ 수정/ 삭제
- [x] 카테고리 조회
- [x] 좋아요 남기기
- [x] 댓글 남기기

### - MyPage
- [x] 본인이 작성한 리뷰/포스팅 조회
- [x] 좋아요 한 포스팅 조회
- [x] 댓글 남긴 포스팅 조회

## 4. (Technical) Difficulties
- [x] 익숙하지 않은 프레임워크
- [x] 부족한 협업 경험
- [x] 코드 구조화 문제, 리팩토링
      

# 5. Final Result Video
https://youtu.be/ffBZl9TwyDI
