package bg.sofia.uni.fmi.mjt.shopping.portal;

import java.util.Comparator;

public class DateDescendingOrder implements Comparator<PriceStatistic> {

	@Override
	public int compare(PriceStatistic first, PriceStatistic second) {

		return second.getDate().compareTo(first.getDate());
	}

}
