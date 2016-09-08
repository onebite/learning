package demo;

import java.util.regex.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;

import org.nutz.json.Json;
import org.nutz.lang.Lang;
import java.util.Map;
import java.io.FileInputStream;
import java.io.IOException;

public class test {
	public static void main(String[] args){
		 SectionProperties sprop = new SectionProperties();
		 try{
			 sprop.load(new FileInputStream("C:\\workspace\\source\\demo\\test.properties"));
		 }catch(IOException e){
			 e.printStackTrace();
		 }
		 
		 for(Object key:sprop.keySet()){
			 System.out.println(key.toString());
		 }
		 
		 String s = Json.toJson("{eventCode:'17',eventName:'Session_Resume',SESSION_RESUME:'SGW'}");
		 
		 System.out.println(s);
		 
		 System.out.println(LoadSample.reverse2("[abcdef]"));
		 
		 System.out.println("end1");
		 
		 String line = "[123456]";
		 int pos = 0;
		 System.out.println(line.indexOf('['));
		 System.out.println(line.charAt(pos++));
		 
		 LoadSample ls = new LoadSample();
		 
		 String filename = "A20151208.1703+0800-20151208.1704+0800_0.SZHSAEGW303BEr.BEARER_CREATION.csv.gz";
		 
		System.out.println(filename.substring(10, 12));
		String g = ls.getNameGroup("A20151208.1703+0800-20151208.1704+0800_0.SZHSAEGW303BEr.BEARER_CREATION.csv.gz", "^.*?\\..*?\\..*?\\.[A-Za-z]*(\\d+)[A-Za-z]*\\..*", 1);
		// String g = ls.getNameGroup("sjflsjfl.l_attach.csv.gz", "(\\w+)\\.(\\w+)\\.csv\\.gz$", 1);
		System.out.println(g);
	}
}
