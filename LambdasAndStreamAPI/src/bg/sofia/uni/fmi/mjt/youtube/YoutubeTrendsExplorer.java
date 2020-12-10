package bg.sofia.uni.fmi.mjt.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;

public class YoutubeTrendsExplorer {

	static final int VIEWS = 100_000;
	private List<TrendingVideo> videos = null;

	/**
	 * Loads the dataset from the given {@code dataInput} stream.
	 */
	public YoutubeTrendsExplorer(InputStream dataInput) {

		try (Reader reader = new InputStreamReader(dataInput);
				BufferedReader bufferedReader = new BufferedReader(reader);) {
			videos = bufferedReader.lines().skip(1).map(TrendingVideo::createTrendingVideo)
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns all videos loaded from the dataset.
	 **/
	public Collection<TrendingVideo> getTrendingVideos() {
		return Collections.unmodifiableList(videos);
	}

	// Other methods ...
	public String findIdOfLeastLikedVideo() {
		return videos.stream().min(Comparator.comparing(TrendingVideo::getLikes)).get().getId();
	}

	public String findIdOfMostLikedLeastDislikedVideo() {
		return videos.stream().max(Comparator.comparing(p -> p.getLikes() - p.getDislikes())).get().getId();

	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public List<String> findDistinctTitlesOfTop3VideosByViews() {
		final int thirdItem = 3;
		return videos.stream().filter(distinctByKey(p -> p.getId()))
				.sorted(Comparator.comparing(TrendingVideo::getViews).reversed()).map(TrendingVideo::getTitle)
				.collect(Collectors.toList()).subList(0, thirdItem);
	}

	public String findIdOfMostTaggedVideo() {
		return videos.stream().max(Comparator.comparing(p -> p.getTags().size())).get().getId();
	}

	public String findTitleOfFirstVideoTrendingBefore100KViews() {
		return videos.stream().filter(p -> p.getViews() < VIEWS)
				.min(Comparator.comparing(TrendingVideo::getPublishDate)).get().getTitle();
	}

	public String findIdOfMostTrendingVideo() {
		Map<String, Long> result = videos.stream()
				.collect(Collectors.groupingBy(TrendingVideo::getId, Collectors.counting()));

		return result.entrySet().stream().max(Comparator.comparing(x -> x.getValue())).get().getKey();
	}
}