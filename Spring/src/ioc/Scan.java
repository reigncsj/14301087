package ioc;


import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.beans.*;

public class Scan {
	
	public static Set<Class<?>> getClasses(String pack,int i) {  
		  
        // 第一个class类的集合  
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();  
        // 是否循环迭代  
        boolean recursive = true;  
        // 获取包的名字 并进行替换  
        String packageName = pack;  
        String packageDirName = packageName.replace('.', '/');  
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things  
        Enumeration<URL> dirs;  
        try {  
            dirs = Thread.currentThread().getContextClassLoader().getResources(  
                    packageDirName);  
            // 循环迭代下去  
            while (dirs.hasMoreElements()) {  
                // 获取下一个元素  
                URL url = dirs.nextElement();  
                // 得到协议的名称  
                String protocol = url.getProtocol();  
                // 如果是以文件的形式保存在服务器上  
                if ("file".equals(protocol)) {  
                    System.err.println("file类型的扫描");  
                    // 获取包的物理路径  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                    // 以文件的方式扫描整个包下的文件 并添加到集合中  
                    findAndAddClassesInPackageByFile(packageName, filePath,  
                            recursive, classes,i);  
                } 
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
        return classes;  
    }  
	
	 public static void findAndAddClassesInPackageByFile(String packageName,  
	            String packagePath, final boolean recursive, Set<Class<?>> classes,int i) {  
	        // 获取此包的目录 建立一个File  
	        File dir = new File(packagePath);  
	        // 如果不存在或者 也不是目录就直接返回  
	        if (!dir.exists() || !dir.isDirectory()) {  
	            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");  
	            return;  
	        }  
	        // 如果存在 就获取包下的所有文件 包括目录  
	        File[] dirfiles = dir.listFiles(new FileFilter() {  
	            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)  
	            public boolean accept(File file) {  
	                return (recursive && file.isDirectory())  
	                        || (file.getName().endsWith(".class"));  
	            }  
	        });  
	        // 循环所有文件  
	        for (File file : dirfiles) {  
	            // 如果是目录 则继续扫描  
	            if (file.isDirectory()) {  
	                findAndAddClassesInPackageByFile(packageName + "."  
	                        + file.getName(), file.getAbsolutePath(), recursive,  
	                        classes,i);  
	            } else {  
	                // 如果是java类文件 去掉后面的.class 只留下类名  
	                String className = file.getName().substring(0,  
	                        file.getName().length() - 6);  
	                try {  
	                    // 添加到集合中去  
	                    //classes.add(Class.forName(packageName + '.' + className));  
	                                         //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净  
	                    if(i==1){ 
	                	   if(Class.forName(packageName + '.' + className).isAnnotationPresent(Component.class))           
	                	   classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className)); 
	                    }
	                    else{
	                    	Class<?> c=Class.forName(packageName + '.' + className, true, Thread.currentThread().getContextClassLoader());
	                    	 try {
	                    		 BeanInfo info = java.beans.Introspector.getBeanInfo(c);
	                    		 Field[] fields =c.getDeclaredFields();
	                             for(Field field : fields){  
	                                 if(field!=null && field.isAnnotationPresent(Autowired.class)){  
	                                	 classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className)); 
						                 break;
	                                 }
	                             }
							} catch (IntrospectionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	     
	                    }
	                } catch (ClassNotFoundException e) {  
	                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  

}
