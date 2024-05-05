package cn.gzccc.action;

/**
 * @author Kenith-Z
 * @version 1.0.0
 * @since 2024-05-04 14:24
 */


import java.util.List;
import java.util.Map;

import cn.coolcpu.sql.DataView;
import cn.coolcpu.sql.RecordBean;
import cn.coolcpu.sql.SQLBridgeFactory;
import cn.gzccc.ide.AppEnv;
import cn.gzccc.ide.BaseAction;
import cn.gzccc.ide.IAction;
import cn.gzccc.util.DBHelper;

public class ShapeAction extends BaseAction implements IAction{

    private final int POS_COUNT=9;//字出现的位置个数
    /*
     * 实现执行函数的细节
     */
    @Override
    public Map< String, Object> doAction(Map< String, String> paramMap) {
        /*
         * 一、获取页面传入参数
         */
        String word = paramMap.get("word");
        String[] posChars = new String[POS_COUNT];//出现在各个位置上的字的数组

        for(int i=0;i<POS_COUNT; i++){
            posChars[i] = paramMap.get("wd"+(i+1));
        }

        /*
         * 二、交易细节
         */
        if(word != null){
            //Word不为空时通过提交表单进来交易
            if(word.trim().length()==0){
                boolean posCharsIsEmpty=true;//默认位置数组内容全没填
                for(String s:posChars){
                    if(s.length()>0){
                        posCharsIsEmpty=false;
                        break;
                    }
                }
                if(posCharsIsEmpty){
                    setError("请输入成语匹配字样，如：AABB!或某个位置的字");
                    return result;
                }

            }
            //根据符合条件查询数据库
            DataView dataView = null; //数据查询结果视窗
            List<RecordBean> list = null; //查询结果集

            word = word.trim();//去除空格
            //去除字符串的字数,在UTF-8字符编码 下，一个汉字占3个字节
            int charCount = word.length();
            //转出一个字符数组
            char[] cArray = word.toCharArray();
            //根据用户输入的字样，动态拼接SQL,SQl语法规范约定下标从1开始
            String sql = "SELECT * FROM cn_idiom WHERE 1=1 ";
            //根据字样查询
            if(charCount > 0){
                sql +="AND LENGTH(name)="+(charCount*3)+" ";
                for(int i=0;i<charCount;i++){
                    if(cArray[i] == ',' || cArray[i] == '，'){
                        sql += "AND SUBSTRING(name,"+(i+1)+",1)='，' ";
                        continue;
                    }
                    for(int j=i+1; j<charCount;j++){
                        if(cArray[j] == ','||cArray[j] == '，') {continue;}
                        String left = "SUBSTRING(name,"+(i+1)+",1)";
                        String right = "SUBSTRING(name,"+(j+1)+",1)";
                        String opt = (cArray[i]==cArray[j])?"=":"<>";
                        String and= "AND "+left+opt+right+" ";
                        sql+= and;
                    }
                }
            }
            //根据字出现的位置查询
            for(int i=0;i<POS_COUNT; i++){
                if(posChars[i].length()>0){
                    sql +="AND SUBSTRING(name,"+(i+1)+",1)='"+posChars[i]+"' ";
                }
            }

            sql +="ORDER BY name";
            int pageSize=AppEnv.PAGE_SIZE,page=1;//分页标记
            try{page=Integer.parseInt(paramMap.get("page"));}catch(Exception e){;}
            try{
                sqlBridge = SQLBridgeFactory.bindInstance(getClass().getName());// 获取数据库桥

                dataView = sqlBridge.executeQuery(sql, page, pageSize);//分页

                list = dataView.getResult();//将结果集作为环境变量，设置到当前页面中，使得在JSP页面中可以用JSTL语法操作结果集
                result.put("rowsCount", dataView.getRowsCount());
                result.put("page", dataView.getPage());
                result.put("pageCount", dataView.getPagesCount());
                result.put("isFirstPage", dataView.isFirstPage());
                result.put("isLastPage", dataView.isLastPage());
                result.put("list", bean2Map(list));
            }catch(Exception e){
                setError("查询成语出错【"+e.getMessage()+"】");

            }finally {
                SQLBridgeFactory.freeInstance(sqlBridge);//释放数据库桥
            }

        }

        /*
         * 输入结果
         */
        result.put("posCount",POS_COUNT);
        result.put("posChars",posChars);
        return result;

    }

}