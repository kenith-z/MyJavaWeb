/*
 * 交易类借口
 * 接口只有抽象的函数(有形式没内容)
 * 函数的具体实现过程（细节）是由实现该借口的类来定义的
 */
package cn.gzccc.ide;

import java.util.Map;

import jakarta.servlet.http.HttpSession;

public interface IAction {

	public Map< String, Object> doAction(Map< String, String> paramMap);
	
	public String getError();
	
	public void setSession(HttpSession session);
	
	public HttpSession getSession();
	/**输出页面*/
	public String getPage();
	
}
