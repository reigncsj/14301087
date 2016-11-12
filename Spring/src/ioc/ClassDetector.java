package ioc;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ClassDetector {
	private String package1;
	 private ArrayList<String> list;
	 private ArrayList<String> list2;
	public ClassDetector(){
		 list=new ArrayList<String>();
		 list2=new ArrayList<String>();
	}
	
	
	public  ArrayList<String> getCom(){
		return list;
	}
	
	public  ArrayList<String> getAuto(){
		return list2;
	}
	public void getClasses(String pack){
		  
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
	                            list,list2 );  
	                }
	      }

       }catch (IOException e) {  
           e.printStackTrace();  
       }  
	}
	 public static void findAndAddClassesInPackageByFile(String packageName,  
	            String packagePath, ArrayList<String> l,ArrayList<String> l2) {  
	        // ��ȡ�˰���Ŀ¼ ����һ��File  
	        File dir = new File(packagePath);  
	        // ��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���  
	        if (!dir.exists() || !dir.isDirectory()) {  
	            // log.warn("�û�������� " + packageName + " ��û���κ��ļ�");  
	            return;  
	        }  
	        // ������� �ͻ�ȡ���µ������ļ� ����Ŀ¼  
	        File[] dirfiles = dir.listFiles(new FilenameFilter() {  
	            // �Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)  
	            public boolean accept(File file,String name) {  
	                return (file.isDirectory())  
	                        || (file.getName().endsWith(".class"));  
	            }  
	        });  
	        // ѭ�������ļ�  
	        for (File file : dirfiles) {  
	            // �����Ŀ¼ �����ɨ��  
	            if (file.isDirectory()) {  
	                findAndAddClassesInPackageByFile(packageName + "."  
	                        + file.getName(), file.getAbsolutePath(),  
	                        l,l2);  
	            } else {  
	                // �����java���ļ� ȥ�������.class ֻ��������  
	                String className = file.getName().substring(0,  
	                        file.getName().length() - 6);  
	                try {  
	                    // ��ӵ�������ȥ  
	                    //classes.add(Class.forName(packageName + '.' + className));  
	                                         //�����ظ�ͬѧ�����ѣ�������forName��һЩ���ã��ᴥ��static������û��ʹ��classLoader��load�ɾ�  
	                          if(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className).isAnnotationPresent(Component.class))
	                               l.add(packageName + '.' + className);
	                          if(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className).isAnnotationPresent(Autowired.class)) 
	                        	   l2.add(packageName + '.' + className);
	                    } catch (ClassNotFoundException e) {  
	                    // log.error("����û��Զ�����ͼ����� �Ҳ��������.class�ļ�");  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  
}
