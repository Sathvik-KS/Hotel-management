import java.util.HashMap;
import java.util.Map;

/**
 * A hotel guest.
 *
 * <p>Extends {@link Person} with a guest id and a small set of personal
 * preferences (for example {@code "pillow" -> "soft"}).</p>
 */
class Guest extends Person {

	private final String guestId;
	private final Map<String, String> preferences = new HashMap<>();

	public Guest(String name, String guestId, int age, String phone) {
		super(name, age, phone);
		this.guestId = guestId;
	}

	public String getGuestId() { return guestId; }

	/** @return the live preference map owned by this guest (never {@code null}). */
	public Map<String, String> getPreferences() { return preferences; }

	public void addPreference(String key, String value) {
		preferences.put(key, value);
	}

	public void displayGuest() {
		System.out.println("============================");
		System.out.println("ID - " + guestId + "\n" + super.toString());
		System.out.println("============================");
	}
}
