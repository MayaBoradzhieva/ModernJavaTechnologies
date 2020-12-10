package bg.sofia.uni.fmi.mjt.shopping.portal;

import java.util.Comparator;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

public class CompareWhenAdd implements Comparator<Offer> {

	@Override
	public int compare(Offer firstOffer, Offer secondOffer) {

		int nameCompare = firstOffer.getProductName().compareToIgnoreCase(secondOffer.getProductName());
		int dateCompare = firstOffer.getDate().compareTo(secondOffer.getDate());
		int priceCompare = Double.compare(firstOffer.getTotalPrice(), secondOffer.getTotalPrice());

		if (nameCompare == 0 && dateCompare == 0 && priceCompare == 0) {
			return 0;
		}

		return 1;
	}

}