Lombok
다운받은 Lombok.jar 파일을 클릭하여 <install/Update> 버튼을 클릭
사용하고자하는 이클립스 project에 buildpath -> classpath에 Lombok.jar 파일 추가
만약 import가 되지 않는다면 module-info 파일의 내용을 전부 삭제

@Data : 생성자를 뺸 getter, setter, toString 함수 호출 

Maven : jar 파일을 관리할 수 있는 도구

Maven 레파지토리 주소
https://mvnrepository.com/
Maben 사이트에서 라이브러리 의존성 코드를 복사하여
pom.xml에 주입하면 해당 라이브러리의 jar파일을 직접 붙여쓰지 않아도 import할 수 있다.
