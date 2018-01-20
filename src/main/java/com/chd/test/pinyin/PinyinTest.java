package com.chd.test.pinyin;

import java.util.Arrays;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 
 * @author jumili
 *
 */
public class PinyinTest {

	public static final char[] a = {'a', 'ā', 'á', 'ǎ', 'à'}; 
	public static final char[] o = {'o', 'ō', 'ó', 'ǒ', 'ò'};
	public static final char[] e = {'e', 'ē', 'é', 'ě', 'è'};
	public static final char[] i = {'i', 'ī', 'í', 'ǐ', 'ì'};
	public static final char[] u = {'u', 'ū', 'ú', 'ǔ', 'ù'};
	public static final char[] v = {'ü', 'ǖ', 'ǘ', 'ǚ', 'ǜ'};
	
	public static void main(String[] args) throws Exception {
		String chinese = "成都";
		char[] cs = chinese.toCharArray();
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
		outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		for (int i = 0; i < cs.length; i++) {
			String[] array = PinyinHelper.toHanyuPinyinStringArray(cs[i], outputFormat);
			System.out.println(Arrays.toString(array));
		}
	}
}
