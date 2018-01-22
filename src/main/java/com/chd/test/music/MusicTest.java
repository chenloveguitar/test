package com.chd.test.music;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MusicTest {

	private static char[] notes = { 0x0061, 0x0062, 0x0063, 0x0064, 0x0065, 0x0066, 0x0067 };// c-b
	private static char[] NOTES = { 0x0043, 0x0044, 0x0045, 0x0046, 0x0047, 0x0041, 0x0042 };// C-B
	private static final char FLAT = 0x266d;// ♭
	private static final char NATURE = 0x266e;// ♮
	private static final char SHARP = 0x266f;// ♯
	private static final Map<String, String[]> naturalMajorScaleMap = new LinkedHashMap<String,String[]>();
	private static final Map<String, String[]> naturalMinorScaleMap = new LinkedHashMap<String,String[]>();

	public static void main(String[] args) {
		
		printScale();
		
//		for (int i = 0; i < notes.length; i++) {
//			System.out.println(notes[i]);
//		}
	}
	
	//打印大调音阶
	public static void printScale() {
//		sharpMajorScale();//计算带#记号的音阶
//		flatMajorScale();//计算带b记号的音阶
//		System.out.println("自然大调各调音阶:");
//		Set<String> majorSet = naturalMajorScaleMap.keySet();
//		Iterator<String> majorIterator = majorSet.iterator();
//		while(majorIterator.hasNext()) {//遍历出所有音阶
//			String next = majorIterator.next();
//			String[] strings = naturalMajorScaleMap.get(next);
//			System.out.println(next+"大调:"+Arrays.toString(strings));
//		}
		
		sharpMinorScale();
		flatMinorScale();
		System.out.println("自然小调各调音阶:");
		Set<String> minorSet = naturalMinorScaleMap.keySet();
		Iterator<String> minorIterator = minorSet.iterator();
		while(minorIterator.hasNext()) {//遍历出所有音阶
			String next = minorIterator.next();
			String[] strings = naturalMinorScaleMap.get(next);
			System.out.println(next+"小调:"+Arrays.toString(strings));
		}
	}
	
	/**
	 * 标有♭记号的notes,数组逆序查找
	 */
	public static void 	flatMinorScale() {
		int length = notes.length;
		String[] array = new String[length + 1];
		for (int i = 0; i <= notes.length; i++) {
			array[i] = notes[i % length] + "";
		}
		
		for (int i = length; i >= 0; i--) {
			int current = ((i * 5) - i) % length;
			String note = array[current];
			String[] str = new String[length + 1];
			for (int j = 0; j <= length; j++) {
				int index = (current + j) % length;
				String c = array[index];
				if(j == 3 && i != length) {
					str[j] = FLAT + "" + c;
					array[index] = FLAT + "" + c;
				}else {
					str[j] = c;
					array[index] = c;
				}
			}
			naturalMinorScaleMap.put(note, str);
		}
	}
	
	
	/**
	 * 标有♯记号的notes,数组正序查找
	 */
	public static void 	sharpMinorScale() {
		
		int length = notes.length;
		String[] array = new String[length + 1];
		for (int i = 0; i <= notes.length; i++) {
			array[i] = notes[i % length] + "";
		}
		
		for (int i = 0; i <= length; i++) {
			int current = ((i * 5) - i) % length;
			String note = array[current];
			String[] str = new String[length + 1];
			for (int j = 0; j <= length; j++) {
				int index = (current + j) % length;
				String c = array[index];
				if(j == 1 && i != 0) {
					str[j] = SHARP + "" + c;
					array[index] = SHARP + "" + c;
				}else {
					str[j] = c;
					array[index] = c;
				}
			}
			naturalMinorScaleMap.put(note, str);
		}
	}

	/**
	 * 标有♭记号的notes,数组逆序查找
	 */
	public static void 	flatMajorScale() {
		int length = NOTES.length;
		String[] array = new String[length + 1];
		for (int i = 0; i <= NOTES.length; i++) {
			array[i] = NOTES[i % length] + "";
		}
		
		for (int i = length; i >= 0; i--) {
			int current = ((i * 5) - i) % length;
			String note = array[current];
			String[] str = new String[length + 1];
			for (int j = 0; j <= length; j++) {
				int index = (current + j) % length;
				String c = array[index];
				if(j == 3 && i != length) {
					str[j] = FLAT + "" + c;
					array[index] = FLAT + "" + c;
				}else {
					str[j] = c;
					array[index] = c;
				}
			}
			naturalMajorScaleMap.put(note, str);
		}
	}
	
	
	/**
	 * 标有♯记号的notes,数组正序查找
	 */
	public static void 	sharpMajorScale() {
		
		int length = NOTES.length;
		String[] array = new String[length + 1];
		for (int i = 0; i <= NOTES.length; i++) {
			array[i] = NOTES[i % length] + "";
		}
		
		for (int i = 0; i <= length; i++) {
			int current = ((i * 5) - i) % length;
			String note = array[current];
			String[] str = new String[length + 1];
			for (int j = 0; j <= length; j++) {
				int index = (current + j) % length;
				String c = array[index];
				if(j == 6 && i != 0) {
					str[j] = SHARP + "" + c;
					array[index] = SHARP + "" + c;
				}else {
					str[j] = c;
					array[index] = c;
				}
			}
			naturalMajorScaleMap.put(note, str);
		}
	}
}
