/*
 * MVC开发框架之交易执行类
 * 成语查询
 */
package cn.gzccc.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import cn.coolcpu.sql.DataView;
import cn.coolcpu.sql.RecordBean;
import cn.coolcpu.sql.SQLBridgeFactory;
import cn.gzccc.ide.AppEnv;
import cn.gzccc.ide.BaseAction;
import cn.gzccc.ide.IAction;
import cn.gzccc.util.DateUtil;

public class QueryAction extends BaseAction implements IAction{
	
	/*
	 * 实现执行函数的细节
	 */
	@Override
	public Map< String, Object> doAction(Map< String, String> paramMap) {
		/*
		 * 一、获取页面传入参数
		 */
		String option = paramMap.get("option");//交易选项（LIST查询，DOWNLOAD生成EXCEL并下载）
		String word = paramMap.get("word");
		String paraphrase = paramMap.get("paraphrase");
		String provenance = paramMap.get("provenance");
		String shengmu = paramMap.get("shengmu");
		String yunmu = paramMap.get("yunmu");
		String numhead = paramMap.get("numhead");
		String iu = paramMap.get("iu");
		
		
		
		/*
		 * 二、交易细节
		 */
		 if(option==null){
			 /**通过URI链接进入交易类，则不要查询数据库*/
			 return result;
		 }
		
			/**验证数据的完整性和正确性*/
			if(word.length()==0
				&& paraphrase.length()==0
				&& provenance.length()==0
				&& shengmu.length()==0
				&& yunmu.length()==0
				&& numhead.length()==0){
				setError("请输入至少一个条件！");
				return result;
			}
			
			//对查询关键字符串切割成数组，用正则表达式，\s表示匹配任何空白字符，包括空格、制表符、换页符等等。等价于 [ \f\n\r\t\v]。
			String[] wos = word.split("\\s");
			String[] par = paraphrase.split("\\s");
			String[] pro = provenance.split("\\s");
			String sql = "SELECT * FROM cn_idiom WHERE 1=1 ";
			if(word.length()>0){
				for(String s:wos){
					sql += "AND name LIKE '%"+s+"%' ";
				}
			}
			if(paraphrase.length()>0){
				for(String st:par){
					sql += "AND paraphrase LIKE '%"+st+"%' ";
				}
			}
			if(provenance.length()>0){
				for(String str:pro){
					sql += "AND provenance LIKE '%"+str+"%' ";
				}
			}
			if(shengmu.length()>0 && yunmu.length()>0){
				
					sql += "AND pinyin LIKE '"+(shengmu+iu+yunmu+" ")+"%' ";
				
			}if(numhead.length()>0){
				
					sql += "AND name LIKE '"+numhead+"%' ";
				
			}
			
			sql += "ORDER BY name";//排序
			//根据符合条件查询数据库
			int pageSize=AppEnv.PAGE_SIZE,page=1;//分页标记
			try{page=Integer.parseInt(paramMap.get("page"));}catch(Exception e){;}
			DataView dataView = null; //数据查询结果视窗
			List<RecordBean> list = null; //查询结果集
			
			try{
				sqlBridge = SQLBridgeFactory.bindInstance(getClass().getName());// 获取数据库桥
				if(option.equals("LIST")){
					dataView = sqlBridge.executeQuery(sql, page, pageSize);//执行分页查询
					list = dataView.getResult();//将结果集作为环境变量，设置到当前页面中，使得在JSP页面中可以用JSTL语法操作结果集
					result.put("rowsCount", dataView.getRowsCount());
					result.put("page", dataView.getPage());
					result.put("pageCount", dataView.getPagesCount());
					result.put("isFirstPage", dataView.isFirstPage());
					result.put("isLastPage", dataView.isLastPage());
					result.put("list", bean2Map(list));
				}else if(option.equals("DOWNLOAD")){
					//定向下载
					super.setPage("download");
					//查询数据
					dataView = sqlBridge.executeQuery(sql);
					list = dataView.getResult();
					if(list.isEmpty()){
						setError("找不到相关记录!");
					}else{
						result.put("list", bean2Map(list));
						result.put("rowsCount", dataView.getRowsCount());
						result.put("dateTime",DateUtil.fullDateTime(2));
						//生成Excel
						String fullDate = DateUtil.fullDateTime(0);//时间戳
						String srcFile = super.rootPath+"/views/tmpl/IdiomQuery.xls";//模板文件				
						String tgtFile = super.rootPath+"/download/成语查询结果_"+fullDate+".xls";//输出文件
						XLSTransformer transformer = new XLSTransformer();
						transformer.transformXLS(srcFile, result, tgtFile);
						//提供给用户下载的文件全路径
						result.put("filepath",tgtFile);
						//下载另存的默认文件名
						result.put("filename","成语查询结果_"+fullDate+".xls");
						//下载之后是否要删除临时文件
						result.put("isDelete","true");
					}
				}
				
			}catch(FileNotFoundException e){
				setError("没找到模板文件");
			}catch(ParsePropertyException e){
				setError("解析XLS文件出错");
			}catch(IOException e){
				setError("存取XLS文件出错");
			}catch(Exception e){
				setError("查询成语出错【"+e.getMessage()+"】");
				
			}finally {
				SQLBridgeFactory.freeInstance(sqlBridge);//释放数据库桥
			}
		
		
		/*
		 * 三、输入结果
		 */
		return result;
		
	}

}
