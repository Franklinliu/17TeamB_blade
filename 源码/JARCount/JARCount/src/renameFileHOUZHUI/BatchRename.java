package renameFileHOUZHUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Lution 
 * java�����޸�ָ���ļ��������к�׺�����ļ�Ϊ�����׺���Ĵ���
 * apk-->zip
 */
public class BatchRename {
	public static void main(String args[]) {
		String dir = "G:/�Ѳ�apk/Test_dx/apk-zip";
		File file = new File(dir);
		String srcSuffix = "apk";
		String dstSuffix = "zip";
		List<String> paths = listPath(file, srcSuffix);//��ȡָ����׺���ļ���·��
		for (String path : paths) {
			File srcFile = new File(path);//��ȡָ����׺���ļ�
			String name = srcFile.getName();
			int idx = name.lastIndexOf(".");
			String prefix = name.substring(0, idx);

			File dstFile = new File(srcFile.getParent() + File.separator + prefix + "." + dstSuffix);
			if (dstFile.exists()) {
				srcFile.delete();
				continue;
			}
			srcFile.renameTo(dstFile);
		}
	}

	/**
	 * ��ȡָ��·���µ����з����������ļ�
	 * 
	 * @param file
	 *            ·��
	 * @param srcSuffix
	 *            ��׺��
	 * @return
	 */
	private static List<String> listPath(File path, String srcSuffix) {
		List<String> list = new ArrayList<String>();
		File[] files = path.listFiles();
		Arrays.sort(files);
		for (File file : files) {
			if (file.isDirectory()) {// �����Ŀ¼
				// �ؼ������������������(�ݹ��ж��¼�Ŀ¼)
				List<String> _list = listPath(file, srcSuffix);// �ݹ����
				list.addAll(_list);// ��������ӵ�������
			} else {// ����Ŀ¼
//				String name = file.getName();
//				int idx = name.lastIndexOf(".");
//				String suffix = name.substring(idx + 1);
//				if (suffix.equals(srcSuffix)) {
//					list.add(file.getAbsolutePath());// ���ļ��ľ���·����ӵ�������
//				}
				if(file.getName().endsWith(srcSuffix)){
					list.add(file.getAbsolutePath());// ���ļ��ľ���·����ӵ�������
				}
			}
		}
		return list;
	}
}
