package lynn.test;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import lynn.file.MappedByteFileReader;


public class ByteSplitTest {
	public static void main(String[] args){
		try{
			FileInputStream f = new FileInputStream("C:\\workspace\\working\\others\\test2.txt");
			FileChannel channel = f.getChannel();
			MappedByteBuffer mb = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			byte[] barr = new byte[10*1024];
			byte[] rawByte = null;
			List<String> tokens = new ArrayList<String>();
			
			byte[] delimiter = "\n".getBytes();
			boolean isRemaining = false;
			
			int nGet;
			while(mb.hasRemaining()){
				nGet = Math.min(mb.remaining(), 10*1024);
				if(isRemaining){
					barr = new byte[nGet + rawByte.length];
					System.arraycopy(rawByte, 0, barr, 0, rawByte.length);
					mb.get(barr, rawByte.length, nGet);
				}
				else{
					barr = new byte[nGet];
					mb.get(barr, 0, nGet);
				}
				int lastIndex = 0;
				
				for(int ri=0;ri < barr.length-delimiter.length+1;){
					boolean found = true;
					for(int i=0;i<delimiter.length;i++){
						if(delimiter[i] != barr[ri+i]){
							found = false;
							break;
						}
					}
					
					if(found){
						byte[] byteExtracted = new byte[ri-lastIndex];
						System.arraycopy(barr, lastIndex, byteExtracted, 0,ri-lastIndex);
						tokens.add(new String(byteExtracted));
						ri += delimiter.length;
						lastIndex = ri;
					}
					else{
						ri++;
					}
				}
				
				if(lastIndex < barr.length){
					rawByte = new byte[barr.length - lastIndex];
					System.arraycopy(barr, lastIndex, rawByte, 0,barr.length-lastIndex);
					isRemaining = true;
				}
				
//				for(byte[] br:ByteSplitter.splitByteArray(barr, "\n","UTF-8")){
//					System.out.println(new String(br));
//				}
			}
			
			for(String str:tokens){
				System.out.println(str);
			}
			
			System.out.println("size:" + tokens.size());
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
