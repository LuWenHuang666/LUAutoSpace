package com.lu.LUAutoSpace.huizhitong;

import org.testng.annotations.Test;

import com.lu.LUAutoSpace.utils.db.JdbcUtil;
import com.lu.LUAutoSpace.utils.http.HttpClientUtil;
import com.mysql.cj.api.xdevapi.Result;
import com.zf.zson.ZSON;

import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;

public class AddUser {
	public static Logger logger = Logger.getLogger(AddUser.class);  
	HttpClientUtil httpClientUtil = null;
	
  // @Test
  public void addMember() {
	  System.out.println("test");
  }
  @Test
  public void addVolunteerTestDB() {
	  System.out.println("新增志愿者--被帮扶人测试账号");
	  logger.info("新增志愿者--被帮扶人测试账号");
	  String url = "http://www.hzgoo.cn/Admin/Member/save.html";
	  for (int i = 1; i < 11; i++) {
		  Map<String, String> params = new HashMap<>();
		  params.put("vot[name]", "被帮扶人"+i);
		  params.put("vot[province_id]", "");
		  params.put("vot[city_id]", "");
		  params.put("vot[district_id]", "");
		  params.put("vot[address]", "");
		  params.put("vot[telephone]", "");
		  params.put("mobilephone", String.valueOf(15280212000L+i));
		  params.put("password", "test123456");
		  params.put("repassword", "test123456");
		  params.put("vot[card_code]", "");
		  params.put("vot[pro_zhutu_image]", "");
		  params.put("vot[pro_card_image]", "");
		  params.put("vot[status]", "2");
		  params.put("description", "");
		  params.put("amount", "");
		  params.put("charge_type", "1");
		  params.put("id", "");
		  params.put("volunteer_id", "");
		  params.put("identity", "2");
		  String responseString = httpClientUtil.doPost(url, params);
		  System.out.println("返回：" + ZSON.parseJson(responseString).getValue("//info"));
		  
	  }
  }
  
  @Test
  public void addVolunteerTestDB2() {
	  logger.info("teest12321");
	  System.out.println("新增志愿者-帮扶人测试账号");
	  String url = "http://www.hzgoo.cn/Admin/Member/save.html";
	  for (int i = 1; i < 3; i++) {
		  String mobile = String.valueOf(15280213000L+i);
		  Map<String, String> params = new HashMap<>();
		  params.put("vot[name]", "帮扶人"+i);
		  params.put("vot[province_id]", "1");
		  params.put("vot[city_id]", "2");
		  params.put("vot[district_id]", "3");
		  params.put("vot[address]", "丰台区南苑镇警备东路6号");
		  params.put("vot[telephone]", "");
		  params.put("mobilephone", mobile);
		  params.put("password", "test123456");
		  params.put("repassword", "test123456");
		  params.put("vot[card_code]", "");
		  params.put("vot[pro_zhutu_image]", "");
		  params.put("vot[pro_card_image]", "");
		  params.put("vot[status]", "2");
		  params.put("description", "");
		  params.put("amount", "");
		  params.put("charge_type", "1");
		  params.put("id", "");
		  params.put("volunteer_id", "");
		  params.put("identity", "2");
		  String responseString = httpClientUtil.doPost(url, params);
		  System.out.println("新增志愿者:" + mobile + "返回：" + ZSON.parseJson(responseString).getValue("//info"));
		  //查数据库，返回 uid
		  String sql1 = "SELECT id FROM member WHERE identity=2 AND `status`=2 AND mobilephone=?";
		  List<Object> parames1 = new ArrayList<Object>();
		  parames1.add(mobile);
		  JdbcUtil jdbc = new JdbcUtil();
		  Map<String, Object> result = jdbc.findSimpleResult(sql1,parames1);
		  System.out.println("id:" + result.get("id"));
		  //设置为帮扶人
		  String url2 = "http://www.hzgoo.cn/Admin/Volunteer/update_status/id/"+result.get("id")+".html";
		  String responseString2 = httpClientUtil.doGet(url2);
		  System.out.println("更新志愿者状态:" + mobile +"为帮扶人; 返回：" + ZSON.parseJson(responseString2).getValue("//info"));
	  }
  }
  
  @BeforeClass
  public void beforeClass() {
	  httpClientUtil = new HttpClientUtil();
	  httpClientUtil.setProxy("127.0.0.1", 8888);
	  Map<String, String> params = new HashMap<>();
	  params.put("username", "admin");
	  params.put("password", "123123");
	  params.put("verify", "");
	  httpClientUtil.doPost("http://www.hzgoo.cn/Admin/Public/login.html", params);

  }

  @AfterClass
  public void afterClass() {
	  httpClientUtil.closeHttpClient();
  }

}
