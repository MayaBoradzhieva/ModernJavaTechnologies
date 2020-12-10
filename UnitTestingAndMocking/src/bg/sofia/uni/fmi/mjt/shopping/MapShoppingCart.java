package bg.sofia.uni.fmi.mjt.shopping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public class MapShoppingCart implements ShoppingCart {

	private Map<Item, Integer> items;

	public MapShoppingCart() {

		items = new HashMap<>();
	}

	@Override
	public Collection<Item> getUniqueItems() {
		return items.keySet();
	}

	@Override
	public void addItem(Item item) {
		if (item != null) {
			Integer occurrences = items.get(item);
			if (occurrences == null) {
				occurrences = 0;
			}
			items.put(item, ++occurrences);
		}
	}

	@Override
	public void removeItem(Item item) throws ItemNotFoundException {
		if (!items.containsKey(item)) {
			throw new ItemNotFoundException();
		}
		Integer occurrences = items.get(item);

		if (occurrences == 1) {
			items.remove(item);
		} else if (occurrences > 1) {
			items.replace(item, occurrences, --occurrences);
		}
	}

	@Override
	public double getTotal() {
		double total = 0.0;
		for (Map.Entry<Item, Integer> e : items.entrySet()) {
			total += e.getKey().getPrice() * e.getValue();
		}
		return total;
	}

	@Override
	public Collection<Item> getSortedItems() {
		List<Map.Entry<Item, Integer>> list = new LinkedList<Map.Entry<Item, Integer>>(items.entrySet());

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

	int getOccurrences(Item item) {
		return items.get(item);
	}

	boolean isEmpty() {
		return items.size() == 0;
	}

	boolean isItemInCart(Item item) {
		return items.containsKey(item);
	}
}