package cn.gb40;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * Description：Http访问工具类
 * 
 * @author chenmaode<br/>
 * @version 1.0<br/>
 * @date 2015-12-26
 *
 */
public class HttpUtil {

    static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    static String proxyHost = "172.32.100.81";
    static int proxyPort = 8080;
    static HttpHost proxy = new HttpHost(proxyHost, proxyPort);
    static HttpClienProxy httpProxy = new HttpClienProxy();
    static boolean isEnableProxy = false;

    /**
     * 发送post请求
     * 
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public static String sendPost(String url, JSONObject param) throws Exception {
        logger.debug("[HttpUtil.sendPost] url:" + url + ", param:" + param);
        String resultJson = null;
        HttpClient httpClient = httpProxy.initHttpClientWithoutSoTimeOut();
        try {
            if (isEnableProxy) {
                httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            }
            HttpPost httpost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (String key : param.keySet()) {
                nvps.add(new BasicNameValuePair(key, param.getString(key)));
            }
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            HttpResponse resp = httpClient.execute(httpost);
            if (resp != null) {
                resultJson = EntityUtils.toString(resp.getEntity());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        logger.debug("[HttpUtil.sendPost] resultJson:" + resultJson);
        return resultJson;
    }


    /**
     * 发送post请求
     * 
     * @param url
     * @param param
     * @param localContext
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public static String sendPost(String url, JSONObject param, HttpContext localContext)
            throws Exception {
        logger.debug("[HttpUtil.sendPost] url:" + url + ", param:" + param);
        String resultJson = null;
        HttpClient httpClient = httpProxy.initHttpClientWithoutSoTimeOut();
        try {
            if (isEnableProxy) {
                httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            }
            HttpGet httget= new HttpGet(url);
//            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//            for (String key : param.keySet()) {
//                nvps.add(new BasicNameValuePair(key, param.getString(key)));
//            }
//            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            HttpResponse resp = httpClient.execute(httget, localContext);
            if (resp != null) {
                resultJson = EntityUtils.toString(resp.getEntity());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
      System.out.println(resultJson);
        return resultJson;
    }

    public static String get(String url, String param, HttpContext context) throws Exception {
        String content = null;
        HttpClienProxy httpClienProxy = new HttpClienProxy();
        HttpMessage httpMessage = new HttpMessage();
        httpMessage.setRequestUrl(generateGetUrl(url, param));
        httpMessage.setMethod(HttpMessage.METHOD_GET);

        HttpClient httpclient = httpClienProxy.initHttpClientWithoutSoTimeOut();
        try {
            HttpResponse httpResponse =
                    httpClienProxy.sendRequest(httpclient, httpMessage, context,false);
            if (httpResponse != null
                    && HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
                content = EntityUtils.toString(httpResponse.getEntity());
            } else if (httpResponse != null) {
                logger.warn("request url: {} response status: {}", url,
                        httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        return content;
    }

    private static String generateGetUrl(String url, String param) {
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        try {
            JSONObject paramJson = JSON.parseObject(param);
            builder.append("?");
            for (String key : paramJson.keySet()) {
                String value = URLEncoder.encode(paramJson.getString(key));
                builder.append(key).append("=").append(value).append("&");
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
        } catch (Exception e) {
        }
        return builder.toString();
    }

    private static String generatePostBody(String param) {
        StringBuilder builder = new StringBuilder();
        JSONObject paramJson = JSON.parseObject(param);
        //处理多图文参数相同问题
        if(paramJson.containsKey("isManyRichText")){
            String paramString=paramJson.getString("paramStr");
            builder.append(paramString);
        }else{
            for (String key : paramJson.keySet()) {
                String value = URLEncoder.encode(paramJson.getString(key));
                builder.append(key).append("=").append(value).append("&");
            }
        }
     
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public static String post(String url,String type, String param, HttpContext context) throws Exception {
        String content = null;
        HttpClienProxy httpClienProxy = new HttpClienProxy();
        HttpMessage httpMessage = new HttpMessage();
        httpMessage.setRequestUrl(url);
        httpMessage.setMethod(HttpMessage.METHOD_POST);
        httpMessage.addHeader("Content-Type", "application/"+type+";charset=UTF-8");
        httpMessage.setBody(generatePostBody(param));
        HttpClient httpclient = httpClienProxy.initHttpClientWithoutSoTimeOut();
        try {
            HttpResponse httpResponse =
                    httpClienProxy.sendRequest(httpclient, httpMessage, context,false);
            if (httpResponse != null
                    && HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
                content = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
            } else if (httpResponse != null) {
            	System.out.println( httpResponse.getStatusLine().getStatusCode());
        } 
            }catch (Exception e) {
            throw e;
        } finally {
            if (httpclient != null) {
//                httpclient.getConnectionManager().shutdown();
            }
        }
        return content;
    }
    



}
