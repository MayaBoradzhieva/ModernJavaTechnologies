package bg.sofia.uni.fmi.mjt.youtube.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TrendingVideoStreamInitializer {

	private static String[] trendingVideos = {
			"video_id	trending_date	title	publish_time	tags	views	likes	dislikes",
			"rdJdJpwmcKU	17.02.12	Avengers: Infinity War - Trailer Review	2017-11-30T02:21:34.000Z"
					+ "	\"Avengers|\"\"infinity war\"\"|\"\"trailer\"\"|\"\"thanos\"\"|\"\"assemble\"\"|\"\"phase"
					+ " 3\"\"|\"\"marvel\"\"|\"\"movie\"\"|\"\"review\"\"|\"\"Awesometacular\"\"|\"\"jeremy"
					+ " jahns\"\"\"	489930	19524	570",
			"REt-3P5h4Lo	17.02.12	Never Have I Ever with Kylie, Jordyn and Victoria	"
					+ "2017-11-29T23:50:14.000Z	\"Kylie Jenner|\"\"Jordyn Woods\"\"|\"\"Kylie\"\"|\"\"Kylie "
					+ "Cosmetics\"\"\"	1511919	58914	2497",
			"ri1m5j_yq8A	17.02.12	Prince William: Wedding means Harry will stop raiding my fridge!	"
					+ "2017-11-29T17:17:32.000Z	\"prince william|\"\"prince harry\"\"|\"\"prince harry engagement\"\"|\"\"prince "
					+ "harry and meghan markle\"\"|\"\"meghan markle\"\"|\"\"royal engagement\"\"|\"\"royal wedding\"\"|\"\"royal "
					+ "family\"\"|\"\"royals\"\"|\"\"food\"\"|\"\"fridge\"\"\"	210889	1514	57",
			"RQOgG4MLh1I	17.02.12	Ed Sheeran delivers a Perfect performance | Live Shows | The X Factor 2017"
					+ "	2017-11-26T21:11:11.000Z	\"the x factor|\"\"x factor\"\"|\"\"X factor UK\"\"|\"\"x factor 2017\"\"|\"\"simon "
					+ "cowell\"\"|\"\"nicole\"\"|\"\"sharon\"\"|\"\"louis\"\"|\"\"talent\"\"|\"\"auditions\"\"|\"\"judges\"\"|\"\"season"
					+ " 14\"\"|\"\"series 14\"\"|\"\"X Factor UK 2017\"\"|\"\"X Factor 2017 audition\"\"|\"\"The x factor"
					+ " 2017\"\"|\"\"XFactor 2017\"\"|\"\"itv\"\"\"	1041524	30027	252",
			"rRhzbPW3gzw	17.02.12	Marvin Bagley PK80	2017-11-28T17:44:24.000Z	\"DraftExpress|\"\"NBA"
					+ " Draft\"\"|\"\"Draft Express\"\"|\"\"NCAA\"\"|\"\"Prospects\"\""
					+ "|\"\"draftexpress.com\"\"|\"\"Jonathan\"\"|\"\"Givony\"\"|\"\"basketball\"\"|"
					+ "\"\"scouting\"\"|\"\"prospect\"\"|\"\"footage\"\"|\"\"video\"\"|\"\"interview\"\"|"
					+ "\"\"highlights\"\"|\"\"game\"\"\"	1952	14	1",
			"rtJ7JTApIfM	17.02.12	Hillary Clinton On Why She’s Not Running For President Again | Teen"
					+ " Vogue	2017-11-28T10:55:22.000Z	\"hillary clinton|\"\"2016 "
					+ "election\"\"|\"\"clinton\"\"|\"\"pant suit\"\"|\"\"hrc\"\"|\"\"hillary rodham"
					+ " clinton\"\"|\"\"hillary 2016\"\"|\"\"hillary 2020\"\"|\"\"hillary clinton"
					+ " 2016\"\"|\"\"hillary clinton 202\"\"|\"\"hillary clinton president\"\"|\"\"president"
					+ " clinton\"\"|\"\"hillary clinton interview\"\"|\"\"hillary clinton interview"
					+ " 2017\"\"|\"\"hillary clinton trump\"\"|\"\"clinton trump\"\"|\"\"clinton "
					+ "interview\"\"|\"\"hillary clinton teen vogue\"\"|\"\"hillary rodham\"\"|\"\"teen "
					+ "vogue\"\"|\"\"teenvogue.com\"\"\"	15150	998	174",
			"S2Cq_TpNXoQ	17.02.12	Zero Mass' solar panels turn air into drinking water"
					+ "	2017-11-28T14:00:04.000Z	\"Zero Mass Water|\"\"water\"\"|\"\"scarcity\"\"|\"\"clean"
					+ " energy\"\"|\"\"solar panels\"\"|\"\"global warming\"\"|\"\"sustainable energy\"\"|\"\"green "
					+ "energy\"\"|\"\"green tech\"\"|\"\"tech\"\"|\"\"technology\"\"|\"\"verge\"\"|\"\"the "
					+ "verge\"\"|\"\"Lauren Goode\"\"|\"\"next level\"\"|\"\"next level with lauren goode\"\"|\"\"Cody "
					+ "Friesen\"\"|\"\"Ashok Gadgil\"\"|\"\"UC Berkeley\"\"\"	117240	3889	200",
			"SM1w9PEQOE8	17.02.12	Demi Lovato - Tell Me You Love Me	2017-12-01T15:00:02.000Z	\"Demi"
					+ " Lovato|\"\"Tell Me You Love Me\"\"|\"\"Jesse Williams\"\"|"
					+ "\"\"Demi\"\"|\"\"Lovato\"\"|\"\"Tell\"\"|\"\"Me\"\"|\"\"You\"\"|"
					+ "\"\"Love\"\"|\"\"Island\"\"|\"\"Records\"\"|\"\"Pop\"\"\"	6296393	633622	8307",
			"DgOUdtUlL24	17.07.12	My Pretty Little Liars Audition Tape! | Shay Mitchell"
					+ "	2017-12-01T19:00:00.000Z	\"Shay Mitchell|\"\"Shay\"\"|\"\"Mitchell\"\"|\"\"Shay"
					+ " Mitchell YouTube\"\"|\"\"YouTube Shay Mitchell\"\"|\"\"Shannon"
					+ " Mitchell\"\"|\"\"ShayM\"\"|\"\"Shay Mitch\"\"|\"\"Pretty Little"
					+ " Liars\"\"|\"\"PLL\"\"|\"\"Emily Fields\"\"|\"\"ABC Family\"\"|\"\"Actress\"\"|\"\"Amore"
					+ " and Vita\"\"|\"\"Fashion\"\"|\"\"Beauty\"\"|\"\"Audition\"\"|\"\"Audition Tape\"\"\"	"
					+ "3706524	164188	867",
			"_rBaHuQi9gQ	18.04.04	My Everyday Makeup Tutorial	2018-03-25T01:22:00.000Z	"
					+ "\"everyday makeup|\"\"makeup\"\"|\"\"make up\"\"|\"\"makeup tutorial\"\"|\"\"my "
					+ "everyday makeup routine\"\"|\"\"makeup routine\"\"|\"\"everyday makeup"
					+ " routine\"\"|\"\"tutorial\"\"|\"\"christen dominique\"\"|\"\"dominique "
					+ "cosmetics\"\"|\"\"routine\"\"|\"\"dominique\"\"|\"\"everyday makeup "
					+ "tutorial\"\"|\"\"new makeup\"\"|\"\"kkw concealer "
					+ "review\"\"|\"\"kkw\"\"|\"\"kardashian\"\"|\"\"how to\"\"|"
					+ "\"\"review\"\"|\"\"christen\"\"|\"\"glam\"\"|\"\"glam makeup\"\"\"	451772	21789	692",
			"SM1w9PEQOE8	17.03.12	Demi Lovato - Tell Me You Love Me"
					+ "	2017-12-01T15:00:02.000Z	\"Demi Lovato|\"\"Tell Me You "
					+ "Love Me\"\"|\"\"Jesse Williams\"\"|\"\"Demi\"\"|\"\"Lovato\"\"|"
					+ "\"\"Tell\"\"|\"\"Me\"\"|\"\"You\"\"|\"\"Love\"\"|\"\"Island\"\"|"
					+ "\"\"Records\"\"|\"\"Pop\"\"\"	12310289	905712	15947" };

	public static InputStream initTrendingVideoStream() {
		return new ByteArrayInputStream(
				Arrays.stream(trendingVideos).collect(Collectors.joining(System.lineSeparator())).getBytes());
	}
}
