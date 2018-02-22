package com.chd.test.html;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class HTTPClientTest {
	private static final Logger logger = LogManager.getLogger(HTTPClientTest.class);
	private static final WebClient client = new WebClient(BrowserVersion.CHROME);
	private static final String requestUrl = "http://cuishou.emai888.com/rainbow/job/list";
	private static final String cookie = "SESSION=27a33f11-0dca-412f-8004-61ec9a3b7a9b; gr_user_id=3003dfe0-4dc3-45f1-920d-656f0366e695; gr_session_id_8ba41a6d48ba28a7=7c9fdd40-2fde-44cb-9cc2-82e920c80f78; token=cc5a2bd6-4b7b-43fb-b363-f4c391a1168a285078";
	private static final File file = new File("C:\\Users\\CHD\\Desktop\\new.xls");
	private static WritableWorkbook workbook = null;
	private static WritableSheet ws = null;
	private static int column = 0;
	static {
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			workbook = Workbook.createWorkbook(file);
			ws = workbook.createSheet("通讯录", 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		getList();
		long endTimeMillis = System.currentTimeMillis();
		System.out.println("总共耗时:"+((endTimeMillis-currentTimeMillis)/1000)+"秒");
	}
	
	public static void getList() throws Exception {
		WebRequest webRequest = new WebRequest(new URL(requestUrl), HttpMethod.POST);
		webRequest.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded");
		webRequest.setAdditionalHeader("Cookie", cookie);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		NameValuePair pageNameValuePair = new NameValuePair("page", "1");
		NameValuePair rowsNameValuePair = new NameValuePair("rows", String.valueOf(Integer.MAX_VALUE));
		requestParameters.add(pageNameValuePair);
		requestParameters.add(rowsNameValuePair);
		webRequest.setRequestParameters(requestParameters);
		WebResponse webResponse = client.loadWebResponse(webRequest);
		int statusCode = webResponse.getStatusCode();
		if (statusCode == 200) {
			JSONObject result = JSONObject.parseObject(webResponse.getContentAsString());
			Integer total = result.getInteger("total");
			System.out.println(total);
			JSONArray rows = result.getJSONArray("rows");
			Label nameLabel = new Label(0, 0, "姓名");
			Label mobileLabel = new Label(1, 0, "手机号码");
			ws.addCell(nameLabel);
			ws.addCell(mobileLabel);
			column++;
			for (int i = 0; i < total; i++) {
				JSONObject row = rows.getJSONObject(i);
				String idCard = row.getString("idCard");
				String userName = row.getString("userName");
				String mobile = row.getString("mobile");
				String source = row.getString("source");
//				System.out.println("姓名:" + userName + " 手机:" + mobile + " 源:"+source+" 身份证:"+idCard);
				getDetail(idCard, source);
			}
		}
		workbook.write();// 写入数据
		workbook.close();// 关闭文件
	}
	
	public static void getDetail(String idCard,String source) throws Exception {
		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(false);
		String pageNumber = "1",pageSize = "1";
		String url = "http://cuishou.emai888.com/rainbow/job/detail";
		WebRequest webRequest = new WebRequest(new URL(url), HttpMethod.POST);
		webRequest.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded");
		webRequest.setAdditionalHeader("Cookie", cookie);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		NameValuePair pageNumberNameValuePair = new NameValuePair("pageNumber", pageNumber);
		NameValuePair pageSizeNameValuePair = new NameValuePair("pageSize", pageSize);
		NameValuePair idCardNameValuePair = new NameValuePair("idCard", idCard);
		NameValuePair sourceNameValuePair = new NameValuePair("source", source);
		requestParameters.add(pageNumberNameValuePair);
		requestParameters.add(pageSizeNameValuePair);
		requestParameters.add(idCardNameValuePair);
		requestParameters.add(sourceNameValuePair);
		webRequest.setRequestParameters(requestParameters);
		HtmlPage htmlPage = client.getPage(webRequest);
		String text = htmlPage.asXml();
		Map<String, String> detail = regExpDetail(text);
	}
	
	public static Map<String, String> regExpDetail(String pageText) throws Exception {
		String regex = "var database = ([^;]+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pageText);
		Map<String, String> result = new HashMap<String,String>();
		while(matcher.find()) {
			String find = matcher.group(1);
			JSONObject detail = JSONObject.parseObject(find);
			String userName = detail.getString("userName");
			String mobileShow = detail.getString("mobileShow");
			String idCard = detail.getString("idCard");
			String source = detail.getString("source");
			String mobile = detail.getString("mobile");
			result.put("name", userName);
			result.put("mobile", mobileShow);
			System.out.println(userName + ":" + mobileShow);
			jxl(userName,mobileShow);
		}
		return result;
	}
	
	public static void jxl(String userName,String mobileShow) throws Exception{
		Label nameLabel = new Label(0, column, userName);
		Label mobileLabel = new Label(1, column, mobileShow);
		ws.addCell(nameLabel);
		ws.addCell(mobileLabel);
		column++;
	}
}
