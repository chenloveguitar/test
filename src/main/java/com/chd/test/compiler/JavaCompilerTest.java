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
		// Java源代码
//		String sourceStr = "public class Hello{    public String sayHello (String name) {return \"Hello,\" + name + \"!\";}}";
		// 类名及文件名
//		String clsName = "Hello";
		// 方法名
//		String methodName = "sayHello";
		// 当前编译器 需要tools.jar 包的支持,不能直接使用jre环境,需要使用jdk环境,一般的项目不会使用jdk环境运行,所以此处需要通过其他手段加入tools.jar包
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		// Java标准文件管理器
		StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
		// Java文件对象
		JavaFileObject jfo = new JavaStringObject(className, source);
		// 编译参数，类似于javac <options>中的options
		List<String> optionsList = new ArrayList<String>();
		// 编译文件的存放地方，注意：此处是为Eclipse工具特设的,后面一个参数设置的是编译后class文件的存放路径.
		// 1.普通java项目 ./bin
		// 2.javaweb项目 ./WEB-INF/classes
		// 3.maven web项目./target/classes
		// 如果web项目被打成了war包统一是./WEB-INF/classes
		optionsList.addAll(Arrays.asList("-d", "./WEB-INF/classes"));//这里也需要特殊设置
//		optionsList.addAll(Arrays.asList("-d", "./target/classes"));//这里也需要特殊设置
		// 要编译的单元
		List<JavaFileObject> jfos = Arrays.asList(jfo);
		// 设置编译环境
		JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardJavaFileManager, null, optionsList, null, jfos);
		// 编译成功
		if (task.call()) {
			// 生成对象
			Object obj = Class.forName(className).newInstance();
			Class<? extends Object> cls = obj.getClass();
			// 调用sayHello方法
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

// 文本中的Java对象
class JavaStringObject extends SimpleJavaFileObject {
	// 源代码
	private String content = "";
	
	// 遵循Java规范的类名及文件
	protected JavaStringObject(String javaFileName, String content) {
		super(createJavaStringObjectUri(javaFileName), Kind.SOURCE);
		this.content = content;
	}
	// 产生一个URL资源路径
	private static URI createJavaStringObjectUri(String name) {
		// 注意此处没有设置包名
		return URI.create(name + Kind.SOURCE.extension);
	}

	// 文本文件代码
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return content;
	}

}
