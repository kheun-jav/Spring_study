# Java Web Project 설정 가이드

## ✅ Lombok 설치 및 설정

1. [Lombok 공식 사이트](https://projectlombok.org/download)에서 `lombok.jar` 다운로드
2. 다운로드한 `lombok.jar` 실행 (더블클릭) → `<Install/Update>` 버튼 클릭
3. Eclipse에서 해당 프로젝트 `build path > classpath`에 `lombok.jar` 추가
4. 만약 Lombok import가 되지 않는다면 `module-info.java` 파일의 내용을 모두 삭제

## ✅ Maven 이란?

- **Maven**은 jar 파일(라이브러리)을 관리하는 도구입니다.
- 라이브러리를 직접 다운받을 필요 없이 `pom.xml`에 의존성을 추가하면 자동으로 다운로드 및 관리해줍니다.
- 라이브러리는 [https://mvnrepository.com/](https://mvnrepository.com/)에서 검색 후 사용

### 🔧 예시: Lombok Maven 설정

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>
```

## ✅ Spring 기반 Maven Web Project: web.xml 설정

Spring 기반의 Maven Web Project를 사용할 때는 `web.xml`의 헤더 부분을 다음과 같이 수정해야 합니다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
         xmlns="http://xmlns.jcp.org/xml/ns/javaee" 
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
                             http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd" 
         id="WebApp_ID" version="4.0">
    <!-- 설정 내용 -->
</web-app>
```

## ✅ Exception.jsp 설정시 유의사항

Spring Legacy 프로젝트 Exception.jsp 설정시 isErrorPage="true" 설정을 해야 합니다.

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<script>
	alert("${exception.message}");
	location.href="${exception.url}";
</script>

## ✅ 실행 환경

- **Tomcat 내장 서버 사용 안함** → `Java Application` 방식으로 실행
- **Spring Boot Version**: 권장 최신 버전 사용
- **의존성 선택 (Initializr 기준)**  
  - Web: `Spring Web`, `Spring Web Services`  
  - SQL: 사용 중인 DB에 맞는 Driver  
  - 기타: `Lombok`, 필요한 추가 기능

## ✅ ServletInitializer 설정

- `ServletInitializer.java` 파일은 **수정하지 않아도 됨**
- 실행 후 브라우저에서 호출 주소:  
  `http://localhost:8080`

---

## ⚠️ 실행 실패 예시: 포트 중복

```txt
Web server failed to start. Port 8080 was already in use.
🔧 해결 방법
8080 포트를 사용하는 프로세스 종료

또는 application.properties에서 포트 변경:

properties
server.port=8081
이미 실행 중이라면 브라우저에서 주소 입력 시 정상 접근 가능

📦 pom.xml 의존성 관리
<dependencies> 영역에서 "Add Spring Boot Starters..." 사용 시 필요한 jar 자동 설치

직접 추가할 경우 기존 Spring Legacy 방식처럼 <dependency> 태그 사용

🧠 Spring Boot 자동 구성
@ComponentScan은 기본적으로 자동 수행됨
→ 별도 설정 또는 어노테이션 불필요

🚀 DB 연결 최적화 - Connection Pool
매 요청마다 Connection 객체 생성 방지

Connection Pool 미리 생성하여 DB와 연결 유지

요청이 오면 유휴 연결을 재사용 → 성능 저하 방지

🔄 경로 변경 시 확인 사항
AOP joinPoint 경로

DAO에서 Mapper 함수의 경로 지정 여부

클래스 import 경로


