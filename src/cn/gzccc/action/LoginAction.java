/*
 * MVC开发框架之交易执行类
 */
package cn.gzccc.action;


import java.util.List;
import java.util.Map;

import cn.gzccc.data.User;
import cn.gzccc.ide.BaseAction;
import cn.gzccc.ide.IAction;
import cn.gzccc.util.DBHelper;
import cn.gzccc.util.MD5;

public class LoginAction extends BaseAction implements IAction{
	
	
	/*
	 * 实现执行函数的细节
	 */
	@Override
	public Map< String, Object> doAction(Map< String, String> paramMap) {
		/*
		 * 一、获取页面传入参数
		 */
		String userId = paramMap.get("userId");
		String userPd = paramMap.get("userPd");
		String randomCode = paramMap.get("randomCode");
		String verifyCode = (String)getSession().getAttribute("verifyCode");
		/*
		 * 二、交易细节
		 */
		//1.验证数据的完整性和正确性
		if(userId==null||userId.length()==0
				||userPd==null||userPd.length()==0
				||randomCode==null||randomCode.length()==0){
			setError("请输入完整数据");
			return result;
		}
		//判断随机验证是否一致
		if(!randomCode.equalsIgnoreCase(verifyCode)){
			setError("验证码错误！");
			return result;
		}
		
		//判断用户和密码是否正确
		userPd = MD5.encrypt(userPd);//加密成32位不可逆字符串
		try{
			String sql = "SELECT * FROM user WHERE user_id='"+userId+"' AND pwd='"+userPd+"' ";
			DBHelper dbh = DBHelper.getInstance();
			List<Map<String, String>> list = dbh.query(sql);//查询数据库
			if(list.isEmpty()){
				setError("您输入的用户名或密码不正确！");
				return result;
			}
			Map<String, String> map = list.get(0);//取出该用户
			String userName = map.get("user_name");//取出用户姓名
			//如果用户名和密码正确,则实例化一个用户对象，并设置到会话中，再定向到欢迎页
			User user = new User(userId);
			user.setUserName(userName);
			//会话(session) :再同一款浏览器窗口中，对同一个站点，持续地访问不同的页面
			getSession().setAttribute("USER", user);//设置为环境变量
		}catch(Exception e){
			setError("发生异常【"+e.getMessage()+"】");
			return result;
		}
		
		
		
		
		/*
		 * 输入结果
		 */
		
		return result;
		
	}

}
