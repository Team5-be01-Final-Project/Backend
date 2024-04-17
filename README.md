# BPS_Backend Sever

### 개발 환경
```
JDK 17
SpringBoot 3.2.3
```
___
### 폴더 구조

```bash
├── BPS
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── sales
│   │   │   │           └── BPS
│   │   │   │               ├── BpsApplication.java
│   │   │   │               ├── config
│   │   │   │               ├── mexception
│   │   │   │               ├── mproduct
│   │   │   │               │   ├── controller
│   │   │   │               │   ├── dto
│   │   │   │               │   ├── entity
│   │   │   │               │   ├── repository
│   │   │   │               │   └── service
│   │   │   │               ├── msales
│   │   │   │               │   ├── controller
│   │   │   │               │   ├── dto
│   │   │   │               │   ├── entity
│   │   │   │               │   ├── repository
│   │   │   │               │   └── service
│   │   │   │               └── msystem
│   │   │   │                   ├── controller
│   │   │   │                   ├── dto
│   │   │   │                   ├── entity
│   │   │   │                   ├── repository
│   │   │   │                   └── service
│   │   │   └── resources
│   │   │       ├── application.yml
│   │   │       └── templates
│   │   └── test
│   │       └── java
│   │           └── com
│   │               └── sales
│   │                   └── BPS
│   │                       ├── BpsApplicationTests.java
│   │                       └── service
│   └── target
│       ├── classes
│       ├── generated-sources
│       ├── generated-test-sources
│       └── test-classes
├── Dockerfile
├── README.md
├── appspec.yml
├── changelog.md
├── docker-compose.yml
├── img
├── nginx
│   └── conf
│       └── proxy.conf
├── start.sh
└── stop.sh
```

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
- [Release v6.0.0]
- [Release v5.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v5.0.0)
- [Release v4.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v4.0.0)
- [Release v3.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v3.0.0)
- [Release v2.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v2.0.0)
- [Release v1.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v1.0.0)

