package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.UserMapper;
import logic.User;

@Repository
public class UserDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String,Object> param = new HashMap<>();
	private Class<UserMapper> cls = UserMapper.class;
	
	//회원 가입
	public void insert(User user) {
		template.getMapper(cls).insert(user);
	}
	//1개 계정 선택
	public User selectOne(String userid) {
		param.clear();
		param.put("userid", userid);
		return template.selectOne("dao.mapper.UserMapper.select", param);
		
	}
	//회원 정보 수정
	public void update(User user) {
		template.getMapper(cls).update(user);
	}
	//회원 탈퇴
	public void delete(String userid) {
		template.getMapper(cls).delete(userid);
	}
	//비밀번호 수정
	public void chgpass(String userid, String chgpass) {
		template.getMapper(cls).chgpass(userid, chgpass);
	}
	
	//아이디찾기, 비밀번호 초기화
	public String search(User user) { 
		param.clear(); //map 안에 들어있는게 있으면 clear()
		param.put("email", user.getEmail()); //id/pw 공통부분 먼저 put
		param.put("phoneno", user.getPhoneno()); //id/pw 공통부분 먼저 put
		
		if(user.getUserid() != null) { //userid가 null이면 비밀번호 초기화
			String randpw = getTempPw(); //임시비밀번호 6자리	
			param.put("randpw", randpw); //map에 put
			param.put("userid",user.getUserid()); //userid도 put
			template.getMapper(cls).resetPw(param); //mapper 함수 실행
			return randpw;
		} else { // 아이디 찾기
			param.put("userid", "userid");
			return template.getMapper(cls).searchId(param);
		}
	}
	
	//비밀번호초기화알고리즘
	public  String getTempPw() {
		List<String> lowerList = Arrays.asList //lower(소문자) 리스트 생성
				("a" ,"b" ,"c" ,"d" ,"e" ,"f" ,"g" ,"h" ,"i" ,"j" ,"k"
						,"l" ,"m" ,"n" ,"o" ,"p","q","r","s","t","z");
		List<String> upperList = new ArrayList<>(); //upper(대문자)를 추가할 리스트 생성
		for (String string : lowerList) { //lowerList의 요소를 toUpperCase()로 대문자로 변경하여 upperList에 추가
			upperList.add(string.toUpperCase());
		}	
		List<Object> combineList = new ArrayList<>(); //대문자, 소문자, 숫자가 모두 들어갈 combineList 생성
		combineList.addAll(lowerList); //lowerList 모두 combineList에 추가
		combineList.addAll(upperList); //upperList 모두 combineList에 추가
		for (int i = 0; i < 30; i++) { //랜덤한0~9 숫자 30개집어넣기
			combineList.add(new Random().nextInt(10));
		}
		//무작위 섞기
		Collections.shuffle(combineList);
		//최종 6자리 임시비밀번호 String 생성
		String tempNum = "";
		for (int i = 0; i < 6; i++) {
			//combineList의 랜덤 index 번호인 num
			int num = new Random().nextInt(combineList.size());
			//랜덤 index 번호의 값을 tempNum에 추가
			tempNum += combineList.get(num);
		}
		//임시 비밀번호 반환
		return tempNum;
	}
}
