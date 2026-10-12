/** Thrown when a guest id cannot be matched to a known guest. */
class GuestNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public GuestNotFoundException(String message) {
		super(message);
	}
}
