/** Thrown when a maintenance task is given a priority outside the range 1-9. */
class MaintenancePriorityException extends Exception {
	private static final long serialVersionUID = 1L;

	public MaintenancePriorityException(String message) {
		super(message);
	}
}
