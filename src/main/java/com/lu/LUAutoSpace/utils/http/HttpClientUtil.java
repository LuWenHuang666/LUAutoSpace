package com.lu.LUAutoSpace.utils.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;

public class HttpClientUtil {
	
	private final HttpClientContext context = new HttpClientContext() ;  //初始化上下文实例
	
	public HttpClientUtil() {
        CookieStore cookieStore = new BasicCookieStore() ;
        context.setCookieStore(cookieStore);
	}
	
	public void get(String url){
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
            CloseableHttpResponse response = httpClient.execute(httpGet,context);
            HttpEntity httpEntity = response.getEntity();
            System.out.println(EntityUtils.toString(httpEntity,"utf-8"));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(httpGet!=null){
                    httpGet.releaseConnection();
                }
                if(httpClient!=null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void post(String url, Map<String, String> params){
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String pKey : params.keySet()) {
                ps.add(new BasicNameValuePair(pKey, params.get(pKey)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(ps));
            CloseableHttpResponse response = httpClient.execute(httpPost,context);
            
            HttpEntity httpEntity = response.getEntity();
            String string =  EntityUtils.toString(httpEntity,"UTF-8");
            System.out.println("response:"+string);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(httpPost!=null){
                    httpPost.releaseConnection();
                }
                if(httpClient!=null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void post(String url, String body){
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new StringEntity(body));
            CloseableHttpResponse response = httpClient.execute(httpPost,context);
            HttpEntity httpEntity = response.getEntity();
            System.out.println(EntityUtils.toString(httpEntity,"utf-8"));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(httpPost!=null){
                    httpPost.releaseConnection();
                }
                if(httpClient!=null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String doGet(String url){
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        String responseString = null;
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
            CloseableHttpResponse response = httpClient.execute(httpGet,context);
            HttpEntity httpEntity = response.getEntity();
            responseString = EntityUtils.toString(httpEntity,"utf-8");
            System.out.println("response"+responseString);
            
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(httpGet!=null){
                    httpGet.releaseConnection();
                }
                if(httpClient!=null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseString;
        
    }
    public String doPost(String url, Map<String, String> params){
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String responseString = null;
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String pKey : params.keySet()) {
                ps.add(new BasicNameValuePair(pKey, params.get(pKey)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(ps));
            CloseableHttpResponse response = httpClient.execute(httpPost,context);
            HttpEntity httpEntity = response.getEntity();
            responseString =  EntityUtils.toString(httpEntity,"UTF-8");
            System.out.println("response:"+responseString);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(httpPost!=null){
                    httpPost.releaseConnection();
                }
                if(httpClient!=null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseString;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String URL = "http://www.hzgoo.cn/index.php/Seller/Login/checkLogin";
		Map<String, String> params = new HashMap<>();
		params.put("username", "15862637184");
		params.put("password", "test123456");
		params.put("verify", "");
		String responseString = httpClientUtil.doPost(URL, params);
		ZsonResult result = ZSON.parseJson(responseString);
		System.out.println(result);
		System.out.println(result.getValue("//info"));
		String URL1 = "http://www.hzgoo.cn/index.php/Seller/Index/index";
		//httpClientUtil.doGet(URL1);

	}

}
