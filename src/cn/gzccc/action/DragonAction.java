/*
 * 成语接龙交易执行类
 */
package cn.gzccc.action;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gzccc.ide.BaseAction;
import cn.gzccc.ide.IAction;
import cn.gzccc.util.DBHelper;

public class DragonAction extends BaseAction implements IAction{
	
	
	/*
	 * 实现执行函数的细节
	 */
	@Override
	public Map< String, Object> doAction(Map< String, String> paramMap) {
		/*
		 * 一、获取页面传入参数
		 */
		String word = paramMap.get("word");
		
		/*
		 * 二、交易细节
		 */
		//1.验证数据完整性和正确性
		if(word != null){
			//Word不为空时通过提交表单进来交易
			if(word.trim().length()==0){
				setError("请输入您要接龙的龙首成语!");
				return result;
			}
			
			word = word.trim();//去除空格
			try{
				//2.判断成语是否存在与数据库中
				String sql = "SELECT * FROM cn_idiom WHERE name='"+word+"' ";
				DBHelper dbh = DBHelper.getInstance();
				List<Map<String,String>> list = dbh.query(sql);//执行查询返回结果集
				if(list.isEmpty()){
					setError("您输入的成语【"+word+"】不存在");
					return result;
				}
				//3.把开始成语接到龙头处
				List<Map<String, String>> dragon = new ArrayList<Map<String, String>>();
				dragon.add(list.get(0));
				//3.寻找下一个成语并将它追加到龙体
				
				String last = word.substring(word.length()-1);
				appendToDragon(dragon, last);
				
				
				result.put("dragon", dragon);
			}catch(Exception e){
				setError("查询成语出错【"+e.getMessage()+"】");
				
			}
		}
		/*
		 * 输入结果
		 */
		return result;
	}
	/**
	 * 递归找出所有龙身成语<br>
	 * 递归三部曲:定初值，自调自己，找出口
	 *@param dragon 龙体<br>
	 *@param head 成语首字 <br>
	 */
	private void appendToDragon(List<Map<String, String>> dragon ,String head)
	throws Exception {
		if(dragon==null || head==null || head.length()==0){
			return;
		}
		String sql ="SELECT * FROM cn_idiom WHERE name LIKE '"+head+"%' ";
		try{
			DBHelper dbh = DBHelper.getInstance();
			List<Map<String,String>> list = dbh.query(sql);
			if(list.isEmpty()){
				return;//递归出口*
			}
			//从找出的符合条件的成语中随机定位一个
			while(!list.isEmpty()){//还有剩余的元素
				int count = list.size();
				int index = (int)(count*Math.random());//随机定位ids位置
				Map<String, String> map = list.get(index);//取出其中一个
				//取出这一次成语的尾字作为下一次成语的首字
				boolean isExist = false;//表示是否已经在龙体中出现过
				String name = map.get("name");
				//判断这个成语是否出现过
				for(Map<String, String> _map:dragon){
					String _name = _map.get("name");
					if(name.equals(_name)){
						isExist = true;
						break;
					}
				}
				if(isExist){
					//出现重复,丢弃之，再次从其余的寻找一个随机位置
					list.remove(index);
					continue;
				}else{
					//未重复,可添加到龙体，停止其余的随机位置
					//追加到龙体
					dragon.add(map);
					String last = name.substring(name.length()-1);//定初值*
					appendToDragon(dragon,last);//实施递归*//自调自
					break;
				}
			}
			
		
		}catch(Exception e){
			throw e;//向上层调用处抛出异常
		}
		
	}
	
}
