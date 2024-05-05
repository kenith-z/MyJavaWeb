/*
 * 启动类POJO
 */
package cn.gzccc.run;

import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import cn.gzccc.util.DBHelper;

public class MyStartup extends HttpServlet {
	/*
	 * 
	 * 启动函数，当Web服务器时，该函数会自动执行
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {

		//工程的上下文对象(应用对象)，包含了整个Web的详细描述
		ServletContext context = config.getServletContext();
		
		String appName = context.getServletContextName();//应用名称
		
		//以"键值对"的形式，设置环境变量到应用对象中，使得所有页面都可以直接使用该属性
		context.setAttribute("APP_NAME", appName);
		
		
		String version = context.getInitParameter("version");//获取Web，xml中的参数
		
		
		/**精美输出**************************/
		int symbol = "*".charAt(0);
		String runInfo = (char)symbol+"   "+appName+version+"    "+(char)symbol;
		StringBuffer stars = new StringBuffer();
		StringBuffer space = new StringBuffer();
		int length = runInfo.getBytes().length;
		for(int i=0; i<length; i++){
			stars.append((char)symbol);
			if(i==0 || i==length-1){
				space.append((char)symbol);//星号
			}else{
				space.append((char)32);//空格
			}
		}
		System.out.println("\n"+stars.toString()+"\n"+space+"\n"+runInfo+"\n"+space+"\n"+stars.toString()+"\n");
		/***********************************/
		
		//测试数据库链接、
		try {
			String sql="SELECT NULL FROM cn_idiom";
			DBHelper dbh = DBHelper.getInstance();//获取数据库助手实例
			List<Map<String, String>> list = dbh.query(sql);//执行查询，返回结果集
			int count = list.size();//记录数
			System.out.println("连接数据库成功，共有"+count+"条成语。\n");
		} catch (Exception e) {
			System.err.println("连接数据库戳错【"+e.getMessage()+"】");
			e.printStackTrace();
		}
		
		
		
	}

}
