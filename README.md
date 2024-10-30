## 목차
1. [프로젝트 개요](#프로젝트-개요)
2. [프로젝트 목표](#프로젝트-목표)
3. [프로젝트 일정](#프로젝트-일정)
4. [개발 환경](#개발-환경)
5. [시연영상](#시연영상)
6. [시스템 아키텍처 구성도](#시스템-아키텍처-구성도)
7. [ERD](#ERD)
8. [프로세스 플로우 차트](#프로세스-플로우-차트)

## 프로젝트 개요

### 프로젝트 주제
1인 가구 및 P형 성향의 여행자들을 위한 서울 랜덤 여행 플래너

### 프로젝트 인원 (총 5명, 풀스택)
| **팀원**      | **역할**                                     |
|---------------|----------------------------------------------|
| **⭐️박주희**    | 팀장, 부루마블 게임 구현                     |
| **🙋‍♂️김경민**    | 백엔드, 프론트 프로젝트 구성, Vue 라우터 구성, 여행 리뷰 게시판 및 공유 |
| **🙆‍♂️김동혁**    | 데이터베이스 설계 총 책임, 회원 정보 관리    |
| **🙋‍♀️이푸름**    | 부루마블 게임 구현                        |
| **💁‍♂️조성현**    | 카카오 API 경로 구현                      |

## 기획 배경
- **최근 여행 트렌드**
    - 자유 여행 및 개별 맞춤형 여행에 대한 수요 증가
- **필요성**
    - 현대인의 바쁜 일상 속에서 효율적으로 여행 일정을 계획하고 관리할 수 있는 도구 필요
    - 서울의 다양한 카테고리(맛집, 카페, 놀거리)에 따른 랜덤 경로 추천 제공
- **타겟층**
    - MBTI가 P형인 사람들
    - 서울 여행을 계획 중인 사람들
    - 계획을 짤 시간이 없는 사람들
    - 서울에 거주하지만 서울 여행 경험이 없는 외국인들

## 차별성
- **지역 특화 및 세분화**
    - 서울의 다양한 구역에 특화된 여행 코스를 제공하며 지역 특색을 반영한 여행 경험 제공
- **게임 요소 도입**
    - 돌림판, 주사위 등 게임 요소를 도입하여 재미있는 여행 계획 제공
- **랜덤 장소 추천**
    - 랜덤 추천으로 예상치 못한 여행지를 방문할 수 있는 기회 제공, 계획 부족한 P형 사용자에게 매력적

## 기대효과
- **수익 확대**
    - 제휴 및 협업을 통한 수익 확대와 다양한 비즈니스 모델 개발 가능
- **지역 경제 활성화**
    - 비관심 지역에 대한 관광객 유입으로 지역 경제 활성화
- **여행 트렌드 선도 및 문화 확산**
    - 독특하고 창의적인 여행 경험 제공으로 새로운 여행 트렌드를 선도하며 여행 문화를 확산

## 프로젝트 목표
- **SPA(Single Page Application) 구현**
    - 단일 페이지에서 동적 콘텐츠 로딩으로 부드러운 사용자 경험 제공
- **프론트엔드 기술 숙련**
    - Vue.js를 활용한 컴포넌트 기반 설계 및 라우팅 학습 및 적용
- **백엔드 기술 활용**
    - RESTful API 설계, 데이터베이스 연동, 사용자 인증/권한 관리 구현
- **프론트엔드와 백엔드의 통합**
    - API를 통한 데이터 통신, 비동기 통신 및 JWT 이용한 보안 강화
- **실무 능력 배양 및 협업 경험**
    - Git을 통한 버전 관리, 코드 리뷰 및 데일리 스크럼을 통한 협업 역량 강화

## 프로젝트 일정
| **기간**        | **활동 내용**             |
|-----------------|---------------------------|
| 2024.08.05-08.06 | 기획 및 설계              |
| 2024.08.06-08.14 | 개발                     |
| 2024.08.14-08.16 | 문서 정리 및 발표         |

## 개발 환경
## Tech Stack

### Backend
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white)
![DevTools](https://img.shields.io/badge/DevTools-6DB33F?style=flat&logo=spring&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-CA0C4D?style=flat&logo=lombok&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-BD3F15?style=flat&logo=mybatis&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle%20Driver-F80000?style=flat&logo=oracle&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=thymeleaf&logoColor=white)
![Spring Web](https://img.shields.io/badge/Spring%20Web-6DB33F?style=flat&logo=spring&logoColor=white)

### Frontend
![Vue.js](https://img.shields.io/badge/Vue.js-4FC08D?style=flat&logo=vue.js&logoColor=white)
![axios](https://img.shields.io/badge/axios-671DDF?style=flat&logo=axios&logoColor=white)
![cors](https://img.shields.io/badge/cors-2E3A40?style=flat&logo=graphql&logoColor=white)
![vue-router](https://img.shields.io/badge/vue--router-4FC08D?style=flat&logo=vue.js&logoColor=white)
![vuex](https://img.shields.io/badge/vuex-4FC08D?style=flat&logo=vue.js&logoColor=white)
![HTML](https://img.shields.io/badge/HTML-E34F26?style=flat&logo=html5&logoColor=white)
![CSS](https://img.shields.io/badge/CSS-1572B6?style=flat&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black)

### Database
![Oracle](https://img.shields.io/badge/Oracle%2021c-F80000?style=flat&logo=oracle&logoColor=white)

### Version Control
![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)

### Build Tool
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven&logoColor=white)

### Collaboration
![Notion](https://img.shields.io/badge/Notion-000000?style=flat&logo=notion&logoColor=white)
![Figma](https://img.shields.io/badge/Figma-F24E1E?style=flat&logo=figma&logoColor=white)

### IDE
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=flat&logo=intellij-idea&logoColor=white)
![Visual Studio Code](https://img.shields.io/badge/VS%20Code-007ACC?style=flat&logo=visual-studio-code&logoColor=white)

## 시연영상
[프로젝트 시연 영상 보기](https://www.youtube.com/watch?v=zX_NX8SYDVg)​

## 시스템 아키텍처 구성도
<img width="1329" alt="image" src="https://github.com/user-attachments/assets/794eed56-33c6-4117-aa5e-17970a1fec4f">

## ERD
![image](https://github.com/user-attachments/assets/2690c2ee-27e5-486b-a8d1-49afbe4ea36f)

## 프로세스 플로우 차트
<img width="876" alt="image" src="https://github.com/user-attachments/assets/7eafd025-a853-4a1b-8099-e4eed782d13a">
