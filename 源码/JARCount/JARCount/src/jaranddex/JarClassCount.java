package jaranddex;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;  
import java.net.JarURLConnection;  
import java.net.MalformedURLException;  
import java.net.URL;  
import java.util.ArrayList;  
import java.util.Enumeration;  
import java.util.List;
import java.util.Map.Entry;
import java.util.jar.JarEntry;  
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils; 

public class JarClassCount {
	
private static ReadClassInDex readClassInDex = new ReadClassInDex();
private static FileWriter out = null;
private static List<String> allClassNames = new ArrayList<>();

	public static void main(String[] args)  throws IOException{
		// TODO Auto-generated method stub
		
		readClassInDex.readFileByBytes("G:/�Ѳ�apk/Test_dx/dex");
		int class_dex = readClassInDex.getSum();
		System.out.println("dex��class����"+class_dex);
		
		int class_jar = getSpringClassNumber(); //��һ������
		System.out.println("jar����class����"+class_jar);
		if(class_dex==class_jar){
			System.out.println("dex��jar��class����һ��,��Ŀ�ǣ�" + class_jar);
		}else{
			System.out.println("dex��jar��class������ͬ");
		}
		
		FileUtils.writeLines(new File("F:/temp2.txt"), allClassNames);
		
//		JarFile jf = null;
//		jf = new JarFile("G:/�Ѳ�apk/Test_dx/app-debug/jar/classes-dex2jar.jar");
//		Enumeration enu = jf.entries();
//		int i = 0;
//		while(enu.hasMoreElements()){
//			JarEntry element = (JarEntry) enu.nextElement();
//			String name = element.getName();
//			if(name.toUpperCase().endsWith(".CLASS")){
////		        System.out.println(name);   
//				i++;
//		    }
//		}
//		System.out.printf( "jar��������ܵ�class�ļ�����" + i );
	}
	
	private static int getSpringClassNumber() throws IOException  
    {  
        File springJar = new File( "G:/�Ѳ�apk/Test_dx/jar" );
//		File springJar = new File( "G:/�Ѳ�apk/Test_dx/mojitianqi" ); 
        List list = new ArrayList<URL>();  
          
        File[] allSpringJar = springJar.listFiles();  
        int result = 0;  
        
        File outputFile = new File("G:/�Ѳ�apk/Test_dx/�����Ա�/jar.txt");  //����������ݵ��ļ� 
        try {
			out = new FileWriter(outputFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  //�ļ�д���� 
        for(File temp : allSpringJar)  
        {   
            String urlName = "jar: file:";  
            String name = temp.toString();  
            name.replace( "\\", "//" );  
              
            urlName += name;  
            urlName += "!/";  
//            System.out.println( urlName );  
            
            name = name.replaceAll(".zip", "");
            result += classNumberPerJarFile( urlName,name ) ;  
        }  
//        System.out.printf( "jar��������ܵ�class�ļ�����" + result );
        out.close();
        return result;  
    }  
	 private static int classNumberPerJarFile(String urlName,String name) throws IOException  
	    {  
	        URL url = new URL( urlName );  
	        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();  
	        JarFile jarFile = jarURLConnection.getJarFile();  
	          
	        Enumeration<JarEntry> jarEntries = jarFile.entries();  
	        int i = 0;  
	        while(jarEntries.hasMoreElements())  
	        {  
	            JarEntry jarEntry = jarEntries.nextElement();  
	            if(jarEntry.getName().endsWith( ".class" ))  
	            {  
	            	int start = jarEntry.getName().lastIndexOf("/");
	            	out.write(jarEntry.getName().substring(start+1)+"\r\n");//�������� 
	                i++;
	                allClassNames.add(name +'\\' + jarEntry.getName().replace('/', '\\'));
	                
	            }  
	              
	        }  
	        return i;  
	          
	    }  
}
