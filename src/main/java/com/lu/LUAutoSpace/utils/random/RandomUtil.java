package com.lu.LUAutoSpace.utils.random;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

public class RandomUtil {
	
	public Double randomDouble(int max, int min) {
		DecimalFormat df =new DecimalFormat("#.00");  //格式 化两位小数
        java.util.Random random=new java.util.Random(); 
        Double numDouble = random.nextDouble()%(max-min+1) + min;
        String numString = df.format(numDouble);
        Double numD = Double.parseDouble(numString);
		return numD;
	}
	
	public BigDecimal randomBigDecimal(int max, int min) {
		DecimalFormat df =new DecimalFormat("#.00");  //格式 化两位小数
        java.util.Random random=new java.util.Random(); 
        int numInt = random.nextInt(max)%(max-min+1) + min;
        BigDecimal numBigD = new BigDecimal(numInt);
        //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入) 
		return numBigD.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomUtil randomUtil = new RandomUtil();
		for (int i=1;i<10;i++) {
		System.out.println(randomUtil.randomDouble(50,60));
		
		int max=20;
        int min=10;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        System.out.println(new BigDecimal(s).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	}

}
