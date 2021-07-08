package jaranddex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;


public class ReadClassNameFromDex {
	private static int[] class_idx = null;
	private static int[] descriptor_idx = null;
	private static int[] string_data_off = null;
	private static String[] className = null;
	private static ReadClassInDex mReadClassInDex = new ReadClassInDex();
	private static int class_def_off = 0;
	private static RandomAccessFile r = null;  //��RandomAccessFile����������ʵ��ļ��ض��ֽ�
	public static int countClass = 0;
	
	public static void setClass_idx(){
		File file = new File("G:/test/dex");
		File[] allDexFile = file.listFiles();
		File outputFile = new File("G:/test/�����Ա�/dex.txt");  //����������ݵ��ļ� 
		FileWriter out = null;
		try {
			out = new FileWriter(outputFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  //�ļ�д����  
		for(File temp : allDexFile){
			mReadClassInDex.readFileByBytes(temp);
			int count_classINdex = mReadClassInDex.getSum();
			class_idx = new int[count_classINdex];
			descriptor_idx = new int[count_classINdex];
			string_data_off = new int[count_classINdex];
			className = new String[count_classINdex];
			ReadClassOffsetInDex.readOffsetByBytes(temp);
			class_def_off = ReadClassOffsetInDex.getOffset();//��ȡclass_def��ƫ�Ƶ�ַ��ʮ���ƣ�
			try {
				r = new RandomAccessFile(temp,"r");
				for(int i=0;i<count_classINdex;i++){
					r.seek(class_def_off+i*32);//����offset��
					class_idx[i] = read4Bytes();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//������ɶ�ȡ���е�class_idx
			
			try {
				r.seek(68);
				int type_ids_off = read4Bytes();
				for(int i=0;i<count_classINdex;i++){
					r.seek(type_ids_off + 4* class_idx[i]);
					descriptor_idx[i] = read4Bytes();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//������ɶ�ȡ���е�descriptor_idx
			
			try {
				r.seek(60);
				int string_ids_off = read4Bytes();
				for(int i=0;i<count_classINdex;i++){
					r.seek(string_ids_off + 4* descriptor_idx[i]);
					string_data_off[i] = read4Bytes();
//					System.out.println(string_data_off[i]);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//������ɶ�ȡ���е�descriptor_idx
			//���������������������������ͣ��ˡ�
			
			try {
				for(int i=0;i<count_classINdex;i++){//����ȫ�������ʾ������̨�ط����޻Ḳ��ǰ�������
					r.seek(string_data_off[i]);
					className[i] = readStringData();//ԭʼ������Ҫ�ĳɾ���������������.class
					int start = className[i].lastIndexOf("/");
					int end = className[i].lastIndexOf(";");
					className[i] = className[i].substring(start+1, end);
					className[i]+=".class";
					out.write(className[i]+"\r\n");  
				}
				r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public static int read4Bytes(){ //һ�ζ��ĸ��ֽ�
		int idx = 0;
		int times=0;
		try {
			int tempbyte;
			String hex;
			for(times=0;times<4;times++){
				tempbyte=r.read();
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				int j = hexToInt(hex);
				idx+=j*Math.pow(256, times);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return idx;
	}
	
	public static String readStringData(){//��ȡһ������������
		int tempbyte;
		String hex;
		String strClassName = new String();
		try {
			while(true){
				tempbyte=r.read();
				hex = Integer.toHexString(tempbyte & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				hex = hex.toUpperCase();
				if(hex.equals("00")){
					break;
				}else{
					strClassName+=hex;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toStringHex(strClassName); 
	}
//	public static int read4Bytes(int offset){//��offset��һ�ζ��ĸ��ֽ�
//		int idx = 0;
//		InputStream in = null;
//		int index = -1;
//		int times=0;
//		try {
//			in = new FileInputStream(currentFile);
//			int tempbyte;
//			String hex;
//			for (index = 0; (tempbyte = in.read()) != -1 && times<4; index++) {
//				if (index < offset) {
//					continue;
//				}
//				times++;
//				hex = Integer.toHexString(tempbyte & 0xFF);
//				if (hex.length() == 1) {
//					hex = '0' + hex;
//				}
//				hex = hex.toUpperCase();
//				int j = hexToInt(hex);
//				idx+=j*Math.pow(256, index-offset);
//			}
//			in.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		if (in != null) {
//			try {
//				in.close();
//			} catch (IOException e1) {
//			}
//		}
//		return idx;
//	}
	
	private static int hexToInt(String str){
		Integer in = Integer.valueOf(str,16);
		return in;
	}
	
	public static String toStringHex(String s) {	//��16����ת��ΪASCII�ַ�
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	
	public static void main(String[] args){
		setClass_idx();//���dex���ļ���������д��dex.txt��
		//�����ǱȽ�dex.txt �� jar.txt
		//��pythonȥ�Ƚϻ����õ���������commonsIO
		PythonInterpreter interpreter = new PythonInterpreter(); //����python����
		interpreter.execfile("G:\\test\\�����Ա�\\compare.py");
		PyFunction func = (PyFunction)interpreter.get("getLength",PyFunction.class);//�Ȼ�ȡ�ļ�����
		PyObject pyobj = func.__call__(new PyString("G:\\test\\�����Ա�\\dex.txt"));
		int countClass = pyobj.asInt();
		System.out.println(countClass);
		
		func = (PyFunction)interpreter.get("equal",PyFunction.class);//�ٽ��жԱȣ�����һ�£����ȵ���ԭ���ȣ�
		pyobj = func.__call__(new PyString("G:\\test\\�����Ա�\\dex.txt"),
										new PyString("G:\\test\\�����Ա�\\jar.txt"),
										new PyInteger(countClass));
		String flag = pyobj.toString();
		flag = flag.toUpperCase();
		if(flag.equals("TRUE")){
			System.out.println("success! dex �� jar���е���һһ��Ӧ");
		}else if(flag.equals("FALSE")){
			System.out.println("FAIL! dex �� jar���е��಻��Ӧ");
		}else{
			System.out.println("ERROR " + flag);
		}
	}
}
