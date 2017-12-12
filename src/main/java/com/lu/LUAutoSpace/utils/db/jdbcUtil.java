package com.lu.LUAutoSpace.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;


public class jdbcUtil {
	private static Logger logger = Logger.getLogger(jdbcUtil.class);
	//数据库用户名  
    private static final String USERNAME = "testhost";  
    //数据库密码  
    private static final String PASSWORD = "SbyDfPWGXVbhhtKL";  
    //驱动信息   
    private static final String DRIVER = "com.mysql.jdbc.Driver";  
    //数据库地址  
    private static final String URL = "jdbc:mysql://106.14.196.16:3306/fjmall";  
    private Connection connection;  
    private PreparedStatement pstmt;  
    private ResultSet resultSet;  
    
    public jdbcUtil() {  
        // TODO Auto-generated constructor stub  
/*        try{  
            Class.forName(DRIVER);  
            System.out.println("成功加载驱动！");  
        }catch(Exception e){  
        	e.printStackTrace();  
        }  */
        try {  
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);  
            System.out.println("成功获取连接");
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
      

    /** 
     * 释放数据库连接 
     */  
    public void releaseConn(){  
        if(resultSet != null){  
            try{  
                resultSet.close();  
            }catch(SQLException e){  
                e.printStackTrace();  
            }  
        }  
    } 
    
    /** 
     * 增加、删除、改 
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public boolean updateByPreparedStatement(String sql, List<Object>params){  
        boolean flag = false;  
        int result = -1;  
        try {
			pstmt = connection.prepareStatement(sql);  
			int index = 1;  
			if(params != null && !params.isEmpty()){  
			    for(int i=0; i<params.size(); i++){  
			        pstmt.setObject(index++, params.get(i));  
			    }  
			}  
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
        flag = result > 0 ? true : false;  
        return flag;  
    }
    
    /** 
     * 查询单条记录 
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public Map<String, Object> findSimpleResult(String sql, List<Object> params) {  
        Map<String, Object> map = new HashMap<String, Object>();  
        int index  = 1;  
        try {
			pstmt = connection.prepareStatement(sql);  
			if(params != null && !params.isEmpty()){  
			    for(int i=0; i<params.size(); i++){  
			        pstmt.setObject(index++, params.get(i));  
			    }  
			}  
			resultSet = pstmt.executeQuery();//返回查询结果  
			ResultSetMetaData metaData = resultSet.getMetaData();  
			int col_len = metaData.getColumnCount();  
			while(resultSet.next()){  
			    for(int i=0; i<col_len; i++ ){  
			        String cols_name = metaData.getColumnName(i+1);  
			        Object cols_value = resultSet.getObject(cols_name);  
			        if(cols_value == null){  
			            cols_value = "";  
			        }  
			        map.put(cols_name, cols_value);  
			    }  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
        return map;  
    } 

    /**查询多条记录 
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        int index = 1;  
        try {
			pstmt = connection.prepareStatement(sql);  
			if(params != null && !params.isEmpty()){  
			    for(int i = 0; i<params.size(); i++){  
			        pstmt.setObject(index++, params.get(i));  
			    }  
			}  
			resultSet = pstmt.executeQuery();  
			ResultSetMetaData metaData = resultSet.getMetaData();  
			int cols_len = metaData.getColumnCount();  
			while(resultSet.next()){  
			    Map<String, Object> map = new HashMap<String, Object>();  
			    for(int i=0; i<cols_len; i++){  
			        String cols_name = metaData.getColumnName(i+1);  
			        Object cols_value = resultSet.getObject(cols_name);  
			        if(cols_value == null){  
			            cols_value = "";  
			        }  
			        map.put(cols_name, cols_value);  
			    }  
			    list.add(map);  
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return list;  
    }  
    
	public static void main(String[] args) {
		logger.error("23423423423423432423");
		jdbcUtil jdbcUtil = new jdbcUtil();
		String sql = "SELECT * FROM business where province_id = ?";
		List<Object> parames = new ArrayList<Object>();
		parames.add("227");
/*		Map<String, Object> result = jdbcUtil.findSimpleResult(sql,parames);
		System.out.println("result:" + result);*/
/*		List<Map<String, Object>> result = jdbcUtil.findModeResult(sql,parames);
		Iterator<Map<String, Object>> iterator = result.iterator();
		while (iterator.hasNext()) {
			System.out.println("result:" + iterator.next().get("name"));
			Iterator<Entry<String, Object>> entries =iterator.next().entrySet().iterator();  
			while (entries.hasNext()) {
				Entry<String, Object>  entry =entries.next();  
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
		}
		*/	
		
		
	}

}
