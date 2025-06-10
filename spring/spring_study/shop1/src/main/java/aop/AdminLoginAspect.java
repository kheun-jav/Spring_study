package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import exception.ShopException;
import logic.User;

@Component
@Aspect
public class AdminLoginAspect {
	@Around
	//joinPoint : controller 패키지에 admin으로 시작하는 클래스의 admincheck로 시작하는 메서드 && 매개변수로 session을 무조건 가지고 있어야함
	("execution(* controller.Admin*.adminCheck*(..)) && args(..,session)")
		public Object AdminIdCheck
				(ProceedingJoinPoint joinPoint, HttpSession session) throws Throwable {
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null ||!(loginUser instanceof User)) { //로그아웃상태
			throw new ShopException("[AdminCheck]로그인이 필요합니다","../user/login");
		}
		if(!loginUser.getUserid().equals("admin")) {
			throw new ShopException
				("[AdminCheck]관리자만 접근 가능합니다","../user/mypage?userid=" + loginUser.getUserid());
		}
		return joinPoint.proceed();
	}
}
