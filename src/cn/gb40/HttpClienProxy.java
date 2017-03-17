package cn.gb40;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.CharEncoding;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/**
 * Comment
 * 
 * @author zcg<br/>
 * @version 1.0<br/>
 * @email: zcg@pci-suntektech.com<br/>
 * @datetime: 2013-7-25 <br/>
 */
public class HttpClienProxy {

    static final String BOUNDARY = "----------V2ymHFg03ehbqgZCaKO6jy";

    /**
     * 连接超时
     */
    private static final int HTTP_CONNECTION_TIMEOUT = 4000;
    /**
     * 请求超时
     */
    private static final int HTTP_REQUEST_TIMEOUT = 10000;

    /**
     * 初始化HttpClient
     * 
     * @return
     */
    public HttpClient initHttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, HTTP_CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, HTTP_REQUEST_TIMEOUT);
        return new DefaultHttpClient(params);
    }

    public HttpResponse sendRequest(HttpClient httpclient, HttpMessage message) throws IOException {
        return this.sendRequest(httpclient, message, true);
    }

    public HttpResponse sendRequest(HttpClient httpclient, HttpMessage message, HttpContext context)
            throws IOException {
        return this.sendRequest(httpclient, message, context, true);
    }

    public HttpResponse sendRequest(HttpClient httpclient, HttpMessage message, boolean closeHttp)
            throws IOException {
        if (HttpMessage.METHOD_GET.equalsIgnoreCase(message.getMethod())) {
            return httpGet(httpclient, message, closeHttp);
        } else if (HttpMessage.METHOD_POST.equalsIgnoreCase(message.getMethod())) {
            return httpPost(httpclient, message, closeHttp);
        } else if (HttpMessage.METHOD_PUT.equalsIgnoreCase(message.getMethod())) {
            return httpPut(httpclient, message, closeHttp);
        } else if (HttpMessage.METHOD_DELETE.equalsIgnoreCase(message.getMethod())) {
            return httpDelete(httpclient, message, closeHttp);
        }
        return null;
    }

    public HttpResponse sendRequest(HttpClient httpclient, HttpMessage message,
            HttpContext context, boolean closeHttp) throws IOException {
        if (HttpMessage.METHOD_GET.equalsIgnoreCase(message.getMethod())) {
            return httpGet(httpclient, message, context, closeHttp);
        } else if (HttpMessage.METHOD_POST.equalsIgnoreCase(message.getMethod())) {
            return httpPost(httpclient, message, context, closeHttp);
        } else if (HttpMessage.METHOD_PUT.equalsIgnoreCase(message.getMethod())) {
            return httpPut(httpclient, message, context, closeHttp);
        } else if (HttpMessage.METHOD_DELETE.equalsIgnoreCase(message.getMethod())) {
            return httpDelete(httpclient, message, closeHttp);
        }
        return null;
    }

    private HttpResponse httpGet(HttpClient httpclient, HttpMessage message, boolean closeHttp) {
        HttpResponse response = null;
        try {
            HttpGet httpget = new HttpGet(message.getRequestUrl());
            // 初始HTTP请求头
            handleRequestHeader(httpget, message);
            response = httpclient.execute(httpget);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null && closeHttp) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        return response;
    }

    private HttpResponse httpPost(HttpClient httpclient, HttpMessage message, boolean closeHttp)
            throws IOException {

        HttpResponse response = null;

        try {
            HttpPost httppost = new HttpPost(message.getRequestUrl());

            // 初始HTTP请求头
            handleRequestHeader(httppost, message);

            HttpEntity entity = new StringEntity(message.getBody(), CharEncoding.UTF_8);
            httppost.setEntity(entity);

            response = httpclient.execute(httppost);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null && closeHttp) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        return response;
    }

    private HttpResponse httpGet(HttpClient httpclient, HttpMessage message, HttpContext context,
            boolean closeHttp) {
        HttpResponse response = null;
        try {

            HttpGet httpget = new HttpGet(message.getRequestUrl());
            // 初始HTTP请求头
            handleRequestHeader(httpget, message);
            response = httpclient.execute(httpget, context);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null && closeHttp) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        return response;
    }

    private HttpResponse httpPost(HttpClient httpclient, HttpMessage message, HttpContext context,
            boolean closeHttp) throws IOException {

        HttpResponse response = null;

        try {
            HttpPost httppost = new HttpPost(message.getRequestUrl());

            // 初始HTTP请求头
            handleRequestHeader(httppost, message);

            HttpEntity entity = new StringEntity(message.getBody(), CharEncoding.UTF_8);
            httppost.setEntity(entity);

            response = httpclient.execute(httppost, context);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null && closeHttp) {
//                httpclient.getConnectionManager().shutdown();
            }
        }
        return response;
    }

    /**
     * 初始化Header
     * 
     * @param request
     * @param message
     */
    private void handleRequestHeader(HttpRequestBase request, HttpMessage message) {
        List<HttpHeader> list = message.getHeaders();
        for (HttpHeader header : list) {
            if (!request.containsHeader(header.getKey()))
                request.addHeader(header.getKey(), header.getValue());
        }

        String contentType = message.getContentType();
        String attachPath = message.getAttachPath();
        if (message.isFrom() && attachPath != null && !"".equals(attachPath)) {
            contentType += "; boundary=" + BOUNDARY;
        }

        // 设置ContentType
        if ((request instanceof HttpPut) || (request instanceof HttpPost)) {
            request.addHeader("Content-Type", contentType);
        }
    }

    private HttpResponse httpPut(HttpClient httpclient, HttpMessage message, boolean closeHttp) {

        HttpResponse response = null;

        try {

            HttpPut httpput = new HttpPut(message.getRequestUrl());
            // 初始HTTP请求头
            handleRequestHeader(httpput, message);
            HttpEntity entity = new StringEntity(message.getBody(), CharEncoding.UTF_8);
            httpput.setEntity(entity);

            response = httpclient.execute(httpput);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null && closeHttp) {
                httpclient.getConnectionManager().shutdown();
            }
        }

        return response;
    }

    private HttpResponse httpPut(HttpClient httpclient, HttpMessage message, HttpContext context, boolean closeHttp) {

        HttpResponse response = null;

        try {

            HttpPut httpput = new HttpPut(message.getRequestUrl());
            // 初始HTTP请求头
            handleRequestHeader(httpput, message);
            HttpEntity entity = new StringEntity(message.getBody(), CharEncoding.UTF_8);
            httpput.setEntity(entity);

            response = httpclient.execute(httpput, context);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null && closeHttp) {
                httpclient.getConnectionManager().shutdown();
            }
        }

        return response;
    }

    private HttpResponse httpDelete(HttpClient httpclient, HttpMessage message, boolean closeHttp) {

        HttpResponse response = null;
        try {

            HttpDelete httpdelete = new HttpDelete(message.getRequestUrl());
            // 初始HTTP请求头
            handleRequestHeader(httpdelete, message);
            response = httpclient.execute(httpdelete);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null && closeHttp) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        return response;
    }

    public HttpResponse uploadFile(HttpClient httpclient, HttpMessage message, String filename,
            InputStream stream, HttpContext context, boolean closeHttp) throws IOException {
        HttpResponse response = null;
        try {
            HttpPost httppost = new HttpPost(message.getRequestUrl());

            // 初始HTTP请求头
            handleRequestHeader(httppost, message);

            InputStreamBody body = new InputStreamBody(stream, filename);
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("uploadFile", body);

            httppost.setEntity(entity);

            response = httpclient.execute(httppost, context);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null && closeHttp) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        return response;
    }
    
   /**
   * 初始化client
   * 不添加请求超时参数
   * 添加请求超时参数导致httpclient读取返回数据异常
   * @return
   */
    public HttpClient initHttpClientWithoutSoTimeOut() {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, HTTP_CONNECTION_TIMEOUT);
        return new DefaultHttpClient(params);
    }
}
