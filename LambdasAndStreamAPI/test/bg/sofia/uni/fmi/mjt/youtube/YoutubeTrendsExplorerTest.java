package bg.sofia.uni.fmi.mjt.youtube;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;
import bg.sofia.uni.fmi.mjt.youtube.util.TrendingVideoStreamInitializer;

public class YoutubeTrendsExplorerTest {

	private static List<TrendingVideo> videos;
	private static YoutubeTrendsExplorer explorer;

	@BeforeClass
	public static void setUp() throws FileNotFoundException, IOException {

		InputStream trendingVideosStream = TrendingVideoStreamInitializer.initTrendingVideoStream();

		try (InputStreamReader inputStreamReader = new InputStreamReader(trendingVideosStream);
				BufferedReader reader = new BufferedReader(inputStreamReader)) {

			videos = reader.lines().skip(1).map(TrendingVideo::createTrendingVideo).collect(Collectors.toList());
		}

		InputStream explorerStream = TrendingVideoStreamInitializer.initTrendingVideoStream();
		explorer = new YoutubeTrendsExplorer(explorerStream);
	}

	@Test
	public void testIfExistingDatasetIsLoadedCorrectly() {
		final int expected = videos.size();
		final int actual = explorer.getTrendingVideos().size();

		assertEquals(expected, actual);
	}

	@Test
	public void testLeastLikedVideoID() {
		final String expected = "rRhzbPW3gzw";
		String actual = explorer.findIdOfLeastLikedVideo();

		assertEquals(expected, actual);
	}

	@Test
	public void testMostLikedLeastDislikedVideoID() {
		final String expected = "SM1w9PEQOE8"; // Demi Lovato
		String actual = explorer.findIdOfMostLikedLeastDislikedVideo();

		assertEquals(expected, actual);
	}

	@Test
	public void testTop3VideosByViews() {

		List<String> titles = new ArrayList<>();
		titles.add("Demi Lovato - Tell Me You Love Me");
		titles.add("My Pretty Little Liars Audition Tape! | Shay Mitchell");
		titles.add("Never Have I Ever with Kylie, Jordyn and Victoria");

		List<String> actual = explorer.findDistinctTitlesOfTop3VideosByViews();

		assertArrayEquals(titles.toArray(), actual.toArray());
	}

	@Test
	public void testMostTaggedVideoID() {
		final String expected = "_rBaHuQi9gQ"; // My Everyday Makeup Tutorial
		String actual = explorer.findIdOfMostTaggedVideo();

		assertEquals(expected, actual);
	}

	@Test
	public void testFirstVideoTrendingBefore100KViews() {
		final String expected = "Marvin Bagley PK80";
		String actual = explorer.findTitleOfFirstVideoTrendingBefore100KViews();

		assertEquals(expected, actual);
	}

	@Test
	public void testFindIDOfMostTrendingVideo() {
		final String expected = "SM1w9PEQOE8";
		String actual = explorer.findIdOfMostTrendingVideo();

		assertEquals(expected, actual);
	}

}
