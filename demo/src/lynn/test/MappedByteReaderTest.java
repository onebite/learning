package lynn.test;

import lynn.file.MappedByteFileReader;
import lynn.common.Fastu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.Charset;
import java.io.File;

import junit.framework.TestCase;
import org.junit.runner.RunWith;

public class MappedByteReaderTest extends TestCase {
	public void test_ByteReader() throws Exception{
		File fs = new File("C:\\workspace\\working\\others\\test");
		if(fs.isDirectory()){
			for(File f:fs.listFiles()){
				ByteReader(f);
			}
		}
	}
	
	public void test_ByteReader2() throws Exception{
		File fs = new File("C:\\workspace\\working\\others\\test");
		if(fs.isDirectory()){
			for(File f:fs.listFiles()){
				ByteReader2(f);
			}
		}
	}
	
	public void test_ReadLine()throws Exception{
		File fs = new File("C:\\workspace\\working\\others\\test");
		if(fs.isDirectory()){
			for(File f:fs.listFiles()){
				ReadLine(f);
			}
		}
	}
	
	public void ByteReader(File file) throws Exception{
		FileInputStream f = new FileInputStream(file);
		FileChannel channel = f.getChannel();
		MappedByteFileReader mReader = new MappedByteFileReader(channel,8192);
//		MappedByteBuffer mb = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
//		MappedByteFileReader mReader = new MappedByteFileReader(mb,1*1024);
		String line = null;
		int i=0;
		while((line = mReader.readLine()) != null){
			//System.out.println(line);
			i++;
		}
		System.out.println("ByteReader length:" + i);
		f.close();
	}
	
	public void ReadLine(File file) throws Exception{
		FileInputStream in = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		int i=0;
		while((line = reader.readLine()) != null){
			//System.out.println(line);
			i++;
		}
		System.out.println("ReadLine length:" + i);
		reader.close();
		in.close();
	}
	//需要处理channel.size() 大于 Integer.MAX_VALUE 
	public static void ByteReader2(File file) throws Exception{
		//StringBuilder sb = new StringBuilder();
		FileInputStream f = new FileInputStream(file);
		FileChannel channel = f.getChannel();
		MappedByteBuffer mb = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
		
		//System.out.println("File size: " + channel.size());
		int pos = 0;
		int lastIndex = 0;
		int nGet = mb.remaining() > 8192 ? 8192:mb.remaining();
		byte[] buffer = new byte[nGet];
		mb.get(buffer, 0, nGet);
		int found = 0;
		
		for(;;){
			if(!mb.hasRemaining() && pos == buffer.length){
				break;
			}
			
			if(pos >= buffer.length && mb.hasRemaining()){
				//remaining=1, missing one byte ?
				//java.lang.ArrayIndexOutOfBoundsException ?
				nGet = mb.remaining() > 8192 ? 8192:mb.remaining();
				int remaining = pos - lastIndex;
				if(remaining > 0){
					byte[] temp = new byte[remaining];
					System.arraycopy(buffer, lastIndex, temp, 0, remaining);
					buffer = new byte[nGet + remaining];
					System.arraycopy(temp, 0, buffer, 0, remaining);
					mb.get(buffer,remaining, nGet);
				}
				else{
					buffer = new byte[nGet];
					mb.get(buffer,0, nGet);
				}
				
				pos = 0;
				lastIndex = 0;
			}
			
			if(buffer[pos] == '\n'){
				//System.out.println(new String(buffer,lastIndex,pos - lastIndex));
				//System.out.println(Fastu.decode(buffer,lastIndex,pos - lastIndex));
				found++;
				//sb.append(new String(buffer,lastIndex,pos - lastIndex));
				lastIndex = pos + 1;
				//pos++;
			}
			
			pos++;
		}
		f.close();
		System.out.println("ByteReader2 rows:" + found);
	}
}
