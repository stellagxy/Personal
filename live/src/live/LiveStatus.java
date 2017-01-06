package live;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.model.Comment;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

//import weibo4j.Timeline;
//import weibo4j.model.Status;
//import weibo4j.model.WeiboException;

public class LiveStatus extends TimerTask {
	private HashMap<String, Integer> zhanqiLive;
	private HashMap<String, Integer> huyaLive;
	private HashMap<String, Integer> douyuLive;
	private HashMap<String, Integer> pandaLive;
	private HashMap<String, Integer> quanminLive;
	private HashMap<String, Integer> afreecaLive;
	private HashMap<String, Integer> longzhuLive;
	private HashMap<String, Integer> twitchLive;
	private HashMap<String, String> urlToName;
	private HashMap<String, Integer> urlToTime;
	private HashMap<String, Integer> urlToMonth;
	private HashMap<String, String> urlToStart;
	private static String access_token1;
	private static String access_token2;

	public LiveStatus(HashMap<String, String> urlToName, String access_token1, String access_token2, HashMap<String, Integer> zhanqiLive,
			HashMap<String, Integer> huyaLive, HashMap<String, Integer> douyuLive, HashMap<String, Integer> pandaLive,
			HashMap<String, Integer> quanminLive, HashMap<String, Integer> afreecaLive,
			HashMap<String, Integer> longzhuLive, HashMap<String, Integer> twitchLive) {
		this.zhanqiLive = zhanqiLive;
		this.huyaLive = huyaLive;
		this.douyuLive = douyuLive;
		this.pandaLive = pandaLive;
		this.quanminLive = quanminLive;
		this.afreecaLive = afreecaLive;
		this.longzhuLive = longzhuLive;
		this.twitchLive = twitchLive;
		this.urlToName = urlToName;
		LiveStatus.access_token1 = access_token1;
		LiveStatus.access_token2 = access_token2;

	}
	public LiveStatus(HashMap<String, String> urlToName, HashMap<String, String> urlToStart, HashMap<String, Integer> urlToTime,
			HashMap<String, Integer> urlToMonth, String access_token1, String access_token2, HashMap<String, Integer> zhanqiLive,
			HashMap<String, Integer> huyaLive, HashMap<String, Integer> douyuLive, HashMap<String, Integer> pandaLive,
			HashMap<String, Integer> quanminLive, HashMap<String, Integer> afreecaLive,
			HashMap<String, Integer> longzhuLive, HashMap<String, Integer> twitchLive) {
		this.zhanqiLive = zhanqiLive;
		this.huyaLive = huyaLive;
		this.douyuLive = douyuLive;
		this.pandaLive = pandaLive;
		this.quanminLive = quanminLive;
		this.afreecaLive = afreecaLive;
		this.longzhuLive = longzhuLive;
		this.twitchLive = twitchLive;
		this.urlToName = urlToName;
		LiveStatus.access_token1 = access_token1;
		LiveStatus.access_token2 = access_token2;
		this.urlToTime = urlToTime;
		this.urlToStart = urlToStart;
		this.urlToMonth = urlToMonth;

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

		//LiveStatus t = new LiveStatus(urlToName, urlToStart, urlToTime, urlToMonth, access_token1, access_token2, zhanqiLive, huyaLive, douyuLive, pandaLive, quanminLive,
		//		afreecaLive, longzhuLive, twitchLive);

		System.out.print("--------------PandaTV\n");
		for (String anchor : pandaLive.keySet()) {
			
				int previousStatus = pandaLive.get(anchor);
				int status = getPandaStatus(anchor, "GBK");
				int flag = 1;
				if (previousStatus == 0 && status == 1) {
					
					Date startDate = new Date();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(startDate);
					int month = -1;
					if (urlToMonth.containsKey(anchor)){
						month = urlToMonth.get(anchor);
					}
					if(calendar.get(Calendar.MONTH) != month - 1){
						urlToTime.replace(anchor, 0);
						urlToMonth.replace(anchor, calendar.get(Calendar.MONTH) + 1);
					}
					urlToStart.put(anchor, startDate.toString());
					flag = reminder(urlToName.get(anchor), anchor, urlToTime.get(anchor));
				} else if (previousStatus == 1 && status == 0) {
					String start = urlToStart.get(anchor);
					try {
						Date startDate = new SimpleDateFormat().parse(start);
						
						int time = 0;
						if (urlToTime.containsKey(anchor)){
							time = urlToTime.get(anchor);
						}
						urlToTime.put(anchor, time + (int) (new Date().getTime() - startDate.getTime() / 1000/ 60));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (status != -1 && status != previousStatus && flag == 1) {
					pandaLive.replace(anchor, status);
				}
			

		}
/*
		System.out.print("--------------ZhanqiTv\n");
		for (String anchor : zhanqiLive.keySet()) {
			int previousStatus = zhanqiLive.get(anchor);
			int status = getZhanqiStatus(anchor, "GBK");
			int flag = 1;
			if (previousStatus == 0 && status == 1) {
				flag = reminder(urlToName.get(anchor), anchor);
			}
			if (status != -1 && status != previousStatus && flag == 1) {
				zhanqiLive.replace(anchor, status);
			}
		}

		System.out.print("--------------DouyuTv\n");
		for (String anchor : douyuLive.keySet()) {
			int previousStatus = douyuLive.get(anchor);
			int status = getDouyuStatus(anchor, "GBK");
			int flag = 1;
			if (previousStatus == 0 && status == 1) {
				flag = reminder(urlToName.get(anchor), anchor);
			}
			if (status != -1 && status != previousStatus && flag == 1) {
				douyuLive.replace(anchor, status);
			}
		}

		System.out.print("--------------HuyaTv\n");
		for (String anchor : huyaLive.keySet()) {
			int previousStatus = huyaLive.get(anchor);
			int status = getHuyaStatus(anchor, "GBK");
			int flag = 1;
			if (previousStatus == 0 && status == 1) {
				flag = reminder(urlToName.get(anchor), anchor);
			}
			if (status != -1 && status != previousStatus && flag == 1) {
				huyaLive.replace(anchor, status);
			}
		}

		System.out.print("--------------QuanminTv\n");
		for (String anchor : quanminLive.keySet()) {
			int previousStatus = quanminLive.get(anchor);
			int status = getQuanminStatus(anchor, "GBK");
			int flag = 1;
			if (previousStatus == 0 && status == 1) {
				flag = reminder(urlToName.get(anchor), anchor);
			}
			if (status != -1 && status != previousStatus && flag == 1) {
				quanminLive.replace(anchor, status);
			}
		}

		System.out.print("--------------AfreecaTv\n");
		for (String anchor : afreecaLive.keySet()) {

			int previousStatus = afreecaLive.get(anchor);
			int status = getAfreecaStatus(anchor, "GBK");
			int flag = 1;
			if (previousStatus == 0 && status == 1) {
				flag = reminder(urlToName.get(anchor), anchor);
			}
			if (status != -1 && status != previousStatus && flag == 1) {
				afreecaLive.replace(anchor, status);
			}

		}

		System.out.print("--------------longzhuTV\n");
		for (String anchor : longzhuLive.keySet()) {

			int previousStatus = longzhuLive.get(anchor);
			int status = getLongzhuStatus(anchor, "UTF-8");
			int flag = 1;
			if (previousStatus == 0 && status == 1) {
				flag = reminder(urlToName.get(anchor), anchor);
			}
			if (status != -1 && status != previousStatus && flag == 1) {
				longzhuLive.replace(anchor, status);

			}

		}

		System.out.print("--------------twitchTV\n");
		for (String anchor : twitchLive.keySet()) {

			int previousStatus = twitchLive.get(anchor);
			int status = getTwitchStatus(anchor, "UTF-8");
			int flag = 1;
			if (previousStatus == 0 && status == 1) {
				flag = reminder(urlToName.get(anchor), anchor);
			}
			if (status != -1 && status != previousStatus && flag == 1) {
				twitchLive.replace(anchor, status);

			}

		}
		*/
		File file = new File("status.txt");
		if (file.exists()) {
			file.delete();
		}

		
		displayWithTime(quanminLive, urlToName, urlToStart, urlToTime, urlToMonth);
		displayWithTime(zhanqiLive, urlToName, urlToStart, urlToTime, urlToMonth);
		displayWithTime(douyuLive, urlToName, urlToStart, urlToTime, urlToMonth);
		displayWithTime(pandaLive, urlToName, urlToStart, urlToTime, urlToMonth);
		displayWithTime(huyaLive, urlToName, urlToStart, urlToTime, urlToMonth);
		displayWithTime(afreecaLive, urlToName, urlToStart, urlToTime, urlToMonth);
		displayWithTime(longzhuLive, urlToName, urlToStart, urlToTime, urlToMonth);
		displayWithTime(twitchLive, urlToName, urlToStart, urlToTime, urlToMonth);
		System.out.print("--------------Rest\n");
	}
	public static void displayWithTime(HashMap<String, Integer> live, HashMap<String, String> urlToName,
			HashMap<String, String> urlToStart, HashMap<String, Integer> urlToTime,HashMap<String, Integer> urlToMonth){
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("status.txt", true), "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(out);
			for (String anchor : live.keySet()) {
				bufferedWriter.write(urlToName.get(anchor) + "\t" + live.get(anchor) + "\t"
			+ anchor + "\t" 
			+ urlToStart.get(anchor) + "\t" + urlToTime.get(anchor) +"\t" + urlToMonth.get(anchor) +"\n");
			}
			bufferedWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void display(HashMap<String, Integer> live, HashMap<String, String> urlToName) {

		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("status.txt", true), "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(out);
			for (String anchor : live.keySet()) {
				bufferedWriter.write(urlToName.get(anchor) + "\t" + live.get(anchor) + "\t" + anchor + "\n");
			}
			bufferedWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * for(String anchor : live.keySet()) {
		 * System.out.println(nameToURL.get(anchor) + " " + live.get(anchor)); }
		 */
	}
	public static int reminder(String name, String url, int time) {

		/*
		// String access_token2 = "2.00x74ZHEeDVg8B9299dcfe458i6paE";
		String statuses = name + "开播了" + url;
		// String statuses = "t";
		String filename = "anchor\\" + name + ".txt";
		String id = "-1";
		try {
			Timeline tm = new Timeline(access_token1);
			Status status = tm.updateStatus(statuses);
			id = status.getId();
			// Log.logInfo(status.toString());
		} catch (WeiboException e) {
			System.err.print("\nError-----------\n" + name + "\n");
			e.printStackTrace();
			switch (e.getErrorCode()) {
			case 20111:
				return 1;
			case 20019:
				return 1;
			}

		}

		if (id.equals("-1")) {
			return 0;
		} else {
			try {
				File file = new File(filename);

				if (file.isFile() && file.exists()) {
					InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");//
					BufferedReader bufferedReader = new BufferedReader(read);
					String line = null;
					Comments cm = new Comments(access_token1);
					while ((line = bufferedReader.readLine()) != null) {
						try {
							Comment ct = cm.createComment(line, id);
						} catch (WeiboException e) {
							e.printStackTrace();
							switch (e.getErrorCode()) {
							case 10024:
								Comments cm2 = new Comments(access_token2);
								Comment ct2 = cm2.createComment(line, id);
							}

						}
					}
					read.close();
					bufferedReader.close();
				} else {
					System.out.println("找不到指定的文件");
				}
			} catch (Exception e) {
				System.out.println("读取文件内容出错");
				e.printStackTrace();
			}
		}
		return 1;
*/
		MyThread thread = new MyThread(name, url, time);
		thread.start();
		return 1;

	}

	public int getTwitchStatus(String pageUrl, String encoding) {
		String[] split = pageUrl.split("/");
		if (split.length >= 2) {
			String roomID = split[split.length - 1];
			String newUrl = "https://api.twitch.tv/kraken/streams/" + roomID + "?stream_type=all&on_site=1";
			String result = null;
			try {
				URL url = new URL(newUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Client-ID", "jzkbprff40iqj646a697cyrvl0zt2m6");
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				result = rd.readLine();
				if (result == null) {
					return -1;
				}
				if (!result.contains("stream")) {
					return -1;
				} else if (result.contains("viewers")){
					return 1;
				} else{
					return 0;
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}
		return -1;

	}

	public int getAfreecaStatus(String pageUrl, String encoding) {
		String result = null;
		try {

			URL url = new URL(pageUrl);
			URLConnection c = url.openConnection();
			c.setConnectTimeout(10000);
			c.setReadTimeout(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), encoding));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.contains("<meta property=\"og:updated_time\"")) {
					result = line;
					break;
				}

			}
			in.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
		if (result == null) {
			return -1;
		}
		String[] split = result.split("\"");
		if (split.length == 5) {
			if (split[3].equals("")) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return -1;
		}

	}

	public int getLongzhuStatus(String pageUrl, String encoding) {
		String result = null;
		try {

			URL url = new URL(pageUrl);
			URLConnection c = url.openConnection();
			c.setConnectTimeout(10000);
			c.setReadTimeout(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), encoding));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.contains("id=\"live-title\"")) {
					result = line;
					break;
				}

			}
			in.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
		if (result == null) {
			return -1;
		}
		if (result.contains("主播现在不在，看下其它直播和视频吧")) {

			return 0;
		} else {
			return 1;
		}

	}

	public int getQuanminStatus(String pageUrl, String encoding) {
		String result = null;
		String[] split = pageUrl.split("/");
		if (split.length >= 4) {
			String roomID = split[split.length - 1];
			String newUrl = "http://www.quanmin.tv/json/rooms/" + roomID + "/info.json";
			try {
				URL url = new URL(newUrl);
				URLConnection c = url.openConnection();
				c.setConnectTimeout(10000);
				c.setReadTimeout(10000);
				BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), encoding));
				String line;
				while ((line = in.readLine()) != null) {
					if (line.contains("play_status")) {
						result = line;
						break;
					}

				}
				in.close();
			} catch (Exception ex) {
				System.err.println(ex);
			}
			if (result == null) {
				return -1;
			}
			int index = result.indexOf("play_status");
			if (index + 17 < result.length() && index != -1) {
				String status = result.substring(index + 13, index + 17);
				if (status.equals("true")) {
					return 1;
				} else if (status.equals("fals")) {
					return 0;
				} else {
					return -1;
				}
			} else {
				return -1;
			}
		} else {
			return -1;
		}

	}

	public int getPandaStatus(String pageUrl, String encoding){
		String result = null;
		String[] split = pageUrl.split("/");
		if (split.length >= 2) {
			String roomID = split[split.length - 1];
			String newUrl = "http://www.panda.tv/api_room_v2?roomid=" + roomID;
			
			try {
				URL url = new URL(newUrl);
				URLConnection c = url.openConnection();
				c.setConnectTimeout(10000);
				c.setReadTimeout(10000);
				BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), encoding));
				result = in.readLine();
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			if (result == null){
				return -1;
			}
			int index = result.indexOf("videoinfo");
			if (index < 0) {
				return -1;
			}
			int index2 = result.indexOf("status", index);
			if (index2 < 0) {
				return -1;
			}
			int status = Integer.valueOf(result.substring(index2 + 9, index2 + 10));
			if (status == 3) {
				return 0;
			} else if (status == 2) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return -1;
		}

	}

	/*
	 * public int getPandaStatus(String pageUrl) throws
	 * FailingHttpStatusCodeException, MalformedURLException, IOException {
	 * final WebClient w = new WebClient(BrowserVersion.CHROME);// 鍒濆鍖栦竴涓祻瑙堝櫒
	 * 
	 * 
	 * //w.setAjaxController(new NicelyResynchronizingAjaxController()); final
	 * HtmlPage htmlPage = w.getPage(pageUrl);
	 * w.getOptions().setCssEnabled(false);
	 * w.getOptions().setActiveXNative(false);
	 * w.getOptions().setThrowExceptionOnScriptError(false);
	 * w.waitForBackgroundJavaScript(1000*3); w.setJavaScriptTimeout(0); //utf-8
	 * String result = htmlPage.asXml(); int index = result.indexOf(
	 * "class=\"room-head-info-cate room-head-info-playing\""); if(index < 0){
	 * return 0; }else{ return 1; }
	 * 
	 * 
	 * }
	 */
	public int getHuyaStatus(String pageUrl, String encoding) {
		String result = null;
		try {

			URL url = new URL(pageUrl);
			URLConnection c = url.openConnection();
			c.setConnectTimeout(10000);
			c.setReadTimeout(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), encoding));
			String line;

			while ((line = in.readLine()) != null) {
				if (line.contains("isNotLive")) {
					result = line;
					break;
				}

			}
			in.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
		if (result == null) {
			return 0;// maybe var REPLAY = 1
		}
		if (result.length() != 0) {
			if (result.contains("1")) {

				return 0;
			} else {
				return 1;
			}
		} else {
			return -1;
		}
	}

	public int getZhanqiStatus(String pageUrl, String encoding) {
		
		String result = null;
		try {

			URL url = new URL(pageUrl);
			URLConnection c = url.openConnection();
			c.setConnectTimeout(10000);
			c.setReadTimeout(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), encoding));
			String line;

			while ((line = in.readLine()) != null) {
				if (line.contains("window.oPageConfig.oRoom")) {
					result = line;
					break;
				}

			}
			in.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
		if (result == null) {
			return -1;
		}
		int index = result.indexOf("\"status\"");
		if (index + 11 < result.length() && index != -1) {
			int status = Integer.valueOf(result.substring(index + 10, index + 11));
			if (status == 4) {
				return 1;
			} else if (status == 0) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

	public int getDouyuStatus(String pageUrl, String encoding) {
		String result = null;
		try {
			URL url = new URL(pageUrl);

			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), encoding));
			String line;

			while ((line = in.readLine()) != null) {
				if (line.contains("var $ROOM =")) {
					result = line;
					break;
				}

			}
			in.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}

		if (result == null) {

			return -1;
		}
		int index = result.indexOf("\"show_status\"");
		if (index + 14 < result.length() && index != -1) {
			int status = Integer.valueOf(result.substring(index + 14, index + 15));
			if (status == 1) {
				return 1;
			} else if (status == 2) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

}
