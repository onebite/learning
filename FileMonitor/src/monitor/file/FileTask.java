package monitor.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTask extends FileMonitorTask {
	static Logger log = LoggerFactory.getLogger(FileTask.class);
	
	public FileTask(String directory){
		super(directory);
	}
	
	public void onDelete(Object filename){
		log.info(filename + "  删除");
	}
	
	public void onAdd(Object filename){
		log.info(filename + "  新增");
	}
	
	public void loadsuccess(Object filename){
		log.info(filename + "  加载成功");
	}
	
	public void onUpdate(Object filename){
		log.info(filename + "  更新");
	}
	
	
	public void isloading(Object filename){
		log.info(filename + "  加载中");
	}
	
	public void initialFileInfo(Object filename){
		log.info(filename + "  初始化成功");
	}


}
