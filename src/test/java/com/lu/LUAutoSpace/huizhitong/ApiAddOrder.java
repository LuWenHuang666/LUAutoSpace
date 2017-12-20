package com.lu.LUAutoSpace.huizhitong;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lu.LUAutoSpace.utils.db.JdbcUtil;
import com.lu.LUAutoSpace.utils.http.HttpClientUtil;
import com.lu.LUAutoSpace.utils.random.RandomUtil;
import com.zf.zson.ZSON;

public class ApiAddOrder {
	private static Logger logger = Logger.getLogger(TestMoney.class);
	private static HttpClientUtil http;
	private static JdbcUtil jdbc;
	RandomUtil randomUtil = new RandomUtil();
	private static String hostString = "http://www.hzgoo.cn";
	
  @Test(dataProvider="addOrder_Data")
  public void addOrder(String mid, String product_id, String assertStatus, String caseName) {
/*	  String mid = "6265";            //被帮扶人 user_id
	  String product_id = "2114";*/
	  String consignee_id = "4951";
	  
	  logger.info("api提交订单 : " + caseName);
	  Map<String, String> params_addOrder = new HashMap<>();
	  params_addOrder.put("params", "{\"mid\":" + mid + ",\"consignee_id\":" + consignee_id + ",\"products\":[{\"product_id\":" + product_id + ",\"key\":\"" + product_id + "\",\"quantity\":1}],\"remarks\":\"lujianhuang测试数据\"}");
	  String response_addOrder = http.doPost(hostString+"/index.php/api/order/addOrder", params_addOrder);
	  logger.info(ZSON.parseJson(response_addOrder).getValue("//msg"));
	  String responseStatus = ZSON.parseJson(response_addOrder).getValue("//status").toString();
	  Assert.assertEquals(assertStatus,responseStatus);
	  if (responseStatus.equals("1")) {
		  String order_id =(String) ZSON.parseJson(response_addOrder).getValue("//order_id");
		  String ordernum =(String) ZSON.parseJson(response_addOrder).getValue("//ordernum"); 
		  logger.info("order_id: " + order_id + " ordernum: " + ordernum);
	  }
	  
  }
  
  @BeforeClass
  public void beforeClass() {
	  http = new HttpClientUtil();
	  jdbc = new JdbcUtil();
  }

  @AfterClass
  public void afterClass() {
	  http.closeHttpClient();
	  jdbc.closeConn();
  }
  
	@DataProvider(name="addOrder_Data")
	public static  Object[][] testData() {
		Map<String, Object> map1 = jdbc.findSimpleResult("SELECT id FROM product WHERE status=1 AND inventory>0 order BY id DESC limit 1;");
		String product_id1 = map1.get("id").toString();
		Map<String, Object> map2 = jdbc.findSimpleResult("SELECT id FROM product WHERE status='-1' order BY id DESC limit 1;");
		String product_id2 = map2.get("id").toString();
		Map<String, Object> map3 = jdbc.findSimpleResult("SELECT id FROM product WHERE status=1 AND inventory=0 order BY id DESC limit 1;");
		String product_id3 = map3.get("id").toString();
		
		return new Object[][] {
			{"6265",product_id1,"1","测试正常提交订单"},
			{"6265",product_id2,"1010","测试购买-商品删除"},
			{"6265",product_id3,"1011","测试购买-库存为0"},
		};
		
		
	}
  

}
