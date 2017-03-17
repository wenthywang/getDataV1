package cn.gb40;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 
 * <pre>
 * httpClient 请求后台数据并生成excel 报表。
 * </pre>
 * @author wangwenhui   946374340@qq.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class HttpClientTest {
	public  static String root="";
	private static int pageSize=1000;


	/**
	 * 初始化桌面路径
	 */
    static  {
		File desktopDir = FileSystemView.getFileSystemView()
				.getHomeDirectory();
			String desktopPath = desktopDir.getAbsolutePath();
			root=desktopPath+"\\";
			
			
	}
	
    /**
     * 初始化配置
     */
	private static	RequestConfig requestConfig = RequestConfig.custom()
			.setConnectTimeout(6000000)
			.setConnectionRequestTimeout(6000000)
			.setSocketTimeout(6000000)
			.setCookieSpec(CookieSpecs.STANDARD_STRICT).
		
			build();
	/**
	 * 初始化client
	 */
	private static   CloseableHttpClient httpClient = HttpClients.custom().
			setDefaultRequestConfig(requestConfig)
			
			.build();


   //导出目录以及操作目录
	private    static  File resultTxt=new File(root+"result.txt");
	//下载验证码路径
	private static final String codeSource=root+"code\\code.jpg";
	

	
	
	/**
	 * 主函数
	 * @param date 选择日期
	 * @return 结果总数
	 * @throws Exception
	 */
	public static int main( final String  date) throws Exception {
		
		 //尝试登陆 登陆成功则不重新登陆
		String content="";
		HttpContext context=null;
		List<String>contentList=new ArrayList<String>();
		JSONArray json =new JSONArray();
		try {
			 context=successLogin(true);
			if(context!=null){
				content=	query(date,context,1);
				contentList.add(content);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj =null;
		int result=0;
		if(StringUtils.isNotEmpty(content)){
			 obj = JSONObject.parseObject(content);
			  result=obj.getIntValue("iTotalRecords");
			  System.out.println("记录总数->"+result);
			  if(result>0){
				  int pageCount=result/pageSize;
				  int hasNextPage=result%pageSize;
				  if(hasNextPage>0){
					  pageCount++;
				  }
				  
				  if(pageCount>1){
					  for(int i=2;i<=pageCount;i++){
							content=	query(date,context,i);
						  contentList.add(content);
					  }
				  }
				  for (String s : contentList) {
					  obj = JSONObject.parseObject(s);
					JSONArray temp=  obj.getJSONArray("aaData");
					json.addAll(temp);
				}
			  }
			 
		}
			
			//根据返回的数据判断是否登陆成功，登陆失败后会抛出异常，外层尝试重新登陆
			final JSONArray jsonFinal = json;
			if(result>0){
				FileCopyUtils.copy(json.toJSONString().getBytes(), resultTxt);
				try{
				//导出数据 使用线程池 分开线线程执行
					     ThreadPoolUtil.executorService.execute(new Runnable() {
								@Override
								public void run() {
									 try {
										 //执行文章id任务
										Test_1.main(jsonFinal,date);
									} catch (FileNotFoundException e) {
				                       e.printStackTrace();
									}
								}
							});
					     ThreadPoolUtil.executorService.execute(new Runnable() {
								@Override
								public void run() {
									 try {
										 //执行标题线程
										Test_2.main(jsonFinal,date);
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
                
          
                 
//			if (json.size() > 0) {
//				System.out.println("文章总数："+json.size());
//				for (int i = 0; i < json.size(); i++) {
//					JSONObject job = json.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//					System.out.println("文章标题="+job.get("title")); // 得到 每个对象中的属性值
//				}
//			}
		} catch ( Exception e) {
					FileCopyUtils.copy(content.getBytes(), resultTxt);
			}
//			if (json.size() > 0) {
//				System.out.println("文章总数："+json.size());
//				for (int i1 = 0; i1 < json.size(); i1++) {
//					JSONObject job = json.getJSONObject(i1); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//					System.out.println("文章标题="+job.get("title")); // 得到 每个对象中的属性值
//				}
//			}
			}
			
			return result;
	}
	
	/**
	 * 打印响应信息
	 * @param httpResponse
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static boolean printResponse(HttpResponse httpResponse)
			throws ParseException, IOException {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		boolean result = false;
		// 响应状态
		System.out.println("status:" + httpResponse.getStatusLine());
		if (httpResponse.getStatusLine().toString().indexOf("302") > 0) {
			result = true;
		}
		
//		System.out.println("headers:");
//		HeaderIterator iterator = httpResponse.headerIterator();
//		while (iterator.hasNext()) {
//			 iterator.next();
//			System.out.println("\t" + iterator.next());
//		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			System.out.println("response length:" + responseString.length());
			System.out.println("response content:" + responseString.replace("\r\n", ""));
			
		}
		return result;
	}
	
	
	
/**
 * 获取验证码登陆后台-登陆失败用-全部请求需要用同一个context 
 * @return Map<String,Object>
 * @throws ClientProtocolException
 * @throws IOException
 */
	public static Map<String,Object> login() throws ClientProtocolException, IOException{
		         HttpGet getCaptcha = new
				 HttpGet("http://xmiles.cn/xmiles-manager/system/vacode.jsp");
			       CookieStore cookieStore = new BasicCookieStore();
			       // 创建一个本地上下文信息
			       HttpContext localContext = new BasicHttpContext();
			       // 在本地上下问中绑定一个本地存储
			       localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
				 CloseableHttpResponse imageResponse = httpClient.execute(getCaptcha,localContext);
				
				 FileOutputStream out = new FileOutputStream(codeSource);
				 byte[] bytes = new byte[8192];
				 int len;
				 while ((len = imageResponse.getEntity().getContent().read(bytes)) != -1) {
				 out.write(bytes,0,len);
				 }
				 out.close();
				 String validateCode ="";
				 File f=new File(codeSource);
				 try {
				 validateCode = ImageTest.getValidateCode(f);
				 } 
				 catch (Exception e) {
					 System.out.println("[login] occur exception!"+e.getMessage());
					 e.printStackTrace();
				 }finally{
					 //删除验证码图片
					 f.delete();
				 }
				 System.out.println(validateCode);
				 MessageDigest md = null;
				 try {
				 md = MessageDigest.getInstance("MD5");
				 } catch (NoSuchAlgorithmException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 }
				 String password="sqz@2015#";
				 md.update(password.getBytes());
				 //通过执行诸如填充之类的最终操作完成哈希计算。
				 byte b[] = md.digest();
				 //生成具体的md5密码到buf数组
				 int i;
				 StringBuffer buf = new StringBuffer("");
				 for (int offset = 0; offset < b.length; offset++) {
				 i = b[offset];
				 if (i < 0)
				 i += 256;
				 if (i < 16)
				 buf.append("0");
				 buf.append(Integer.toHexString(i));
				 }
				 System.out.println("32位: " + buf.toString());// 32位的加密
				 password= buf.toString().toUpperCase();
				 
				 //构造post数据
				 List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
				 valuePairs.add(new BasicNameValuePair("userId", "sheqiaozhi"));
				 valuePairs.add(new BasicNameValuePair("password",password));
				 valuePairs.add(new BasicNameValuePair("valicode", validateCode));
				 valuePairs.add(new BasicNameValuePair("autologin", "on"));
				 UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
				// 创建一个post请求
				HttpPost post = new HttpPost("http://xmiles.cn/xmiles-manager/system/login.action");
			      
				// //注入post数据
				 post.setEntity(entity);
				HttpResponse httpResponse = httpClient.execute(post,localContext);
				Map<String,Object>dataMap=new HashMap<String,Object>();
				
				dataMap.put("localContext", localContext);
				dataMap.put("result", printResponse(httpResponse));
				dataMap.put("response", httpResponse);
				return dataMap;
	}
	
	
	
	/**
	 * 调用后台获取业务数据
	 * @param c cookie 
	 * @param date 查询时间
	 * @return CloseableHttpResponse
	 */
	public static String query(String date,HttpContext context,int pageNum){
		String result=null;
		String url="http://xmiles.cn/xmiles-manager/discovery/daily/querydiscoverystat.action";
		int startCount=(pageNum-1)*pageSize;
		String aoData="[{'name':'sEcho','value':"+pageNum+"},{'name':'iColumns','value':8},"
				+ "{'name':'sColumns','value':'title,stylename,categoryname,pushdate,PV,UV,ck_module,discoveryid'},"
				+ "{'name':'iDisplayStart','value':"+startCount+"},{'name':'iDisplayLength','value':"
		         +pageSize+"},"
				+ "{'name':'mDataProp_0','value':'title'},{'name':'sSearch_0','value':''},{'name':'bRegex_0','value':false},{'name':'bSearchable_0','value':true},{'name':'bSortable_0','value':false},{'name':'mDataProp_1','value':'stylename'},"
				+ "{'name':'sSearch_1','value':''},{'name':'bRegex_1','value':false},{'name':'bSearchable_1','value':true},{'name':'bSortable_1','value':false},{'name':'mDataProp_2','value':'categoryname'},{'name':'sSearch_2','value':''},{'name':'bRegex_2','value':false},{'name':'bSearchable_2','value':true},"
				+ "{'name':'bSortable_2','value':false},{'name':'mDataProp_3','value':'pushdate'},{'name':'sSearch_3','value':''},"
				+ "{'name':'bRegex_3','value':false},{'name':'bSearchable_3','value':true},{'name':'bSortable_3','value':false},"
				+ "{'name':'mDataProp_4','value':'PV'},{'name':'sSearch_4','value':''},{'name':'bRegex_4','value':false},"
				+ "{'name':'bSearchable_4','value':true},{'name':'bSortable_4','value':true},{'name':'mDataProp_5','value':'UV'},"
				+ "{'name':'sSearch_5','value':''},{'name':'bRegex_5','value':false},{'name':'bSearchable_5','value':true},"
				+ "{'name':'bSortable_5','value':true},{'name':'mDataProp_6','value':'ck_module'},{'name':'sSearch_6','value':''},"
				+ "{'name':'bRegex_6','value':false},{'name':'bSearchable_6','value':true},{'name':'bSortable_6','value':false},"
				+ "{'name':'mDataProp_7','value':'discoveryid'},{'name':'sSearch_7','value':''},{'name':'bRegex_7','value':false},"
				+ "{'name':'bSearchable_7','value':true},{'name':'bSortable_7','value':false},{'name':'sSearch','value':''},"
				+ "{'name':'bRegex','value':false},{'name':'iSortCol_0','value':4},{'name':'sSortDir_0','value':'desc'},{'name':'iSortingCols','value':1}]";
		   JSONObject json = new JSONObject();
	        json.put("fromDate", date);
	        json.put("jquery-table_length", pageSize);
	        json.put("aoData", aoData);
	        json.put("discoveryid", "");
			try {
				result = HttpUtil.post(url, "x-www-form-urlencoded", json.toJSONString(),context);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
		
	}
	
	
	/**
	 * 登陆失败时可用，因为验证码不是100% 所以要用循环登陆直到登陆成功
	 * @param check
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
   public  static HttpContext successLogin(boolean check) throws Exception{
	   HttpContext localContext =null;
	   while(check){
   	    Map<String,Object>data=login();
   	    if((boolean) data.get("result")){
		     	 System.out.println("登陆成功");
		     	localContext=(HttpContext) data.get("localContext");
					break;
		     }else{
		      	 System.out.println("登陆失败");
		     }
      }
	   return localContext;
   }
   
}
