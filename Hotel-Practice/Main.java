import java.util.Scanner;

/**
 * Console entry point for the Hotel Management application.
 *
 * <p>Run it from the {@code Hotel-Practice} directory so that the data files
 * (Guests.txt and Customers.txt) are read and written there.</p>
 */
class Main {

	public static void main(String[] args) {
		// A single scanner is shared by Main, Hotel and Restaurant. Creating one
		// Scanner per class is a common source of lost console input.
		Scanner sc = new Scanner(System.in);
		Hotel hotel = new Hotel(sc);
		Restaurant restaurant = new Restaurant(sc);

		// Seed data so the application has something to work with on first run.
		restaurant.addToMenu("ABC", 10);
		hotel.addRoom(new Room(101, "AC", 1800));
		hotel.addRoom(new Room(102, "AC", 1250));
		hotel.addRoom(new Room(103, "AC", 4200));

		// Guests must be loaded before bookings so booking lines can be matched.
		hotel.loadGuestsFromFile();
		hotel.loadBookingsFromFile();

		printMenu();
		while (true) {
			System.out.print("Enter choice: ");
			if (!sc.hasNextInt()) {
				System.out.println("Please enter a number.");
				sc.nextLine();
				continue;
			}
			int choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
				case 1:
					hotel.addGuest();
					break;

				case 2: {
					Guest guest = hotel.selectGuest();
					if (guest == null) {
						break;
					}
					System.out.print("Enter booking ID: ");
					String bookingId = sc.nextLine();
					System.out.print("Enter date (dd/mm/yyyy): ");
					String date = sc.nextLine();
					hotel.showAvailableRooms();
					System.out.print("Enter room number: ");
					if (!sc.hasNextInt()) {
						System.out.println("Room number must be a number.");
						sc.nextLine();
						break;
					}
					int roomNumber = sc.nextInt();
					sc.nextLine();
					try {
						hotel.bookRoom(roomNumber, bookingId, guest, date);
						hotel.saveBookings();
					} catch (RoomNotFoundException | InvalidDateException e) {
						System.out.println(e.getMessage());
					}
					hotel.printOccupiedRoomCount();
					break;
				}

				case 3:
					hotel.checkout();
					break;

				case 4:
					hotel.displayGuests();
					break;

				case 5:
					hotel.showAmenities();
					break;

				case 6:
					hotel.showAvailableRooms();
					break;

				case 7:
					hotel.displayFilledRooms();
					break;

				case 8:
					restaurant.placeOrder();
					break;

				case 9:
					restaurant.displayOrderQueue();
					break;

				case 10:
					restaurant.serveOrder();
					break;

				case 11:
					System.out.print("Enter maintenance task: ");
					String task = sc.nextLine();
					try {
						System.out.print("Enter priority (1-9): ");
						if (!sc.hasNextInt()) {
							System.out.println("Priority must be a number.");
							sc.nextLine();
							break;
						}
						int priority = sc.nextInt();
						sc.nextLine();
						hotel.addMaintenance(priority, task);
					} catch (MaintenancePriorityException e) {
						System.out.println(e.getMessage());
					}
					break;

				case 12:
					hotel.displayMaintenance();
					break;

				case 13:
					if (!hotel.hasMaintenance()) {
						System.out.println("There is nothing to maintain.");
						break;
					}
					hotel.performMaintenance();
					break;

				case 14: {
					Guest guest = hotel.selectGuest();
					if (guest == null) {
						break;
					}
					System.out.print("Enter preference key: ");
					String key = sc.nextLine();
					System.out.print("Enter preference value: ");
					String value = sc.nextLine();
					hotel.addPreference(guest, key, value);
					break;
				}

				case 15: {
					Guest guest = hotel.selectGuest();
					if (guest == null) {
						break;
					}
					hotel.showPreferences(guest);
					break;
				}

				case 16:
					if (!hotel.hasBookings()) {
						System.out.println("There are no bookings yet.");
						break;
					}
					hotel.showBookings();
					break;

				case 17:
					if (!hotel.hasRooms()) {
						System.out.println("There are no rooms in the building to display.");
						break;
					}
					hotel.displayAllRooms();
					break;

				case 19:
					hotel.showCheapestAvailableRoom();
					break;

				case 20:
					System.out.println("Exiting...");
					return;

				case 21:
					System.out.print("Enter a starting letter: ");
					hotel.showGuestsByPrefix(sc.nextLine());
					break;

				case 22:
					hotel.showCheapestRooms();
					break;

				case 23:
					System.out.print("Enter guest ID: ");
					try {
						hotel.searchGuest(sc.nextLine()).displayGuest();
					} catch (GuestNotFoundException e) {
						System.out.println(e.getMessage());
					}
					break;

				case 24:
					System.out.print("Enter guest ID: ");
					hotel.showGuestBooking(sc.nextLine());
					break;

				case 25:
					hotel.generateReport();
					break;

				case 26:
					hotel.saveGuests();
					break;

				default:
					System.out.println("Invalid choice. Enter a number from the menu (20 to exit).");
			}
		}
	}

	/** Prints the console menu. */
	private static void printMenu() {
		System.out.println("========== HOTEL MANAGEMENT ==========");
		System.out.println(" 1. Add guest");
		System.out.println(" 2. Book a room");
		System.out.println(" 3. Check out a room");
		System.out.println(" 4. Display guests");
		System.out.println(" 5. Show amenities");
		System.out.println(" 6. Show available rooms");
		System.out.println(" 7. Show occupied rooms");
		System.out.println(" 8. Place restaurant order");
		System.out.println(" 9. Show order queue");
		System.out.println("10. Serve next order");
		System.out.println("11. Add maintenance task");
		System.out.println("12. Show maintenance tasks");
		System.out.println("13. Complete next maintenance task");
		System.out.println("14. Add guest preference");
		System.out.println("15. Show guest preferences");
		System.out.println("16. Show bookings");
		System.out.println("17. Show all rooms (sorted by price)");
		System.out.println("19. Cheapest available room");
		System.out.println("20. Exit");
		System.out.println("21. Find guests by name prefix");
		System.out.println("22. Two cheapest available rooms");
		System.out.println("23. Find guest by ID");
		System.out.println("24. Show a guest's booking");
		System.out.println("25. Generate hotel report");
		System.out.println("26. Save guests to file");
		System.out.println("======================================");
	}
}
