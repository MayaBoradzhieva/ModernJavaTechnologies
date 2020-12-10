package bg.sofia.uni.fmi.mjt.shopping.portal;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.PremiumOffer;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.RegularOffer;

public class ShoppingDirectoryImplTest {

	private static final double DELTA = 1e-15;
	private ShoppingDirectoryImpl shoppingDirectory;
	private RegularOffer regOffer;
	private PremiumOffer prOffer;
	private RegularOffer secondOffer;
	//private PriceStatistic stat;
	
	@Before
	public void setUp()
	{
		shoppingDirectory = new ShoppingDirectoryImpl();
		regOffer = new RegularOffer("apple", LocalDate.now(), "", 0.30, 5.00);
		secondOffer = new RegularOffer("apple", LocalDate.now(), "", 0.20, 3.00);
		prOffer = new PremiumOffer("choco", LocalDate.now(), "", 8.00, 2.00, 10.00);
		
//		stat = new PriceStatistic(, lowestPrice, averagePrice)
	}
	
	@Test
	public void testTotalPrice() throws OfferAlreadySubmittedException 
	{
		shoppingDirectory.submitOffer(regOffer);
		double price = prOffer.getTotalPrice();
		
		final double expected = 9.00;
		assertEquals(expected, price, DELTA );
		
		double sum = 96.00;
		double discount = 7.5444;
		double result = (double ) sum - (double) (discount/ 100.00 * sum) ;
	
		assertEquals(88.7616, result, 0.0001);
	}
	
	@Test
	public void testLowestPrice() throws OfferAlreadySubmittedException, ProductNotFoundException
	{
		shoppingDirectory.submitOffer(regOffer);
		shoppingDirectory.submitOffer(prOffer);
		shoppingDirectory.submitOffer(secondOffer);
		
		Set<PriceStatistic> actual = (Set<PriceStatistic>) shoppingDirectory.collectProductStatistics("apple");
		
		Set<PriceStatistic> expected = new TreeSet<>(new DateDescendingOrder());
		PriceStatistic first = new  PriceStatistic(LocalDate.now(), 3.20, 8.50);
		
		
		expected.add(first);
		Object[] arr1 = actual.toArray();
		System.out.println(Arrays.toString(arr1));
		Object[] arr2 = expected.toArray();
		System.out.println(Arrays.toString(arr2));
		
		assertArrayEquals(arr2, arr1);
		
	}
	
	

}
