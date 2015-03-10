package Project04132014;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetDataBaseData {
	private ArrayList<DataStructure> allData = new ArrayList<DataStructure>();
	
	public GetDataBaseData(){
		String driver ="org.postgresql.Driver";
		String url="jdbc:postgresql://localhost:5432/postgres";
		String userName="postgres";
		String password="65254408";
		Connection conn=null;
		Statement stmt=null;
		try{
			Class.forName(driver);
			System.out.println("Driver Successfully!");
		}catch(ClassNotFoundException e){
			System.err.print("ClassNotFoundException");
		}
		try{
			conn=DriverManager.getConnection(url,userName,password);
			System.out.println("connect database successfully!");
			stmt = conn.createStatement();
			String sqlSelect="select * from sales";
			ResultSet rs=stmt.executeQuery(sqlSelect);
			//need change
			String customer;
			String product;
			int day;
			int month;
			int year;
			String state;
			int quan;
			while(rs.next()){
				customer=rs.getString(1);
				product=rs.getString(2);
				day=rs.getInt(3);
				month=rs.getInt(4);
				year=rs.getInt(5);
				state=rs.getString(6);
				quan=rs.getInt(7);
				allData.add(new DataStructure(customer,product,day,month,year,state,quan));
			}
			//need not change
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				stmt.close();
				if(conn!=null)
					conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<DataStructure> getAllData(){
		return allData;
	}
	
	public static void main(String[] args){
		GetDataBaseData t = new GetDataBaseData();
		for(int i=0;i<t.allData.size();i++)
			System.out.println(t.allData.get(i));
	}
}
