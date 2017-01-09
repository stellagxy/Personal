import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class Vote extends TimerTask{
	public Vote(){
		
	}
	@Override
	public void run(){
		final WebClient w = new WebClient(BrowserVersion.CHROME);
		// w.setAjaxController(new NicelyResynchronizingAjaxController()); 
		HtmlPage htmlPage;
		try {
			htmlPage = w.getPage("http://lol.qq.com/act/a20161018allstar/index.htm?ADTAG=lol.landing&atm_cl=ad&atm_pos=6408&e_code=277218");
			w.getOptions().setCssEnabled(false);
			w.getOptions().setActiveXNative(false);
			w.getOptions().setThrowExceptionOnScriptError(false);
			w.waitForBackgroundJavaScript(1000 * 3);
			w.setJavaScriptTimeout(0); // utf-8
			String result = htmlPage.asXml();
			Document doc = Jsoup.parse(result,"UTF-8");
			Elements divs = doc.select("li[id^=prodlist");
			OutputStreamWriter out;
			try {
				out = new OutputStreamWriter(new FileOutputStream("vote.txt", true), "UTF-8");
				BufferedWriter bufferedWriter = new BufferedWriter(out);
				TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
				for (Element element : divs) {
			        String position = element.attributes().get("position");
			        String team = element.select("div > h3").html();
			        String name =  element.select("div > p").html();
			        String vote =  element.select("h4 > span").html();
			        String time = new Date(System.currentTimeMillis()).toString();
			        bufferedWriter.write(position + "\t" + team + "\t" + name + "\t" + vote + '\t' + time +"\n");
			    }
				bufferedWriter.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
