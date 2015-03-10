package Main_Project;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Func {
	
	private ArrayList<String> str = new ArrayList<String>();
	private int n;
	private ArrayList<String> v;
	private ArrayList<ArrayList<String>> f;
	private ArrayList<ArrayList<String>> c;
	
	public Func(Query q){
		this.n = q.getN();
		this.v=q.getV();
		this.f = q.getF();
		this.c = q.getC();
	}
	
	public void str(){
		str.add("ArrayList<String> has_ga = new ArrayList<String>();");
		str.add("for(int i=0;i<allData.size();i++){");
		str.add("StringBuffer ga_temp=new StringBuffer();");
		str.add("DataStructure dTemp = allData.get(i);");
		
		
		
		for(int i=0;i<v.size();i++){
			str.add("String gA"+i+"=dTemp."+v.get(i)+"();");
			str.add("ga_temp.append(gA"+i+");");
		}
		str.add("String ga = ga_temp.toString();");
		str.add("if(has_ga.contains(ga)){;}");
		str.add("else{");
		for(int i=0;i<v.size();i++){
			str.add("mf."+v.get(i)+".add(gA"+i+");");
		}
		str.add("has_ga.add(ga);");
		str.add("}}");
		

		for(int i=0;i<f.get(0).size();i++){
			if(f.get(0).get(0).equals("_"))
				break;
			else{
				str.add("for(int i=0; i<allData.size();i++){");
				str.add("DataStructure dTemp = allData.get(i);");
				str.add("StringBuffer buffer = new StringBuffer();");
				for(int j=0;j<v.size();j++){
					str.add("buffer.append(dTemp."+v.get(j)+"());");
				}
				str.add("String key = buffer.toString();");
				for(int j=0;j<f.get(i).size();j++){
					String ft = f.get(i).get(j);
					//if(ft.equals("_"))
					//	break;
					this.parseFunction(ft,i,j);
				}
				str.add("}");
			}
		}
		for(int i=1;i<=n;i++){
			str.add("for(int i=0; i<allData.size();i++){");
			str.add("DataStructure dTemp = allData.get(i);");
			str.add("StringBuffer buffer = new StringBuffer();");
			StringBuffer condition = new StringBuffer();
			for(int j=0;j<c.get(i).size();j++){
				String s2 = c.get(i).get(j);
				String result = this.parseCondition(s2);
				condition.append(result);
				if(j+1<c.get(i).size())
					condition.append("&&");
			}
			str.add("if("+condition.toString()+"){");
			for(int j=0;j<v.size();j++){
				str.add("buffer.append(dTemp."+v.get(j)+"());");
			}
			str.add("String key = buffer.toString();");
			for(int j=0;j<f.get(i).size();j++){
				String ft = f.get(i).get(j);
				//if(ft.equals("_"))
				//	break;
				this.parseFunction(ft,i,j);
			}
			str.add("}}");
		}
	}
	
	public String parseCondition(String condition){
		StringBuffer result = new StringBuffer();
		int point = condition.indexOf(".");
		String s = condition.substring(point+1);
		String regex ="([a-zA-Z0-9]+)(=|<|>|<=|>=|!=)(([\'|\"][a-zA-Z0-9]+[\'|\"])|([0-9]+))";
		String regex2 ="^[0-9]+$";
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
		Pattern p2 = Pattern.compile(regex2);
		Matcher m2 = p2.matcher(right);
		if(operator.equals("=")){
			if(m2.matches()){
				operator="==";
			}
			else{
				operator=".equals(";
				right=right+")";
			}
		}
		if(operator.equals("!=")&&!(m2.matches())){
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
		String regex="([a-zA-z0-9]+)\\(([a-zA-z0-9]+)\\)";//question
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
				str.add("mf.max"+i+j+".put(key,now);}");
				break;
			case "min":
				str.add("if(mf.min"+i+j+".containsKey(key)){");
				str.add("int orig = mf.min"+i+j+".get(key);");
				str.add("int now = dTemp."+operand+"();");
				str.add("if(now<orig){");
				str.add("mf.min"+i+j+".put(key,now);}");
				break;
				default: break;
			}
		}
	}
	
	
	public ArrayList<String> getStr(){
		return str;
	}
	

}
