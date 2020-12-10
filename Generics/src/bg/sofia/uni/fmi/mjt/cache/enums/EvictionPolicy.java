package bg.sofia.uni.fmi.mjt.cache.enums;

public enum EvictionPolicy {
	RANDOM_REPLACEMENT("Random Replacement"), LEAST_FREQUENTLY_USED("Least Frequently Used");

	private final String name;

	private EvictionPolicy(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
