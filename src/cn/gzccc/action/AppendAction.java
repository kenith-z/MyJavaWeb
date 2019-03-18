/*
 * 添加成语交易执行类
 */
package cn.gzccc.action;


import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.poi.util.TempFile;

import cn.gzccc.ide.BaseAction;
import cn.gzccc.ide.IAction;
import cn.gzccc.util.ChineseSpelling;
import cn.gzccc.util.DBHelper;
import cn.gzccc.util.DateUtil;
import cn.gzccc.util.FileUtil;

public class AppendAction extends BaseAction implements IAction{
	
	
	/*
	 * 实现执行函数的细节
	 */
	@Override
	public Map< String, Object> doAction(Map< String, String> paramMap) {
		/*
		 * 一、获取页面传入参数
		 */
		String word = paramMap.get("word");
		String pinyin = paramMap.get("pinyin");
		String paraphrase = paramMap.get("paraphrase");
		String provenance = paramMap.get("provenance");
		String example = paramMap.get("example");
		
		/*
		 * 二、交易细节
		 */
		//1.验证数据完整性和正确性
		if(word != null){
			//Word不为空时通过提交表单进来交易
			if(word.trim().length()==0){
				setError("请输入您要添加的成语!");
				return result;
			}
			
			word = word.trim();//去除空格
			try{
				//2.判断成语是否存在与数据库中
				String sql = "SELECT * FROM cn_idiom WHERE name='"+word+"' ";
				DBHelper dbh = DBHelper.getInstance();
				List<Map<String, String>> list = dbh.query(sql);//查询数据库
				if(!list.isEmpty()){
					setError("您要添加的成语【"+word+"】已经存在!");
					return result;
				}
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
				//取出上传的图片文件
				String imgName=null;//要保持的图片文件名
				String fileName = paramMap.get("fileName");
				String filePath = paramMap.get("filePath");
				if(fileName != null){//如果用户有上传文件
					File tempFile =new File(filePath);//临时文件
					if(tempFile.exists()){
						//要求必须是图片格式的文件
						if(fileName.endsWith(".jpg")
								||fileName.endsWith(".jpeg")
								||fileName.endsWith(".gif")
								||fileName.endsWith(".png")){
							imgName = ChineseSpelling.getSpelling(word);//讲成语名称转变成拼音
							String postFix = fileName.substring(fileName.lastIndexOf("."));//取出文件名后缀
							imgName = imgName+"_"+DateUtil.getFlowNo()+postFix;
							//将临时文件复制成为正式图片文件
							String imgPath = rootPath+"/image/idiom/"+imgName;
							File imgFile = new File(imgPath);
							FileUtil.copy(tempFile, imgFile);
						}else{
							//删除临时文件
							setError("请上传合法的图片格式文件(jpg、jpeg、gif、png)!");
							return result;
						}
						tempFile.delete();
					}
				}
				
				//将成语添加到数据库
				String itemId = DateUtil.getFlowNo();//以系统当前流水号作为成语的索引
				sql = "INSERT INTO cn_idiom (item_id,name,pinyin,paraphrase,provenance,example,img_name) "
					+"VALUES ('"+itemId+"','"+word+"','"+pinyin+"','"+paraphrase+"','"+provenance+"','"+example+"','"+imgName+"') ";	
				dbh.update(sql);//执行插入
				//将刚添加的成语查询并显示到页面上
				sql = "SELECT * FROM cn_idiom WHERE item_id='"+itemId+"' ";
				list = dbh.query(sql);//查询数据库
				Map<String,String> map = list.get(0);
				result.put("map",map);
				result.put("list",list);
				
			}catch(Exception e){
				setError("添加成语出错【"+e.getMessage()+"】");
			}
		}
		/*
		 * 输入结果
		 */
		return result;
	}
}