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
    
<%-- /WEB-INF/view/exception.jsp
	isErrorPage="true" : ShopException 객체가 전달됨
--%>
<script>
	alert("${exception.message}");
	location.href="${exception.url}";
</script>

