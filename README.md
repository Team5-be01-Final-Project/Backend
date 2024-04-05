## BPS_Backend Sever

### 개발 환경
```
JDK 17
SpringBoot 3.2.3
```
___
### 폴더 구조
. </br>
├── BPS -> 스프링부트 프로젝트 폴더 </br>
├── README.md </br>
└── 그 외 -> 배포 관련 파일</br>
___
### 환경 변수
#### DB 관련
```
DB_URL
DB_USERNAME
DB_PASSWORD
```
#### 이메일 관련
```
GMAIL_PASSWORD
```
#### CI/CD 배포 관련
```
S3_BUCKET_NAME
CODE_DEPLOY_APP_NAME
CODE_DEPLOY_DEPLOYMENT_GROUP_NAME
AWS_REGION
```
___
### CI/CD
#### EC2 환경설정
```
# JDK 17 설치
# Docker 설치
```

#### Github Action Deploy to EC2 with docker container
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/150888333/5d092b4d-9ff5-40e0-a55a-133ef07085ca)

___
### Release History
- [v2.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v2.0.0)
- [v1.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v1.0.0)

