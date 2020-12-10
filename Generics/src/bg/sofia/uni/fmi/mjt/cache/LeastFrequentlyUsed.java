package bg.sofia.uni.fmi.mjt.cache;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LeastFrequentlyUsed<K, V> extends CacheEvictionPolicy<K, V> {

	long capacity;

	public LeastFrequentlyUsed() {

		super();
	}

	public LeastFrequentlyUsed(long capacity) {
		super(capacity);

	}

	private K sortByFrequency() {
		List<Map.Entry<K, Integer>> list = new LinkedList<Map.Entry<K, Integer>>(frequency.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<K, Integer>>() {
			public int compare(Map.Entry<K, Integer> firstItem, Map.Entry<K, Integer> secondItem) {
				return (firstItem.getValue()).compareTo(secondItem.getValue());
			}
		});

		HashMap<K, Integer> temp = new LinkedHashMap<K, Integer>();

		for (Map.Entry<K, Integer> item : list) {
			temp.put(item.getKey(), item.getValue());
		}

		Map.Entry<K, Integer> firstElement = temp.entrySet().iterator().next();

		return firstElement.getKey();
	}

	@Override
	public void set(K key, V value) {
		if (key != null && value != null) {

			if (cache.containsKey(key)) {
				cache.replace(key, value);
				frequency.replace(key, frequency.get(key), frequency.get(key) + 1);
			} else if (cache.size() == currentCapacity) {

				K deleteByKey = sortByFrequency();

				cache.remove(deleteByKey);
				frequency.remove(deleteByKey);

				cache.put(key, value);
				frequency.put(key, 1);
			} else {
				cache.put(key, value);
				frequency.put(key, 1);
			}

		}

	}
}
