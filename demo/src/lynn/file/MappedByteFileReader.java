package lynn.file;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.StringBuilder;

public class MappedByteFileReader {
	private  MappedByteBuffer curMapped;
	private final int defaultSize = 1024*10;
	private final int bufferSize;
	private byte[] remainingBufer = null;
	private long remainingSize = 0;
	private int nextPos = 0;
	private int lineNum = 0;
	private int nGet;//length of read bytes
	private final FileChannel channel;
	private final boolean bigMode;
	/**
	 * Read line from MappedByteBuffer
	 * @param curMapped
	 * @param bufferSize
	 */
	public MappedByteFileReader(MappedByteBuffer curMapped,int bufferSize){
		this.curMapped = curMapped;
		this.bufferSize = bufferSize <= 0? defaultSize:bufferSize;
		this.nGet = this.bufferSize > this.curMapped.remaining()? this.curMapped.remaining():this.bufferSize;
		this.remainingBufer = new byte[this.nGet];
		this.curMapped.get(this.remainingBufer, 0, this.nGet);
		this.nextPos = 0;
		this.channel = null;
		this.bigMode = false;
	}
	
	public MappedByteFileReader(FileChannel channel,int bufferSize) throws IOException{
		this.channel = channel;
		this.bigMode = channel.size() > Integer.MAX_VALUE;
		this.bufferSize = bufferSize <= 0? defaultSize:bufferSize;
		this.nextPos = 0;
		this.curMapped = this.channel.map(FileChannel.MapMode.READ_ONLY, 0, Math.min(channel.size(), Integer.MAX_VALUE));
		this.remainingSize = channel.size() - this.curMapped.remaining();
		this.nGet =  this.bufferSize > this.curMapped.remaining()? this.curMapped.remaining():this.bufferSize;
		this.remainingBufer = new byte[this.nGet];
		this.curMapped.get(this.remainingBufer, 0, this.nGet);
	}
	
	public String readLineFromChannel() throws IOException{		
		int pos = this.nextPos;
		StringBuilder sb = new StringBuilder();
		
		for(;;){
			if(this.remainingSize <= 0 && !this.curMapped.hasRemaining() && pos == this.remainingBufer.length){
				break;
			}
			
			if(!this.curMapped.hasRemaining() && this.remainingSize > 0){
				this.curMapped = this.channel.map(FileChannel.MapMode.READ_ONLY, channel.position(), Math.min(this.remainingSize, Integer.MAX_VALUE));
				this.remainingSize = this.remainingSize - this.curMapped.remaining();
				this.nextPos = 0;
				pos = 0;
			}
			
			if(pos >= this.remainingBufer.length - 1 && this.curMapped.hasRemaining()){
				nGet = curMapped.remaining() > defaultSize ? defaultSize:curMapped.remaining();
				int remaining = pos - this.nextPos;
				if(remaining > 0){
					sb.append(new String(this.remainingBufer,this.nextPos,pos));
				}
				
				this.remainingBufer = new byte[nGet];
				this.curMapped.get(remainingBufer,0, nGet);
				
				pos = 0;
				this.nextPos = 0;
			}
			
			if(this.remainingBufer[pos] == '\n'){
				sb.append(new String(this.remainingBufer,this.nextPos,pos - this.nextPos));
				this.nextPos = pos + 1;
				this.lineNum++;
				return sb.toString();
			}
			
			pos++;
		}
		
		this.lineNum++;
		return sb.toString();
	}
	
	public String readLine() throws IOException{
		if(this.bigMode){
			return readLineFromChannel();
		}
		
		StringBuilder sb = new StringBuilder();
		int pos = this.nextPos;
		
		for(;;){
			if(!this.curMapped.hasRemaining() && pos == this.remainingBufer.length){
				this.nextPos = 0;
				break;
			}
			
			if(pos >= remainingBufer.length - 1  && curMapped.hasRemaining()){
				//remaining=1, missing one byte
				//remaining=remainingBufer.length -1 and !curMapped.hasRemaining(), this.remainingBufer[pos] will have java.lang.ArrayIndexOutOfBoundsException
				nGet = curMapped.remaining() > defaultSize ? defaultSize:curMapped.remaining();
				int remaining = pos - this.nextPos;
				if(remaining > 0){
					sb.append(new String(this.remainingBufer,this.nextPos,pos - this.nextPos));
				}
				
				this.remainingBufer = new byte[nGet];
				this.curMapped.get(remainingBufer,0, nGet);
				
				pos = 0;
				this.nextPos = 0;
			}
			
			if(this.remainingBufer[pos] == '\n'){
				this.lineNum++;
				sb.append(new String(this.remainingBufer,this.nextPos,pos - this.nextPos));
				this.nextPos = pos + 1;
				return sb.toString();
			}
			
			pos++;
		}
		
		if(sb.length() == 0)
			return null;
		
		return sb.toString();
	}
	
	/**
	 * notes: 
	 * too slow, and deprecated later
	 * @return
	 * @throws IOException
	 */
	public String readLine2() throws IOException{
		//read finished
		if(!this.curMapped.hasRemaining() && this.nextPos == 0)
			return null;
		
		StringBuilder sb = new StringBuilder();
		
		int pos = 0;
		//deprecated: indexOfLineFeed call and walk aroud byte array take more time
		while((pos = this.indexOfLineFeed(this.remainingBufer, this.nextPos)) == -1 && curMapped.hasRemaining()){
			sb.append(new String(this.remainingBufer,this.nextPos,this.remainingBufer.length - this.nextPos));
			this.nGet = this.bufferSize > this.curMapped.remaining()? this.curMapped.remaining():this.bufferSize;
			this.remainingBufer = new byte[this.nGet];
			this.curMapped.get(this.remainingBufer, 0, this.nGet);
			this.nextPos = 0;
			//this.remainingSize = this.curMapped.remaining();
		}
		
		if(pos == -1){
			sb.append(new String(this.remainingBufer,this.nextPos,this.remainingBufer.length - this.nextPos));
			this.nextPos = 0;
		}
		else{
			sb.append(new String(this.remainingBufer,this.nextPos,pos - this.nextPos));
			this.nextPos = pos + 1;
		}
		
		return sb.toString();
	}
	
	/**
	 * notes:
	 * too slow, and deprecated later
	 * @param rawByte  source byte array
	 * @param start  start index
	 * @return  first index of line feed 
	 */
	public int indexOfLineFeed(byte[] rawByte,int start){
		for(int i=start;i<rawByte.length;i++){
			if(rawByte[i] == '\n')
				return i;
		}
		
		
		return -1;
	}
	
//	public int getLineNubmer(){
//		return this.readNum;
//	}
//	
}
