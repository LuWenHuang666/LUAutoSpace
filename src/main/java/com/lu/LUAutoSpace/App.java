package com.lu.LUAutoSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.lu.LUAutoSpace.utils.db.JdbcUtil;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        double v1 = 0.01;
        double v2 = 1.01;
        double sum = v1 + v2;
        System.out.println( "Hello World!=="+ sum );

        Map<String, Object> map = new HashMap<String, Object>();        
        Object[] obj = map.values().toArray();        
        ArrayList<Object> objlist = new ArrayList<Object>();        
        for (Object object : obj) {            
        	objlist.add(object);       
        }

      //后面忽略 直接有values方法 可以转成数组的  
        Object object = new Object[][] {
			{"6265","2114"},
			{"6265","2114"}
		};
		
		JdbcUtil jdbc = new JdbcUtil();
		Map<String, Object> map1 = jdbc.findSimpleResult("SELECT id FROM product WHERE status=1 AND inventory>0 order BY id DESC limit 1;");
		String product_id1 =map1.get("id").toString();
		System.out.println(product_id1);
        
        

    }
    

}
