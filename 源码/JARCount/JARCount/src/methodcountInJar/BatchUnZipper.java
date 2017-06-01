package methodcountInJar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BatchUnZipper {
	private static int sum = 0;
	private static List<String> fileName = null;
	private static List<String> fileDir = null;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		fileName =  new ArrayList<String>();
		String path = "G:/�Ѳ�apk/Test_dx/ClassInJar/";
		File file = new File("G:/�Ѳ�apk/Test_dx/jar");
		File[] allFile = file.listFiles();
		File[] allZipFile = new File[allFile.length];// �������ļ���ɸѡ��zip
		int j = 0;
		for (int i = 0; i < allFile.length; i++) {
			if (allFile[i].getName().endsWith("zip")) {
				allZipFile[j++] = allFile[i];
			}
		}
		sum = 0;// �ѽ�ѹ��zip����
		for (File temp : allZipFile) {
			fileDir = new ArrayList<String>();
			unZipFiles(temp, path);
			sum++;
		}
		System.out.println("�ѽ�ѹ��ѹ���ļ�:	"+sum);
	}

	/**
	 * ��ѹ��ָ��Ŀ¼
	 * 
	 * @param zipPath
	 * @param descDir
	 * @author isea533
	 */
	public static void unZipFiles(String zipPath, String descDir) throws IOException {
		unZipFiles(new File(zipPath), descDir);
	}

	/**
	 * ��ѹ�ļ���ָ��Ŀ¼
	 * 
	 * @param zipFile
	 * @param descDir
	 * @author isea533
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile, String descDir) throws IOException {
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
		String name = zipFile.getName();
		name = name.substring(0, name.lastIndexOf('.'));
		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			
			if(zipEntryName.endsWith(".class")){
				String fileName_UpperCase = zipEntryName.toUpperCase();
				boolean flag = false;
				for(int i=0;i<fileName.size();i++){
					if(fileName_UpperCase.equals(fileName.get(i))){
						flag = true;
						break;
					}
				}
				/*if(flag==false){//û�д�Сд�����ļ�
					fileName.add(fileName_UpperCase);
				}else{
					int start = zipEntryName.lastIndexOf('/');
					int end = zipEntryName.lastIndexOf('.');
					String str = zipEntryName.substring(start+1, end);
					str = str + "LXHLXHLXH";
					String pre = zipEntryName.substring(0, start+1);
					zipEntryName = pre+str+".class";
					fileName_UpperCase = zipEntryName.toUpperCase();
					fileName.add(fileName_UpperCase);
				}*/
				int start = zipEntryName.lastIndexOf('/');
				int end = zipEntryName.lastIndexOf('.');
				String str = zipEntryName.substring(start+1, end);
				if(str.toUpperCase().equals("AUX")||str.toUpperCase().equals("CON")||str.toUpperCase().equals("NUL")
						||str.toUpperCase().equals("PRN")||str.toUpperCase().equals("COM1")||str.toUpperCase().equals("COM2")
						||str.toUpperCase().equals("COM3")||str.toUpperCase().equals("COM4")||str.toUpperCase().equals("COM5")
						||str.toUpperCase().equals("COM6")||str.toUpperCase().equals("COM7")||str.toUpperCase().equals("COM8")
						||str.toUpperCase().equals("COM9")||str.toUpperCase().equals("LPT1")||str.toUpperCase().equals("LPT2")
						||str.toUpperCase().equals("LPT3")||str.toUpperCase().equals("LPT4")||str.toUpperCase().equals("LPT5")
						||str.toUpperCase().equals("LPT6")||str.toUpperCase().equals("LPT7")||str.toUpperCase().equals("LPT8")
						||str.toUpperCase().equals("LPT9")){//�뱣���ļ���һ�»����
					str = str + "LXHLXH";
					String pre = zipEntryName.substring(0, start+1);
					zipEntryName = pre+str+".class";
					fileName_UpperCase = zipEntryName.toUpperCase();
					while(fileName.contains(fileName_UpperCase)){
						str = str + "LXHLXH";
						pre = zipEntryName.substring(0, start+1);
						zipEntryName = pre+str+".class";
						fileName_UpperCase = zipEntryName.toUpperCase();
					}
				}
				if(flag){
					/*str = str + "LXHLXH";
					String pre = zipEntryName.substring(0, start+1);
					zipEntryName = pre+str+".class";
					fileName_UpperCase = zipEntryName.toUpperCase();*/
					while(fileName.contains(fileName_UpperCase)){
						str = str + "LXHLXH";
						String pre = zipEntryName.substring(0, start+1);
						zipEntryName = pre+str+".class";
						fileName_UpperCase = zipEntryName.toUpperCase();
					}
				}
				fileName.add(fileName_UpperCase);
			}
			
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + name + "/" + zipEntryName).replaceAll("\\*", "/");
			// �ж�·���Ƿ����,�������򴴽��ļ�·��
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
				fileDir.add(outPath.substring(0, outPath.lastIndexOf('/')));
			}else{
				if(!fileDir.contains(outPath.substring(0, outPath.lastIndexOf('/')))){//Windows·�������ִ�Сд
					String firstOFPath = outPath.substring(0, outPath.lastIndexOf('/')) + "LXHLXH";
					fileDir.add(firstOFPath);
					boolean isFirstTime = showTimes(fileDir,firstOFPath);
					if(isFirstTime){//��һ�γ��֣���Ŀ¼
						firstOFPath = untilNotInclude(fileDir,firstOFPath);
						firstOFPath = theLongestMatch(fileDir,firstOFPath);
					}else{//��Ŀ¼
						firstOFPath = theLongestMatch(fileDir,firstOFPath);
					}
					String lastOFPath = outPath.substring(outPath.lastIndexOf('/'));
					outPath = firstOFPath + lastOFPath;
					file = new File(firstOFPath);
					if(!file.exists())/////////
						file.mkdirs();
				}
			}
			// �ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ
			if (new File(outPath).isDirectory()) {
//				System.out.println(outPath);
				continue;
			}
			// ����ļ�·����Ϣ
//			System.out.println(outPath);

			OutputStream out = null;
			try{
				out = new FileOutputStream(outPath);//��ѹ��ĳ��С�ļ�����ʱ��������ѹ
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
			}catch(Exception e){
				
			}finally{
				in.close();
				out=null;
			}
		}
		zip.close();
	}
	
	/**
	 * 
	 * @param fileDir
	 * @param firstOFPath
	 * ֱ����д������,��ʵ������equalsIgnoreCases
	 * @return
	 */
	private static String untilNotInclude(List<String> fileDir,String firstOFPath){
		List<String> tempFileDir = new ArrayList<String>();
		for(int i=0;i<fileDir.size();i++){
			String str = fileDir.get(i).toUpperCase(); 
			tempFileDir.add(str);
		}
		String temp = firstOFPath.toUpperCase();
		int count = 0;
		while(tempFileDir.contains(temp)){
			temp = temp + "LXHLXH";
			count++;
		}
		for(int i=0;i<count;i++){
			firstOFPath+="LXHLXH";
		}
		fileDir.add(firstOFPath);
		return firstOFPath;
	}
	
	/**
	 * 
	 * @param firstOFPath
	 * @return
	 * �ҵ��ƥ��
	 */
	private static String theLongestMatch(List<String> fileDir,String firstOFPath){
		String answer = new String();
		for(String str:fileDir){
			if(str.contains(firstOFPath)){
				answer = str;
			}
		}
		return answer;
	}
	
	private static boolean showTimes(List<String> fileDir,String firstOFPath){
		boolean isFirstTime = true;
		int times = 0;
		for(String str:fileDir){
			if(str.equals(firstOFPath)){
				times++;
			}
		}
		if(times>1){
			isFirstTime = false;
		}
		return isFirstTime;
	}
}
