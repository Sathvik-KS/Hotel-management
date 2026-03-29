class room implements Comparable<room>{
	private int roomNumber;
	private String type;
	private double price;
	private boolean isAvailable = true;
	
	Guest guest;
	
	public room(int num, String type, double p){
		this.roomNumber	= num;
		this.type = type;
		this.price = p;
	}
	
	public int getRoomNumber(){return roomNumber;}
	
	public boolean getAvailable(){
		return isAvailable;
	}
	
	public void setAvailable(boolean b){
		this.isAvailable = b;
	}
	
	public void bookRoom(Guest guest){
		this.guest = guest;
		this.isAvailable= false;
	}
	
	public void displayRoom(){
		if(!isAvailable)
			System.out.println("Room number: "+roomNumber+", Guest ID: "+this.guest.getGuestId()+" "+this.price);
		else
			System.out.println("Room number: "+roomNumber+", Room empty."+this.price);
	}
	
	public double getPrice(){return price;}
	
	@Override
	public int compareTo(room r){return Double.compare(this.price, r.price);}
	
}