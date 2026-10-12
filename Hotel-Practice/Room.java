/**
 * A single hotel room.
 *
 * <p>A room knows its number, type and nightly price, and whether it is
 * currently occupied. When a room is booked the assigned {@link Guest} is
 * stored so the room can report who is staying in it.</p>
 */
class Room implements Comparable<Room> {

	private final int roomNumber;
	private final String type;
	private final double price;
	private boolean available = true;
	private Guest guest;

	public Room(int roomNumber, String type, double price) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.price = price;
	}

	public int getRoomNumber() { return roomNumber; }

	public String getType() { return type; }

	public double getPrice() { return price; }

	public boolean isAvailable() { return available; }

	/** @return the guest staying in this room, or {@code null} when empty. */
	public Guest getGuest() { return guest; }

	public void setAvailable(boolean available) { this.available = available; }

	public void setGuest(Guest guest) { this.guest = guest; }

	/** Books this room for the given guest, marking it unavailable. */
	public void bookRoom(Guest guest) {
		this.guest = guest;
		this.available = false;
	}

	/** Prints a one-line description of the room and its current status. */
	public void displayRoom() {
		if (available) {
			System.out.println("Room number: " + roomNumber + ", Room empty. Price: " + price);
		} else {
			System.out.println("Room number: " + roomNumber
					+ ", Guest ID: " + guest.getGuestId() + ", Price: " + price);
		}
	}

	@Override
	public String toString() {
		if (guest == null) {
			return roomNumber + " " + price + " Empty";
		}
		return guest.getGuestId() + " " + guest.getName() + " " + guest.getAge();
	}

	/** Rooms are ordered from cheapest to most expensive. */
	@Override
	public int compareTo(Room other) {
		return Double.compare(this.price, other.price);
	}
}
