package project;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


class DataStructure {
	private ArrayList<String> str = new ArrayList<String>();    // output file
	private ArrayList<String> strData = new ArrayList<String>();  //attribute data list
	private ArrayList<String> strName = new ArrayList<String>();  // attribute data name
	private ArrayList<String> strType = new ArrayList<String>();   //attribute data type
	
	
	public DataStructure(){
		Connection conn=null;
		Statement stmt=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");                //run the driver
			System.out.println("Driver Successfully!");
		}catch(ClassNotFoundException e){
			System.err.print("ClassNotFoundException");
		}
		try{
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/sales","root","1220");  //connect the database
			System.out.println("connect database successfully!");
			stmt = conn.createStatement();           //get the statement
			String sqlSelect="select * from information_schema.columns where table_name='sales'";
			ResultSet rs=stmt.executeQuery(sqlSelect);     //get the ResultSet
		    while(rs.next()){
		 	    String name = rs.getString(4);         //get attribute name
		 	    String type = rs.getString(8);         //get the data type, integer or character
		 	    if(type.equals("int")){           //if attribute is integer
		 	    	String temp = "int"+"  "+name;
		 	    	strData.add(temp);
		 	    	strName.add(name);
		 	    	strType.add("int");
		 	    }
		 	    else{                         //if attribute is character/string
		 	    	String temp = "String"+"  "+name;   
		 	    	strData.add(temp);
		 	    	strName.add(name);
		 	    	strType.add("String");
		 	    }
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
		str();   //get the list that output to the file
	}
	
	public void str(){
		str.add("import java.sql.Connection;");
		str.add("import java.sql.DriverManager;");
		str.add("import java.sql.ResultSet;");
		str.add("import java.sql.SQLException;");
		str.add("import java.sql.Statement;");
		str.add("import java.util.ArrayList;");
		str.add("import java.util.HashMap;");
		
		StringBuffer allstrData_Buffer = new StringBuffer(); //the list of parameter in the constructor function
		
		for(int i=0;i<strData.size();i++){
			allstrData_Buffer.append(strData.get(i));
			if(i+1<strData.size())
				allstrData_Buffer.append(",");
		}
		
		str.add("class DataStructure{");   // output the Class DataStructure
		for(int i=0;i<strData.size();i++){  // list the all of the private data
			str.add("private  "+strData.get(i)+";");
		}
		str.add("public DataStructure("+allstrData_Buffer.toString()+"){");  // get the constructor
		for(int i=0;i<strName.size();i++){
			str.add("this."+strName.get(i)+"="+strName.get(i)+";");
		}
		str.add("}");
		
		for(int i=0;i<strData.size();i++){    // get the function that return the private data
			str.add("public  "+strData.get(i)+"(){");
			str.add("return  "+strName.get(i)+";");
			str.add("}");
		}
		str.add("}");
	}
	
	public ArrayList<String> getStrData(){
		return strData;
	}
	
	public ArrayList<String> getStrName(){
		return strName;
	}
	
	public ArrayList<String> getStrType(){
		return strType;
	}
	
	public ArrayList<String> getStr(){
		return str;
	}
	
}

class GetDataBaseData {    //get the database data
	private ArrayList<String> str = new ArrayList<String>();  // the data that output to the file
	private ArrayList<String> strData = new ArrayList<String>();
	private ArrayList<String> strName = new ArrayList<String>();
	private ArrayList<String> strType = new ArrayList<String>();
	
	public GetDataBaseData(){
		DataStructure d = new DataStructure();  //get the database data
		strData=d.getStrData(); 
		strName=d.getStrName();
		strType=d.getStrType();
		str();
	}
	
	public void str(){
		str.add("class GetDataBaseData{");          // Class GetDataBaseData
		str.add("private ArrayList<DataStructure> allData = new ArrayList<DataStructure>();");
		// the private data in the Class GetDataBaseData
		str.add("public GetDataBaseData(){");  // the constructor
		str.add("String driver =\"org.postgresql.Driver\";");    //drive the database
		str.add("String url=\"jdbc:postgresql://localhost:5432/postgres\";");
		str.add("String userName=\"postgres\";");
		str.add("String password=\"65254408\";");
		
		str.add("Connection conn=null;");
		str.add("Statement stmt=null;");
		
		str.add("try{");
		str.add("Class.forName(driver);");   //begin to drive the database
		str.add("System.out.println(\"Driver Successfully!\");");
		str.add("}catch(ClassNotFoundException e){");
		str.add("System.err.print(\"ClassNotFoundException\");");
		str.add("}");
		str.add("try{");
		str.add("conn=DriverManager.getConnection(url,userName,password);");
		//connect to the database
		str.add("System.out.println(\"connect database successfully!\");");
		str.add("stmt = conn.createStatement();");
		str.add("String sqlSelect=\"select * from sales\";");
		str.add("ResultSet rs=stmt.executeQuery(sqlSelect);");
		//get the resultSet
		for(int i=0;i<strData.size();i++){
			str.add(strData.get(i)+";");    // declaration of the data in the database
		}
		str.add("while(rs.next()){");     //parse the data in the database one by one
		for(int i=0;i<strData.size();i++){
			if(strType.get(i).equals("int")){   // parse the data is integer or String
				str.add(strName.get(i)+"=rs.getInt("+(i+1)+");");
			}
			else{
				str.add(strName.get(i)+"=rs.getString("+(i+1)+");");
			}
		}
		
		StringBuffer name_buffer = new StringBuffer(); // the string that in the constructor for Class DataStructure
		
		for(int i=0;i<strName.size();i++){
			name_buffer.append(strName.get(i));
			if(i+1<strName.size()){
				name_buffer.append(",");
			}
		}
		
		str.add("allData.add(new DataStructure("+name_buffer.toString()+"));");
		str.add("}");
		// deal for exception and exit the database
		str.add("}catch(SQLException e){");
		str.add("e.printStackTrace();");
		str.add("}finally{");
		str.add("try{");
		str.add("stmt.close();");
		str.add("if(conn!=null)");
		str.add("conn.close();");
		str.add("}catch(SQLException e){");
		str.add("e.printStackTrace();");
		str.add("}}}");
		str.add("public ArrayList<DataStructure> getAllData(){");
		str.add("return allData;}");
		str.add("}");
	}
	
	public ArrayList<String> getStr(){
		return str;
	}
		
}

class Input {      // Class Input to get customers' input
	private ArrayList<String> s;   //select
	private int n;                // number of grouping variables
	private ArrayList<String> v;  // grouping attribute
	private ArrayList<ArrayList<String>> f;   //aggregate function
	private ArrayList<ArrayList<String>> c;   //condition 
	
	public Input(String s, int n, String v, ArrayList<String> f, ArrayList<String> c){
		this.s = new ArrayList<String>();
		this.v = new ArrayList<String>();
		this.f = new ArrayList<ArrayList<String>>();
		this.c = new ArrayList<ArrayList<String>>();
		
		StringTokenizer st;     
		st= new StringTokenizer(s," ,");    //parse the s
		while(st.hasMoreTokens()){
			String tokens = st.nextToken();
			this.s.add(tokens);
		}
		
		this.n=n;
		
		st = new StringTokenizer(v," ,");   //parse the v
		while(st.hasMoreTokens()){
			String tokens = st.nextToken();
			this.v.add(tokens);
		}
		
		for(int i=0;i<f.size();i++){    //parse the f
			String f_temp = f.get(i);
			ArrayList<String> f_each= new ArrayList<String>();
			st = new StringTokenizer(f_temp," ,");
			while(st.hasMoreTokens()){
				String tokens=st.nextToken();
				f_each.add(tokens);
			}
			this.f.add(f_each);
		}
		 
		for(int i=0;i<c.size();i++){     //parse the c
			String c_temp = c.get(i);
			ArrayList<String> c_each= new ArrayList<String>();
			st = new StringTokenizer(c_temp," ,");
			while(st.hasMoreTokens()){
				String tokens=st.nextToken();
				c_each.add(tokens);
			}
			this.c.add(c_each);
		}
	}
	
	public ArrayList<String> getS(){
		return s;
	}
	public int getN(){
		return n;
	}
	public ArrayList<String> getV(){
		return v;
	}
	public ArrayList<ArrayList<String>> getF(){
		return f;
	}
	public ArrayList<ArrayList<String>> getC(){
		return c;
	}
}

class MfStructure {
	private ArrayList<String> str = new ArrayList<String>();  //the data that output to the file
	private ArrayList<String> attr;         //grouping attribute list
	private ArrayList<ArrayList<String>> func;   //aggregate functions
	
	//add a new statement that contains in S but not in V and F
	private ArrayList<String> select;
	
	public MfStructure(Input q){
		attr = q.getV();
		func = q.getF();
		select=q.getS();
		str();
	}
	
	public void str(){
		str.add("class MfStructure{");    //Class MfStructure
		
		//add a new function that initialized the HashMap for the attribute that contains in S but not in F and V
		for(int i=0;i<select.size();i++){
			String temp=select.get(i);
			if(temp.contains(".")&&!temp.contains("(")){
				String sub=temp.substring(temp.indexOf('.')+1);
				str.add("HashMap<String,String>" + sub + i+"=new HashMap<String,String>();");
			}
		}
		
		for(int i=0;i<attr.size();i++){
			str.add("private ArrayList<String> "+attr.get(i)+"= new ArrayList<String>();");
			// declaration of all of the private data in the Class MfStrucuture
		}
		for(int i=0;i<func.size();i++){     // traverse the function list 
			for(int j=0;j<func.get(i).size();j++){      // traverse the function list for each grouping variable
				String operator = func.get(i).get(j);   // get one function
				if(operator.equals("_"))   // if there is no function, break
					break;
				String opcode = operator.substring(0,3);  // get the first 3 word of the function as opcode
				switch(opcode){
				// choose the opcode and create the hashmap to store the data
				case "sum":   //if the opcode is sum
					str.add("HashMap<String,Integer> sum"+i+j+"=new HashMap<String,Integer>();");
					break;
				case "avg":   //avg
					str.add("HashMap<String,Double> avg"+i+j+"= new HashMap<String,Double>();");
					str.add("HashMap<String,Integer> sum"+i+j+"=new HashMap<String,Integer>();");
					str.add("HashMap<String,Integer> count"+i+j+"= new HashMap<String,Integer>();");
					break;
				case "cou":  //count
					str.add("HashMap<String,Integer> count"+i+j+"= new HashMap<String,Integer>();");
					break;
				case "max":   //max
					str.add("HashMap<String,Integer> max"+i+j+"= new HashMap<String,Integer>();");
					break;
				case "min":  //min
					str.add("HashMap<String,Integer> min"+i+j+"= new HashMap<String,Integer>();");
					break;
				default:
					break;
				}
			}
		}
		str.add("}");
	}
	
	public ArrayList<String> getStr(){
		return str;
	}
	
}

class Func {
	
	private ArrayList<String> str = new ArrayList<String>();
	private int n;
	private ArrayList<String> v;
	private ArrayList<ArrayList<String>> f;
	private ArrayList<ArrayList<String>> c;
	private ArrayList<String> s;
	
	public Func(Input q){
		this.n = q.getN();
		this.v=q.getV();
		this.f = q.getF();
		this.c = q.getC();
		this.s=q.getS();
		str();
	}
	
	public void str(){
		str.add("ArrayList<String> has_ga = new ArrayList<String>();");
		// create the list that combine all of the grouping attributes
		str.add("for(int i=0;i<allData.size();i++){");
		str.add("StringBuffer ga_temp=new StringBuffer();");
		//every time create a new ga_temp
		str.add("DataStructure dTemp = allData.get(i);");
		//get one of the database data
		
		for(int i=0;i<v.size();i++){
			str.add("String gA"+i+"=dTemp."+v.get(i)+"();");
			str.add("ga_temp.append(gA"+i+");");
			//combine all of the grouping attribute
		}
		str.add("String ga = ga_temp.toString();");
		str.add("if(has_ga.contains(ga)){;}");
		// if we have the couple of the grouping attributes, not do anything
		// instead, add it
		str.add("else{");
		for(int i=0;i<v.size();i++){
			str.add("mf."+v.get(i)+".add(gA"+i+");");
			//add different grouping attributes into the mfStructure
		}
		str.add("has_ga.add(ga);"); // add to our criteria
		str.add("}}");
		
		//create the for loop for the condition 0
		//for(int i=0;i<f.get(0).size();i++){
//			if(f.get(0).get(0).equals("_"))
//				;
//			else{
//				str.add("for(int i=0; i<allData.size();i++){");
//				// traverse all of the database data
//				str.add("DataStructure dTemp = allData.get(i);");
//				//get one of the data
//				str.add("StringBuffer buffer = new StringBuffer();");
//				for(int j=0;j<v.size();j++){
//					str.add("buffer.append(dTemp."+v.get(j)+"());");
//				}//get the combination of the grouping attribute
//				str.add("String key = buffer.toString();");
//				for(int j=0;j<f.get(0).size();j++){
//					String ft = f.get(0).get(j);  //get the function
//					//if(ft.equals("_"))
//					//	break;
//					this.parseFunction(ft,0,j); //parse the function
//				}
//				str.add("}");
//			}
	//	}
		for(int i=0;i<=n;i++){
			//if(f.get(i).get(0).equals("_"))
			//	continue;
			str.add("for(int i=0; i<allData.size();i++){");
			str.add("DataStructure dTemp = allData.get(i);");
			str.add("StringBuffer buffer = new StringBuffer();");
			
			for(int j=0;j<v.size();j++){
				str.add("buffer.append(dTemp."+v.get(j)+"());");
			}
			
			str.add("String key = buffer.toString();");
			
			StringBuffer condition = new StringBuffer();  //initial the condition string
			if(i==0)
				condition.append("true");
			else{
				for(int j=0;j<c.get(i).size();j++){
					String s2 = c.get(i).get(j);
					
					
					String result = this.parseCondition(s2,0,0);  //parse condition
					condition.append(result);  //finish the condition string
					if(j+1<c.get(i).size())
						condition.append("&&");
				}
			}
			str.add("if("+condition.toString()+"){");
			
			
			
			for(int q=0;q<s.size();q++){
				if(s.get(q).startsWith(new Integer(i).toString())&&!s.get(q).contains("(")){
					String htName=s.get(q).substring(2)+q;
					str.add("mf."+htName+".put(key,new Integer(dTemp."+s.get(q).substring(2)+"()).toString());");
				}
			}
			
			
			for(int j=0;j<f.get(i).size();j++){
				String ft = f.get(i).get(j);
				//if(ft.equals("_"))
				//	break;
				this.parseFunction(ft,i,j);
			}
			str.add("}}");
		}
	}
	
	public String parseCondition(String condition,int i,int j){
		StringBuffer result = new StringBuffer();
		int point = condition.indexOf(".");
		String s = condition.substring(point+1);
		
		//regex for aggregation function in condition
		String regex0="([a-zA-Z0-9]+)\\(([a-zA-Z0-9]+)\\)";
		
		String regex ="([a-zA-Z0-9]+)(=|<|>|<=|>=|!=)(([\'|\"][a-zA-Z0-9]+[\'|\"])|([0-9]+)|([a-zA-Z0-9]+)\\(([a-zA-Z0-9]+)\\))";
		String regex2 ="^[0-9]+$";
		
		//initial regex
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		String left = null;
		String operator = null;
		String right = null;
		if(m.matches()){
			left=m.group(1);   
			operator=m.group(2);
			right=m.group(3);
		}
		
		
		
		//if the right contains F, then change it to format : mf.max00.get(key)
		Pattern p0=Pattern.compile(regex0);
		Matcher m0=p0.matcher(right);
		if(m0.matches()){
			for(int n=0;n<f.get(0).size();n++){
				if(f.get(0).get(n).equals(right)){
					j=n;
				}
			}
			String opcode=m0.group(1).toLowerCase();
			String operand=m0.group(2).toLowerCase();
			right="mf."+opcode+i+j+".get(key)";
		}
		
		
		
		Pattern p2 = Pattern.compile(regex2);
		Matcher m2 = p2.matcher(right);
		if(operator.equals("=")){
			if(m2.matches()||m0.matches()){
				operator="==";
			}
			else{
				operator=".equals(";
				right=right+")";
			}
		}
		if(operator.equals("!=")&&!(m2.matches())&&!(m0.matches())){
			left="!(dTemp."+left;
			operator=".equals(";
			right=right+"))";
		}
		else{
			left="dTemp."+left;
		}
		right=right.replaceAll("\'","\"");
		result.append(left);
		result.append("()");
		result.append(operator);
		result.append(right);
		return result.toString();
	}
	
	public void parseFunction(String function,int i,int j){
		String regex="([a-zA-Z0-9]+)\\(([a-zA-Z0-9]+|\\*)\\)";//initial regex
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(function);
		if(m.matches()){
			String opcode = m.group(1);
			String operand = m.group(2);
			opcode=opcode.toLowerCase();
			operand=operand.toLowerCase();
			switch(opcode){
			case "sum": 
				str.add("if(mf.sum"+i+j+".containsKey(key)){");
				str.add("int current = mf.sum"+i+j+".get(key);");
				str.add("int more = dTemp."+operand+"();");
				str.add("mf.sum"+i+j+".put(key, current+more);}");
				str.add("else{");
				str.add("int more = dTemp."+operand+"();");
				str.add("mf.sum"+i+j+".put(key,more);}");
				break;
			case "avg":
				str.add("if(mf.avg"+i+j+".containsKey(key)){");
				str.add("int sum_current = mf.sum"+i+j+".get(key);");
				str.add("int sum_more = dTemp."+operand+"();");
				str.add("int sum=sum_current+sum_more;");
				str.add("mf.sum"+i+j+".put(key,sum);");
				str.add("int count=mf.count"+i+j+".get(key)+1;");
				str.add("mf.count"+i+j+".put(key,count);");
				str.add("double average = (double)sum/count;");
				str.add("mf.avg"+i+j+".put(key,average);}");
				str.add("else{");
				str.add("int sum_more = dTemp."+operand+"();");
				str.add("mf.sum"+i+j+".put(key,sum_more);");
				str.add("mf.count"+i+j+".put(key,1);");
				str.add("mf.avg"+i+j+".put(key,(double)sum_more);}");
				break;
			case "count":
				str.add("if(mf.count"+i+j+".containsKey(key)){");
				str.add("int current = mf.count"+i+j+".get(key);");
				str.add("mf.count"+i+j+".put(key,current+1);}");
				str.add("else{");
				str.add("mf.count"+i+j+".put(key,1);}");
				break;
			case "max":
				str.add("if(mf.max"+i+j+".containsKey(key)){");
				str.add("int orig = mf.max"+i+j+".get(key);");
				str.add("int now = dTemp."+operand+"();");
				str.add("if(now>orig){");
				str.add("mf.max"+i+j+".put(key,now);}}");//add a "}"
				str.add("else{");
				str.add("mf.max"+i+j+".put(key,dTemp."+operand+"());");
				str.add("}");
				break;
			case "min":
				str.add("if(mf.min"+i+j+".containsKey(key)){");
				str.add("int orig = mf.min"+i+j+".get(key);");
				str.add("int now = dTemp."+operand+"();");
				str.add("if(now<orig){");
				str.add("mf.min"+i+j+".put(key,now);}}");	//add a "}"
				break;
				default: break;
			}
		}
	}
	
	
	public ArrayList<String> getStr(){
		return str;
	}
	
}

class DoQuery {
	private ArrayList<String> str = new ArrayList<String>();
	private ArrayList<String> s;
	private ArrayList<String> v;
	private ArrayList<ArrayList<String>> f;
	private Input q;
	
	public DoQuery(Input q){
		s=q.getS();
		v=q.getV();
		f=q.getF();
		this.q=q;
		str();
	}
	
	public void str(){

		str.add("class DoQuery {");
		MfStructure mf = new MfStructure(q);
		str.addAll(mf.getStr());
		str.add("private MfStructure mf = new MfStructure();");
		str.add("public DoQuery(){");  //constructor of Class DoQuery
		str.add("GetDataBaseData g = new GetDataBaseData();");  //get all database data
		str.add("ArrayList<DataStructure> allData = g.getAllData();");
		Func fc = new Func(q);
		str.addAll(fc.getStr());
		str.add("}");
		
		//initial the print function
		str.add("public void print(){");
		str.add("for(int i=0;i<mf."+v.get(0)+".size();i++){");
		
		str.add("StringBuffer buffer = new StringBuffer();");
		for(int k=0;k<v.size();k++){
			str.add("buffer.append(mf."+v.get(k)+".get(i));");
		}
		str.add("String key = buffer.toString();");

		for(int j=0;j<s.size();j++){
			
			if(v.contains(s.get(j))){
				int index = v.indexOf(s.get(j));
				str.add("String o"+j+"=mf."+v.get(index)+".get(i);");
				continue;
			}
			else{
				int point = s.get(j).indexOf(".");
				String command0 = s.get(j).substring(0,point);
				String command = s.get(j).substring(point+1);
				int index1 = Integer.parseInt(command0);
				int index2 = f.get(index1).indexOf(command);
				String postfix=""+index1+index2;
				String opcode=command.substring(0,3);
				if(!command.contains("(")){
					postfix=""+j;
					opcode =command;
				}
				
				if(opcode.equals("cou"))
					opcode="count";
				
				
				str.add("String o"+j+"=String.valueOf(mf."+opcode+postfix+".get(key));");
			}
		}
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<s.size();i++){
			buf.append("o"+i);
			if(i+1<s.size()){
				buf.append("+\"\\t\"+");
			}
		}
		str.add("System.out.println("+buf.toString()+");");
		str.add("}}");
		
		str.add("public static void main(String[] args){");
		str.add("DoQuery d = new DoQuery();");
		str.add("d.print();}}");
	}
	
	public int find_operator(String x){
		for(int i=0;i<x.length();i++){
			if(x.charAt(i)=='='||x.charAt(i)=='>'||x.charAt(i)=='<'||x.charAt(i)=='!')
				return i;
		}
		return -1;
	}
	
	
	
	public ArrayList<String> getStr(){
		return str;
	}
	
}

class Output {
	
	public Output(Input in) throws IOException{
		DataStructure d = new DataStructure();
		GetDataBaseData g = new GetDataBaseData();
		DoQuery dq = new DoQuery(in);
		
		File file = new File("/result.txt");
		if(file.exists()){
			try{
				FileWriter fw = new FileWriter(file,false);
				BufferedWriter bw = new BufferedWriter(fw);
				filePrint(d.getStr(), bw);
				filePrint(g.getStr(), bw);
				filePrint(dq.getStr(), bw);
				bw.close();
				fw.close();
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		else{
			file.createNewFile();
			try{
				FileWriter fw = new FileWriter(file,false);
				BufferedWriter bw = new BufferedWriter(fw);
				filePrint(d.getStr(), bw);
				filePrint(g.getStr(), bw);
				filePrint(dq.getStr(), bw);
				bw.close();
				fw.close();
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void filePrint(ArrayList<String> str , BufferedWriter bw) throws IOException{
		for(int i=0;i<str.size();i++){
			bw.write(str.get(i));
			bw.newLine();
		}
	}
	
}


public class Final_Project {
	public static void main(String[] args) throws IOException{
		String s = "cust,prod,1.count(*),2.count(*)";
		int n=2;
		String v = "cust,prod";
		ArrayList<String> f = new ArrayList<String>();
		//f.add("sum(quantity)");
		//f.add("sum(quantity)");
		//f.add("avg(quantity)");
		f.add("avg(quant)");
		f.add("count(*)");
		f.add("count(*)");
		ArrayList<String> c = new ArrayList<String>();
		c.add("_");
		c.add("1.quant>avg(quant),1.year>1997");
		c.add("2.quant>avg(quant),2.year<=1997");
		//c.add("2.state='NJ'");
		//c.add("3.state='CT'");
		Input in = new Input(s,n,v,f,c);
		Output o =new Output(in);	
	}
}
