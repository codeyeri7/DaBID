# ![image](/uploads/588c46e1dbbb05547cd6d478c9331e41/image.png) **웹 RTC 기반 프로젝트 : 중고 화상거래 경매 플랫폼**

WebRTC 기술을 활용한 화상라이브로 사용자가 직접 경매를 진행하여 개인 중고물품을 판매하는 웹사이트 플랫폼입니다. 

판매자 말만 믿고 하는 택배거래는 사기일까 걱정되고, 직거래할 시간은 부족하고 거리도 먼 경우도 있습니다. 

**Dabid**와 함께 화상라이브로 중고물품의 유무와 상태를 확인하고, 개인 판매자가 직접하는 경매를 통해 신나고 저렴하게 득템하세요!

<br>
<br>

# 목차

- [소개](#소개)
  - [팀원](#팀원)
  - [문서](#문서)
  - [개발환경](#개발환경)
- [⭐ 다비드 둘러보기 ⭐](https://lab.ssafy.com/s05-webmobile1-sub3/S05P13A506/-/blob/develop/DaBID.md)

<br>
<br>

# 소개
## 팀원
1. 팀원
  - **frontend** : 김윤서(팀장), 서예리
  - **backend**  : 심재원, 안영진, 이지수

2. 프로젝트 기간: 210705(부트캠프) ~ 210820    

<br>

## 문서
 - [Notion](https://www.notion.so/PJT-14bf2a880caa4b2f8cb5f220ce270bf3)
 - [기능 명세서 excel](https://docs.google.com/spreadsheets/d/1pazhJn9QQCp9-F--eiMslCsy95N4F8GIuffqEuyxysQ/edit#gid=1438666494)
 - [JIRA](https://jira.ssafy.com/projects/S05P12A506/issues/S05P12A506-3?filter=allopenissues)
 - [Gitlab](https://lab.ssafy.com/s05-webmobile1-sub2/S05P12A506)
 - [Figma](https://www.figma.com/team_invite/redeem/PxGzuHG5EE5SJrjPOExpwX)
 - [StoryBoard](https://docs.google.com/presentation/d/1eBP6ZpRhm4AEdg9Pu2nexNRW_feO2aKZFI18TOPKFzE/edit#slide=id.p)
 - [ERD](https://www.erdcloud.com/d/wbwiRBoxZA4vkFdkf)
 - [Architecture](https://drive.google.com/file/d/1QH-hY0vVtCKsxEMw_OxLQO5paEOxW7UN/view?usp=sharing)

<br>

## 개발환경
- __Framework__ : Vue.js / Spring
- __IDE__ : Visual Studio Code / IntelliJ
- __지원 환경__ : chorme
- __빌드 도구__ : gradle
- __DB__ : MariaDB


<br>
<br>


# 다비드둘러보기
> Dabid는 **모바일에 최적화** 되어있습니다.
<br> 기기를 **Galaxy S5 (360x640)** 로 설정해주세요.
<br><br> **[설정 방법]**
<br> 1. 개발자 모드(F12)를 열어 device mode on (Ctrl + Shift + m)
<br>![image](/uploads/a8af3f2d286b20227ff3cf1016fa6ab9/image.png)
<br> 2. Galaxy S5 기기 선택
<br>![image](/uploads/e229472ea6c4fd980944b09cd8a82ad2/image.png)
<br><br> **처음 화면은 로그인 화면입니다.**

### 1. 로그인
1. 화면 하단에 있는 ![image](/uploads/035fdfc4c1143f607ee4bd6e120e5c07/image.png) 클릭
2. 구글 계정 로그인<br>
  Email: dabid7423@gmail.com<br>
  Password: dabiddabid7423**
3. 로그인 후 메인화면으로 입장
> **[로그인 오류 시]**
<br> 1. 쿠키 모두 삭제
<br> 2. ctrl + f5 
<br> 3. 로그인 재시도

### 2. 라이브 등록
1. 우측 하단의 ![image](/uploads/b4409d2f374f3dfa970b3b8cc0a63245/image.png) 버튼을 클릭하여 라이브 등록 페이지로 이동
2. 판매할 상품, 라이브 관련 정보 입력 후 ![image](/uploads/28fd0c9d04168e57fae2eef18f9b97b4/image.png) 버튼을 눌러 등록 완료!
3. 등록 후 마이 라이브 리스트 화면으로 이동

### 3. 마이 라이브 리스트
1. 내가 등록한 라이브 전체 확인 가능
2. 하단의 네비게이션 메뉴바의 ![image](/uploads/11331572e8aeb6c6468524e2f00d8c98/image.png) 버튼을 클릭하여 이동 가능

### 4. 로그아웃
1. 하단의 네비게이션 메뉴바에서 마이페이지![image](/uploads/21f09db07808a5bb00fdf5004fd33e80/image.png) 이동 가능
2. 마이페이지에서 로그아웃 가능
