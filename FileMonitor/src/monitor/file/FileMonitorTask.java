package monitor.file;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.io.File;

public abstract class FileMonitorTask implements FileMonitor{
	private boolean firstRun = true;
	
	private String directory = "";
	/**initial files list <filname,lastmodifiedtime> **/
	private HashMap<String,Long> currentFiles = new HashMap<String,Long>();
	//currents file infos
	private Set<String> newfiles = new HashSet<String>();
	//用于过滤文件
	
	public FileMonitorTask(){
		
	}
	
	public FileMonitorTask(String directory){
		this.directory = directory;
	}
	
	public void run(){
		File file = new File(this.directory);
		if(firstRun){
			firstRun = false;
			this.loadFileInfo(file);
			this.initialFileInfo(file.getAbsolutePath());
		}else{
			//检查文件更新状态[add,update]
			checkFileUpdate(file);
			//检查文件删除
			this.checkRemoveFiles(file);
			this.newfiles.clear();
		}
	}
	
	/**
	 * 初始化文件信息
	 * @param file
	 */
	
	private void loadFileInfo(File file){
		if(file.isFile()){
			this.currentFiles.put(file.getAbsolutePath(), file.lastModified());
			return;
		}
		
		File[]  files = file.listFiles();
		for(File f : files){
			loadFileInfo(f);
		}
	}
	
	/**
	 * 检查文件更新
	 * @param file
	 */
	private void checkFileUpdate(File file){
		if(file.isFile()){
			this.newfiles.add(file.getAbsolutePath());
			
			Long lastmodified = this.currentFiles.get(file.getAbsolutePath());
			
			if(null == lastmodified){
				this.currentFiles.put(file.getAbsolutePath(), file.lastModified());
				//发现文件新增，后续处理
				this.onAdd(file.getAbsolutePath());
				return;
			}
			
			if(lastmodified.doubleValue() != file.lastModified()){
				//文件更新
				this.currentFiles.put(file.getAbsolutePath(), file.lastModified());
				this.onUpdate(file.getAbsolutePath());
				return;
			}
		return;
		}
		else if(file.isDirectory()){
			File[] files = file.listFiles();
			if(files == null || files.length == 0){
				return;
			}
			
			for(File f : files){
				checkFileUpdate(f);
			}
		}
	}
	
	/**
	 * 检查文件删除
	 * @param file
	 */
	private void checkRemoveFiles(File file){
		//新增或增加时，newFiles.size == currentFiles.size
		//删除时，newFiles.size < currentFiles.size
		//不可能出现 newFiles.size > currentFiles.size
		if(this.newfiles.size() == this.currentFiles.size()){
			return;
		}
		
		Iterator<String> kt = this.currentFiles.keySet().iterator();
		
		while(kt.hasNext()){
			String filename = kt.next();
			if(!this.newfiles.contains(filename)){
				kt.remove();
				this.onDelete(filename);
			}
		}
	}
	
	public String getDirectory(){
		return this.directory;
	}
	
	public void setDirectory(String directory){
		this.directory = directory;
	}

}
