package kr.gdu.logic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemSet {
	private Item item; //상품
	private Integer quantity; //수량
}
