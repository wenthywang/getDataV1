package com.parseLog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ParseLog {

	private static String VIEWSTRING = "[INFO ] [Dispatch-Thread-for-org-cmu2owcydjvmyq-89]";
	private static String VIEWSTRING1 = "Average Dispatch order";
	private static final  File resultTxt=new File("C:\\Users\\Administrator\\Desktop\\result.txt");

	/**
	 * 用戶名，用戶行為，以key-value方式存储
	 * 用户行为 以json方式存储
	 * @throws Exception 
	 */
	public static void readFileByLines(String fileName) throws Exception {
		File file = new File(fileName);
		BufferedReader reader = null;
		FileOutputStream fos=null;
	      OutputStreamWriter osw=null;
	        BufferedWriter  bw=null;
	        fos=new FileOutputStream(resultTxt);
	        osw=new OutputStreamWriter(fos, "UTF-8");
	        bw=new BufferedWriter(osw);
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(isr);
			String tempString = null;
			Map<String, Object> map = new HashMap<String, Object>();
			// 一次读入一行，直到读入null为文件结束
			int count =0;
			while ((tempString = reader.readLine()) != null) {
				if (tempString.contains(VIEWSTRING)) {
					if(tempString.contains(VIEWSTRING1)){
						System.out.println(tempString);
						count++;
					     
					        //简写如下：
					        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					        //        new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8"));
					            bw.write(tempString+"\t\n");
					}
			}
//			Iterator<Entry<String, Object>> it = map.entrySet().iterator();
//			while (it.hasNext()) {
//				Entry<String, Object> entry = it.next();
//				System.out.println(entry.getKey() + "-----------" + entry.getValue());
//			}
		}
			  System.out.println(count);
			  bw.close();
		       osw.close();
		       fos.close();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					  bw.close();
				       osw.close();
				       fos.close();
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	/**
	 * 用戶名，用戶行為，以key-value方式存储
	 * 用户行为 以json方式存储
	 * @throws Exception 
	 */
	public static void findSameOrderNo(File f) throws Exception {
		File file =f;
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(isr);
			String tempString = null;
			List<String> list=new ArrayList<String>();
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				String orderNo=tempString.substring(tempString.indexOf("orderNo")+8, tempString.indexOf("orderNo")+14);
				list.add(orderNo);
				}
			for (int i = 0; i < list.size(); i++) {
				System.out.println("orderNo:"+list.get(i));
				for(int j=i+1;j<list.size();j++){
					if(list.get(i).equals(list.get(j))){
						System.out.println("存在相同工单号:"+list.get(j));
					}
				}
			}
//			Iterator<Entry<String, Object>> it = map.entrySet().iterator();
//			while (it.hasNext()) {
//				Entry<String, Object> entry = it.next();
//				System.out.println(entry.getKey() + "-----------" + entry.getValue());
//			}
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ParseLog.readFileByLines("C:\\Users\\Administrator\\Desktop\\jiaxin_gw_order.log");
//		ParseLog.findSameOrderNo(resultTxt);
	}

}
