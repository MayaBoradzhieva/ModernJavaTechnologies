package bg.sofia.uni.fmi.mjt.cache;

import java.util.HashMap;
import java.util.Map;

public abstract class CacheEvictionPolicy<K, V> implements Cache<K, V> {

	private static final int MAX_CAPACITY = 10_000;
	protected Map<K, Integer> frequency;
	protected Map<K, V> cache;
	protected long successHit = 0;
	protected long missedHit = 0;
	protected long currentCapacity = 0;

	public CacheEvictionPolicy() {
		cache = new HashMap<K, V>();
		frequency = new HashMap<K, Integer>();
		currentCapacity = MAX_CAPACITY;
	}

	public CacheEvictionPolicy(long capacity) {

		cache = new HashMap<K, V>();
		frequency = new HashMap<K, Integer>();
		currentCapacity = capacity;

	}

	public V get(K key) {
		if (cache.containsKey(key)) {
			frequency.replace(key, frequency.get(key), frequency.get(key) + 1);
			successHit++;
			return cache.get(key);
		}

		missedHit++;
		return null;
	}

	public abstract void set(K key, V value);

	public boolean remove(K key) {
		if (cache.containsKey(key)) {
			cache.remove(key);
			frequency.remove(key);
			return true;
		} else {
			return false;
		}
	}

	public long size() {
		return cache.size();
	}

	public void clear() {
		cache.clear();
		frequency.clear();
		successHit = 0;
		missedHit = 0;
	}

	public double getHitRate() {

		if (cache.isEmpty()) {
			return 0.0;
		}

		if (successHit == 0) {
			return 0.0;
		} else if (successHit > 0 && missedHit == 0) {
			return 1.0;
		} else {

			return (double) successHit / (double) (successHit + missedHit);
		}

	}

	public int getUsesCount(K key) {
		if (cache.containsKey(key)) {
			return frequency.get(key);
		}

		return 0;
	}

}
