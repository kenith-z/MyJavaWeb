/*
 * 删除成语交易执行类
 */
package cn.gzccc.action;


import java.util.Map;

import cn.gzccc.ide.BaseAction;
import cn.gzccc.ide.IAction;
import cn.gzccc.util.DBHelper;

public class RemoveAction extends BaseAction implements IAction{
	
	
	/*
	 * 实现执行函数的细节
	 */
	@Override
	public Map< String, Object> doAction(Map< String, String> paramMap) {
		/*
		 * 一、获取页面传入参数
		 */
		String itemId = paramMap.get("itemId");
		
		/*
		 * 二、交易细节
		 */
		//1.验证数据完整性和正确性
		if(itemId != null){
			//Word不为空时通过提交表单进来交易
			if(itemId.trim().length()==0){
				setError("缺少索引号!");
				return result;
			}
			
			itemId = itemId.trim();//去除空格
			try{
				//2.判断成语是否存在与数据库中
				String sql = "DELETE FROM cn_idiom WHERE item_id='"+itemId+"' ";
				DBHelper dbh = DBHelper.getInstance();
				dbh.update(sql);//执行删除
			}catch(Exception e){
				setError("删除成语出错【"+e.getMessage()+"】");
			}
		}
		/*
		 * 输入结果
		 */
		return result;
	}
	
}
