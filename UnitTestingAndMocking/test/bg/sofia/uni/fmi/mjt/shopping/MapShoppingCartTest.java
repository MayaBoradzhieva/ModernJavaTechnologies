package bg.sofia.uni.fmi.mjt.shopping;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import bg.sofia.uni.fmi.mjt.shopping.item.Apple;
import bg.sofia.uni.fmi.mjt.shopping.item.Chocolate;
import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public class MapShoppingCartTest {
	private static final double DELTA = 1e-15;

	private static final double PRICE_GOLDEN_APPLE = 0.30;
	private static final double PRICE_GREEN_APPLE = 0.40;

	private static final double PRICE_CHOCOLATE = 1.90;

	private MapShoppingCart mapShoppingCart;
	private Apple apple;
	private Apple greenApple;
	private Chocolate chocolate;

	@Before
	public void setUp() {
		mapShoppingCart = new MapShoppingCart();
		apple = new Apple("Golden", "", PRICE_GOLDEN_APPLE);
		greenApple = new Apple("Green Apple", "", PRICE_GREEN_APPLE);
		chocolate = new Chocolate("Milka", "Chocolate Mousse", PRICE_CHOCOLATE);
	}

	@Test()
	public void givenNullWhenAddingItemThenDoNothing() {
		mapShoppingCart.addItem(null);

		boolean checkIfEmpty = mapShoppingCart.isEmpty();

		assertTrue(checkIfEmpty);
	}

	@Test()
	public void testIfItemIsAddedSuccessfully() {
		mapShoppingCart.addItem(apple);

		int actual = mapShoppingCart.getOccurrences(apple);

		final int expected = 1;

		assertEquals(expected, actual);

		mapShoppingCart.addItem(apple);

		int newOccurence = mapShoppingCart.getOccurrences(apple);
		final int newOccurenceExpected = 2;

		assertEquals(newOccurenceExpected, newOccurence);
	}

	@Test()
	public void testIfItemIsRemovedSuccessfully() throws ItemNotFoundException {
		mapShoppingCart.addItem(apple);
		mapShoppingCart.addItem(apple);
		mapShoppingCart.addItem(chocolate);

		mapShoppingCart.removeItem(apple);

		int actual = mapShoppingCart.getOccurrences(apple);
		final int expected = 1;

		assertEquals(expected, actual);

		mapShoppingCart.removeItem(apple);

		boolean noMoreApples = mapShoppingCart.isItemInCart(apple);

		assertFalse(noMoreApples);

	}

	@Test(expected = ItemNotFoundException.class)
	public void givenItemToRemoveWhenThereIsNoSuchItemThenThrowItemNotFoundException() throws ItemNotFoundException {
		mapShoppingCart.addItem(apple);
		mapShoppingCart.addItem(apple);

		mapShoppingCart.removeItem(chocolate);

	}

	@Test
	public void testIfTotalPriceOfItemsIsCorrect() {
		mapShoppingCart.addItem(apple); // 0.30
		mapShoppingCart.addItem(apple); // 0.30
		mapShoppingCart.addItem(greenApple); // 0.40
		mapShoppingCart.addItem(chocolate); // 1.90

		final double totalExpected = 2.90;
		double actual = mapShoppingCart.getTotal();

		assertEquals(totalExpected, actual, DELTA);
	}

	@Test
	public void testIfItemsAreSortedDCorrectlyByAdd() {
		mapShoppingCart.addItem(greenApple);
		mapShoppingCart.addItem(greenApple);
		mapShoppingCart.addItem(apple);
		mapShoppingCart.addItem(apple);
		mapShoppingCart.addItem(apple);
		mapShoppingCart.addItem(chocolate);

		Object[] arrayOfItems = { apple, greenApple, chocolate };

		Collection<Item> sorted = mapShoppingCart.getSortedItems();

		Object[] sortedItems = sorted.toArray();

		assertArrayEquals(arrayOfItems, sortedItems);
	}

	@Test
	public void testIfItemsAreUnique() {
		mapShoppingCart.addItem(greenApple);
		mapShoppingCart.addItem(greenApple);
		mapShoppingCart.addItem(apple);
		mapShoppingCart.addItem(apple);
		mapShoppingCart.addItem(apple);
		mapShoppingCart.addItem(chocolate);

		Set<Item> items = new HashSet<>();
		items.add(greenApple);
		items.add(apple);
		items.add(chocolate);
		Set<Item> actual = (Set<Item>) mapShoppingCart.getUniqueItems();

		assertEquals(items, actual);

	}

}
