package demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.StringBuffer;

public class LoadSample {
	 public String getNameGroup(String filename,String regex,int index){
	    	Pattern p = Pattern.compile(regex);
	    	Matcher m = p.matcher(filename);
	    	String ret = null;
	    	
	    	if(m.matches()){
	    		ret = m.group(index);
	    	}
	    	
	    	return ret;
	    }
	 
	 public static String reverse1(String str){
		 if(null == str || str.length() <= 1)
			 return str;
		 
		 char[] ch = str.toCharArray();
		 int len = ch.length - 1;
		 
		 for(int i=0;i < len;i++,len--){
			 ch[i] ^= ch[len];
			 ch[len] ^= ch[i];
			 ch[i] ^= ch[len];
		 }
		 
		 return new String(ch);
	 }
	 
	 
	 public static String reverse2(String str){
		 if(null == str || str.length() <= 1)
			 return str;
		 
		 int len = str.length();
		 StringBuffer sb = new StringBuffer(len);
		 for(int i=len-1;i >= 0; i--){
			 sb.append(str.charAt(i));
		 }
		 
		 return sb.toString();
	 }
}
