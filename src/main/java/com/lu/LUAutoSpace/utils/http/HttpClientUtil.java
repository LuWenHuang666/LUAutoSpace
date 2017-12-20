package com.lu.LUAutoSpace.utils.http;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;

public class HttpClientUtil {
	
	private final HttpClientContext context = new HttpClientContext() ;  //初始化上下文实例
	private CloseableHttpClient httpClient = null;  //
	private RequestConfig requestConfig = null;
	
	public HttpClientUtil() {
		// 自动保存cookies
        CookieStore cookieStore = new BasicCookieStore() ;
        context.setCookieStore(cookieStore);
        // 创建连接
        httpClient = HttpClients.createDefault();
        requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
        
        
	}
	
	
	/** 使用代理 
	 * @param host
	 * @param post
	 */
	public void setProxy(String host, int post) {
		requestConfig = RequestConfig.copy(requestConfig).setProxy(new HttpHost(host, post)).build();
	}
	
	public void closeHttpClient() {
        try {
            if(httpClient!=null){
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        //CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        String responseString = null;
        try {
            //httpClient = HttpClients.createDefault();
            //RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
            CloseableHttpResponse response = httpClient.execute(httpGet,context);
            HttpEntity httpEntity = response.getEntity();
            responseString = EntityUtils.toString(httpEntity,"utf-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(httpGet!=null){
			    httpGet.releaseConnection();
			}
			/*if(httpClient!=null){
			    httpClient.close();
			}*/
        }
        //System.out.println("请求url： " + url);
        //System.out.println("responseString： " + responseString);
        return responseString;
        
    }
    public String doPost(String url, Map<String, String> params){
        //CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String responseString = null;
        try {
            //httpClient = HttpClients.createDefault();
            //RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String pKey : params.keySet()) {
                ps.add(new BasicNameValuePair(pKey, params.get(pKey)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(ps,HTTP.UTF_8));
            CloseableHttpResponse response = httpClient.execute(httpPost,context);
            HttpEntity httpEntity = response.getEntity();
            responseString =  EntityUtils.toString(httpEntity,"UTF-8");
        } catch (UnknownHostException e) {
        	System.out.println("Host连接异常，请检查网络 和接口 host：");
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(httpPost!=null){
			    httpPost.releaseConnection();
			}
			/*if(httpClient!=null){
			    httpClient.close();
			}*/
        }
        //System.out.println("请求url： " + url);
        //System.out.println("responseString： " + responseString);
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
		System.out.println("responseString= "+responseString);
		System.out.println("result= "+result);
		System.out.println(result.getValue("//info"));
		String URL1 = "http://www.hzgoo.cn/index.php/Seller/Index/index";
		//httpClientUtil.doGet(URL1);

	}

}
