package kr.gdu.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.gdu.service.BoardService;
/*
 * @Controller : @Component + Controller 기능
 * 		Mapping 메서드의 리턴 타입 : ModelAndView : 뷰이름 + 데이터
 * 		Mapping 메서드의 리턴 타입 : String : 뷰이름
 * @RestController : @Component + Controller 기능 + 클라이언트로 데이터 직접 전달(view가 없음) 
 * 		Mapping 메서드의 리턴 타입 : String : 클라이언트로 전달되는 문자열 값
 * 		Mapping 메서드의 리턴 타입 : Object : 클라이언트로 전달되는 값 . JSON 형식 처리
 * 
 * Spring 4.0 이후에 추가됨.
 * Spring 4.0 이전에는 @ResponseBody 기능으로 사용함
 */
@RestController // view가 없음
@RequestMapping("ajax")
public class AjaxController {
	@Autowired
	BoardService service;
	
	//produces="text/plain; charset=utf-8" : 전송될 데이터 형식
	@PostMapping(value="uploadImage", produces="text/plain; cahrset=utf-8")
	public String summernoteImageUpload
	(@RequestParam("image") MultipartFile multipartFile) {
		return service.summernoteImageUpload(multipartFile);
	}
	
	@RequestMapping(value="select1", produces="text/plain; charset=utf-8")
	  public String sidoSelect1(String si, String gu) {
		return service.sidoSelect1(si,gu);
	}
	@RequestMapping("select2")
	public List<String> sigunSelect2(String si, String gu) {
		return service.sigunSelect2(si,gu); //리스트 객체를 클라이언로 직접 전달
		//클라이언트에서 오류 발생 가능
		//pom.xml에 fasterxml.jackson.... 설정 필요함.
		//현재는 오류 발생 안함 : 자동으로 변형
	}
	//크롤링
	@RequestMapping(value="exchange1", produces="text/html; charset=utf-8")
	public String exchange1() {
		return service.exchange1(); //US달러, 중국, 일본, 유로 4개 통화만 처리
	}
	@RequestMapping("exchange2")
	public Map<String,Object> exchange2() { //json 데이터로 전송
		return service.exchange2(); //미국달러, 중국, 일본, 유로 4개의 통화만 처리
	}
	
	//파이그래프
	@RequestMapping("graph1")
	public List<Map.Entry<String, Integer>> graph1(String id) {
		Map<String,Integer> map = service.graph1(id); //글쓴이별 등록 건수
		//map : {홍길동:10, 김삿갓:7, ...}
		List<Map.Entry<String,Integer>> list = new ArrayList<>();
		for(Map.Entry<String, Integer> m : map.entrySet()) {
			list.add(m); // [{홍길동:10}, {김삿갓:7}, ...]
		}
		//cnt값의 큰 순으로 정렬
		Collections.sort(list,(m1,m2) -> m2.getValue() - m1.getValue());
		return list; //[{홍길동:10},{김삿갓:7},...]
	}
	
	//선/막대 그래프
	@RequestMapping("graph2")
	public List<Map.Entry<String, Integer>> graph2(String id) {
		Map<String,Integer> map = service.graph2(id); 
		List<Map.Entry<String,Integer>> list =
							new ArrayList<>(map.entrySet());
		return list;
	}
	
	//메인로고 크롤링
	@RequestMapping(value="mainlogo", produces="text/html; charset=utf-8")
	public String mainlogo() {
		return service.mainlogo();
	}
}
