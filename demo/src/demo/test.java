package demo;

import com.alibaba.fastjson.JSON;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.UUID;

import org.nutz.json.Json;
import org.nutz.lang.Lang;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.List;
import java.lang.StringBuilder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.io.File;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.ConfigurationException;

public class test {
	public static void main(String[] args){
		int i = 0;
		int j = 1;
		System.out.println("\\.".length());
		long start1 = System.currentTimeMillis();
		int count = 10000000;
		while(count > 0){
			"RX_RAR_RAA_TRANSACTION|SUCCESS|8|0|15|107|56|RX|2001|epc.mnc000.mcc460.3gppnetwork.org;3354411601;2138;413||||2409:8809:8520:db82:0:0:0:0||ACCESS_NETWORK_INFO_REPORT|||460|0|9352|46087681|||||||||||460078135746513||8618813685600|c14-10-130-63-131-SZHSAEGW404BEr.szh.gd.node.epc.mnc000.mcc460.3gppnetwork.org;1459872049;208281384|IMS|".split("\\|",-1);
			count--;
		}
		System.out.println(System.currentTimeMillis() - start1);
		
		start1 = System.currentTimeMillis();
		count = 10000000;
		while(count > 0){
			splitString("RX_RAR_RAA_TRANSACTION|SUCCESS|8|0|15|107|56|RX|2001|epc.mnc000.mcc460.3gppnetwork.org;3354411601;2138;413||||2409:8809:8520:db82:0:0:0:0||ACCESS_NETWORK_INFO_REPORT|||460|0|9352|46087681|||||||||||460078135746513||8618813685600|c14-10-130-63-131-SZHSAEGW404BEr.szh.gd.node.epc.mnc000.mcc460.3gppnetwork.org;1459872049;208281384|IMS|","\\|");
			count--;
		}
		
		System.out.println(System.currentTimeMillis() - start1);
		
		
		start1 = System.currentTimeMillis();
		count = 10000000;
		while(count > 0){
			splitString3("RX_RAR_RAA_TRANSACTION|SUCCESS|8|0|15|107|56|RX|2001|epc.mnc000.mcc460.3gppnetwork.org;3354411601;2138;413||||2409:8809:8520:db82:0:0:0:0||ACCESS_NETWORK_INFO_REPORT|||460|0|9352|46087681|||||||||||460078135746513||8618813685600|c14-10-130-63-131-SZHSAEGW404BEr.szh.gd.node.epc.mnc000.mcc460.3gppnetwork.org;1459872049;208281384|IMS|","|");
			count--;
		}
		
		System.out.println(System.currentTimeMillis() - start1);
		
		
		start1 = System.currentTimeMillis();
		Map<Integer,String> map = new HashMap<Integer,String>(4);
		map.put(0, "event");
		map.put(1, "result");
		map.put(9, "id");
		map.put(13, "null");
		
		count = 10000000;
		while(count > 0){
			getSplitString("RX_RAR_RAA_TRANSACTION|SUCCESS|8|0|15|107|56|RX|2001|epc.mnc000.mcc460.3gppnetwork.org;3354411601;2138;413||||2409:8809:8520:db82:0:0:0:0||ACCESS_NETWORK_INFO_REPORT|||460|0|9352|46087681|||||||||||460078135746513||8618813685600|c14-10-130-63-131-SZHSAEGW404BEr.szh.gd.node.epc.mnc000.mcc460.3gppnetwork.org;1459872049;208281384|IMS|","|",map);
			count--;
		}
		
		System.out.println(System.currentTimeMillis() - start1);
		
		Map<String,String> res = getSplitString("RX_RAR_RAA_TRANSACTION|SUCCESS|8|0|15|107|56|RX|2001|epc.mnc000.mcc460.3gppnetwork.org;3354411601;2138;413||||2409:8809:8520:db82:0:0:0:0||ACCESS_NETWORK_INFO_REPORT|||460|0|9352|46087681|||||||||||460078135746513||8618813685600|c14-10-130-63-131-SZHSAEGW404BEr.szh.gd.node.epc.mnc000.mcc460.3gppnetwork.org;1459872049;208281384|IMS|","|",map);
		
		for(String key:res.keySet()){
			System.out.println("test 4 " + key + " = " + res.get(key));
		}
		
		String[] sb = splitString3("RX_RAR_RAA_TRANSACTION|SUCCESS|8|0|15|107|56|RX|2001|epc.mnc000.mcc460.3gppnetwork.org;3354411601;2138;413||||2409:8809:8520:db82:0:0:0:0||ACCESS_NETWORK_INFO_REPORT|||460|0|9352|46087681|||||||||||460078135746513||8618813685600|c14-10-130-63-131-SZHSAEGW404BEr.szh.gd.node.epc.mnc000.mcc460.3gppnetwork.org;1459872049;208281384|IMS|","|");
		for(String str:sb){
			System.out.println("test1  " + str);
		}
		
		for(String str:splitString("1.2.3.4.5.6.7.8.9.0.12","\\.")){
			System.out.println("test  " + str);
		}
		
		for(String str:"RX_RAR_RAA_TRANSACTION|SUCCESS|8|0|15|107|56|RX|2001|epc.mnc000.mcc460.3gppnetwork.org;3354411601;2138;413||||2409:8809:8520:db82:0:0:0:0||ACCESS_NETWORK_INFO_REPORT|||460|0|9352|46087681|||||||||||460078135746513||8618813685600|c14-10-130-63-131-SZHSAEGW404BEr.szh.gd.node.epc.mnc000.mcc460.3gppnetwork.org;1459872049;208281384|IMS|".split("\\|",-1)){
			System.out.println("test2  " + str);
		}
		
		File file = new File("C:\\迅雷下载\\jprofiler_windows-x64_9_2.exe.td");
		System.out.println(file.getTotalSpace());
		System.out.println(file.length());
		
		try{
			XMLConfiguration hbaseXml = new XMLConfiguration("C:\\workspace\\svn\\4G_GUBAS\\03代码\\test-main(withVolteAndUI)\\4Gubas\\src\\hbase-site.xml");
			List<Object> cfs = hbaseXml.getList("property.name");
			int index = 0;
			for(Object cf:cfs){
				if(cf.equals("hbase.zookeeper.quorum") || cf.equals("hbase.zookeeper.property.clientPort")){
					System.out.println(hbaseXml.getString("property(" + index + ").name") + " = "  + hbaseXml.getString("property(" + index + ").value"));
				}
				index++;
			}
			
			Properties prop = hbaseXml.getProperties("configuration.property");
			for(Object key:prop.keySet()){
				System.out.println(key.toString() + "=" + prop.getProperty(key.toString()));
			}
		}catch(ConfigurationException e){
			System.out.println("Could not load local hbase-site.xml.");
		}
		
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
	
	public static Map<String,String> getSplitString(String str,String delimiter,Map<Integer,String> needMap){
		Set<Integer> needCols = needMap.keySet();
		
		int len = needCols.size();
		Map<String,String> resultMap = new HashMap<String,String>(len);
		
		if(delimiter.length() == 1){
			char ch = delimiter.charAt(0);
			int off = 0;
			int next = 0;
			int index = 0;
			
			while((next = str.indexOf(ch, off)) != -1){
				if(needCols.contains(index))
					resultMap.put(needMap.get(index), str.substring(off,next));
				
				index++;
				
				if(resultMap.keySet().size() == len)
					break;
				
				off = next + 1;
				
			}
			
			if(off == 0){
				return resultMap;
			}
			
			if(off <= str.length() && resultMap.keySet().size() != len){
				if(needCols.contains(index))
					resultMap.put(needMap.get(index), str.substring(off,str.length()));
			}
			
			return resultMap;
		}
		
		int index = 0;
		
		for(String element:str.split(delimiter,-1)){
			if(needCols.contains(index))
				resultMap.put(needMap.get(index), element);
			index++;
		}
		
		return resultMap;
	}
	
	
	public static String[] splitString3(String str,String delimiter){
		if(delimiter.length() == 1){
			char ch = delimiter.charAt(0);
			int off = 0;
			int next = 0;
			ArrayList<String> list = new ArrayList<>();
			while((next = str.indexOf(ch, off)) != -1){
				list.add(str.substring(off,next));
				off = next + 1;
			}
			
			if(off == 0){
				return new String[]{str};
			}
			
			if(off <= str.length()){
				list.add(str.substring(off, str.length()));
			}
			
			String[] result = new String[list.size()];
			
			return list.subList(0, list.size()).toArray(result);
		}
		
		return str.split(delimiter,-1);
	}
	
	public static String[] splitString(String str,String delimiter){
		StringTokenizer stoken = new StringTokenizer(str,delimiter);
		String[] result = new String[stoken.countTokens()];
		int index = 0;
		
		while(stoken.hasMoreTokens()){
			result[index] = stoken.nextToken();
			index++;
		}
		
		return result;
	}
	
	public static List<String> splitString2(String str,String delimiter){
		StringTokenizer stoken = new StringTokenizer(str,delimiter);
		ArrayList<String> arr = new ArrayList<String>(stoken.countTokens());

		while(stoken.hasMoreTokens()){
			arr.add(stoken.nextToken());
		}
		
		return arr;
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
