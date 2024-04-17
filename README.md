# BPS_Backend Sever

### 개발 환경
```
JDK 17
SpringBoot 3.2.3
```
___
### 폴더 구조

<details>
<summary>  tree  </summary>
<div markdown="1">

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

</div>
</details>

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

___
### EC2 환경설정

```
# JDK 17 설치
# Docker 설치
```

___
### Github Action Deploy to EC2 with docker container
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/150888333/5d092b4d-9ff5-40e0-a55a-133ef07085ca)

___
### BackEnd API Test - Swagger

<details>
<summary>  로그인  </summary>
<div markdown="1">

### 로그인 / 로그아웃
- DB에 저장되어있는 사번과 비밀번호로 로그인한다.
- 퇴사자는 로그인할 수 없다.
- 로그인에 성공한 사람만 로그아웃을 할 수 있다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/0db5e0ec-e2c8-4393-9252-da1f002967d8)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/6fa3f60c-a615-450f-b520-98105cb2b539)

</div>
</details>

<details>
<summary>  사원 관리  </summary>
<div markdown="1">

### 사원 조회
- 대표와 팀장은는 전직원 조회가 가능하지만 팀원은 조회할 수 없다.
- 대표 권한자만 수정할 수 있다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/e28110c3-8413-4451-8dfb-dde16f13a260)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/3ee2ae8e-0aaf-4e1f-a0d7-0c07ba44ec3b)

</div>
</details>

<details>
<summary>  이상 온도  </summary>
<div markdown="1">

### 이상 온도 알림
- 온도가 특정 범위(냉장(2∼8℃))를 벗어나면 관리자(알림대상자) 이메일로 알림을 전송한다.
- 관리자(알림대상자)는 알림 로그 목록을 조회할 수 있다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/ae0cd521-2fc9-4c6a-937c-1e8baec25891)

</div>
</details>

<details>
<summary>  제품 관리  </summary>
<div markdown="1">

### 제품 조회 및 권한 수정
- 상품 목록 조회 시 단가도 볼 수 있으며 카테고리별로 검색이 가능하다

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/1dcd57eb-c022-42dc-8be6-ecbbbd105e7d)

### 제품 등록, 수정, 삭제
- 거래처별 판매 목록에 등록, 수정, 삭제는 대표와 팀장은 가능하지만 사원은 불가능하다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/145fd939-6ecd-4d92-9a0a-1da1e8f93a67)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/c9eed2eb-72dd-4f1e-9d2a-ceda10d004e7)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/8a2e6a6c-9ee1-4b4f-8e3c-95fbb79fdc07)

</div>
</details>

<details>
<summary>  재고 관리  </summary>
<div markdown="1">

### 재고 조회 및 등록
- 재고 조회는 현재 남아있는 재고를 조회하며 전 직원이 조회할 수 있다.
- 재고 등록은 대표와 팀장만 가능하다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/74c35082-121f-4a84-a4b5-35fa806af50c)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/059c9385-0ff6-4567-ac58-405a3f89fb44)

</div>
</details>

<details>
<summary>  전표 관리  </summary>
<div markdown="1">

### 전표 조회
- 전표의 상태는 승인대기, 승인완료, 반려가 있다.
- 모든 전표 목록은 전 직원이 조회할 수 있다.
- 해당 전표 조회는 전표를 생성한 팀만 조회가 가능하다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/35e40c03-7a36-4bb5-a1e9-9283b605288d)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/94b8eeda-56c9-4b68-818d-ce10ae28395a)

### 전표 등록
- 전표 등록은 사원만 가능하다.
- 일자별로 출고 전표를 등록 할 수 있다. 
- 전표를 등록하면 승인 대기 상태가 된다.
- 판매가는 거래처별 판매상품관리 메뉴에서 자동으로 가져온다.
- 전표 등록 시 재고는 차감이 된다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/3f39b35a-2811-47ba-a047-941680fa0dbc)

### 전표 승인 및 반려
- 대표, 팀장만 전표 승인 권한이 있다.
- 대기 상태의 출고전표를 승인 및 반려 할 수 있다.
- 반려 시 물품 수량은 재고에 재등록 된다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/33000999-6860-4299-81a5-5cfddf36b0d1)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/43745ad3-9e00-4aef-bff2-c8d1f8eb44ce)

</div>
</details>

<details>
<summary>  판매 관리  </summary>
<div markdown="1">

### 매출 
- 상품과 거래처 별로 판매 현황을 테이블로 조회할 수 있다.
- 사원은 상품명, 판매가, 수량, 매출액 만 확인할 수 있다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/ddde96ec-8f55-4496-918a-959e24de3f83)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/7d692f57-8238-4cbb-9a1d-61ef13442b81)

</div>
</details>

<details>
<summary>  거래처 관리  </summary>
<div markdown="1">

### 거래처 조회
- 매출 거래처 목록을 조회 할 수 있다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/57a769ba-c1ad-4baf-8a36-795382018e8a)

### 거래처 등록 및 수정
- 대표, 팀장만 매출 거래처 등록, 수정과 삭제 권한이 있으며 사원은 등록, 수정 삭제 기능을 이용할 수 없다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/49afb06d-2284-4ce0-a991-e8f566243081)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/e84ac17e-9b70-4c2a-8c28-fff4eb881c91)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/32b257c7-aac8-454f-988f-d29f97e2202f)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/4c985664-5f68-41a0-af6a-af1c7a349795)

</div>
</details>

<details>
<summary>  인센티브 관리  </summary>
<div markdown="1">

### 인센티브 현황
- 대표는 전 직원에 대한 인센티브 조회가 가능하다.
- 팀장인 본인 팀원에 대한 인센티브 조회가 가능하다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/0bc1dcd8-459b-40ef-b2c3-73c704f61b55)

### 내 인센티브
- 사원은 로그인한 정보로 현재매출 대비 인센티브를 확인할 수 있다.
- 추가 매출을 기입하여 인센티브를 확인할 수 있다.

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/2758101a-d4a0-4755-bf13-189c57667890)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/71abc8c5-557b-40f7-b9df-02ea694a646b)


</div>
</details>

___
### Release History
- [Release v6.0.0]
- [Release v5.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v5.0.0)
- [Release v4.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v4.0.0)
- [Release v3.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v3.0.0)
- [Release v2.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v2.0.0)
- [Release v1.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v1.0.0)

