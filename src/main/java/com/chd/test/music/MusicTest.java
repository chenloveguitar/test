package com.chd.test.music;

public class MusicTest {

	// ̲6
	private static final int[] notes = { 0x0063, 0x0064, 0x0065, 0x0066, 0x0067, 0x0061, 0x0062 };// c-b
	private static final int[] NOTES = { 0x0043, 0x0044, 0x0045, 0x0046, 0x0047, 0x0041, 0x0042 };// C-B
	private static final int QUAVER = 0x0332;// ̲
	private static final int SEMIQUAVER = 0x0;// ̣
	// 以下字符支持QQ聊天窗口发送
	private static final int FLAT = 0x266d;// ♭
	private static final int NATURE = 0x266e;// ♮
	private static final int SHARP = 0x266f;// ♯

	// ⌒¨22 ̲ 6̣
	public static void main(String[] args) {

//		for (int i = 0x0100; i < 0x0500 + 10; i++) {
//			System.out.printf("%c%s%x%s", i, "\t", i, "\n");
//		}
		for (int i = 0,num = 7; i < NOTES.length; i++) {
			for (int j = 0; j <= i ; j++) {
				int n = num >> j == 0 ? num : num >> j;
				n = n >= 3 ? num >> j : (n << j) + j;
				System.out.print(n);
			}
			System.out.println();
		}
		
		System.out.println();
//		printNotes();
		// a加上不占位置的字符
		// int start = 0x0300;
		// int end = 0x036f;
		// for(int i = start; i < end;i++) {
		// System.out.printf("%c%s%s",i,"a,","\n");
		// }

		// ̲6̲6̲6
		// System.out.printf("%c",0x0332);
		// System.out.printf("%c",0x0036);
		// System.out.printf("%c",0x0332);
		// System.out.printf("%c",0x0036);
		// System.out.printf("%c",0x0332);
		// System.out.printf("%c",0x0036);

	}

	public static void printNotes() {
		int length = NOTES.length;
		for (int i = 0; i <= length; i++) {
			int current = ((i * 5) - i) % length;
			int next = ((i + 1) * 5 - (i + 1)) % length;
			int note = NOTES[current];
			if(i == 6 || i == 7) {
				System.out.printf("%c%c%s", SHARP, note, ":");
			}else {
				System.out.printf("%c%s", note, ":");
			}
			// System.out.println();
			// 7/2 = 3; 7 3
			// 
			//      7          1     
			//     3 7         2     
			//    6 3 7        3     
			//   2 6 3 7       4     
			//  5 2 6 3 7      5     
			// 1 5 2 6 3 7     6
			//4 1 5 2 6 3 7    7
			
			//7/2
			
			// 第一次循环 7-i = 7; i = 0
			// 第二次循环 7-i/2 = 3; i = 1;
			// 第三次循环 7-i*2 = 6; i = 2;
			int[] previous = new int[7];
			for (int j = 0; j <= length; j++) {
				for(int k = 0; k <= length; k++) {
				}
				if(j == 6 && i != 0) {
					System.out.printf("%c%c%s", SHARP, NOTES[(current + j) % length], "\t");
				}else {
					System.out.printf("%c%s", NOTES[(current + j) % length], "\t");
				}
				previous[j] = NOTES[(current + j) % length];
			}
			System.out.println();
		}
	}

	/* 该方法无法将F/C 转换成 #E/#B 的形式,故需从新定义算法 */
	public static void bate1() {

		String[] sharp = { "C", "#C", "D", "#D", "E", "F", "#F", "G", "#G", "A", "#A", "B" };
		String[] flat = { "C", "bD", "D", "bE", "E", "F", "bG", "G", "bA", "A", "bB", "B" };

		int mark = 7;// 7个半音
		int index = 0;
		for (int i = 0; i <= 7; i++) {
			String keynote = sharp[index];
			System.out.println(i + "个#为:");
			System.out.print(keynote + "大调音阶:");
			int flag = index;
			for (int j = 0; j < 8; j++) {
				String note = sharp[flag];
				System.out.print(note + ",");
				if (j == 2 || j == 6) {
					flag = flag + 1;
				} else {
					flag = flag + 2;
				}
				if (flag >= 12) {
					flag -= 12;
				}
			}
			System.out.println("");
			index += mark;
			if (index >= 12) {
				index -= 12;
			}
		}
	}
}
