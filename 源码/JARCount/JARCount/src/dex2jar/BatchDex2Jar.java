package dex2jar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

public class BatchDex2Jar {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File toolDir = new File("G:/huaweiPro/dex2jar-2.x/dex-tools/build/distributions/dex-tools-2.1-SNAPSHOT");
		
		File file = new File("G:/�Ѳ�apk/newly�Ѳ�");
		File[] allFile = file.listFiles();
		
		for(File dir:allFile){//dir��һ�����ļ���
			deleteFile(toolDir);//ɾ��dex2jarĿ¼�µ�dex��jar
			File judgeFile = new File("F:/�������ʵ��/waitforjar");
			if(judgeFile.exists()){
				FileUtils.deleteDirectory(judgeFile);
			}
			File[] fileInDir = dir.listFiles();
			File[] dexFile = new File[fileInDir.length];
			int i = 0;
			for(File temp:fileInDir){
				if(temp.getName().endsWith(".dex")){
					//copy
					dexFile[i++] = temp;
					FileUtils.copyFileToDirectory(temp, toolDir);
				}
			}
			//����dex2jar.bat
			runbat("G:/eclipseproject/JARCount/dex2jar.bat",dexFile);
			//Ҫ����ִ����Ϸ���Ŀ¼�л�û��ת�õ�jar����
			waitForJar(toolDir,i);
			File[] fileInToolDir = toolDir.listFiles();
			for(File temp:fileInToolDir){
				if(temp.getName().endsWith(".jar")){
					FileUtils.copyFileToDirectory(temp, dir);
				}
			}
		}
	}
	
	private static void deleteFile(File file){
		File[] toolFile = file.listFiles();
		for(File temp:toolFile){
			if(temp.getName().endsWith(".dex") ||temp.getName().endsWith(".jar")){
				temp.delete();
			}
		}
	}
	private static void runbat(String batName,File[] dexFile) {
		String cmd = "cmd /c start /b " + batName + " ";
		
		Process ps = null;
		 try {
			ps = Runtime.getRuntime().exec(cmd);
			ps.waitFor();
			ps.destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void waitForJar(File file,int size){
		File judgeFile = new File("F:/�������ʵ��/waitforjar");
		while(true){
			int count = 0;
			File[] fileInToolDir = file.listFiles();
			for(File temp:fileInToolDir){
				if(temp.getName().endsWith(".jar")){
					count++;
				}
			}
			if(count==size && judgeFile.exists()){
				break;
			}
		}
	}
}
