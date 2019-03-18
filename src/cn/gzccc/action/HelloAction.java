/*
 * MVC开发框架之交易执行类
 */
package cn.gzccc.action;


import java.util.Map;

import cn.gzccc.ide.BaseAction;
import cn.gzccc.ide.IAction;

public class HelloAction extends BaseAction implements IAction{
	
	
	/*
	 * 实现执行函数的细节
	 */
	@Override
	public Map< String, Object> doAction(Map< String, String> paramMap) {
		/*
		 * 一、获取页面传入参数
		 */
		String word = paramMap.get("word");
		String city = paramMap.get("city");
		
		/*
		 * 二、交易细节
		 */
		String hello = "你好，"+word+"欢迎来到"+city+"!";
		String info = "今天发工资! ";
		int money = 100000;
		
		
		
		/*
		 * 输入结果
		 */
		result.put("hello", hello);
		result.put("info", info);
		result.put("money", money);
		return result;
		
	}

}
