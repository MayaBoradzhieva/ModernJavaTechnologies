package bg.sofia.uni.fmi.mjt.cache;

public interface Cache<K, V> {

	public V get(K key);

	void set(K key, V value);

	boolean remove(K key);

	long size();

	void clear();

	double getHitRate();

	int getUsesCount(K key);
}
