package demo;

import com.alibaba.fastjson.JSON;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.UUID;

import org.nutz.json.Json;
import org.nutz.lang.Lang;
import java.util.Map;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

public class test {
	public static void main(String[] args){
		System.out.println(Timestamp.valueOf("2016-10-20 0:0:0").getTime());
		String date = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date());
		System.out.println(date);
		System.out.println(Timestamp.valueOf(date).getTime());
		System.out.println("/opt/data/SAPC/_BKF/201610191205/A20161019.1205-0800-20161019.1209-0800_ebs".split("\\|"));
		
		Path p = Paths.get("C:\\workspace\\source\\demo");
		System.out.println(p.toFile().getAbsolutePath());
		
		User user = new User();
		user.setId(123456l);
		user.setName("凯勒");
		List<User> objs = JSON.parseArray("[{\"id\":123456,\"name\":\"凯勒\"},{\"id\":45678,\"name\":\"高圆圆\"}]", User.class);
		for(User obj:objs){
			System.out.println(obj.name);
		}
		
		//System.out.println(JSON.toJSONString(user));
		String template = "0123456789";
		String[] strs = "NO_VALUE,,,0,,,,,".split(",",-1);
		for(String str:strs){
			if(str.equals(""))
				System.out.println("test");
		}
		
		System.out.println(("00000000"+"1222222222222222").substring("1222222222222222".length()));
		
		System.out.println(new SimpleDateFormat("yyMMdd").format(new Date()));
		ArrayList<String> buffer = new ArrayList<String>(100);
		
		System.out.println(buffer.isEmpty());
		
		System.out.println(String.join(",", ",,,,,,,,,,,,,,123,,,,,,,,,,".split(",",Integer.MAX_VALUE)));
		
		SectionPropertiesMap sprop = new SectionPropertiesMap();
		 //Properties sprop = new Properties();
		 try{
			 sprop.load(new FileInputStream("C:\\workspace\\source\\demo\\test.properties"));
		 }catch(IOException e){
			 e.printStackTrace();
		 }
		 
		 //toFolder = C:\Users\X230\Desktop\0725\to sfs
		 
		 for(String section:sprop.getSections()){
			 for(Object key:sprop.get(section).keySet()){
				 System.out.println(section + "." + key.toString() + " = " + sprop.get(section).getProperty(key.toString()));
			 }
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
	
	public static class User {
		private Long id;
		private String name;
		public User(){
			
		}
		public Long getId() { return id; }
		public void setId(Long id) { this.id = id; }

		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
	}
	
}
