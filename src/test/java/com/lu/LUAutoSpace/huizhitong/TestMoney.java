package com.lu.LUAutoSpace.huizhitong;

import org.testng.annotations.Test;

import com.lu.LUAutoSpace.utils.db.JdbcUtil;
import com.lu.LUAutoSpace.utils.http.HttpClientUtil;
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
	  
	  logger.info("前置条件1:登录");
	  Map<String, String> params = new HashMap<String, String>();
	  params.put("username", "15280212001");
	  params.put("password", "test123456");
	  String loginUrl = "http://www.hzgoo.cn/Mobile/Public/checkLogin.html";
	  String response = http.doPost(loginUrl, params);
	  logger.info(ZSON.parseJson(response).getValue("//info"));
	  
  }

  @AfterClass
  public void afterClass() {
	  http.closeHttpClient();
	  
  }
  
  @Test
  public void test() {
	  logger.info("前置条件1: 查被帮扶人帮扶余额");
	  BigDecimal moneyBigD_bef = getMoney(6275);
	  
	  logger.info("前置条件2: 随机商品价格");
	  BigDecimal price = new BigDecimal("1202.09");
	  // UPDATE product set price='1201.99' WHERE id=2856;
	  List<Object> params = new ArrayList<>();
	  params.add(price);
	  boolean update_status = jdbc.updateByPreparedStatement("UPDATE product set price=? WHERE id=2856", params);
	  System.out.println("update_status: " + update_status);
	  
	  logger.info("api提交订单");
	  Map<String, String> params_addOrder = new HashMap<>();
	  params_addOrder.put("params", "{\"mid\":6265,\"consignee_id\":4951,\"products\":[{\"product_id\":2856,\"key\":\"2856\",\"quantity\":1}],\"remarks\":\"这个是测试\"}");
	  String response_addOrder = http.doPost("http://www.hzgoo.cn/index.php/api/order/addOrder", params_addOrder);
	  logger.info(ZSON.parseJson(response_addOrder).getValue("//msg"));
	  String order_id =(String) ZSON.parseJson(response_addOrder).getValue("//order_id"); 
	  logger.info("order_id: " + order_id);
	  
	  
	  logger.info("帮扶支付");
	  Map<String, String> params_volunteerPay = new HashMap<>();
	  params_volunteerPay.put("confirm", "4764");  //帮扶人 volunteer表的ID
	  params_volunteerPay.put("order_id", order_id); //订单id
	  String response_volunteerPay = http.doPost("http://www.hzgoo.cn/Mobile/Seekvol/confirmVolunteer.html", params_volunteerPay);
	  logger.info(response_volunteerPay);
	  logger.info(ZSON.parseJson(response_volunteerPay).getValue("//info"));
	  
	  logger.info("验证余额");
	  BigDecimal moneyBigD_aft = getMoney(6275);
	  Assert.assertEquals(moneyBigD_aft, moneyBigD_bef.subtract(price));
	  System.out.println(moneyBigD_bef.subtract(price));
	  
  }
}
