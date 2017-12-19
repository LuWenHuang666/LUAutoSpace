package com.lu.LUAutoSpace.huizhitong;

import org.testng.annotations.Test;

import com.lu.LUAutoSpace.utils.db.JdbcUtil;
import com.lu.LUAutoSpace.utils.http.HttpClientUtil;
import com.lu.LUAutoSpace.utils.random.RandomUtil;
import com.zf.zson.ZSON;

import org.testng.annotations.BeforeClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestMoney {
	private static Logger logger = Logger.getLogger(TestMoney.class);
	private static HttpClientUtil http;
	private static JdbcUtil jdbc;
	RandomUtil randomUtil = new RandomUtil();
	private static String hostString = "http://www.hzgoo.cn";

	
	private BigDecimal getMoney(int user_id) {
		  List<Object> parames = new ArrayList<>();
		  parames.add(user_id);
		  Map<String, Object> present_moneyMap = jdbc.findSimpleResult("SELECT present_money FROM user_income WHERE user_id=? ORDER BY user_flow_id DESC LIMIT 1;", parames);
		  Map<String, Object> moneyMap = jdbc.findSimpleResult("SELECT money FROM volunteer WHERE mid=?;", parames);
		  BigDecimal present_moneyBigD = (BigDecimal) present_moneyMap.get("present_money");
		  BigDecimal moneyBigD = (BigDecimal) moneyMap.get("money");
		  System.err.println(present_moneyBigD);
		  System.err.println(moneyBigD);
		  Assert.assertEquals(present_moneyBigD,moneyBigD,"两张表的余额不相等");
		  return moneyBigD;
	}
	
  @BeforeClass
  public void beforeClass() {
	  http = new HttpClientUtil();
	  jdbc = new JdbcUtil();
	  
	  logger.info("前置条件0:登录");
	  Map<String, String> params = new HashMap<String, String>();
	  params.put("username", "15280212001");
	  params.put("password", "test123456");
	  String response = http.doPost(hostString+"/Mobile/Public/checkLogin.html", params);
	  logger.info(ZSON.parseJson(response).getValue("//info"));
	  Assert.assertEquals("1", ZSON.parseJson(response).getValue("//status").toString());
	  
  }

  @AfterClass
  public void afterClass() {
	  http.closeHttpClient();
	  jdbc.closeConn();
	  
  }
  
  @Test
  public void test() {
	  // mid  consignee_id   product_id
	  
	  String mid = "6265";            //被帮扶人 user_id
	  String volunteer_id = "4764";  //帮扶人ID
	  String volunteer_user_id = "6275";  //帮扶人 user_id
	  String product_id = "2856";
	  String consignee_id = "4951";
	  
	  
	  logger.info("前置条件1: 查被帮扶人帮扶余额");
	  BigDecimal moneyBigD_bef = getMoney(Integer.valueOf(volunteer_user_id));
	  
	  logger.info("前置条件2: 随机商品价格");
	  BigDecimal price = randomUtil.randomBigDecimal(50, 1000);
	  //BigDecimal price = new BigDecimal("1202.09");
	  List<Object> params = new ArrayList<>();
	  params.add(price);
	  boolean update_status = jdbc.updateByPreparedStatement("UPDATE product set price=? WHERE id=2856", params);
	  logger.error("价格更新为：" + price +" update_status: " + update_status);
	  
	  logger.info("api提交订单");
	  Map<String, String> params_addOrder = new HashMap<>();
	  params_addOrder.put("params", "{\"mid\":" + mid + ",\"consignee_id\":" + consignee_id + ",\"products\":[{\"product_id\":" + product_id + ",\"key\":\"" + product_id + "\",\"quantity\":1}],\"remarks\":\"lujianhuang测试数据\"}");
	  String response_addOrder = http.doPost(hostString+"/index.php/api/order/addOrder", params_addOrder);
	  logger.info(ZSON.parseJson(response_addOrder).getValue("//msg"));
	  String order_id =(String) ZSON.parseJson(response_addOrder).getValue("//order_id");
	  String ordernum =(String) ZSON.parseJson(response_addOrder).getValue("//ordernum"); 
	  logger.info("order_id: " + order_id + " ordernum: " + ordernum);
	  
	  logger.info("支付方式确认为：帮扶  volunteer_mobile");
	  Map<String, String> params_payType = new HashMap<>();
	  params_payType.put("radio", "12");
	  params_payType.put("order_id", order_id);
	  http.doPost(hostString+"/Mobile/Seekvol/index.html", params_payType);
	  
	  logger.info("帮扶支付");
	  Map<String, String> params_volunteerPay = new HashMap<>();
	  params_volunteerPay.put("confirm", volunteer_id);  //帮扶人 volunteer表的ID
	  params_volunteerPay.put("order_id", order_id); //订单id
	  String response_volunteerPay = http.doPost(hostString+"/Mobile/Seekvol/confirmVolunteer.html", params_volunteerPay);
	  logger.info(ZSON.parseJson(response_volunteerPay).getValue("//info"));
	  
	  logger.info("验证余额");
	  BigDecimal moneyBigD_aft = getMoney(Integer.valueOf(volunteer_user_id));
	  Assert.assertEquals(moneyBigD_aft, moneyBigD_bef.subtract(price));
	  System.out.println(moneyBigD_bef.subtract(price));
	  
  }
}
