package cn.gb40;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.filechooser.FileSystemView;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <pre>
 * 模拟登陆-httpClien-https的网址
 * </pre>
 * @author 王文辉  wangwenhui@jiaxincloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@SuppressWarnings("deprecation")
public class HttpClientTest2 {
	
	
	public  static String root="";
	/**
	 * 初始化桌面路径
	 */
    static  {
		File desktopDir = FileSystemView.getFileSystemView()
				.getHomeDirectory();
			String desktopPath = desktopDir.getAbsolutePath();
			root=desktopPath+"\\";
	}
	
	private    static  File resultTxt=new File(root+"trainInfo.txt");

	
	public static void main(String [] args) throws Exception {
//		orderAction();
		while(true){

			//模拟访问12306 官网 获取余票信息
				ThreadUtil.executeThread(new Runnable() {
					
					@Override
					public void run() {
						String url1="https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date=2017-01-23"
								+ "&leftTicketDTO.from_station=GZQ"
								+ "&leftTicketDTO.to_station=CBQ"
								+ "&purpose_codes=ADULT";
						try{
							 sendHttpsGetUrl(null, url1, null);
						}catch(Exception e){
						
						}
					}
				});
				
				
				ThreadUtil.executeThread(new Runnable() {
//					
					@Override
					public void run() {
						String url="https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date=2017-01-24"
								+ "&leftTicketDTO.from_station=GZQ"
								+ "&leftTicketDTO.to_station=CBQ"
								+ "&purpose_codes=ADULT";
						try{
							 sendHttpsGetUrl(null, url, null);
						}catch(Exception e){
						
						}
					}
				});
			
			
		}
		
		
		
		
	
	}
	
	
	
	
	
	public static void orderAction() throws ClientProtocolException, IOException{
		
		HttpClient httpClient1 = new DefaultHttpClient();
//		HttpClient httpClient =		httpClient1;
		
		// 创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("SSL");
			
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			
			SSLSocketFactory sf = new SSLSocketFactory(
					ctx,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", 443, sf);
			httpClient1.getConnectionManager().getSchemeRegistry().register(sch);
			}catch(Exception e){
				
			}
		
		//下单操作 REPEAT_SUBMIT_TOKEN和key_check_isChange 这两个参数的定义不清楚
		HttpPost g = new HttpPost(
				"https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue");
		g.setHeader("Cookie", "__NRF=47FBEDB9BE5B556676B9784A09A32C30; JSESSIONID=0A01D72AFCCE092B055AEA8777AB7E9BEF693BC0D5; BIGipServerotn=718733578.64545.0000; _jc_save_fromStation=%u5E7F%u5DDE%2CGZQ; _jc_save_toStation=%u6F6E%u6C55%2CCBQ; _jc_save_fromDate=2017-01-23; _jc_save_toDate=2016-12-29; _jc_save_wfdc_flag=dc; current_captcha_type=Z");
		List<NameValuePair> valuePairs2 = new LinkedList<NameValuePair>();
		valuePairs2.add(new BasicNameValuePair("purpose_codes", "00"));
		valuePairs2.add(new BasicNameValuePair("key_check_isChange", UUID.randomUUID().toString()));
		valuePairs2.add(new BasicNameValuePair("leftTicketStr", "W0kTOWxZe1gTaP3BqFt3MklsxVPjr0o4%2BynYGZn1A2j0Lmj8%2FvVyS2RxusE%3D"));
		valuePairs2.add(new BasicNameValuePair("train_location", "Q7"));
		valuePairs2.add(new BasicNameValuePair("seatDetailType", "000"));
		valuePairs2.add(new BasicNameValuePair("roomType", "00"));
		valuePairs2.add(new BasicNameValuePair("dwAll", "N"));
		valuePairs2.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", UUID.randomUUID().toString()));
		UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(valuePairs2, Consts.UTF_8);
		g.setEntity(entity1);
		HttpResponse r = null;
		try {
			r = httpClient1.execute(g);
			HttpEntity respentity = r.getEntity();
			String resp=EntityUtils.toString(respentity);
			System.out.println(resp);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
/**
 * 初始化https无证书，再发送请求
 * @param httpClient1
 * @param url
 * @param contextType
 */
	public static void sendHttpsGetUrl(HttpClient httpClient1 ,String url,String contextType) {
		// 创建默认的httpClient实例
		@SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();
//		HttpClient httpClient =		httpClient1;
		
		// 创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("SSL");
			
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			
			SSLSocketFactory sf = new SSLSocketFactory(
					ctx,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", 443, sf);
			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			
			//上面是初始化httpClient 访问https 无证书
			
			// 创建HttpGet
			HttpGet httpPost = new HttpGet(url); 
			httpPost.setHeader("content-type", "text/html; charset=utf-8");
			// 执行POST请求
			HttpResponse response = httpClient.execute(httpPost); 
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			String s=EntityUtils.toString(entity);
			System.out.println(s);
			JSONObject obj=JSONObject.parseObject(s);
			if(obj.getInteger("httpstatus")==200){
				JSONArray dataArray=obj.getJSONArray("data");
				 Iterator<Object> it = dataArray.iterator();
				 
				 while (it.hasNext()) {
					 JSONObject ob = (JSONObject) it.next();
					 JSONObject result = ob.getJSONObject("queryLeftNewDTO");
					 if(result!=null){
							String isHasBill=result.getString("ze_num");
							if(isHasBill.equals("无")||isHasBill.equals("--")){
							}else{
									HttpPost g = new HttpPost(
											"https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue");
									g.setHeader("Cookie", "__NRF=47FBEDB9BE5B556676B9784A09A32C30; JSESSIONID=0A01D72AFCCE092B055AEA8777AB7E9BEF693BC0D5; BIGipServerotn=718733578.64545.0000; _jc_save_fromStation=%u5E7F%u5DDE%2CGZQ; _jc_save_toStation=%u6F6E%u6C55%2CCBQ; _jc_save_fromDate=2017-01-23; _jc_save_toDate=2016-12-29; _jc_save_wfdc_flag=dc; current_captcha_type=Z");
									List<NameValuePair> valuePairs2 = new LinkedList<NameValuePair>();
									valuePairs2.add(new BasicNameValuePair("purpose_codes", "00"));
									valuePairs2.add(new BasicNameValuePair("key_check_isChange", UUID.randomUUID().toString()));
									valuePairs2.add(new BasicNameValuePair("leftTicketStr", result.getString("yp_info")));
									valuePairs2.add(new BasicNameValuePair("train_location", "Q7"));
									valuePairs2.add(new BasicNameValuePair("seatDetailType", "000"));
									valuePairs2.add(new BasicNameValuePair("roomType", "00"));
									valuePairs2.add(new BasicNameValuePair("dwAll", "N"));
									valuePairs2.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", UUID.randomUUID().toString()));
									UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(valuePairs2, Consts.UTF_8);
									g.setEntity(entity1);
									HttpResponse r = null;
									try {
										r = httpClient.execute(g);
										HttpEntity respentity = r.getEntity();
										String resp=EntityUtils.toString(respentity);
										System.out.println(resp);
									} catch (ClientProtocolException e) {
										e.printStackTrace();
									} catch (IOException e) {
										e.printStackTrace();
									}
								
								
								String start_train_date=result.getString("start_train_date");
								String year=start_train_date.substring(0, 4);
								String month=start_train_date.substring(4, 6);
								String day=start_train_date.substring(6);
								start_train_date=year+"-"+month+"-"+day;
								String start_time=result.getString("start_time");
								String arrive_time=result.getString("arrive_time");
								String content="有票->"+"start_time->"+start_time+",arrive_time->"+arrive_time+",开车时间->"+start_train_date;
								System.out.println(content);
								   FileReader fr=new FileReader(resultTxt);
									 String info=FileCopyUtils.copyToString(fr);
									 content=content+"/n"+info;
							   FileCopyUtils.copy(content.getBytes(), resultTxt);
							}
					 }
				
					 }
			}
//			 bs = IOUtils.toByteArray(entity.getContent());
//			 if (null != entity) {
//					EntityUtils.consume(entity); // Consume response content
//				}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			httpClient.getConnectionManager().closeExpiredConnections();
		}
	}
   
   
}
