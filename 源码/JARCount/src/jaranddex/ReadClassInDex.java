package jaranddex;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ReadClassInDex {
	private static int sum =0;
	/**
	 * @param fileName ����dex�ļ����е�·��
	 * ���ֽ�Ϊ��λ��ȡ�ļ��������ڶ��������ļ�����ͼƬ��������Ӱ����ļ���
	 */
	public static void readFileByBytes(String fileName) {
		File file = new File(fileName);
		
		File[] allDexFile = file.listFiles();
		sum = 0;//class����
		for(File temp : allDexFile){
			InputStream in = null;
			int index = -1;
			try {
//				System.out.println("���ֽ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���ֽڣ�");
				// һ�ζ�һ���ֽ�
				in = new FileInputStream(temp);
				int tempbyte;
				String hex;
				for (index = 0; (tempbyte = in.read()) != -1 && index <= 99; index++) {
					if (index < 96) {
						continue;
					}
					hex = Integer.toHexString(tempbyte & 0xFF);
					if (hex.length() == 1) {
						hex = '0' + hex;
					}
					hex = hex.toUpperCase();
					int j = hexToInt(hex);
					sum+=j*Math.pow(256, index-96);
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	/**
	 * @param file ������һ��dex�ļ�
	 */
	public static void readFileByBytes(File file) {
		sum = 0;// class����
		InputStream in = null;
		int index = -1;
		try {
			in = new FileInputStream(file);
			int tempbyte;
			String hex;
			for (index = 0; (tempbyte = in.read()) != -1 && index <= 99; index++) {
				if (index < 96) {
					continue;
				}
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				int j = hexToInt(hex);
				sum += j * Math.pow(256, index - 96);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e1) {
			}
		}
	}
	
	/**
	 * ��ʾ�������л�ʣ���ֽ���
	 */
	private static void showAvailableBytes(InputStream in) {
		try {
			System.out.println("��ǰ�ֽ��������е��ֽ���Ϊ:" + in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int hexToInt(String str){
		Integer in = Integer.valueOf(str,16);
		return in;
	}
	
	public int getSum(){
		return sum;
	}
}
