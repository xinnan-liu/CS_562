package Main_Project;

import java.util.ArrayList;


class DoQuery {
	private ArrayList<String> str = new ArrayList<String>();
	private ArrayList<String> s;
	private ArrayList<String> v;
	private ArrayList<ArrayList<String>> f;
	private Query q;
	
	public DoQuery(Query q){
		s=q.getS();
		v=q.getV();
		f=q.getF();
		this.q=q;
	}
	
	public void str(){

		str.add("public class DoQuery {");
		MfStructure mf = new MfStructure(q);
		mf.str();
		str.addAll(mf.getStr());
		str.add("private MfStructure mf = new MfStructure();");
		str.add("public DoQuery(){");
		str.add("GetDataBaseData g = new GetDataBaseData();");
		str.add("ArrayList<DataStructure> allData = g.getAllData();");
		Func fc = new Func(q);
		fc.str();
		str.addAll(fc.getStr());
		str.add("}");
		
		
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
				String opcode = command.substring(0,3);
				if(opcode.equals("cou"))
					opcode="count";
				
				
				str.add("String o"+j+"=String.valueOf(mf."+opcode+index1+index2+".get(key));");
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
