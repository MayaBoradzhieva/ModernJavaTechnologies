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

public class ListShoppingCartTest {

	private static final double DELTA = 1e-15;

	private static final double PRICE_GOLDEN_APPLE = 0.30;
	private static final double PRICE_GREEN_APPLE = 0.40;

	private static final double PRICE_CHOCOLATE = 1.90;

	private ListShoppingCart listShoppingCart;
	private Apple goldenApple;
	private Apple greenApple;
	private Chocolate chocolate;

	@Before
	public void setUp() {
		listShoppingCart = new ListShoppingCart();
		goldenApple = new Apple("Golden", "", PRICE_GOLDEN_APPLE);
		greenApple = new Apple("Green Apple", "", PRICE_GREEN_APPLE);
		chocolate = new Chocolate("Milka", "Chocolate Mousse", PRICE_CHOCOLATE);
	}

	@Test()
	public void givenNullWhenAddingItemThenDoNothing() {
		listShoppingCart.addItem(null);

		boolean checkIfEmpty = listShoppingCart.isEmpty();

		assertTrue(checkIfEmpty);
	}

	@Test()
	public void testIfItemIsAddedSuccessfully() {
		listShoppingCart.addItem(goldenApple);

		assertTrue(listShoppingCart.itemIsInTheList(goldenApple));
		assertFalse(listShoppingCart.itemIsInTheList(greenApple));
	}

	@Test()
	public void testIfItemsAreBeingAdded() {
		listShoppingCart.addItem(goldenApple);
		listShoppingCart.addItem(goldenApple);
		listShoppingCart.addItem(chocolate);

		int actualSize = listShoppingCart.getListSize();
		final int expected = 3;
		assertEquals(expected, actualSize);
	}

	@Test()
	public void testIfItemIsRemovedSuccessfully() throws ItemNotFoundException {
		listShoppingCart.addItem(goldenApple);
		listShoppingCart.addItem(greenApple);
		listShoppingCart.addItem(chocolate);

		listShoppingCart.removeItem(goldenApple);

		int actual = listShoppingCart.getListSize();
		final int expected = 2;

		assertEquals(expected, actual);

	}

	@Test(expected = ItemNotFoundException.class)
	public void givenItemToRemoveWhenThereIsNoSuchItemThenThrowItemNotFoundException() throws ItemNotFoundException {
		listShoppingCart.addItem(goldenApple);
		listShoppingCart.addItem(greenApple);

		listShoppingCart.removeItem(chocolate);

	}

	@Test
	public void testIfTotalPriceOfItemsIsCorrect() {
		listShoppingCart.addItem(goldenApple); // 0.30
		listShoppingCart.addItem(greenApple); // 0.40
		listShoppingCart.addItem(chocolate); // 1.90
		listShoppingCart.addItem(goldenApple);

		double actual = listShoppingCart.getTotal();
		final double expected = 2.90;

		assertEquals(expected, actual, DELTA);
	}

	@Test
	public void testIfItemsAreSortedDCorrectlyByAdd() {
		listShoppingCart.addItem(greenApple);
		listShoppingCart.addItem(goldenApple);
		listShoppingCart.addItem(goldenApple);
		listShoppingCart.addItem(goldenApple);
		listShoppingCart.addItem(chocolate);
		listShoppingCart.addItem(chocolate);

		Object[] arrayOfItems = { goldenApple, chocolate, greenApple };

		Collection<Item> sorted = listShoppingCart.getSortedItems();

		Object[] sortedItems = sorted.toArray();

		assertArrayEquals(arrayOfItems, sortedItems);
	}

	@Test
	public void testIfItemsAreUnique() {
		listShoppingCart.addItem(greenApple);
		listShoppingCart.addItem(goldenApple);
		listShoppingCart.addItem(goldenApple);
		listShoppingCart.addItem(goldenApple);
		listShoppingCart.addItem(chocolate);
		listShoppingCart.addItem(chocolate);

		Set<Item> items = new HashSet<>();
		items.add(greenApple);
		items.add(goldenApple);
		items.add(chocolate);
		Set<Item> actual = (Set<Item>) listShoppingCart.getUniqueItems();

		assertEquals(items, actual);

	}

}
