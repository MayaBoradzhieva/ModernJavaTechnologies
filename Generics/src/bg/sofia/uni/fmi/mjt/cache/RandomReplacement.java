package bg.sofia.uni.fmi.mjt.cache;

import java.util.Iterator;
import java.util.Map.Entry;

public class RandomReplacement<K, V> extends CacheEvictionPolicy<K, V> {

	public RandomReplacement() {
		super();

	}

	public RandomReplacement(long capacity) {
		super(capacity);

	}

	@Override
	public void set(K key, V value) {
		if (key != null && value != null) {

			if (cache.containsKey(key)) {

				cache.replace(key, cache.get(key), value);
			} else if (cache.size() == currentCapacity) {

				Iterator<Entry<K, V>> iterator = cache.entrySet().iterator();
				cache.remove(iterator.next().getKey());
				cache.put(key, value);

			} else {
				cache.put(key, value);

			}

		}
	}

	@Override
	public int getUsesCount(K key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(K key) {
		if (cache.containsKey(key)) {
			successHit++;
			return cache.get(key);
		}

		missedHit++;
		return null;
	}

}
