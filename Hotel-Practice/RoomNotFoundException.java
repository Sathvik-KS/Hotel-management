/** Thrown when a requested room does not exist or is already occupied. */
class RoomNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public RoomNotFoundException(String message) {
		super(message);
	}
}
