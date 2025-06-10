package logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Sale { //db의 sale 테이블의 내용 + 사용자정보 + 주문상품정보
	private int saleid; //주문번호
	private String userid; //주문 고객 아이디
	private Date saledate; //주문 일자
	private User user; //고객 정보
	private List<SaleItem> itemList = new ArrayList<>(); //주문 상품 목록
	public int getTotal() {
		return itemList.stream()
				.mapToInt(s->s.getItem().getPrice() * s.getQuantity()).sum();
	}
}
