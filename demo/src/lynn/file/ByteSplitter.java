package lynn.file;

import java.util.List;
import java.util.ArrayList;
import java.io.UnsupportedEncodingException;
/**
 * KMP
 * ISO-8859-1
 * @author lynn
 *
 */
public class ByteSplitter {
	
	public static List<byte[]> splitByteArray(byte[] rawByte,byte[] delimiterArr){
		List<byte[]> tokens = new ArrayList<byte[]>();
		if(delimiterArr.length == 0)
			return tokens;
		
		int lastIndex = 0;
		for(int ri=0;ri < rawByte.length - delimiterArr.length + 1;){
			boolean found = true;
			for(int i=0;i<delimiterArr.length;i++){
				if(delimiterArr[i] != rawByte[ri+i]){
					found = false;
					break;
				}
			}
			
			if(found){
				byte[] byteExtracted = new byte[ri - lastIndex];
				System.arraycopy(rawByte, lastIndex, byteExtracted, 0,ri-lastIndex);
				ri += delimiterArr.length;
				lastIndex = ri;
				tokens.add(byteExtracted);
			}
			else{
				ri++;
			}
		}
		
		return tokens;
	}
	
	public static List<byte[]> splitByteArray(byte[] rawByte,String delimiter,String encoding) throws UnsupportedEncodingException{
		List<byte[]> tokens = new ArrayList<byte[]>();
		if(delimiter==null || encoding == null)
			return tokens;
		
		byte[] pattern = delimiter.getBytes(encoding);
		return splitByteArray(rawByte,pattern);
	}
	
}
