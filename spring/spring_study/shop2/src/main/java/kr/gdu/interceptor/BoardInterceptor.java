package kr.gdu.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.gdu.exception.ShopException;
import kr.gdu.logic.User;

public class BoardInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle // /addPath한 경로에서 요청 시 Controller보다 먼저 호출됨
	(HttpServletRequest request, HttpServletResponse response, Object handler) 
				throws Exception {
		String boardid = request.getParameter("boardid");
		HttpSession session = request.getSession();
		User login = (User)session.getAttribute("loginUser");
		//관리자 검증
		if(boardid == null || boardid.equals("1")) {
			if(boardid == null || !login.getUserid().equals("admin")) {
				String msg = "관리자만 등록 가능합니다.";
				String url = request.getContextPath() + "/board/list?boardid=1";
				throw new ShopException(msg,url);
			}
		}
		return true; //정상적으로 거래. BoardContoller의 매핑함수 실행
	}	
	

}
