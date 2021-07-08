package methodcountInDex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class CompareMethodNameInJarToDex {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {//�Ƚ������ı��Ƿ���ͬ
		// TODO Auto-generated method stub
		PythonInterpreter interpreter = new PythonInterpreter(); //����python����
		interpreter.execfile("G:\\�Ѳ�apk\\Test_dx\\�����Ա�\\compare.py");
		PyFunction func = (PyFunction)interpreter.get("getLength",PyFunction.class);//�Ȼ�ȡ�ļ�����
		PyObject pyobj = func.__call__(new PyString("G:/huaweiPro/��Ŀ����/class�ļ���ʽ/MethodNameInJar.txt"));
		int rowLength = pyobj.asInt();
//		System.out.println(rowLength);
		func = (PyFunction)interpreter.get("equal",PyFunction.class);//�ٽ��жԱȣ�����һ�£����ȵ���ԭ���ȣ�
		pyobj = func.__call__(new PyString("G:/huaweiPro/��Ŀ����/class�ļ���ʽ/DirectAndVirtualMethodNameInDex.txt"),
				new PyString("G:\\huaweiPro\\��Ŀ����\\class�ļ���ʽ\\MethodNameInJar.txt"),
				new PyInteger(rowLength));
		String flag = pyobj.toString();
		flag = flag.toUpperCase();
		if(flag.equals("TRUE")){
			System.out.println("success! dex �� jar���е�direct��virtual����һһ��Ӧ");
		}else if(flag.equals("FALSE")){
			System.out.println("FAIL! dex �� jar���еķ�������Ӧ");
		}
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("F:/allDexClassName.txt")));
		Set<String> set = (Set<String>) ois.readObject();
		List<String> classFromAllMethod = new ArrayList<String>(set);
		ois = new ObjectInputStream(new FileInputStream(new File("F:/partDexClassName.txt")));
		List<String> classFromDriectAndVirtualMethodInDex = (List<String>) ois.readObject();
		File outputFile = new File("G:/huaweiPro/��Ŀ����/class�ļ���ʽ/���������.txt");
		FileWriter out = new FileWriter(outputFile);
		int j=0;
		for(int i = 0;i<classFromAllMethod.size();i++){
			if(!classFromDriectAndVirtualMethodInDex.contains(classFromAllMethod.get(i))){
				out.write(classFromAllMethod.get(i) + "\r\n");
				j++;
			}
		}
		out.close();
		//�ж�������Ƿ����ڻ������
		interpreter.execfile("G:\\�Ѳ�apk\\Test_dx\\�����Ա�\\filter.py");
		func = (PyFunction)interpreter.get("myfilter",PyFunction.class);
		func.__call__(new PyString("G:\\huaweiPro\\��Ŀ����\\class�ļ���ʽ\\���������.txt"));
	}
}
