# Gagebu

본인의 소비내역을 기록/관리 하는 가계부 API 서버

## [가계부 REST API 데모서버](http://kimyo.ga)

### ERD

<img src="https://user-images.githubusercontent.com/41179265/193021828-1ed6e7d8-b268-4fea-b681-5bf569d67e40.png" width="400px" />

## 저장소 사용 방법
```
git clone https://github.com/kimziou77/gagebu.git
git submodule update --init --recursive
cd gagebu
docker-compose up -d
```

### 구현사항

- soft delete
- 이메일 인증
- 서브모듈 적용
- 데모서버 배포
- docker-compose
- LoginUser Argument Resolver

### 이메일 인증
![image](https://user-images.githubusercontent.com/41179265/196014384-2652b739-c786-46f5-9f78-98dc5dca056b.png)

### 기술스택

카테고리 | 사용 툴  
-- | --  
OS | Ubuntu 18.04  
언어 | Java JDK 17  
프레임워크 | Spring Boot 2.7.4  
빌드 | Gradle 7.5  
DB | mysql 5.7, H2  
ORM | Data JPA (Hibernate)  
문서화 | RestDocs + Swagger  
테스트 | Junit5, Mockito  
보안 | Spring Security  
DevOps| Github Actions, GCP, Docker

