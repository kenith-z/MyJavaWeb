/*
 * 编辑成语交易执行类
 */
package cn.gzccc.action;


import java.util.List;
import java.util.Map;

import cn.gzccc.ide.BaseAction;
import cn.gzccc.ide.IAction;
import cn.gzccc.util.DBHelper;

public class EditAction extends BaseAction implements IAction{
	
	
	/*
	 * 实现执行函数的细节
	 */
	@Override
	public Map< String, Object> doAction(Map< String, String> paramMap) {
		/*
		 * 一、获取页面传入参数
		 */
		String option = paramMap.get("option");//交易选项(EDIT、SAVE、SHOW)
		String itemId = paramMap.get("itemId");
		String word = paramMap.get("word");
		String pinyin = paramMap.get("pinyin");
		String paraphrase = paramMap.get("paraphrase");
		String provenance = paramMap.get("provenance");
		String example = paramMap.get("example");
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
			List<Map<String,String>> list =null;
			itemId = itemId.trim();//去除空格
			try{
				//2.查询成语是否存在与数据库中
				DBHelper dbh = DBHelper.getInstance();
				String sql = null;
				if("SHOW".equals(option)||"EDIT".equals(option)){
					/*
					 * 从列表页点“成语”进入明细页面
					 * 从列表页点“修改”进入编辑页面
					 */
					if("SHOW".equals(option))super.setPage("show");
					sql = "SELECT * FROM cn_idiom WHERE item_id='"+itemId+"' ";
					
					list = dbh.query(sql);//执行查询返回结果集
					if(list.isEmpty()){
						setError("找不到该成语!");
						return result;
					}
					Map<String,String> map = list.get(0);
					//将结果集作为环境变量，设置到当前页面中，使得在JSP页面中可以用JSTL语法操作结果集
					result.put("map", map);
				}else if("SAVE".equals(option)){//从列表页面的“保存”进入
					//名称，拼音，释义不能为空
					if(word==null||word.trim().length()==0
							||pinyin==null||pinyin.trim().length()==0
							||paraphrase==null||paraphrase.trim().length()==0){
						setError("名称，拼音，释义不能为空!");
						return result;
					}
					word=word.trim();
					pinyin=pinyin.trim();
					paraphrase=paraphrase.trim();
					provenance=provenance.trim();
					example=example.trim();
					
					//避免将成语名称修改成与其他成语同名
					sql="SELECT * FROM cn_idiom WhERE "
							+ "name='"+word+"' AND item_id<>'"+itemId+"' ";
					list = dbh.query(sql);
					if(!list.isEmpty()){
						setError("该成语【"+word+"】已经在数据库存在!");
						return result;
					}
					//执行更新
					sql = "UPDATE cn_idiom SET "
							+"name='"+word+"',"
							+"pinyin='"+pinyin+"',"
							+"paraphrase='"+paraphrase+"',"
							+"provenance='"+provenance+"',"
							+"example='"+example+"' "
							+"WHERE item_id='"+itemId+"' ";
					dbh.update(sql);
					//将刚修改的成语查询并显示到页面上
					sql = "SELECT * FROM cn_idiom WHERE item_id='"+itemId+"' ";
					list = dbh.query(sql);//查询数据库
					Map<String,String> map = list.get(0);
					result.put("map",map);
					result.put("list",list);
				}
				
			}catch(Exception e){
				setError("出错【"+e.getMessage()+"】");
				
			}
		}
		/*
		 * 输入结果
		 */
		return result;
	}
	
}
