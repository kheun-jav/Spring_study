package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Cart;
import logic.Item;
import logic.ItemSet;
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
		boolean itemExist = false; //상품 존재여부 관련 변수
		for( ItemSet i : cart.getItemSetList()) { //전체 리스트 forEach문으로
			if(i.getItem().equals(item)) { //만약 동일한 제품이 ItemSetList에 있는경우
				i.setQuantity(quantity + i.getQuantity()); //저장되어있는 수 + 현재 추가한 수 
				itemExist = true; //상품 존재여부 true로 변경
				break; //반복문 탈출
			} 
		}
		if(!itemExist) { //만약 상품존재여부가 false일 경우 = 상품이 없는 경우
			cart.push(new ItemSet(item,quantity)); // 새로운 상품목록 추가
		}
		//장바구니 추가시 message 띄우고 cart 객체 view로 전달
		mav.addObject("message",item.getName()+":"+quantity+"개 장바구니 추가");
		mav.addObject("cart", cart);
		return mav;
	}
}
