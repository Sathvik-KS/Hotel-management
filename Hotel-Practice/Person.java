/**
 * Data shared by every person known to the hotel.
 *
 * <p>{@link Guest} extends this class and adds hotel-specific details such as
 * the guest id and preferences.</p>
 */
class Person {

	private String name;
	private int age;
	private String phone;

	public Person(String name, int age, String phone) {
		this.name = name;
		this.age = age;
		this.phone = phone;
	}

	public String getName() { return name; }

	public int getAge() { return age; }

	public String getPhone() { return phone; }

	@Override
	public String toString() {
		return "Name - " + name + "\nAge - " + age + "\nPhone - " + phone;
	}
}
