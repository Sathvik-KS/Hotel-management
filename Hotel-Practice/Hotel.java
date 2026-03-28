import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Collections;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

class Hotel{
	ArrayList<room> rooms = new ArrayList<>();
	ArrayList<Guest> guests = new ArrayList<>();
	ArrayList<Booking> bookings = new ArrayList<>();
	HashSet<String> amenities=  new HashSet<>();
	PriorityQueue<String> maintanance = new PriorityQueue<>();
	ArrayList<room> filledRooms = new ArrayList<>();

	public void addAmenity(String a){
		amenities.add(a);
	}

	public void addGuest(Guest g){
		guests.add(g);
	}
	
	public void addRoom(room r){
		rooms.add(r);
	}
	
	public void bookRoom(int Rid, String BID, Guest guest, String date)throws RoomNotFoundExc, InvalidDateException{
		boolean found = false;
		for(room r : rooms){
			if(r.getRoomNumber() == Rid){
				found = true;
				if(r.getAvailable()){
					r.setAvailable(false);
					r.guest = guest;
					//availableRooms.remove(r);
					filledRooms.add(r);
					if(date.matches(".+/.+/.+"))
						bookings.add(new Booking(BID, guest, r, date));
					else
						throw new InvalidDateException("Invalid fucking date");
				}
				else
					//throw new InputMismatchException("Room unaivalable");
					throw new RoomNotFoundExc("Room unaivalable");
			}		
		}
		if(!found)
			throw new RoomNotFoundExc("Room not found");
		
	}
	
	public void checkout(room r){
		if(!r.getAvailable()){
			r.setAvailable(true);
			filledRooms.remove(r);
		}
		else
			System.out.println("Room already checked out");
	}
	
	public void showAvailableRooms(){
		if(filledRooms.size() == rooms.size())
			System.out.println("All rooms are full.");
		for(room r: rooms){
			if(r.getAvailable())
				System.out.println(r.getRoomNumber());
		}
	}
	
	public void displayGuests(){
		if(guests.isEmpty()){
			System.out.println("============================");
			System.out.println("There are no guests to display.");
		}
		for(Guest g: guests)
			g.displayGuest();
	}
	
	public void showAmenitites(){
		if(amenities.isEmpty()){System.out.println("Amenities are empty.");return;}
		for(String s: amenities){
			System.out.println(s);
		}
	}
	
	public void displayFilledRooms(){
	if(filledRooms.isEmpty()){System.out.println("All the Rooms are empty.");}
		for(room r: filledRooms){
			//if(!r.getAvailable())
				r.displayRoom();
		}
	}
	
	public void displayAllRooms(){
		Collections.sort(rooms);
		for(room r: rooms){
			r.displayRoom();
		}
	}
	
	public void addMaintanance(int m, String n)throws MaintanancePriorityException{
		if(m>0 && m<10)
			maintanance.add(m+"-"+n);
		else
			throw new MaintanancePriorityException("Enter a valid priority");
	}
	
	public void displayMaintain(){
		if(maintanance.isEmpty()){
			System.out.println("There is nothing to maintain.");
		}
		for(String m : maintanance)System.out.println(m);
	}	
	
	public void maintain(){
		System.out.println("Maintained: "+maintanance.poll());
	}
	
	public void addPreference(Guest g, String key, String value){
		g.preferences.put(key, value);
	}
	
	public void showPreferences(Guest g){
		for(String key : g.preferences.keySet()){
			System.out.println(key+" "+g.preferences.get(key));
		}
	}
	
	public void book(Booking b){
		bookings.add(b);
	}
	
	public void showBooks(){
		for(Booking b : bookings)
			b.showBook();
	}
	
	
	//Collections.sort(rooms);
	public void saveBookings(){
		if(bookings.isEmpty())
			return;
		Booking b = bookings.get(bookings.size()-1);
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("Customers.txt", true))){
			writer.write(b.bookId+" "+b.guest.getName()+" "+b.rooms.getRoomNumber()+" ");
			writer.newLine();
			System.out.println("Added");
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
	public void ReadFile(){
		try(BufferedReader reader = new BufferedReader(new FileReader("Customers.txt"))){
			String line;
			while((line = reader.readLine()) != null){
				System.out.println(line);
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
	
}
	