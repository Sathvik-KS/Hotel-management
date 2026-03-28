class person{
	private String name;
	private int age;
	private String phone;
	
	public person(String name, int age, String phone){
		this.name = name;
		this.age = age;
		this.phone = phone;
	}
	
	public String getName(){return this.name;}
	
	public String toString(){
		return "Name - "+this.name+"\nAge - "+this.age+"\nPhone - "+this.phone;
	}
	
}