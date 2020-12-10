package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.enums.EvictionPolicy;

public interface CacheFactory<K, V> {

	static <K, V> Cache<K, V> getInstance(long capacity, EvictionPolicy policy) {
		if (capacity <= 0) {
			throw new IllegalArgumentException();
		}
		if (policy.equals(EvictionPolicy.RANDOM_REPLACEMENT)) {
			return new RandomReplacement<K, V>(capacity);
		} else {
			return new LeastFrequentlyUsed<K, V>(capacity);
		}
	}

	static <K, V> Cache<K, V> getInstance(EvictionPolicy policy) {
		if (policy.equals(EvictionPolicy.RANDOM_REPLACEMENT)) {
			return new RandomReplacement<K, V>();
		} else {
			return new LeastFrequentlyUsed<K, V>();
		}
	}
}
