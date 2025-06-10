package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Cart;
import logic.Item;
import logic.ItemSet;
import logic.Sale;
import logic.User;
import service.ShopService;

@Controller
@RequestMapping("cart")
public class CartController {
	@Autowired
	private ShopService service;
	/*
	 * 1. 문제
	 * 장바구니에 존재하는 상품의 경우 수량만 증가하기
	 * 장바구니에 존재하는 상품이 아닌 경우는 상품 추가하기
	 * 
	 */
	@RequestMapping("cartAdd")
	public ModelAndView add(Integer id, Integer quantity, HttpSession session) {
		// new ModelAndView(뷰명) : WEB-INF/view/cart/cart.jsp 
		ModelAndView mav = new ModelAndView("cart/cart");
		Item item = service.getItem(id); //id에 해당하는 item 객체 조회
		Cart cart = (Cart) session.getAttribute("CART");
		if(cart == null) { //세션에 CART 이름의 객체가 없는 경우
			cart = new Cart(); 
			session.setAttribute("CART", cart); //새로 생성하여 세션에 추가
		}
		cart.push(new ItemSet(item,quantity));
		mav.addObject("message",item.getName()+":"+quantity+"개 장바구니 추가");
		mav.addObject("cart", cart);
		return mav;
	}
	
	@RequestMapping("cartDelete")
	public ModelAndView delete(int index, HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");
		Cart cart = (Cart)session.getAttribute("CART");
		/*
		 * E remove(int) : 인덱스에 해당하는 객체를 제거. 제거된 객체를 리턴
		 * boolean remove(Object) : 객체를 입력받아서 객체를 제거. 제거여부를 리턴
		 */
		ItemSet removeObj = cart.getItemSetList().remove(index);
		mav.addObject("message" , removeObj.getItem().getName() 
				+ "가(이) 삭제되었습니다.");
		mav.addObject("cart",cart);
		return mav;
	}
	
	@RequestMapping("cartView")
	public ModelAndView view(HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");
		mav.addObject("message","장바구니 상품 조회");
		mav.addObject("cart", session.getAttribute("CART"));
		return mav;
	}
	/*
	 * 주문전 확인 페이지
	 * 1. 장바구니에 상품 존재해야함
	 *    상품이 없는경우 예외 발생.
	 * 2. 로그인된 상태여야함
	 *    로그아웃 상태 : 예외 발생.
	 */
	@RequestMapping("checkout")
	public String checkout(HttpSession session) {
		return null;
	}
	
	@RequestMapping("end")
	public ModelAndView checkend(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Cart cart = (Cart)session.getAttribute("CART"); //장바구니 상품
		User loginUser = (User)session.getAttribute("loginUser"); //로그인 정보
		Sale sale = service.checkend(loginUser,cart);
		session.removeAttribute("CART"); //장바구니 제거
		mav.addObject("sale",sale);
		return mav;
	}
}
