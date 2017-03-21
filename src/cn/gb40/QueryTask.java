package cn.gb40;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.HttpContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * <pre>
 * http请求后台方法任务。
 * </pre>
 * @author 王文辉  946374340@qq.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class QueryTask implements Callable<JSONArray> {
	/**
	 * 当前页数
	 */
	private int i;
	/**
	 * client上下文
	 */
	private HttpContext context;
	/**
	 * 查询日期
	 */
	private String  date;



	/**构造方法
	 * @param i
	 * @param context
	 * @param date
	 * @param contentList
	 * @param jsonArray
	 */
	public QueryTask(int i, HttpContext context, String date ) {
		super();
		this.i = i;
		this.context = context;
		this.date = date;

	}

	/**
	 * 查询任务
	 * 返回数据json串
	 */
	public 	JSONArray  call() throws Exception {
	    	JSONArray temp=new JSONArray();
	    	try{
	    	 	String content=GetJsonData.query(date, context, i);
		     	if(StringUtils.isNotEmpty(content)){
		     	      JSONObject obj = JSONObject.parseObject(content);
		     	      if(obj!=null&&obj.getJSONArray("aaData")!=null){
		     	    	 temp=  obj.getJSONArray("aaData");
		     	      }
		     	}
	    	}catch(Exception e){
	    		System.out.println("[QueryTask.call] occur exception!,->"+e.getMessage());
	    	}
	    	return temp;
	}
}
