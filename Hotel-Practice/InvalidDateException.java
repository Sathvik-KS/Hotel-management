/** Thrown when a booking is created with a date that is not in a valid format. */
class InvalidDateException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidDateException(String message) {
		super(message);
	}
}
