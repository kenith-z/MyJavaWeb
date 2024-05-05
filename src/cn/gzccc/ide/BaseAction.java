/*
 * 交易基类（所有执行类的父类）
 */

package cn.gzccc.ide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import cn.coolcpu.sql.RecordBean;
import cn.coolcpu.sql.SQLBridge;

public abstract class BaseAction {
	/**站点根的绝对路径*/
	protected String rootPath = null;//站点根的绝对路径(后面不带路径杠)
	private String page=null;//输出页面
	/**输出页面*/
	public String getPage() {
		return page;
	}
	/**输出页面*/
	public void setPage(String page) {
		this.page = page;
	}

	/** 页面会话对象 **/
	private HttpSession session = null;
	
	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
		this.rootPath = session.getServletContext().getRealPath("");
	}

	/**出错信息*/
	private String error = null;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	// 向前端返回的结果映射
	protected Map<String, Object> result = new HashMap<String, Object>();

	/** 数据库相关 **/
	protected SQLBridge sqlBridge = null;
	protected List<Map<String, String>> bean2Map(List<RecordBean> result) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for(RecordBean bean:result){
			Map<String, String> map = new HashMap<String, String>();
			String[] fields = bean.fieldNames();
			for(String field:fields){
				String value = bean.getValue(field);
				map.put(field.toLowerCase(), value);
				map.put(field.toUpperCase(), value);
			}
			list.add(map);
		}
		return list;
	}
	
	protected Map<String, String> bean2Map(RecordBean bean) {
		Map<String, String> map = new HashMap<String, String>();
		String[] fields = bean.fieldNames();
		for(String field:fields){
			String value = bean.getValue(field);
			map.put(field.toLowerCase(), value);
			map.put(field.toUpperCase(), value);
		}
		return map;
	}
	
}
