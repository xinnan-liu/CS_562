package Project04132014;

import java.util.ArrayList;
import java.util.StringTokenizer;

//initial for the input
//For A project will be fine

public class Query {
	private ArrayList<String> s;
	private int n;
	private ArrayList<String> v;
	private ArrayList<ArrayList<String>> f;
	private ArrayList<ArrayList<String>> c;
	
	public Query(String s, int n, String v, ArrayList<String> f, ArrayList<String> c){
		this.s = new ArrayList<String>();
		this.v = new ArrayList<String>();
		this.f = new ArrayList<ArrayList<String>>();
		this.c = new ArrayList<ArrayList<String>>();
		
		StringTokenizer st;
		st= new StringTokenizer(s," ,");
		while(st.hasMoreTokens()){
			String tokens = st.nextToken();
			this.s.add(tokens);
		}
		this.n=n;
		st = new StringTokenizer(v," ,");
		while(st.hasMoreTokens()){
			String tokens = st.nextToken();
			this.v.add(tokens);
		}
		for(int i=0;i<f.size();i++){
			String f_temp = f.get(i);
			ArrayList<String> f_each= new ArrayList<String>();
			st = new StringTokenizer(f_temp," ,");
			while(st.hasMoreTokens()){
				String tokens=st.nextToken();
				f_each.add(tokens);
			}
			this.f.add(f_each);
		}
		for(int i=0;i<c.size();i++){
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
	
	public void print(){
		System.out.println("s:"+"\t"+s);
		System.out.println("n:"+"\t"+n);
		System.out.println("v:"+"\t"+v);
		System.out.print("f:"+"\t");
		for(int i=0;i<f.size();i++){
			System.out.print(f.get(i));
		}
		System.out.println();
		System.out.println("c:"+"\t"+c);
	}
	
	
	public static void main(String[] args){
		String s = "customer,product,sum(quan)";
		int n=2;
		String v = "customer,product";
		ArrayList<String> f = new ArrayList<String>();
		f.add("_");
		f.add("avg(quan),count(customer)");
		f.add("sum(quan)");
		ArrayList<String> c = new ArrayList<String>();
		c.add("1.state='NY'");
		c.add("2.state='NJ'");
		Query q = new Query(s,n,v,f,c);
		q.print();
	}
}
