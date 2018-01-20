package com.chd.test.compiler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaCompilerTest {

	public Object exec(String source,String className,String methodName) throws Exception {
		check();
		// JavaԴ����
//		String sourceStr = "public class Hello{    public String sayHello (String name) {return \"Hello,\" + name + \"!\";}}";
		// �������ļ���
//		String clsName = "Hello";
		// ������
//		String methodName = "sayHello";
		// ��ǰ������ ��Ҫtools.jar ����֧��,����ֱ��ʹ��jre����,��Ҫʹ��jdk����,һ�����Ŀ����ʹ��jdk��������,���Դ˴���Ҫͨ�������ֶμ���tools.jar��
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		// Java��׼�ļ�������
		StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
		// Java�ļ�����
		JavaFileObject jfo = new JavaStringObject(className, source);
		// ���������������javac <options>�е�options
		List<String> optionsList = new ArrayList<String>();
		// �����ļ��Ĵ�ŵط���ע�⣺�˴���ΪEclipse���������,����һ���������õ��Ǳ����class�ļ��Ĵ��·��.
		// 1.��ͨjava��Ŀ ./bin
		// 2.javaweb��Ŀ ./WEB-INF/classes
		// 3.maven web��Ŀ./target/classes
		// ���web��Ŀ�������war��ͳһ��./WEB-INF/classes
		optionsList.addAll(Arrays.asList("-d", "./WEB-INF/classes"));//����Ҳ��Ҫ��������
//		optionsList.addAll(Arrays.asList("-d", "./target/classes"));//����Ҳ��Ҫ��������
		// Ҫ����ĵ�Ԫ
		List<JavaFileObject> jfos = Arrays.asList(jfo);
		// ���ñ��뻷��
		JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardJavaFileManager, null, optionsList, null, jfos);
		// ����ɹ�
		if (task.call()) {
			// ���ɶ���
			Object obj = Class.forName(className).newInstance();
			Class<? extends Object> cls = obj.getClass();
			// ����sayHello����
			Method m = cls.getMethod(methodName, String.class);
			Object result = m.invoke(obj, "Dynamic Compilation");
			return result;
		}
		return null;
	}

	private void check() {
		Properties properties = System.getProperties();
		Enumeration<?> names = properties.propertyNames();
		while(names.hasMoreElements()) {
			Object element = names.nextElement();
			Object object = properties.get(element);
			System.out.println(element+" : "+object);
		}
	}
	
	public static void main(String[] args) {
//		new JavaCompilerTest().check();
//		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();  
//		boolean isLoadToolsJar = false;
//		for (int i = 0; i < urls.length; i++) {  
//		    isLoadToolsJar = urls[i].toExternalForm().contains("tools.jar");  
//		}  
//		if(!isLoadToolsJar) {
//			
//		}
	}
}

// �ı��е�Java����
class JavaStringObject extends SimpleJavaFileObject {
	// Դ����
	private String content = "";
	
	// ��ѭJava�淶���������ļ�
	protected JavaStringObject(String javaFileName, String content) {
		super(createJavaStringObjectUri(javaFileName), Kind.SOURCE);
		this.content = content;
	}
	// ����һ��URL��Դ·��
	private static URI createJavaStringObjectUri(String name) {
		// ע��˴�û�����ð���
		return URI.create(name + Kind.SOURCE.extension);
	}

	// �ı��ļ�����
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return content;
	}

}
