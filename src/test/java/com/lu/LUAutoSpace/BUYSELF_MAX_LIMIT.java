package com.lu.LUAutoSpace;

import org.testng.annotations.Test;

import com.lu.LUAutoSpace.utils.db.JdbcUtil;

import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;

public class BUYSELF_MAX_LIMIT {
  /**
 * 范围内
 */
@Test
  public void test1() {
  }
  @BeforeClass
  public void beforeClass() {
	  // 清除数据，清redis
	  JdbcUtil jdbcUtil = new JdbcUtil();
	  //清除被帮扶人15280213001 的所有订单数据
	  List<Object> mid = new ArrayList<>();
	  //mid.add("6266");
	  //mid.add("6288");
	  mid.add("6289");
	  jdbcUtil.updateByPreparedStatement("DELETE FROM member_reward where mid=?;", mid);
	  jdbcUtil.updateByPreparedStatement("DELETE FROM user_income where user_id=?;", mid);
	  jdbcUtil.updateByPreparedStatement("DELETE FROM volunteer_help where m_uid in (SELECT uuid FROM `member` where id=?);", mid);
	  jdbcUtil.updateByPreparedStatement("DELETE FROM `order` where mid=?;", mid);
	  jdbcUtil.updateByPreparedStatement("DELETE FROM cart where mid=?;", mid);
	  jdbcUtil.updateByPreparedStatement("DELETE FROM recharge where mid=?;", mid);
	  jdbcUtil.updateByPreparedStatement("DELETE FROM user_withdraw where user_id=?;", mid);
	  jdbcUtil.updateByPreparedStatement("update member set point=0 where id=?; ", mid);
	   
  }

  @AfterClass
  public void afterClass() {
  }

}
