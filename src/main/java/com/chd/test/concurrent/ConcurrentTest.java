package com.chd.test.concurrent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ConcurrentTest {
	private static int thread_num = 20000;// 200;
	private static int client_num = 20000;// 460;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ExecutorService exec = Executors.newCachedThreadPool();
		// thread_num���߳̿���ͬʱ����
		final Semaphore semp = new Semaphore(thread_num);
		// ģ��2000���ͻ��˷���
		for (int index = 0; index < client_num; index++) {
			final int NO = index;
			exec.execute(new TaskThread(semp, NO));
		}
		long timeSpend = (System.currentTimeMillis() - start) / 1000;
		System.out.println("����1: " + timeSpend + "��");
		// �˳��̳߳�
		exec.shutdown();
		timeSpend = System.currentTimeMillis() - start;
		System.out.println("����2: " + timeSpend + "��");
		// long end = (System.currentTimeMillis()-start)/1000;//��ǰʱ���뵱��0��ĺ�����

	}

	static class TaskThread implements Runnable {
		Semaphore semp;
		int NO;

		public TaskThread(Semaphore semp, int NO) {
			this.semp = semp;
			this.NO = NO;
		}

		@Override
		public void run() {
			try {
				// ��ȡ���
				semp.acquire();
				// String host =
				// "http://10.99.23.42:7001/KMQueryCenter/query.do?";
				// String para = "method=getQueryResult&pageNum=1&pageSize=5&" +
				// "queryKeyWord=" + getRandomSearchKey(NO)
				// + "&questionID=-1&questionIdPath=-1&searchType=1" +
				// "&proLine=&proSeries=&proType=" + NO;

				// http://192.168.2.115:8080/revitbus/revit/list.html?&tagId=12-&type=0&system=1&year=2016
				String host = "http://localhost:9090/test/concurrent";
				String para = "&type=0&system=1&year=2016";

				System.out.println(host + para);
				URL url = new URL(host);// �˴���д�����Ե�url
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Proxy-Connection", "Keep-Alive");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				PrintWriter out = new PrintWriter(connection.getOutputStream());
				out.print(para);
				out.flush();
				out.close();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = "";
				String result = "";
				while ((line = in.readLine()) != null) {
					result += line;
				}
				// System.out.println(result);
				// Thread.sleep((long) (Math.random()) * 1000);
				// �ͷ�
				System.out.println("�ڣ�" + NO + " ��");
				// System.out.println(result);
				semp.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

	}

}