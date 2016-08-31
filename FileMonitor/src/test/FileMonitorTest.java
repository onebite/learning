package test;

import monitor.file.*;

public class FileMonitorTest {
	public static void main(String args[]){
		FileTask filetask = new FileTask("C:\\workspace\\working\\大数据\\stream2hive");
		FileScheduler scheduler = new FileScheduler();
		scheduler.schedule(filetask, new TimeStep());
	}
}