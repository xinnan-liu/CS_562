package Project04132014;

class DataStructure{
	private String customer;
	private String product;
	private int day;
	private int month;
	private int year;
	private String state;
	private int quan;
	
	public DataStructure(String customer,String product,int day,
			int month,int year,String state, int quan){
		this.customer=customer;
		this.product=product;
		this.day=day;
		this.month=month;
		this.year=year;
		this.state=state;
		this.quan=quan;
	}
	
	public String getCustomer(){
		return customer;
	}
	
	public String getProduct(){
		return product;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	public String getState(){
		return state;
	}
	
	public int getQuan(){
		return quan;
	}
	
	public String toString(){
		return customer+"\t"+product+"\t"+day+"\t"+month+"\t"+year+"\t"+state+"\t"+quan;
	}
}