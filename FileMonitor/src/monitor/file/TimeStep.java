package monitor.file;

import java.util.Calendar;
import java.util.Date;

public class TimeStep {
	private Calendar calendar = Calendar.getInstance();
	private int field = Calendar.SECOND;
	private int amount = 10;
	
	public int getField(){
		return this.field;
	}
	
	public void setField(int field){
		this.field = field;
	}
	
	public int getamount(){
		return this.amount;
	}
	
	public void setamount(int amount){
		this.amount = amount;
	}
	
	public Date next(){
		calendar.add(field, amount);
		return calendar.getTime();
	}
}
