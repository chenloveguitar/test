package com.chd.test.video;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.spi.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;

public class VideoTest {

	private static final Logger logger = LogManager.getLogger(VideoTest.class);
	private static final String url = "https://www.loldytt.com/Dianshiju/RMDMY/";
	private static final WebClient client = new WebClient(BrowserVersion.CHROME);
	public static void main(String[] args) throws Exception{
		WebRequest request = new WebRequest(new URL(url), HttpMethod.GET);
		WebResponse webResponse = client.loadWebResponse(request);
		String string = webResponse.getContentAsString();
		String regex = "\"(thunder://QUF[a-zA-Z0-9=/]*)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
//		System.out.println(string);
		while(matcher.find()) {
			String group = matcher.group(1);
			System.out.println(group);
		}
	}
}
