package cn.gzccc.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class DBHelper {
	
	private static String DRV = "com.mysql.jdbc.Driver";//驱动程序
	private static String URL = "jdbc:mysql://127.0.0.1:3306/testdb";//连接地址
	private static String USR = "java";//用户名
	private static String PWD = "java";//密码
	private static DBHelper dbHelper;//静态实例
	private Connection conn = null;//连接管道
	private PreparedStatement psmt = null;//预编译解释器
	private Statement stmt = null;//语法解释器
	private ResultSet rest = null;//数据结果集
	private ResultSetMetaData rsmd = null;//数据相关信息（字段名等）
	
	static {
		try{
			//装载属性文件
			String path = DBHelper.class.getClassLoader().getResource("config/").getPath();
			path = java.net.URLDecoder.decode(path, "UTF-8");//解决路径中的中文乱码
			File file = new File(path+"db.properties");//文件对象,并联配置文件
			FileInputStream fis = new FileInputStream(file);//将文件读入到输入流中
			java.util.Properties prop = new java.util.Properties();//属性对象
			prop.load(fis);
			fis.close();
			DRV = prop.getProperty("drv");
			URL = prop.getProperty("url");
			USR = prop.getProperty("usr");
			PWD = prop.getProperty("pwd");
			//装载JDBC驱动程序
			Class.forName(DRV).newInstance();
		}catch(Exception e){
			System.err.println("读取数据库配置文件出错");
		}
	}
	
	public synchronized static DBHelper getInstance() throws Exception {
		if (dbHelper==null){
			dbHelper = new DBHelper();
			Class.forName(DRV).newInstance();
		}
		return dbHelper;
	}
	
	//获取连接
	public synchronized Connection getConn() throws SQLException {
		try{
			Connection conn = DriverManager.getConnection(URL, USR, PWD);
			return conn;
		}catch (SQLException e){
			throw new SQLException(e.getMessage());
		}
	}
	
	//关闭连接
	public synchronized void closeDB() {
		if(rest!=null) try{rest.close();}catch(SQLException e){}
		if(psmt!=null) try{psmt.close();}catch(SQLException e){}
		if(stmt!=null) try{stmt.close();}catch(SQLException e){}
		if(conn!=null) try{conn.close();}catch(SQLException e){}
	}
	
	/**
	 * 执行查询,返回结果
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, String>> query(String sql) throws SQLException {
		conn = getConn();
		stmt = conn.createStatement();
		rest = stmt.executeQuery(sql);
		rsmd = rest.getMetaData();
		List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
		while(rest.next()){
			Map<String, String> map = new HashMap<String, String>();
			String value;
			for(int i=1; i<=rsmd.getColumnCount(); i++){
				value = rest.getString(rsmd.getColumnName(i));
				if(value==null || value.trim().length()==0 || value.equalsIgnoreCase("null")){
					value = "";
				}
				value = value.trim();
				map.put(rsmd.getColumnName(i), value);
			}
			listData.add(map);
		}
		closeDB();
		return listData;
	}
	
	/**
	 * 执行更新、删除、插入(UPDATE、DELETE、INSERT)<br>
	 * @param sql<br>
	 * @return表示收到影响的记录数
	 * @throws SQLException
	 * @throws Exception
	 */
	public int update(String sql) throws SQLException, Exception {
		int result = 0;
		try{
			conn = getConn();
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		}catch(SQLException e){
			throw e;
		}catch(Exception e){
			throw e;
		}finally{
			closeDB();
		}
		return result;
	}
	
	//预编译执行更新、删除、插入
	public int update(String sql, Vector<Object[]> params) throws SQLException, Exception {
		int result = 0;
		if(sql==null || params==null || params.isEmpty()) return 0;
		try{
			conn = getConn();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(sql);
			psmt.clearBatch();
			for(Object[] param:params){
				if(param==null || param.length==0) continue;
				for(int i=0; i<param.length; i++){
					psmt.setObject(i+1, param[i]);
				}
				psmt.addBatch();
				result++;
			}
			psmt.executeBatch();
			conn.commit();
		}catch(SQLException e){
			throw e;
		}catch(Exception e){
			throw e;
		}finally{
			closeDB();
		}
		return result;
	}
}
