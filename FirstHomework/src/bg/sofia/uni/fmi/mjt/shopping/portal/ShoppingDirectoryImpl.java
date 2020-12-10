package bg.sofia.uni.fmi.mjt.shopping.portal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

public class ShoppingDirectoryImpl implements ShoppingDirectory {

	private static final int DAYS = 30;

	Map<String, SortedSet<Offer>> list;

	public ShoppingDirectoryImpl() {
		list = new HashMap<>();
	}

	@Override
	public Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException {

		if (productName == null) {
			throw new IllegalArgumentException();
		}

		if (!list.containsKey(productName)) {
			throw new ProductNotFoundException("No such product.");
		}

		LocalDate today = LocalDate.now();
		LocalDate ago = today.minusDays(DAYS);

		SortedSet<Offer> result = new TreeSet<>(new TotalPriceAscendingOrder());

		for (Offer i : list.get(productName)) {
			if (i.getDate().compareTo(ago) > 0) {
				result.add(i);
			}
		}

		return result;

	}

	@Override
	public Offer findBestOffer(String productName) throws ProductNotFoundException, NoOfferFoundException {
		if (productName == null) {
			throw new IllegalArgumentException();
		}

		if (!list.containsKey(productName)) {
			throw new ProductNotFoundException("No such product.");
		}

		if (list.get(productName).isEmpty()) {
			throw new NoOfferFoundException("No offer found for the product.");
		}

		LocalDate today = LocalDate.now();
		LocalDate ago = today.minusDays(DAYS);

		SortedSet<Offer> result = new TreeSet<>(new TotalPriceAscendingOrder());
		for (Offer i : list.get(productName)) {
			if (i.getDate().compareTo(ago) > 0) {
				result.add(i);
			}
		}

		if (result.isEmpty()) {
			throw new NoOfferFoundException("No offer found for the product.");
		}

		return result.first();
	}

	PriceStatistic calculateStatistics(LocalDate date, SortedSet<Offer> offers) {

		double lowestPrice = offers.first().getTotalPrice();
		double totalPriceForDay = 0.0;
		int days = 0;

		// iterate over set
		for (Offer i : offers) {
			totalPriceForDay += i.getTotalPrice();
			days++;
		}

		double averagePrice = (double) totalPriceForDay / (double) days;

		PriceStatistic current = new PriceStatistic(date, lowestPrice, averagePrice);

		return current;

	}

	@Override
	public Collection<PriceStatistic> collectProductStatistics(String productName) throws ProductNotFoundException {

		if (productName == null) {
			throw new IllegalArgumentException();
		}
		if (!list.containsKey(productName)) {
			throw new ProductNotFoundException("No such product.");
		}

		SortedSet<PriceStatistic> temp = new TreeSet<>(new DateDescendingOrder());

		Map<LocalDate, SortedSet<Offer>> date = new HashMap<>();

		for (Offer i : list.get(productName)) {

			if (!date.containsKey(i.getDate())) {
				date.put(i.getDate(), new TreeSet<Offer>(new TotalPriceAscendingOrder()));
				date.get(i.getDate()).add(i);

			} else { // already has the date
				date.get(i.getDate()).add(i);
			}
		}

		for (LocalDate key : date.keySet()) {
			// calculate for the key
			PriceStatistic toAdd = calculateStatistics(key, date.get(key));
			temp.add(toAdd);
		}

		return temp;

	}

	@Override
	public void submitOffer(Offer offer) throws OfferAlreadySubmittedException {
		if (offer == null) {
			throw new IllegalArgumentException();
		}

		if (list.containsKey(offer.getProductName())) {

			boolean isAdded = list.get(offer.getProductName()).add(offer);

			if (isAdded == false) {
				throw new OfferAlreadySubmittedException("The offer has already been added.");
			}
		}

		if (!list.containsKey(offer.getProductName())) {

			list.put(offer.getProductName(), new TreeSet<Offer>(new CompareWhenAdd()));
			list.get(offer.getProductName()).add(offer);

		}

	}

}
