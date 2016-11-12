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
		  
        // ��һ��class��ļ���  
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();  
        // �Ƿ�ѭ������  
        boolean recursive = true;  
        // ��ȡ�������� �������滻  
        String packageName = pack;  
        String packageDirName = packageName.replace('.', '/');  
        // ����һ��ö�ٵļ��� ������ѭ�����������Ŀ¼�µ�things  
        Enumeration<URL> dirs;  
        try {  
            dirs = Thread.currentThread().getContextClassLoader().getResources(  
                    packageDirName);  
            // ѭ��������ȥ  
            while (dirs.hasMoreElements()) {  
                // ��ȡ��һ��Ԫ��  
                URL url = dirs.nextElement();  
                // �õ�Э�������  
                String protocol = url.getProtocol();  
                // ��������ļ�����ʽ�����ڷ�������  
                if ("file".equals(protocol)) {  
                    System.err.println("file���͵�ɨ��");  
                    // ��ȡ��������·��  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                    // ���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������  
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
	        // ��ȡ�˰���Ŀ¼ ����һ��File  
	        File dir = new File(packagePath);  
	        // ��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���  
	        if (!dir.exists() || !dir.isDirectory()) {  
	            // log.warn("�û�������� " + packageName + " ��û���κ��ļ�");  
	            return;  
	        }  
	        // ������� �ͻ�ȡ���µ������ļ� ����Ŀ¼  
	        File[] dirfiles = dir.listFiles(new FileFilter() {  
	            // �Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)  
	            public boolean accept(File file) {  
	                return (recursive && file.isDirectory())  
	                        || (file.getName().endsWith(".class"));  
	            }  
	        });  
	        // ѭ�������ļ�  
	        for (File file : dirfiles) {  
	            // �����Ŀ¼ �����ɨ��  
	            if (file.isDirectory()) {  
	                findAndAddClassesInPackageByFile(packageName + "."  
	                        + file.getName(), file.getAbsolutePath(), recursive,  
	                        classes,i);  
	            } else {  
	                // �����java���ļ� ȥ�������.class ֻ��������  
	                String className = file.getName().substring(0,  
	                        file.getName().length() - 6);  
	                try {  
	                    // ��ӵ�������ȥ  
	                    //classes.add(Class.forName(packageName + '.' + className));  
	                                         //�����ظ�ͬѧ�����ѣ�������forName��һЩ���ã��ᴥ��static������û��ʹ��classLoader��load�ɾ�  
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
	                    // log.error("����û��Զ�����ͼ����� �Ҳ��������.class�ļ�");  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  

}
