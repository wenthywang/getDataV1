package cn.gb40;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.filechooser.FileSystemView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

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
						String url1="https://www.baidu.com";
						try{
							 sendHttpsGetUrl(null, url1, null);
						}catch(Exception e){
						
						}
					}
				});
				
				
//				ThreadUtil.executeThread(new Runnable() {
////					
//					@Override
//					public void run() {
//						String url="https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date=2017-01-24"
//								+ "&leftTicketDTO.from_station=GZQ"
//								+ "&leftTicketDTO.to_station=CBQ"
//								+ "&purpose_codes=ADULT";
//						try{
//							 sendHttpsGetUrl(null, url, null);
//						}catch(Exception e){
//						
//						}
//					}
//				});
			
			
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
		HttpGet g = new HttpGet(
				"https://files.jiaxincloud.com/nnvxytfuync0nq/mcs/2016/11/28/8a0849d5-dd96-4f03-a832-b6cad4f852e6.bmp");
		HttpResponse r = null;
		try {
			r = httpClient1.execute(g);
			HttpEntity respentity = r.getEntity();
//			String resp=EntityUtils.toString(respentity);
			System.out.println(respentity.getContentLength());
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
			httpPost.setHeader("Cookie", "x_hm_tuid=SiFVkJZzxQCtxO/mxEy9b/Q5LSuMutGEJ7s7CcAFUFkXGbGe5mW0OKOozQiAZUGm; cporder=ordervalue=6T4MXI4G4MAU%2fkvG0YwlvmGJ1quJ6ZHRMXkbVm%2bpjxb4dr9u6LFDSnmzM5JX6bpXhLcFZ4AcJyVcTworHghpzCxBkzRVu0VCmRFbT8H3bR6XdpJpejHk1bxH1VlnuVzUkZBjdOMF3PQ%3d; cporderV2=6T4MXI4G4MAU%2fkvG0YwlvmGJ1quJ6ZHRMXkbVm%2bpjxb4dr9u6LFDSnmzM5JX6bpXhLcFZ4AcJyVcTworHghpzCxBkzRVu0VCmRFbT8H3bR6XdpJpejHk1bxH1VlnuVzUkZBjdOMF3PQ%3d; _damai_sour=u2DQ6M02CViWf/6ki0CSk/xMtthY0Ixy9IAak9EAV5gdwyAjg9x6fg==; damai_login_QRCode=a46410ac-9484-465f-b059-9f42fe49a8d3; damai.cn_msgCount=0; damai.cn_user=4YZrINjO/OIJkhSfX66kXLb8IJs8mSdGVlOSRpb2dXysnuVK3QySdKoZgZ6rYih+; damai.cn_email=13660783361; damai.cn_nickName=%e8%be%89%e5%8f%94%e5%8f%94; damai_cn_user=4YZrINjO/OIJkhSfX66kXLb8IJs8mSdGVlOSRpb2dXysnuVK3QySdKoZgZ6rYih+; DaMaiTicketHistory=ProList=115685%402017%E4%BA%94%E6%9C%88%E5%A4%A9%20LIFE%20%5B%20%E4%BA%BA%E7%94%9F%E6%97%A0%E9%99%90%E5%85%AC%E5%8F%B8%20%5D%20%E5%B7%A1%E5%9B%9E%E6%BC%94%E5%94%B1%E4%BC%9A-%E5%B9%BF%E5%B7%9E%E7%AB%99%EF%BC%883%E6%9C%8825%E6%97%A5%EF%BC%89%40gz; cpSTAT_ok_pages=82; cpSTAT_ok_times=1; tkpid=Dx3LfDzyWm16MwoamOEmkKzLL1qtMfxoru6l0h4KXM4iJBjOnTveAo/uXjkDC0GB; CNZZDATA1256416394=1080610434-1486177823-https%253A%252F%252Fwww.damai.cn%252F%7C1486177823; page_tran_time=46.8747; cn_7415364c9dab5n09ff68_dplus=%7B%22distinct_id%22%3A%20%2215a0721311a3c8-01268cec72feae-3e64430f-15f900-15a0721311b3ab%22%2C%22%24_sessionid%22%3A%200%2C%22%24_sessionTime%22%3A%201486178882%2C%22%24dp%22%3A%200%2C%22%24_sessionPVTime%22%3A%201486178882%2C%22initial_view_time%22%3A%20%221486177823%22%2C%22initial_referrer%22%3A%20%22http%3A%2F%2Fappapi.damai.cn%2Fdamaiapi%2Fgotodamai.aspx%3F_action%3DGoURL%26uid%3Daaa893bbbareagzccc111500001ddddy%26api_key%3D05754b44-684e-4ef1-b69c-c49cf373ea53%26ProjectID%3D%26sorce%3D1%26host%3Dgz%2F%22%2C%22initial_referrer_domain%22%3A%20%22appapi.damai.cn%22%7D");
			// 执行POST请求
			HttpResponse response = httpClient.execute(httpPost); 
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			String s=EntityUtils.toString(entity);
			System.out.println(s);
			JSONObject obj=JSONObject.parseObject(s);
			if(obj.getInteger("Status")==200){
				JSONArray dataArray=obj.getJSONObject("Data").getJSONArray("prices");
				 Iterator<Object> it = dataArray.iterator();
				 
				 while (it.hasNext()) {
					 JSONObject ob = (JSONObject) it.next();
					 String result = ob.getString("Status");
					 String priceId=ob.getString("PriceID");
					 if(priceId.equals("11660892")||priceId.equals("11615680")){
						 if(result!=null&&result.equals("0")){
							String name=ob.getString("PriceName");
							 System.out.println(name+"->有票");
						 }
					 }
//				System.out.println(result);
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
