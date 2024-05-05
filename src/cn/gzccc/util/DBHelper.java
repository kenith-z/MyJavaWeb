package cn.gzccc.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class DBHelper {
	
	private static String DRV = "com.mysql.jdbc.Driver";//��������
	private static String URL = "jdbc:mysql://127.0.0.1:3306/testdb";//���ӵ�ַ
	private static String USR = "java";//�û���
	private static String PWD = "java";//����
	private static DBHelper dbHelper;//��̬ʵ��
	private Connection conn = null;//���ӹܵ�
	private PreparedStatement psmt = null;//Ԥ���������
	private Statement stmt = null;//�﷨������
	private ResultSet rest = null;//���ݽ����
	private ResultSetMetaData rsmd = null;//���������Ϣ���ֶ����ȣ�
	
	static {
		try{
			//װ�������ļ�
			String path = DBHelper.class.getClassLoader().getResource("config/").getPath();
			path = java.net.URLDecoder.decode(path, "UTF-8");//���·���е���������
			File file = new File(path+"db.properties");//�ļ�����,���������ļ�
			FileInputStream fis = new FileInputStream(file);//���ļ����뵽��������
			java.util.Properties prop = new java.util.Properties();//���Զ���
			prop.load(fis);
			fis.close();
			DRV = prop.getProperty("drv");
			URL = prop.getProperty("url");
			USR = prop.getProperty("usr");
			PWD = prop.getProperty("pwd");
			//װ��JDBC��������
			Class.forName(DRV).newInstance();
		}catch(Exception e){
			System.err.println("��ȡ���ݿ������ļ�����");
		}
	}
	
	public synchronized static DBHelper getInstance() throws Exception {
		if (dbHelper==null){
			dbHelper = new DBHelper();
			Class.forName(DRV).newInstance();
		}
		return dbHelper;
	}
	
	//��ȡ����
	public synchronized Connection getConn() throws SQLException {
		try{
			Connection conn = DriverManager.getConnection(URL, USR, PWD);
			return conn;
		}catch (SQLException e){
			throw new SQLException(e.getMessage());
		}
	}
	
	//�ر�����
	public synchronized void closeDB() {
		if(rest!=null) try{rest.close();}catch(SQLException e){}
		if(psmt!=null) try{psmt.close();}catch(SQLException e){}
		if(stmt!=null) try{stmt.close();}catch(SQLException e){}
		if(conn!=null) try{conn.close();}catch(SQLException e){}
	}
	
	/**
	 * ִ�в�ѯ,���ؽ��
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
	 * ִ�и��¡�ɾ��������(UPDATE��DELETE��INSERT)<br>
	 * @param sql<br>
	 * @return��ʾ�յ�Ӱ��ļ�¼��
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
	
	//Ԥ����ִ�и��¡�ɾ��������
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
