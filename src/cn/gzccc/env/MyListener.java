/*
 * WEB工程的监听器
 * 主要用于监听WEB工程的实时运行状况
 * 如捕捉WEB工程的启动，来自于浏览器用户的请求
 */
package cn.gzccc.env;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import javax.sql.DataSource;

import cn.coolcpu.sql.DBConfig;
import cn.gzccc.ide.AppEnv;


public class MyListener implements ServletContextListener, HttpSessionListener,
		ServletRequestListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		/** Web工程启动时 */
		ServletContext application = event.getServletContext();

		/*******************
		 * 初始化数据库环境 *
		 *******************/
		Connection conn = null;
		String dbConfFile = null;
		try{
			String path = MyListener.class.getClassLoader().getResource("config/").getPath();
			path = java.net.URLDecoder.decode(path, "UTF-8");//解决路径中的中文乱码
			dbConfFile = path+"db.properties";
			Properties dbProp = new Properties();
			FileInputStream fis = new FileInputStream(dbConfFile);
			dbProp.load(fis);
			DBConfig.PORT = Integer.parseInt(dbProp.getProperty("port"));
			DBConfig.MAXCONN = Integer.parseInt(dbProp.getProperty("maxconn"));
			DBConfig.MAX_RECORDS = Integer.parseInt(dbProp.getProperty("maxrecords"));
			DBConfig.PATH = dbProp.getProperty("path");
			DBConfig.LOGFILE = dbProp.getProperty("logfile");
			DBConfig.ISOUTPUTPOOLLOG = Boolean.parseBoolean(dbProp.getProperty("isoutputpoollog"));
			DBConfig.ISOUTPUTQUERYLOG = Boolean.parseBoolean(dbProp.getProperty("isoutputquerylog"));
			DBConfig.ISOUTPUTUPDATELOG = Boolean.parseBoolean(dbProp.getProperty("isoutputupdatelog"));
			DBConfig.ISJNDI = Boolean.parseBoolean(dbProp.getProperty("isjndi"));
			DBConfig.JNDI = dbProp.getProperty("jndi");
			DBConfig.DRV = dbProp.getProperty("drv");
			DBConfig.URL = dbProp.getProperty("url");
			DBConfig.USR = dbProp.getProperty("usr");
			DBConfig.PWD = dbProp.getProperty("pwd");
			fis.close();
			//规范化jndi名称
			String serverInfo = application.getServerInfo();
			if(serverInfo.indexOf("Apache Tomcat")!=-1){
				DBConfig.JNDI = "java:comp/env/"+DBConfig.JNDI;
			}else if(serverInfo.indexOf("JBoss")!=-1){
				DBConfig.JNDI = "java:/"+DBConfig.JNDI;
			}
		}catch(FileNotFoundException e){
			System.out.println("没找到数据库配置文件["+dbConfFile+"],启动失败");
		}catch(IOException e){
			System.out.println("初始化数据库配置文件["+dbConfFile+"]出错,启动失败");
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("初始化数据库配置文件失败");
			e.printStackTrace();
		}
		try{ Class<?> clazz = Class.forName(DBConfig.DRV);
			Constructor<?> constructor = clazz.getDeclaredConstructor();
			Object instance = constructor.newInstance();}
		catch(ClassNotFoundException e){e.printStackTrace();}
		catch(IllegalAccessException e){e.printStackTrace();}
		catch(InstantiationException e){e.printStackTrace();} catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        //测试数据库
		try{
			if(DBConfig.ISJNDI){
				DBConfig.DSRC = (DataSource)((new InitialContext()).lookup(DBConfig.JNDI));
				conn = DBConfig.DSRC.getConnection();
			}else{
				conn = DriverManager.getConnection(DBConfig.URL,DBConfig.USR,DBConfig.PWD);
			}
		}catch(NamingException e){
			System.out.println("获取JNDI数据源出错");
			e.printStackTrace();
		}catch(SQLException e){
			System.out.println("获取数据库连接出错");
			e.printStackTrace();
		}
		if(conn!=null)try{conn.close();}catch(SQLException e){;}
		/*******************/
		
		/** 查询分页 **/
		int sectionSize=5,pageSize=10;
		try{sectionSize=Integer.parseInt(application.getInitParameter("SECTION_SIZE"));}catch(Exception e){}
		try{pageSize=Integer.parseInt(application.getInitParameter("PAGE_SIZE"));}catch(Exception e){}
		application.setAttribute("SECTION_SIZE",sectionSize); //每页片段数 
		application.setAttribute("PAGE_SIZE",pageSize); //每页记录数 
		AppEnv.SECTION_SIZE = sectionSize;
		AppEnv.PAGE_SIZE = pageSize;
		
		// 汉语拼音声母表
		String[] sms = new String[] { "b", "p", "m", "f", "d", "t", "n", "l",
				"g", "k", "h", "j", "q", "x", "zh", "ch", "sh", "r", "z", "c",
				"s", "y", "w" };
		application.setAttribute("sms", sms);
		String[] num = new String[] { "一", "二", "三", "四", "五", "六", "七", "八",
				"九", "十" };
		application.setAttribute("num", num);
		// 汉语拼音韵母表
		String[] yms = new String[] { "ā", "á", "ǎ", "à", "ē", "é", "ě", "è",
				"ī", "í", "ǐ", "ì", "ō", "ó", "ǒ", "ò", "ū", "ú", "ǔ", "ù",
				"ǖ", "ǘ", "ǚ", "ǜ", "āi", "ái", "ǎi", "ài", "ēi", "éi", "ěi",
				"èi", "uī", "uí", "uǐ", "uì", "āo", "áo", "ǎo", "ào", "uō",
				"uó", "uǒ", "uò", "ōu", "óu", "ǒu", "òu", "iū", "iú", "iǔ",
				"iù", "iē", "ié", "iě", "iè", "uē", "ué", "uě", "uè", "ēr",
				"ér", "ěr", "èr", "ān", "án", "ǎn", "àn", "ēn", "én", "ěn",
				"èn", "īn", "ín", "ǐn", "ìn", "ūn", "ún", "ǔn", "ùn", "ǖn",
				"ǘn", "ǚn", "ǜn", "āng", "áng", "ǎng", "àng", "ēng", "éng",
				"ěng", "èng", "īng", "íng", "ǐng", "ìng", "ōng", "óng", "ǒng",
				"òng" };
		application.setAttribute("yms", yms);

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		/** WEB工程停止时 */

	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		/** 用户的会话创建时 */

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// 用户的会话注销时

	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		// 用户的请求发生时

	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		// 用户的请求提交完毕（结束）时

	}

}