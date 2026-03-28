import java.util.Scanner;

class Main{
	public static void main(String [] args){
		Scanner sc = new Scanner(System.in);
		Hotel hotel = new Hotel();
		Restaurant res = new Restaurant();

		//hotel.addAmenity("pool");

		hotel.addRoom(new room(101, "AC", 1800));
		hotel.addRoom(new room(102, "AC", 1250));
		hotel.addRoom(new room(103, "AC", 4200));
		
		hotel.addGuest(new Guest("ABC", "ASD123", 34, "1234567890"));
		//res.addToMenu("ABC", 12);
		//res.addToMenu("A", 12);
		//res.addToMenu("BC", 12);
		
		/*try{
		hotel.bookRoom(101, "1234", hotel.guests.get(0), "12/12/23");
		}
		catch(InvalidDateException e){
					System.out.println(e.getMessage());
				}
		catch(RoomNotFoundExc e){
			System.out.println(e);
		}*/

		int a;
		while(true){
			System.out.print("Enter choice: ");
			a = sc.nextInt();
			sc.nextLine();
			switch(a){
				case 1:
				System.out.println("Enter Guest ID: ");
				String ID = sc.nextLine();
				System.out.println("Enter Guest name: ");
				String name = sc.nextLine();
				System.out.println("Enter Guest age: ");
				int age = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter Guest Phone: ");
				String phone = sc.nextLine();
				hotel.addGuest(new Guest(name, ID, age, phone));
				hotel.displayGuests();
				System.out.println("");
				break;
				
				case 2:
				if(hotel.guests.size() <= 0){
					System.out.println("There are no guests, add a guest first.");
					break;
				}
				System.out.println("Enter bookID: ");
				String bID = sc.nextLine();
				System.out.println("Enter Date: ");
				String date = sc.nextLine();
				hotel.displayGuests();
				System.out.print("Enter your pick to choose guest: ");
				int pick = sc.nextInt()-1;
				hotel.showAvailableRooms();
				try{
					System.out.print("Enter Room ID: ");
					int RID = sc.nextInt();
					sc.nextLine();
					hotel.bookRoom(RID, bID,hotel.guests.get(pick), date);
				}
				catch(RoomNotFoundExc e){
					System.out.println(e.getMessage());
				}
				catch(InvalidDateException e){
					System.out.println(e.getMessage());
				}
				/*catch(GuestNotFoundException e){
					System.out.println(e.getMessage());
				}*/
				finally{hotel.saveBookings();}
				//hotel.book(new Booking(bID, hotel.guests.get(pick), RID, date));
				break;
				
				case 3:
				if(hotel.filledRooms.isEmpty()){
					System.out.println("Rooms are already empty.");break;
				}
				hotel.displayFilledRooms();
				System.out.println("Pick room to checkout: ");
				pick  = sc.nextInt()-1;
				sc.nextLine();
				hotel.checkout(hotel.filledRooms.get(pick));
				break;
				
				case 4:
				hotel.displayGuests();
				break;
				
				case 5:
				hotel.showAmenitites();
				break;
				
				case 6:
				hotel.showAvailableRooms();
				break;
				
				case 7:
				hotel.displayFilledRooms();
				break;
				
				case 8:
				if(res.menu.isEmpty()){
					System.out.println("Menu empty cannot order.");
					break;
				}
				res.placeOrder();
				break;
				
				case 9:
				res.displayOrderQueue();
				break;
				
				case 10:
				if(res.orderQ.isEmpty()){
					System.out.println("There are no orders yet.");
					break;
				}
				res.serve();
				break;
				
				case 11:
				System.out.println("Enter maintanance: ");
				String maintain = sc.nextLine();
				try{
					System.out.println("Enter Priority(1-9): ");
					int priority = sc.nextInt();
					hotel.addMaintanance(priority,maintain);
				}
				catch(MaintanancePriorityException e){
					System.out.println(e.getMessage());
				}
				break;
				
				case 12:
				hotel.displayMaintain();
				break;
				
				case 13:
				if(hotel.maintanance.isEmpty()){
					System.out.println("There is nothing to maintain.");
					break;
				}
				hotel.maintain();
				break;
				
				case 14:
				if(hotel.guests.isEmpty()){
					System.out.println("There are no guests to add preference to.");
					break;
				}
				hotel.displayGuests();
				System.out.println("Choose a guest to add preference: ");
				pick = sc.nextInt()-1;
				sc.nextLine();
				System.out.println("Enter Key: ");
				String key = sc.nextLine();
				System.out.println("Enter Value: ");
				String value = sc.nextLine();
		
				hotel.addPreference(hotel.guests.get(pick), key, value);
				break;
				
				case 15:
				if(hotel.guests.isEmpty()){
					System.out.println("There are no guests to show preferences of.");
					break;
				}
				hotel.displayGuests();
				System.out.println("Choose a guest to add preference: ");
				pick = sc.nextInt()-1;
				sc.nextLine();
				hotel.showPreferences(hotel.guests.get(pick));
				break;
				
				case 16:
				if(hotel.bookings.isEmpty()){
					System.out.println("There are no bookings yet.");
					break;
				}
				hotel.showBooks();
				break;
				
				case 17:
				if(hotel.rooms.isEmpty()){
					System.out.println("There are no rooms in the building to display.");
					break;
				}
				hotel.displayAllRooms();
				break;
				
				case 18:
				hotel.ReadFile();
				break;
				
				case 20:
				System.out.println("Exiting...");
				return;
			}
		}
	}
}