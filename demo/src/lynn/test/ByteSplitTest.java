package lynn.test;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import lynn.file.ByteSplitter;

public class ByteSplitTest {
	public static void main(String[] args){
		try{
			FileInputStream f = new FileInputStream("C:\\workspace\\source\\demo\\test.properties");
			FileChannel channel = f.getChannel();
			MappedByteBuffer mb = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			byte[] barr = new byte[100];
			int nGet;
			while(mb.hasRemaining()){
				nGet = Math.min(mb.remaining(), barr.length);
				mb.get(barr, 0, nGet);
				for(byte[] br:ByteSplitter.splitByteArray(barr, "\n","UTF-8")){
					System.out.println(new String(br));
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public byte[] getBytesByDelimiter(byte[] rawByte,int start,){
		for(int i= position;i<rawByte.)
	}
}
