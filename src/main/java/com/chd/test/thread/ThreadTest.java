package com.chd.test.thread;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ThreadTest {

	public static void main(String[] args) throws Exception {
		RandomAccessFile aFile = new RandomAccessFile("c://users/jumili/desktop/10776.txt","rw");
		FileChannel inChannel = aFile.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(1);
		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {
//			System.out.print(bytesRead);
			buf.flip();
			while(buf.hasRemaining()){
				System.out.print((char) buf.get());
			}
			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}
}
