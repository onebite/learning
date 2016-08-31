package monitor.file;

public interface FileMonitor extends Monitor,Runnable{
	/**file delete**/
	void onDelete(Object filename);
	/**file add **/
	void onAdd(Object filename);
	/**file update **/
	void onUpdate(Object filename);
	/**file loaded successfully **/
	void loadsuccess(Object filename);
	/**file loading **/
	void isloading(Object filename);
	/** file info initial **/
	void initialFileInfo(Object info);
}
