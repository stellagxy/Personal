package live;

import javax.swing.JOptionPane;

public class MyThread extends Thread{

	private String url;
	private String name;
	private int time;
	
	public MyThread(String name, String url, int time) 
	{ 
		this.name = name; 
		this.url = url;
		this.time = time;
	} 
	public void run() 
	{ 
		JOptionPane.showMessageDialog(null, name + "开播了  " + url + ",本月已播" + time + "分钟", "开播提醒", JOptionPane.ERROR_MESSAGE); 
	} 
}
