package live;

// v5.1 set time out and access_token
// v5.0 add twitch
// v4.5 access token
// v4.4 update status failure & huya replay
// v4.2 comment correct
// v4.0 add longzhu

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Timer;

public class Time {
	
	public static void main(String[] args) { 
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		//boolean isTXT = Boolean.valueOf(args[0]);
		//String access_token1 = args[1];
		//String access_token2 = args[2];
		String access_token1 = "2.00rrhzTGwFFKyD8d06c2f2e90Dj3mt";
		String access_token2 = "2.00x74ZHEeDVg8Bf4e804e6cf0xHThY";
		boolean isTXT = true;
		HashMap<String, String> urlToName = new HashMap<>();
		HashMap<String, Integer> urlToTime = new HashMap<>();
		HashMap<String, String> urlToStart = new HashMap<>();
		HashMap<String, Integer> urlToMonth = new HashMap<>();
        HashMap<String, Integer> zhanqiLive = new HashMap<>();
        HashMap<String, Integer> pandaLive = new HashMap<>();
        HashMap<String, Integer> douyuLive = new HashMap<>();
        HashMap<String, Integer> huyaLive = new HashMap<>();
        HashMap<String, Integer> quanminLive = new HashMap<>();
        HashMap<String, Integer> afreecaLive = new HashMap<>();
        HashMap<String, Integer> longzhuLive = new HashMap<>();
        HashMap<String, Integer> twitchLive = new HashMap<>();
        
        try {
        	File file=new File("url.txt");
        	if(file.isFile() && file.exists()){ 
        		InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");//
        		BufferedReader bufferedReader = new BufferedReader(read);
        		String line = null;
        		//System.out.println("1");
        		while((line = bufferedReader.readLine()) != null){
        			String[] nameUrl = line.split("\t");
        			if(nameUrl.length != 2){
        				continue;
        			}
        			urlToName.put(nameUrl[1], nameUrl[0]);
        			if(nameUrl[1].contains("zhanqi")){
        				zhanqiLive.put(nameUrl[1], 0);
        			}else if(nameUrl[1].contains("douyu")){
        				douyuLive.put(nameUrl[1], 0);
        			}else if(nameUrl[1].contains("panda")){
        				pandaLive.put(nameUrl[1], 0);
        			}else if(nameUrl[1].contains("huya")){
        				huyaLive.put(nameUrl[1], 0);
        			}else if(nameUrl[1].contains("quanmin")){
        				quanminLive.put(nameUrl[1], 0);
        			}else if(nameUrl[1].contains("afreeca")){
        				afreecaLive.put(nameUrl[1], 0);
        			}else if(nameUrl[1].contains("longzhu")){
        				longzhuLive.put(nameUrl[1], 0);
        			}else if(nameUrl[1].contains("twitch")){
        				twitchLive.put(nameUrl[1], 0);
        			}

        		}
        		read.close();
        	}else{
        		System.out.println("找不到指定的文件");
        	}
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        String filename;
		
		for(String url : urlToName.keySet()){
			filename = "anchor\\" + urlToName.get(url) + ".txt";
        	File file=new File(filename);
        	if(!file.exists()){
        		try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
		}
        if(isTXT){
        	try {
        		File st = new File("status.txt");
        		if(st.isFile() && st.exists()) {
        			InputStreamReader read = new InputStreamReader(new FileInputStream(st));//
        			BufferedReader bufferedReader = new BufferedReader(read);
        			String line = null;
        		//System.out.println("1");
        			while((line = bufferedReader.readLine()) != null){
        				String[] nameStatus = line.split("\t");
        				if(nameStatus.length != 6){
            				continue;
            			}
        				if(!nameStatus[3].equals("null")){
        					urlToStart.put(nameStatus[2], nameStatus[3]);
        				}
        				if(!nameStatus[4].equals("null")){
        					urlToTime.put(nameStatus[2], Integer.valueOf(nameStatus[4]));
        				}
        				if(!nameStatus[5].equals("null")){
        					urlToMonth.put(nameStatus[2], Integer.valueOf(nameStatus[5]));
        				}
        				if(nameStatus[2].contains("zhanqi")){
            				zhanqiLive.put(nameStatus[2], Integer.valueOf(nameStatus[1]));
            			}else if(nameStatus[2].contains("douyu")){
            				douyuLive.put(nameStatus[2], Integer.valueOf(nameStatus[1]));
            			}else if(nameStatus[2].contains("panda")){
            				pandaLive.put(nameStatus[2], Integer.valueOf(nameStatus[1]));
            			}else if(nameStatus[2].contains("huya")){
            				huyaLive.put(nameStatus[2], Integer.valueOf(nameStatus[1]));
            			}else if(nameStatus[2].contains("quanmin")){
            				quanminLive.put(nameStatus[2], Integer.valueOf(nameStatus[1]));
            			}else if(nameStatus[2].contains("afreeca")){
            				afreecaLive.put(nameStatus[2], Integer.valueOf(nameStatus[1]));
            			}else if(nameStatus[2].contains("longzhu")){
            				longzhuLive.put(nameStatus[2], Integer.valueOf(nameStatus[1]));
            			}else if(nameStatus[2].contains("twitch")){
            				twitchLive.put(nameStatus[2], Integer.valueOf(nameStatus[1]));
            			}
            		}
            		read.close();
        		}else{
        		System.out.println("找不到指定的文件");
        	}
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        }
        
        Timer timer = new Timer();  
        timer.schedule(new LiveStatus(urlToName, urlToStart, urlToTime, urlToMonth, access_token1, access_token2, zhanqiLive, 
        		huyaLive, douyuLive, pandaLive, quanminLive, afreecaLive, longzhuLive, twitchLive), 0, 5*60000);  
        		
    }  
}
