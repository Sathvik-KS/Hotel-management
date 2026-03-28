class Booking{
	String bookId;
	Guest guest;
	room rooms;
	String date;
	
	public Booking(String bookId, Guest guest, room r, String date){
		this.bookId = bookId;
		this.guest = guest;
		this.rooms = r;
		this.date = date;
	}
	
	public void showBook(){
		System.out.println(bookId+" "+this.guest.getGuestId()+" "+rooms.getRoomNumber()+" "+this.date);
	}
	
}