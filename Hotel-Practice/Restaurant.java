import java.util.*;

class Restaurant{
	Scanner sc = new Scanner(System.in);
	HashMap<String, Double> menu = new HashMap<>();
	Queue<String> orderQ = new LinkedList<>();
	
	public void addToMenu(String food, double price){
		menu.put(food, price);
	}
	
	public void displayMenu(){
		for(String key: menu.keySet()){
			System.out.println(key+" "+menu.get(key));
		}
	}
	
	public void placeOrder(){
		displayMenu();
		System.out.println("Enter food name(case-sensitive): ");
		String f = sc.nextLine();
		if(menu.containsKey(f))
			orderQ.offer(f);
		else
			System.out.println("Food unavailabe, try again.");
	}
	
	public void displayOrderQueue(){
		if(orderQ.isEmpty()){
			System.out.println("There are no orders yet.");
		}
		for(String s: orderQ){System.out.println(s);}
	}
	
	public void serve(){
		System.out.println(orderQ.poll()+" served.");
	}
	
}
	