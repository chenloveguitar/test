package com.chd.test.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

public class NioTest {

	
	
	public static void readAndWrite() throws Exception {
		File file = new File("C:\\Users\\jumili\\Desktop\\10776.txt");
		RandomAccessFile accessFile = new RandomAccessFile (file,"rwd");
		FileChannel channel = accessFile.getChannel();
		int size = (int)channel.size();
		ByteBuffer buff = ByteBuffer.allocateDirect(size);
		while(channel.read(buff) != -1) {
			buff.flip();
			byte[] b = new byte[buff.remaining()];
			buff.get(b);
			System.out.print(new String(b,CharsetEncode.UTF_8));
			buff.clear();
		}
		String msg = "ря╤а";
		ByteBuffer src = ByteBuffer.allocateDirect(msg.length() * 3);
		src.put(msg.getBytes(CharsetEncode.UTF_8));
		src.flip();
		channel.write(src);
		channel.close();
		accessFile.close();
	}
	
	public static void createJavaFile() throws Exception {
		StringBuffer classFile = new StringBuffer();
		SortedMap<String,Charset> availableCharsets = Charset.availableCharsets();
		Set<String> keySet = availableCharsets.keySet();
		Iterator<String> iterator = keySet.iterator();
		classFile.append("public final class  CharsetEncode{\n");
		classFile.append("\n\tprivate CharsetEncode(){}\n");
		
		while(iterator.hasNext()) {
			String fieldValue = iterator.next();
			String fieldName = fieldValue.toUpperCase();
			if(fieldValue.contains("-")) {
				fieldName = fieldName.replaceAll("-", "_");
			}
			classFile.append("\n\tpublic static final String " + fieldName + " = \"" + fieldValue + "\";\n");
		}
		classFile.append("}");
		System.out.println(classFile.toString());
	}
	
	public static void main(String[] args) throws Exception {
		readAndWrite();
		createJavaFile();
	}
}
