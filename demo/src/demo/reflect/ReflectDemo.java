package demo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectDemo {
	public static void main(String[] args) throws Exception{
		Class<?> cls = Class.forName("lynn.file.MappedByteFileReader");
		Constructor<?>[] cons = cls.getConstructors();
		for(int i = 0;i < cons.length;i++){
			System.out.println(cons[i]);
		}
		
		Method[] mtds = cls.getMethods();
		for(Method mtd:mtds){
			System.out.println(mtd.getName());
		}
	}
}
