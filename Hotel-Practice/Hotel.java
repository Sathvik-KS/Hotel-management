import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 * Central model of the hotel.
 *
 * <p>Owns the rooms, guests, bookings, amenities and the maintenance queue.
 * Every public method here is driven by the console menu in {@link Main}, so
 * most of them read from or write to standard output rather than returning
 * values.</p>
 */
class Hotel {

	private final Scanner sc;
	private final List<Room> rooms = new ArrayList<>();
	private final List<Guest> guests = new ArrayList<>();
	private final List<Booking> bookings = new ArrayList<>();
	private final Set<String> amenities = new HashSet<>();
	/** Maintenance tasks stored as {@code "priority-task"} so the queue orders them by priority. */
	private final PriorityQueue<String> maintenance = new PriorityQueue<>();
	private final List<Room> filledRooms = new ArrayList<>();

	public Hotel(Scanner sc) {
		this.sc = sc;
	}

	public void addRoom(Room room) {
		rooms.add(room);
	}

	// ------------------------------------------------------------------
	// Guests
	// ------------------------------------------------------------------

	public void addGuest() {
		System.out.print("Enter Guest ID: ");
		String id = sc.nextLine();
		System.out.print("Enter Guest name: ");
		String name = sc.nextLine();
		System.out.print("Enter Guest age: ");
		if (!sc.hasNextInt()) {
			System.out.println("Age must be a number.");
			sc.nextLine();
			return;
		}
		int age = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter Guest Phone: ");
		String phone = sc.nextLine();

		guests.add(new Guest(name, id, age, phone));
		displayGuests();
	}

	public void displayGuests() {
		if (guests.isEmpty()) {
			System.out.println("There are no guests to display.");
			return;
		}
		for (Guest guest : guests) {
			guest.displayGuest();
		}
	}

	public boolean hasGuests() {
		return !guests.isEmpty();
	}

	/**
	 * Lets the user pick a guest from the numbered list.
	 *
	 * @return the selected guest, or {@code null} when there are no guests or
	 *         the entered index is out of range
	 */
	public Guest selectGuest() {
		if (guests.isEmpty()) {
			System.out.println("There are no guests.");
			return null;
		}
		displayGuests();
		System.out.print("Enter guest number: ");
		if (!sc.hasNextInt()) {
			System.out.println("Please enter a number.");
			sc.nextLine();
			return null;
		}
		int pick = sc.nextInt() - 1;
		sc.nextLine();
		if (pick < 0 || pick >= guests.size()) {
			System.out.println("Invalid pick.");
			return null;
		}
		return guests.get(pick);
	}

	public void addPreference(Guest guest, String key, String value) {
		guest.addPreference(key, value);
	}

	public void showPreferences(Guest guest) {
		if (guest.getPreferences().isEmpty()) {
			System.out.println("This guest has no preferences yet.");
			return;
		}
		for (String key : guest.getPreferences().keySet()) {
			System.out.println(key + " " + guest.getPreferences().get(key));
		}
	}

	/**
	 * Finds a guest by id.
	 *
	 * @throws GuestNotFoundException when no guest has the given id
	 */
	public Guest searchGuest(String id) throws GuestNotFoundException {
		return guests.stream()
				.filter(g -> g.getGuestId().equalsIgnoreCase(id))
				.findFirst()
				.orElseThrow(() -> new GuestNotFoundException("Guest not found: " + id));
	}

	public void showGuestsByPrefix(String prefix) {
		List<Guest> matches = guests.stream()
				.filter(g -> g.getName().startsWith(prefix))
				.toList();
		if (matches.isEmpty()) {
			System.out.println("No guest names start with \"" + prefix + "\".");
			return;
		}
		matches.forEach(Guest::displayGuest);
	}

	// ------------------------------------------------------------------
	// Rooms and bookings
	// ------------------------------------------------------------------

	/**
	 * Books an available room for a guest.
	 *
	 * <p>All validation happens before any room state is changed, so a failed
	 * booking never leaves a room marked as occupied without a matching
	 * booking.</p>
	 *
	 * @throws RoomNotFoundException  when the room does not exist or is occupied
	 * @throws InvalidDateException   when the date is not in {@code d/m/y} form
	 */
	public void bookRoom(int roomNumber, String bookingId, Guest guest, String date)
			throws RoomNotFoundException, InvalidDateException {

		Room target = rooms.stream()
				.filter(r -> r.getRoomNumber() == roomNumber)
				.findFirst()
				.orElse(null);

		if (target == null) {
			throw new RoomNotFoundException("Room " + roomNumber + " not found");
		}
		if (!target.isAvailable()) {
			throw new RoomNotFoundException("Room " + roomNumber + " is already occupied");
		}
		if (!isValidDate(date)) {
			throw new InvalidDateException("Invalid date \"" + date + "\"; expected dd/mm/yyyy");
		}

		// Only mutate state once everything has been validated.
		target.setAvailable(false);
		target.setGuest(guest);
		filledRooms.add(target);
		bookings.add(new Booking(bookingId, guest, target, date));
	}

	/** Checks out the room selected by the user. */
	public void checkout() {
		if (filledRooms.isEmpty()) {
			System.out.println("Rooms are already empty.");
			return;
		}
		displayFilledRooms();
		System.out.print("Pick a room to check out (1-" + filledRooms.size() + "): ");
		if (!sc.hasNextInt()) {
			System.out.println("Please enter a number.");
			sc.nextLine();
			return;
		}
		int pick = sc.nextInt() - 1;
		sc.nextLine();
		if (pick < 0 || pick >= filledRooms.size()) {
			System.out.println("Invalid pick.");
			return;
		}
		Room room = filledRooms.remove(pick);
		room.setAvailable(true);
		room.setGuest(null);
		System.out.println("Room " + room.getRoomNumber() + " checked out.");
	}

	public void showAvailableRooms() {
		if (rooms.isEmpty()) {
			System.out.println("There are no rooms in the hotel.");
			return;
		}
		if (filledRooms.size() == rooms.size()) {
			System.out.println("All rooms are full.");
			return;
		}
		int count = 0;
		for (Room room : rooms) {
			if (room.isAvailable()) {
				System.out.println(room.getRoomNumber());
				count++;
			}
		}
		System.out.println("There are " + count + " rooms available");
	}

	public void displayFilledRooms() {
		if (filledRooms.isEmpty()) {
			System.out.println("All the rooms are empty.");
			return;
		}
		for (Room room : filledRooms) {
			room.displayRoom();
		}
	}

	public void displayAllRooms() {
		Collections.sort(rooms);
		for (Room room : rooms) {
			room.displayRoom();
		}
	}

	public boolean hasRooms() {
		return !rooms.isEmpty();
	}

	public boolean hasBookings() {
		return !bookings.isEmpty();
	}

	public void showBookings() {
		for (Booking booking : bookings) {
			booking.showBooking();
		}
	}

	public void showGuestBooking(String guestId) {
		for (Booking booking : bookings) {
			if (booking.getGuest().getGuestId().equalsIgnoreCase(guestId)) {
				System.out.println("Guest " + booking.getGuest().getName()
						+ " has room " + booking.getRoom().getRoomNumber());
				return;
			}
		}
		System.out.println("This guest doesn't have a room in this hotel");
	}

	/** Prints the cheapest available room, if any. */
	public void showCheapestAvailableRoom() {
		rooms.stream()
				.filter(Room::isAvailable)
				.min(Comparator.comparingDouble(Room::getPrice))
				.ifPresentOrElse(
						Room::displayRoom,
						() -> System.out.println("No rooms are available."));
	}

	/** Prints up to two of the cheapest available rooms. */
	public void showCheapestRooms() {
		rooms.stream()
				.filter(Room::isAvailable)
				.sorted(Comparator.comparingDouble(Room::getPrice))
				.limit(2)
				.forEach(Room::displayRoom);
	}

	/** Prints how many rooms are currently occupied. */
	public void printOccupiedRoomCount() {
		long occupied = rooms.stream().filter(r -> !r.isAvailable()).count();
		System.out.println("Occupied rooms: " + occupied);
	}

	// ------------------------------------------------------------------
	// Amenities
	// ------------------------------------------------------------------

	public void addAmenity(String amenity) {
		amenities.add(amenity);
	}

	public void showAmenities() {
		if (amenities.isEmpty()) {
			System.out.println("Amenities are empty.");
			return;
		}
		for (String amenity : amenities) {
			System.out.println(amenity);
		}
	}

	// ------------------------------------------------------------------
	// Maintenance queue
	// ------------------------------------------------------------------

	/**
	 * Adds a maintenance task. Priority 1 is handled first.
	 *
	 * @throws MaintenancePriorityException when priority is not between 1 and 9
	 */
	public void addMaintenance(int priority, String task) throws MaintenancePriorityException {
		if (priority < 1 || priority > 9) {
			throw new MaintenancePriorityException("Enter a valid priority (1-9)");
		}
		maintenance.add(priority + "-" + task);
	}

	public boolean hasMaintenance() {
		return !maintenance.isEmpty();
	}

	/** Lists pending tasks in priority order without removing them. */
	public void displayMaintenance() {
		if (maintenance.isEmpty()) {
			System.out.println("There is nothing to maintain.");
			return;
		}
		maintenance.stream().sorted().forEach(System.out::println);
	}

	/** Completes (removes) the highest-priority task. */
	public void performMaintenance() {
		System.out.println("Maintained: " + maintenance.poll());
	}

	// ------------------------------------------------------------------
	// Persistence
	// ------------------------------------------------------------------

	/** Appends guests to {@code Guests.txt}, a line per guest: {@code id|name|age|phone}. */
	public void saveGuests() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("Guests.txt"))) {
			for (Guest guest : guests) {
				writer.write(guest.getGuestId() + "|" + guest.getName() + "|"
						+ guest.getAge() + "|" + guest.getPhone());
				writer.newLine();
			}
			System.out.println("Saved " + guests.size() + " guest(s).");
		} catch (IOException e) {
			System.out.println("Could not save Guests.txt: " + e.getMessage());
		}
	}

	/** Loads guests from {@code Guests.txt}, skipping malformed lines. */
	public void loadGuestsFromFile() {
		File file = new File("Guests.txt");
		if (!file.exists()) {
			System.out.println("No saved guests found (Guests.txt).");
			return;
		}
		int loaded = 0;
		int skipped = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length < 4) {
					skipped++;
					continue;
				}
				try {
					guests.add(new Guest(parts[1], parts[0], Integer.parseInt(parts[2].trim()), parts[3]));
					loaded++;
				} catch (NumberFormatException e) {
					skipped++;
				}
			}
			System.out.println("Loaded " + loaded + " guest(s) (skipped " + skipped + ").");
		} catch (IOException e) {
			System.out.println("Could not read Guests.txt: " + e.getMessage());
		}
	}

	/**
	 * Rewrites {@code Customers.txt} so the file mirrors the booking list.
	 *
	 * <p>Each line is {@code bookingId guestName roomNumber}. Rewriting (rather
	 * than appending) keeps the file consistent with the in-memory state.</p>
	 */
	public void saveBookings() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("Customers.txt"))) {
			for (Booking booking : bookings) {
				writer.write(booking.getBookingId() + " " + booking.getGuest().getName()
						+ " " + booking.getRoom().getRoomNumber());
				writer.newLine();
			}
			System.out.println("Saved " + bookings.size() + " booking(s).");
		} catch (IOException e) {
			System.out.println("Could not save Customers.txt: " + e.getMessage());
		}
	}

	/**
	 * Loads bookings from {@code Customers.txt}.
	 *
	 * <p>Guests must already be loaded, otherwise booking lines cannot be
	 * matched. Rooms referenced by a booking are marked occupied so the room
	 * state and booking list stay consistent. Unmatched or malformed lines are
	 * skipped.</p>
	 */
	public void loadBookingsFromFile() {
		File file = new File("Customers.txt");
		if (!file.exists()) {
			System.out.println("No saved bookings found (Customers.txt).");
			return;
		}
		bookings.clear();
		int loaded = 0;
		int skipped = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				Booking booking = parseBookingLine(line);
				if (booking == null) {
					skipped++;
					continue;
				}
				bookings.add(booking);
				Room room = booking.getRoom();
				if (room.isAvailable()) {
					room.setAvailable(false);
					room.setGuest(booking.getGuest());
					filledRooms.add(room);
				}
				loaded++;
			}
			System.out.println("Loaded " + loaded + " booking(s) (skipped " + skipped + ").");
		} catch (IOException e) {
			System.out.println("Could not read Customers.txt: " + e.getMessage());
		}
	}

	/**
	 * Parses a single booking line: {@code bookingId guestName roomNumber}.
	 *
	 * @return the parsed booking, or {@code null} if the line is malformed or
	 *         refers to an unknown guest or room
	 */
	private Booking parseBookingLine(String line) {
		String trimmed = line.trim();
		int firstSpace = trimmed.indexOf(' ');
		int lastSpace = trimmed.lastIndexOf(' ');
		if (firstSpace <= 0 || lastSpace <= firstSpace) {
			return null;
		}
		String bookingId = trimmed.substring(0, firstSpace);
		String guestName = trimmed.substring(firstSpace + 1, lastSpace).trim();
		int roomNumber;
		try {
			roomNumber = Integer.parseInt(trimmed.substring(lastSpace + 1).trim());
		} catch (NumberFormatException e) {
			return null;
		}
		Room room = rooms.stream()
				.filter(r -> r.getRoomNumber() == roomNumber)
				.findFirst()
				.orElse(null);
		Guest guest = guests.stream()
				.filter(g -> g.getName().equals(guestName))
				.findFirst()
				.orElse(null);
		if (room == null || guest == null) {
			return null;
		}
		return new Booking(bookingId, guest, room, "unknown");
	}

	// ------------------------------------------------------------------
	// Report
	// ------------------------------------------------------------------

	/** Writes a human-readable summary of the hotel to {@code Hotel-Report.txt}. */
	public void generateReport() {
		try (PrintWriter writer = new PrintWriter(new FileWriter("Hotel-Report.txt"))) {
			writer.println("Hotel Management");
			writer.println("Generated: " + new Date());
			writer.println();
			writer.println("STATISTICS:");
			writer.println("Total Rooms: " + rooms.size());
			writer.println("Occupied Rooms: " + filledRooms.size());
			writer.println("Available Rooms: " + (rooms.size() - filledRooms.size()));
			writer.println("Total Guests: " + guests.size());
			writer.println("Total Bookings: " + bookings.size());
			writer.println();
			writer.println("BOOKING LIST:");
			for (Booking booking : bookings) {
				writer.printf("  %s | %s | Room %d | %s%n",
						booking.getBookingId(),
						booking.getGuest().getName(),
						booking.getRoom().getRoomNumber(),
						booking.getDate());
			}
			System.out.println("Report written to Hotel-Report.txt");
		} catch (IOException e) {
			System.out.println("Could not write Hotel-Report.txt: " + e.getMessage());
		}
	}

	/** Accepts dates such as {@code 1/2/23} or {@code 25/12/2026}. */
	private boolean isValidDate(String date) {
		return date != null && date.trim().matches("\\d{1,2}/\\d{1,2}/\\d{2,4}");
	}
}
