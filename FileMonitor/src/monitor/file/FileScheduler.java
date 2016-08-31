package monitor.file;

import java.util.Timer;
import java.util.Date;
import java.util.TimerTask;

public class FileScheduler {
	private final Timer timer ;
	
	public FileScheduler(){
		this.timer = new Timer();
	}
	
	public FileScheduler(boolean isDaemon){
		this.timer = new Timer(isDaemon);
	}
	
	public void schedule(Runnable task, TimeStep timestep){
		Date time = timestep.next();
		ScheduleTimerTask timeTask = new ScheduleTimerTask(task,timestep);
		timer.schedule(timeTask, time);
	}
	
	public void reschedule(Runnable task, TimeStep timestep){
		Date time = timestep.next();
		ScheduleTimerTask timeTask = new ScheduleTimerTask(task,timestep);
		timer.schedule(timeTask, time);
	}
	
	
	public void cancel(){
		this.timer.cancel();
	}
	
	private class ScheduleTimerTask extends TimerTask{
		private Runnable task;
		private TimeStep step;
		
		public ScheduleTimerTask(Runnable task, TimeStep step){
			this.task = task;
			this.step = step;
		}
		
		@Override
		public void run(){
			this.task.run();
			reschedule(this.task,this.step);
		}
	}
}
