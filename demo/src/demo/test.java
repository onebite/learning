package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import com.google.gson.Gson;

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
		StringBuilder sb1 = new StringBuilder();
		sb1.append("|");
		sb1.append("");
		sb1.append("|");
		System.out.println(sb1.length() == 2);
		//Gson gson = new Gson();
		AccessInfosArr access = JSON.parseObject("{'REQUIRED_ACCESS_INFO_ARRAY':[{'REQUIRED_ACCESS_INFO':'USER_LOCATION'},{'REQUIRED_ACCESS_INFO_ARRAY':{'REQUIRED_ACCESS_INFO':'USER_LOCATION2'}}]}",AccessInfosArr.class);
		for(AccessInfo ai:access.getRequired_access_info_array()){
			System.out.println("AccessInfosArr id=" + ai.getRequired_access_info());
		}
		
		MediaComponentArr medias = JSON.parseObject("{'MEDIA_COMPONENT':[{'AF_APPLICATION_ID':'12345','MAX_REQUESTED_BANDWIDTH_UL':'49000','MAX_REQUESTED_BANDWIDTH_DL':'49000','SERVICE_ID_ARRAY':[{'SERVICE_ID':'VoLTE_Voice'}],'FLOW_STATUS':'DISABLED','MEDIA_TYPE':'AUDIO','MEDIA_COMPONENT_NUMBER':'1','RESULT_OF_DYNAMIC_SERVICE_CLASSIFICATION':'TRUE','MEDIA_SUBCOMPONENT':[{'FLOW_NUMBER':'1','FLOW_DESCRIPTION_ARRAY':[{'FLOW_DESCRIPTION':'permit in 17 from 2409:8809:8521:54e3:d175:98:64f1:159e 31036 to 0:0:0:0:0:0:0:0/0 65535'},{'FLOW_DESCRIPTION':'permit out 17 from 0:0:0:0:0:0:0:0/0 65535 to 2409:8809:8521:54e3:d175:98:64f1:159e 31036'}],'MAX_REQUESTED_BANDWIDTH_UL':'null','MAX_REQUESTED_BANDWIDTH_DL':'null','FLOW_STATUS':'null'},{'FLOW_NUMBER':'2','FLOW_DESCRIPTION_ARRAY':[{'FLOW_DESCRIPTION':'permit in 17 from 2409:8809:8521:54e3:d175:98:64f1:159e 31037 to 0:0:0:0:0:0:0:0/0 65535'},{'FLOW_DESCRIPTION':'permit out 17 from 0:0:0:0:0:0:0:0/0 65535 to 2409:8809:8521:54e3:d175:98:64f1:159e 31037'}],'MAX_REQUESTED_BANDWIDTH_UL':'null','MAX_REQUESTED_BANDWIDTH_DL':'null','FLOW_STATUS':'null'}]}]}",MediaComponentArr.class);
		//RULE_INFORMATION rule = gson.fromJson("{'RULE_ID':'4294967291','NTP_SECONDS':'test','NTP_SECONDS':'test','AUTHORIZATION_STATE':'test','FAILURE_CODE':'1','ONE_TIME_REDIRECT':'test','QOS':{'BEARER_QCI':'test','ARP_PRIORITY_LEVEL':'test','ARP_PCI':'test','ARP_PVI':'test','BEARER_MBR_UL':'test','BEARER_MBR_DL':'test','BEARER_GBR_UL':'test','BEARER_GBR_DL':'test','APN_AMBR_UL':'test','APN_AMBR_DL':'test'},'CHARGING_INFO':{'RATING_GROUP':'test','SERVICE_IDENTIFIER':'test','REPORTING_LEVEL':'test','ONLINE':'test','OFFLINE':'test','METERING_METHOD':'test'}", RULE_INFORMATION.class);
		
		for(MediaComponent media:medias.getMedia_component()){
			System.out.println("MediaComponent id=" + media.getAf_application_id());
			System.out.println("MediaComponent status=" + media.getFlow_status());
			System.out.println("MediaComponent type=" + media.getMedia_type());
		}
		System.out.println("12345678".substring(0, 8));
			
		Map<Integer,String> map = new HashMap<Integer,String>(4);
		map.put(0, "event");
		map.put(1, "result");
		map.put(9, "id");
		map.put(13, "null");
		
		Map<String,String> res = getSplitString("RX_RAR_RAA_TRANSACTION||SUCCESS|8|0|15|107|56|RX|2001|epc.mnc000.mcc460.3gppnetwork.org;3354411601;2138;413||||2409:8809:8520:db82:0:0:0:0||ACCESS_NETWORK_INFO_REPORT|||460|0|9352|46087681|||||||||||460078135746513||8618813685600|c14-10-130-63-131-SZHSAEGW404BEr.szh.gd.node.epc.mnc000.mcc460.3gppnetwork.org;1459872049;208281384|IMS|","|",map);
		
		for(String key:res.keySet()){
			if(res.get(key) == null){
				System.out.println("test null " + key + " = " + res.get(key));
			}
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
	
	public static class Information{
		private RULE_INFORMATION rule_information;

		public RULE_INFORMATION getRule_information() {
			return rule_information;
		}

		public void setRule_information(RULE_INFORMATION rule_information) {
			this.rule_information = rule_information;
		}
		
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
