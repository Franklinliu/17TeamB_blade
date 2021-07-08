package unZipped;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BatchUnZipper {
	private static int sum = 0;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// File zipFile = new File("G:/�Ѳ�apk/Test_dx/lingshengduoduo/momo.zip");
//		 String path = "d:/zipfile/";
		// unZipFiles(zipFile, path);
		String path = "G:/�Ѳ�apk/";
		File file = new File("G:/�Ѳ�apk/Test_dx/apk-zip");
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
			unZipFiles(temp, path);
			sum++;
		}
		System.out.println(sum);
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
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + name + "/" + zipEntryName).replaceAll("\\*", "/");
			// �ж�·���Ƿ����,�������򴴽��ļ�·��
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// �ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ
			if (new File(outPath).isDirectory()) {
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
//			OutputStream out = new FileOutputStream(outPath);
//			byte[] buf1 = new byte[1024];
//			int len;
//			while ((len = in.read(buf1)) > 0) {
//				out.write(buf1, 0, len);
//			}
//			in.close();
//			out.close();
		}
	}
}
