package demo;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class ClosableLinkedBlockingDeque<T> extends LinkedBlockingDeque<T> {
	private static final long serialVersionUID = 1L;
	//用于控制写入线程数，写入线程开始+1 ,写入线程结束-1 ，=0表示没有写入线程
	private AtomicInteger writeNum = new AtomicInteger(0);
	private boolean finished = false;
	
	public ClosableLinkedBlockingDeque(int capacity){
		super(capacity);
	}
	public int incrementAdderNumber(){
		return this.writeNum.incrementAndGet();
	}
	
	public int decrementAdderNumber(){
		return this.writeNum.decrementAndGet();
	}
	
/*	public void put(T t) throws InterruptedException{
		super.put(t);
	}*/
	
	public int getAdderNumber(){
		return this.writeNum.get();
	}
	
	public void finish(){
		this.finished = true;
	}
	
	public T take() throws InterruptedException{
		T el;
		while((el = super.poll()) == null && (this.writeNum.get() != 0 || !finished)){
			synchronized(this){
				super.wait(100);
			}
		}
		
		return el;
	}
}
