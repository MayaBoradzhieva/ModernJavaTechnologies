package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public class PremiumOffer extends OfferAbstract {

	private double discount;

	public PremiumOffer(String productName, LocalDate date, String description, double price, double shippingPrice,
			double discount) {
		super(productName, date, description, price, shippingPrice);
		this.discount = discount;
	}

	public double getDiscount() {
		return discount;
	}

	@Override
	public double getTotalPrice() {

		final double hundredPercent = 100.0;

		double sum = getPrice() + getShippingPrice();
		double roundOff = Math.round(discount * hundredPercent) / hundredPercent;
		double result = (double) sum - (double) (roundOff * sum) / hundredPercent;

		return result;
	}

}
