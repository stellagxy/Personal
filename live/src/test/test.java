package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.model.Comment;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

public class test {

	public static void main(String[] arg) throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		String access_token = "2.00rrhzTGwFFKyD85c2175cf60VEHjT";
		String access_token2 = "2.00x74ZHEeDVg8Be95056ec0cyvPjLD";
		String statuses = "test2";
		Timeline tm = new Timeline(access_token2);
		Comments cm = new Comments(access_token2);
		String id = "-1";
/*
		try {
			Status status = tm.updateStatus(statuses);
			id = status.getId();
			// Log.logInfo(status.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
*/
		try {
			Comment ct = cm.createComment(
					"@王锐雯要好好学习_ ",
					"3999383316138109");

		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Comments cm2 = new Comments(access_token2);
			try {
				Comment ct2 = cm2.createComment("test", id);
			} catch (WeiboException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

}
