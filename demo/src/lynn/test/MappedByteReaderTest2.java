package lynn.test;

import lynn.file.MappedByteFileReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.io.File;


public class MappedByteReaderTest2 {
	public static void main(String[] args) throws Exception{
		if(args.length < 2){
			System.out.println("param error");
			System.exit(-1);
		}
		
		File path = new File(args[0]);
		int branch = Integer.parseInt(args[1]);
		long  start = System.nanoTime();
		switch(branch){
		case 1:
			if(path.isDirectory()){
				for(File f:path.listFiles()){
					ByteReader(f);
				}
			}
			
			System.out.println("ByteReader time:" + (System.nanoTime() - start));
			break;
		case 2:
			start = System.nanoTime();
			if(path.isDirectory()){
				for(File f:path.listFiles()){
					ReadLine(f);
				}
			}
			
			System.out.println("ByteReader time:" + (System.nanoTime() - start));
			break;
		default:
			start = System.nanoTime();
			if(path.isDirectory()){
				for(File f:path.listFiles()){
					ByteReader2(f);
				}
			}
			
			System.out.println("ByteReader time:" + (System.nanoTime() - start));
		}
	}
	
	public static void test_ByteReader() throws Exception{
		File fs = new File("C:\\workspace\\working\\others\\test");
		if(fs.isDirectory()){
			for(File f:fs.listFiles()){
				ByteReader(f);
			}
		}
	}
	
	public static void test_ReadLine()throws Exception{
		File fs = new File("C:\\workspace\\working\\others\\test");
		if(fs.isDirectory()){
			for(File f:fs.listFiles()){
				ReadLine(f);
			}
		}
	}
	
	public static void ByteReader2(File file) throws Exception{
		//StringBuilder sb = new StringBuilder();
		FileInputStream f = new FileInputStream(file);
		FileChannel channel = f.getChannel();
		MappedByteBuffer mb = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
		
		int pos = 0;
		int lastIndex = 0;
		int nGet = mb.remaining() > 10240 ? 10240:mb.remaining();
		byte[] buffer = new byte[nGet];
		mb.get(buffer, 0, nGet);
		int found = 0;
		
		for(;;){
			if(!mb.hasRemaining() && pos == buffer.length){
				break;
			}
			
			if(buffer[pos] == '\n'){
				//System.out.println(new String(buffer,lastIndex,pos - lastIndex));
				found++;
				//sb.append(new String(buffer,lastIndex,pos - lastIndex));
				lastIndex = pos + 1;
				//pos++;
			}
			if(pos >= buffer.length - 1 && mb.hasRemaining()){
				nGet = mb.remaining() > 10240 ? 10240:mb.remaining();
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
			
			pos++;
		}
		f.close();
		System.out.println("ByteReader2 rows:" + found);
	}
	
	public static void ByteReader(File file) throws Exception{
		FileInputStream f = new FileInputStream(file);
		FileChannel channel = f.getChannel();
		MappedByteBuffer mb = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
		MappedByteFileReader mReader = new MappedByteFileReader(mb,1*1024);
		String line = null;
		int i=0;
		while((line = mReader.readLine()) != null){
			//System.out.println(line);
			i++;
		}
		System.out.println("ByteReader length:" + i);
		f.close();
	}
	
	public static void ReadLine(File file) throws Exception{
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
	
}
