package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public abstract class OfferAbstract implements Offer {

	private String productName;
	private LocalDate date;
	private String description;
	private double price;
	private double shippingPrice;

	public OfferAbstract(String productName, LocalDate date, String description, double price, double shippingPrice) {
		this.productName = productName;
		this.date = date;
		this.description = description;
		this.price = price;
		this.shippingPrice = shippingPrice;
	}

	public String getProductName() {
		return productName;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public double getShippingPrice() {
		return shippingPrice;
	}

	public abstract double getTotalPrice();

}
