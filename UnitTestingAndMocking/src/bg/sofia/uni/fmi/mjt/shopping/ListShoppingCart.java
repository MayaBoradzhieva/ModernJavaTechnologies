package bg.sofia.uni.fmi.mjt.shopping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public class ListShoppingCart implements ShoppingCart {

	private List<Item> list;

	public ListShoppingCart() {
		list = new ArrayList<Item>();
	}

	@Override
	public Collection<Item> getUniqueItems() {

		Set<Item> result = new HashSet<>();

		for (Item i : list) {
			result.add(i);
		}

		return result;

	}

	@Override
	public void addItem(Item item) {
		if (item == null) {
			return;
		}
		list.add(item);

	}

	@Override
	public void removeItem(Item item) throws ItemNotFoundException {
		if (!list.contains(item)) {
			throw new ItemNotFoundException();
		}

		list.remove(item);
	}

	@Override
	public double getTotal() {
		double total = 0;
		for (Item item : list) {
			total += item.getPrice();
		}

		return total;
	}

	private Map<Item, Integer> map() {

		Map<Item, Integer> temp = new HashMap<Item, Integer>();

		for (Item item : list) {
			if (!temp.containsKey(item)) {
				temp.put(item, 1);
			} else {
				temp.replace(item, temp.get(item), temp.get(item) + 1);
			}

		}
		return temp;
	}

	@Override
	public Collection<Item> getSortedItems() {
		Map<Item, Integer> temp = new HashMap<>();
		temp.putAll(map());

		List<Map.Entry<Item, Integer>> list = new LinkedList<Map.Entry<Item, Integer>>(temp.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<Item, Integer>>() {
			public int compare(Map.Entry<Item, Integer> o1, Map.Entry<Item, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		List<Item> itemsList = new ArrayList<>();
		for (Entry<Item, Integer> item : list) {
			itemsList.add(item.getKey());
		}
		return itemsList;
	}

	boolean isEmpty() {
		return list.size() == 0;
	}

	boolean itemIsInTheList(Item item) {
		return list.contains(item);
	}

	int getListSize() {
		return list.size();
	}
}
