package bg.sofia.uni.fmi.mjt.shopping.portal;

import java.util.Comparator;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

public class TotalPriceAscendingOrder implements Comparator<Offer> {

	@Override
	public int compare(Offer firstOffer, Offer secondOffer) {

		if (firstOffer.getTotalPrice() > secondOffer.getTotalPrice()) {
			return 1;
		}
		return -1;
	}

}
