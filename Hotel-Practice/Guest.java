import java.util.HashMap;

class Guest extends person{
	private String guestId;
	HashMap<String, String> preferences = new HashMap<>();
	
	public Guest(String name,String ID, int age, String phone){
		super(name, age, phone);
		this.guestId= ID;
	}
	
	public void displayGuest(){
		System.out.println("============================");
		System.out.println("ID - "+guestId+"\n"+super.toString()+" ");
		System.out.println("============================");
	}
	
	public String getGuestId(){return guestId;}
	
}
		
		