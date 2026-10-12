/**
 * A confirmed booking that links a {@link Guest} to a {@link Room} for a date.
 */
class Booking {

	private final String bookingId;
	private final Guest guest;
	private final Room room;
	private final String date;

	public Booking(String bookingId, Guest guest, Room room, String date) {
		this.bookingId = bookingId;
		this.guest = guest;
		this.room = room;
		this.date = date;
	}

	public String getBookingId() { return bookingId; }

	public Guest getGuest() { return guest; }

	public Room getRoom() { return room; }

	public String getDate() { return date; }

	public void showBooking() {
		System.out.println(bookingId + " " + guest.getGuestId() + " "
				+ room.getRoomNumber() + " " + date);
	}
}
