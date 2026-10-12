import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * A very small restaurant attached to the hotel.
 *
 * <p>The menu maps a food name to its price and orders are served
 * first-in-first-out through a {@link Queue}.</p>
 */
class Restaurant {

	private final Scanner sc;
	private final Map<String, Double> menu = new HashMap<>();
	private final Queue<String> orderQ = new LinkedList<>();

	public Restaurant(Scanner sc) {
		this.sc = sc;
	}

	public void addToMenu(String food, double price) {
		menu.put(food, price);
	}

	public void displayMenu() {
		for (Map.Entry<String, Double> item : menu.entrySet()) {
			System.out.println(item.getKey() + " " + item.getValue());
		}
	}

	public void placeOrder() {
		if (menu.isEmpty()) {
			System.out.println("Menu is empty. Cannot order.");
			return;
		}
		displayMenu();
		System.out.print("Enter food name (case-sensitive): ");
		String food = sc.nextLine();
		if (menu.containsKey(food)) {
			orderQ.offer(food);
			System.out.println(food + " added to the order queue.");
		} else {
			System.out.println("Food unavailable, try again.");
		}
	}

	public void displayOrderQueue() {
		if (orderQ.isEmpty()) {
			System.out.println("There are no orders yet.");
			return;
		}
		for (String order : orderQ) {
			System.out.println(order);
		}
	}

	public void serveOrder() {
		if (orderQ.isEmpty()) {
			System.out.println("There are no orders yet.");
			return;
		}
		System.out.println(orderQ.poll() + " served.");
	}
}
