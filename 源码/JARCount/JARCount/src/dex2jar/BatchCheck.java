package dex2jar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BatchCheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> sourceName = new ArrayList<String>();
		List<String> destName = new ArrayList<String>();
		File sourceDir = new File("G:/�Ѳ�apk/newly�Ѳ�");
		File[] sourceFile = sourceDir.listFiles();
		for(File file:sourceFile){
			sourceName.add(file.getName());
		}
		File destDir = new File("G:/huaweiPro/��Ŀ����/�ύ�ĵ�");
		File[] destFile = destDir.listFiles();
		for(File file:destFile){
			destName.add(file.getName());
		}
		for(String str:sourceName){
			if(!destName.contains(str)){
				System.out.println(str);
			}
		}
	}

}
