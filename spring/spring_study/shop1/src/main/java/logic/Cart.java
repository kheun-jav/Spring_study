package logic;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<ItemSet> itemSetList = new ArrayList<>();
	public List<ItemSet> getItemSetList() {
		return itemSetList;
	}
	public void push(ItemSet itemSet) {
		int count = itemSet.getQuantity();
		for(ItemSet old : itemSetList) {
			if(itemSet.getItem().getId() == old.getItem().getId()) {
				count = old.getQuantity() + itemSet.getQuantity();
				old.setQuantity(count);
				return;
			}
		}
		itemSetList.add(itemSet);
	}
	public int getTotal() { //get프로퍼티 : total 프로퍼티
		return itemSetList.stream()
				.mapToInt(s -> s.getItem().getPrice() * s.getQuantity())
				.sum();
	}
}
