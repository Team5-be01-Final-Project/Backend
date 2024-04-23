# 📠 BPS_Backend Sever
<br/>

### 🛠 개발 환경

<br/>

```
JDK 17
SpringBoot 3.2.3
```
___
### 🗂 프로젝트 구조
<br/>

<details>
<summary>  <b>🌳 Tree 🌳</b>  </summary>
<div markdown="1">

```bash
📦 BPS
├──🖱 BpsApplication.java
├──📂 config                  # 쿠키, 스웨거, 웹 설정 파일
├──📂 mexception              # 예외 처리 관련 클래스
├──📂 mproduct                # 제품, 인센티브, 재고, 전표 디렉토리
│   ├──📁 controller
│   ├──📁 dto
│   ├──📁 entity
│   ├──📁 repository
│   └──📁 service
├──📂 msales                  # 거래처, 상품 판매 디렉토리
│   ├──📁 controller
│   ├──📁 dto
│   ├──📁 entity
│   ├──📁 repository
│   └──📁 service
└──📂 msystem                 # 로그인, 사원, 권한, 알림 디렉토리 
    ├──📁 controller
    ├──📁 dto
    ├──📁 entity
    ├──📁 repository
    └──📁 service

```

</div>
</details>

___
### 🔑 환경 변수
<br/>

#### 💾 DB 관련
<br/>

```
DB_URL
DB_USERNAME
DB_PASSWORD
```
<br/>

#### 📬 이메일 관련
<br/>

```
GMAIL_PASSWORD
```
<br/>

#### 💻 CI/CD 배포 관련
<br/>

```
S3_BUCKET_NAME
CODE_DEPLOY_APP_NAME
CODE_DEPLOY_DEPLOYMENT_GROUP_NAME
AWS_REGION
```



___
### ⚙ EC2 환경설정
<br/>

```
# JDK 17 설치
# Docker 설치
```

___
### 🚩 Github Action Deploy to EC2 with docker container
<br/>

![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/87449c61-ba35-4a9f-97c3-045037c73733)

___
### 📝 BackEnd API Test - Swagger
<br/>

### [API 명세서](https://github.com/Team5-be01-Final-Project/.github/blob/main/Datas/API%20%EB%AA%85%EC%84%B8%EC%84%9C.pdf)

<br/>

<details>
<summary> ⭐ <b>로그인</b> </summary>
<div markdown="1">

### 로그인 / 로그아웃
<br/>

- DB에 저장되어있는 사번과 비밀번호로 로그인한다.
- 퇴사자는 로그인할 수 없다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/0db5e0ec-e2c8-4393-9252-da1f002967d8)
<br/>

- 로그인에 성공한 사람만 로그아웃을 할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/6fa3f60c-a615-450f-b520-98105cb2b539)

</div>
</details>
<br/>

<details>
<summary> ⭐ <b>대시 보드</b> </summary>
<div markdown="1">

### 온도 조회
<br/>

- 창고의 온도를 대시보드에서 확인할 수 있다.
- 각 차량 별로 온도를 그래프를 통해 대시보드에서 확인할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/7f1b9797-821d-4003-bff4-ba90d91ed9e9)
<br/>

### 월별 매출 조회
<br/>

- 월별로 총매출 합계와 순이익을 조회할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/96ea7cae-ce12-4d38-b235-d3f4ff27dc92)

</div>
</details>
<br/>

<details>
<summary> ⭐ <b>사원 관리</b>  </summary>
<div markdown="1">

### 사원 조회
<br/>

- 대표와 팀장은 전직원 조회가 가능하지만 팀원은 조회할 수 없다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/48814216-ad9f-4e47-9384-5536f5e125db)
<br/>

- 대표 권한자만 권한을 수정할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/3ee2ae8e-0aaf-4e1f-a0d7-0c07ba44ec3b)
<br/>

- 대표 권한자만 알림 수신 여부를 수정할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/a886bed5-5e46-431c-8047-58df8ccac359)


</div>
</details>
<br/>

<details>
<summary> ⭐ <b>이상 온도</b>  </summary>
<div markdown="1">

### 이상 온도 알림
- 온도가 특정 범위(냉장(2∼8℃))를 벗어나면 관리자(알림대상자) 이메일로 알림을 전송한다.
- 관리자(알림대상자)는 알림 로그 목록을 조회할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/ae0cd521-2fc9-4c6a-937c-1e8baec25891)

</div>
</details>
<br/>

<details>
<summary> ⭐ <b>제품 관리</b>  </summary>
<div markdown="1">

### 제품 조회
- 제품 목록 조회 시 대표, 팀장의 경우 모두 조회할 수 있지만 사원은 단가를 조회할 수 없다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/1dcd57eb-c022-42dc-8be6-ecbbbd105e7d)
<br/>

- 특정 거래처에 대한 제품을 검색하여 조회할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/4dcc8f6a-5230-4e2b-bc35-c9bd4bbccab0)
<br/>

### 제품 등록, 수정, 삭제
- 거래처별 판매 목록에 등록은 대표와 팀장만 가능하다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/145fd939-6ecd-4d92-9a0a-1da1e8f93a67)
<br/>

- 거래처에 중복된 조건의 제품은 등록이 불가능하다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/629d763f-72ea-4a9d-bf50-b53cc44634f6)
<br/>

- 대표와 팀장은 수정, 삭제가 가능하지만 사원은 불가능하다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/c9eed2eb-72dd-4f1e-9d2a-ceda10d004e7)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/8a2e6a6c-9ee1-4b4f-8e3c-95fbb79fdc07)

</div>
</details>
<br/>

<details>
<summary> ⭐ <b>재고 관리</b>  </summary>
<div markdown="1">

### 재고 조회
- 재고 조회는 현재 남아있는 재고를 조회하며 전 직원이 조회할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/74c35082-121f-4a84-a4b5-35fa806af50c)
<br/>

- 재고를 등록하기 위해서 특정 제품을 검색할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/3d5fbeb7-236b-48a0-93ab-b53fa4b3a6fe)
<br/>

- 재고 등록은 대표와 팀장만 가능하다. 
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/059c9385-0ff6-4567-ac58-405a3f89fb44)

</div>
</details>
<br/>

<details>
<summary> ⭐ <b>전표 관리</b>  </summary>
<div markdown="1">

### 전표 조회
- 전표의 상태는 승인대기, 승인완료, 반려가 있다.
- 모든 전표 목록은 전 직원이 조회할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/35e40c03-7a36-4bb5-a1e9-9283b605288d)
<br/>

- 해당 전표 조회는 전표를 생성한 팀만 조회가 가능하다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/94b8eeda-56c9-4b68-818d-ce10ae28395a)
<br/>

### 전표 등록
- 전표 등록은 사원만 가능하다.
- 일자별로 출고 전표를 등록 할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/36bc001a-7cc1-4a26-8bcb-595453d9c1b2)
<br/>

- 전표를 등록하면 승인 대기 상태가 된다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/3f39b35a-2811-47ba-a047-941680fa0dbc)
<br/>

- 등록된 전표의 목록들은 전 사원이 조회가 가능하다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/2ef69efb-d980-4135-9e63-34d897891986)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/be625d84-d74a-4615-81fb-32c7a473ed09)
<br/>

- 전표 등록 시 재고는 차감이 된다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/4badcf81-0ecd-4574-89cd-e24ded276dff)
<br/>

### 전표 승인 및 반려
- 대표, 팀장만 전표 승인 및 반려 권한이 있으며 대기 상태의 출고전표를 승인 및 반려 할 수 있다.
- 반려 시 전표 등록에 차감되었던 물품 수량은 재고로 재등록 된다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/33000999-6860-4299-81a5-5cfddf36b0d1)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/43745ad3-9e00-4aef-bff2-c8d1f8eb44ce)
<br/>

</div>
</details>
<br/>

<details>
<summary> ⭐ <b>판매 관리</b>  </summary>
<div markdown="1">

### 매출 
- 제품별로 판매 현황을 테이블로 조회할 수 있다.
- 사원은 제품명, 판매가, 수량, 매출액 만 확인할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/ddde96ec-8f55-4496-918a-959e24de3f83)
<br/>

- 거래처별로 판매 현황을 테이블로 조회할 수 있다.
- 사원은 제품명, 판매가, 수량, 매출액 만 확인할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/7d692f57-8238-4cbb-9a1d-61ef13442b81)

</div>
</details>
<br/>

<details>
<summary> ⭐ <b>거래처 관리</b>  </summary>
<div markdown="1">

### 거래처 조회
- 매출 거래처 목록을 조회 할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/57a769ba-c1ad-4baf-8a36-795382018e8a)
<br/>

### 거래처 등록 및 수정
- 대표, 팀장만 매출 거래처 등록, 수정과 삭제 권한이 있으며 사원은 등록, 수정 삭제 기능을 이용할 수 없다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/49afb06d-2284-4ce0-a991-e8f566243081)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/e84ac17e-9b70-4c2a-8c28-fff4eb881c91)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/32b257c7-aac8-454f-988f-d29f97e2202f)
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/4c985664-5f68-41a0-af6a-af1c7a349795)
<br/>

</div>
</details>
<br/>

<details>
<summary> ⭐ <b>인센티브 관리</b>  </summary>
<div markdown="1">

### 인센티브 현황
- 대표는 전 직원에 대한 인센티브 조회가 가능하다.
- 팀장인 본인 팀원에 대한 인센티브 조회가 가능하다.
- 사원은 이 메뉴를 이용할 수 없다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/0bc1dcd8-459b-40ef-b2c3-73c704f61b55)
<br/>

</div>
</details>
<br/>

<details>
<summary> ⭐ <b>My 영업</b>  </summary>
<div markdown="1">

### 내 정보 조회
- 사원은 자신의 정보를 조회할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/4c7bac00-a6f2-4d5f-bdf9-aedfaecbf222)
<br/>

### 내 거래처 조회
- 사원은 자신의 담당 병원에 대한 이름, 담당자, 담당자 전화번호를 조회할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/a66733b1-b0d9-4353-8844-ee537b5bfd4f)
<br/>

### 내 매출 현황
- 사원은 자신의 최근 3개월 거래처 당 매출현황을 볼 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/bf5b60b5-82d1-4e01-b8ad-a33d6791b674)
<br/>

### 내 인센티브
- 사원은 자신의 현재매출 대비 인센티브를 확인할 수 있다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/2758101a-d4a0-4755-bf13-189c57667890)
<br/>

- 추가 매출을 기입하여 예상 인센티브를 확인할 수 있다.
- 시뮬레이션 기능이기 때문에 데이터를 변환하지 않는다.
![image](https://github.com/Team5-be01-Final-Project/Backend/assets/149128094/71abc8c5-557b-40f7-b9df-02ea694a646b)

</div>
</details>
<br/>



___
### 📝 Changelog 
<br/>

[changelog.md](https://github.com/Team5-be01-Final-Project/Backend/blob/dev/changelog.md)

___
### 🧾 릴리즈 내역
<br/>

- [Release v7.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v7.0.0)
<br/>

<details>
<summary>  릴리즈 모음  </summary>
<div markdown="1">

- [Release v6.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v6.0.0)  
- [Release v5.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v5.0.0)
- [Release v4.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v4.0.0)
- [Release v3.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v3.0.0)
- [Release v2.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v2.0.0)
- [Release v1.0.0](https://github.com/Team5-be01-Final-Project/Backend/releases/tag/v1.0.0)

</div>
</details>
