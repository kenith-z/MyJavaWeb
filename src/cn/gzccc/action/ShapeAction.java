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

    private final int POS_COUNT=9;//�ֳ��ֵ�λ�ø���
    /*
     * ʵ��ִ�к�����ϸ��
     */
    @Override
    public Map< String, Object> doAction(Map< String, String> paramMap) {
        /*
         * һ����ȡҳ�洫�����
         */
        String word = paramMap.get("word");
        String[] posChars = new String[POS_COUNT];//�����ڸ���λ���ϵ��ֵ�����

        for(int i=0;i<POS_COUNT; i++){
            posChars[i] = paramMap.get("wd"+(i+1));
        }

        /*
         * ��������ϸ��
         */
        if(word != null){
            //Word��Ϊ��ʱͨ���ύ����������
            if(word.trim().length()==0){
                boolean posCharsIsEmpty=true;//Ĭ��λ����������ȫû��
                for(String s:posChars){
                    if(s.length()>0){
                        posCharsIsEmpty=false;
                        break;
                    }
                }
                if(posCharsIsEmpty){
                    setError("���������ƥ���������磺AABB!��ĳ��λ�õ���");
                    return result;
                }

            }
            //���ݷ���������ѯ���ݿ�
            DataView dataView = null; //���ݲ�ѯ����Ӵ�
            List<RecordBean> list = null; //��ѯ�����

            word = word.trim();//ȥ���ո�
            //ȥ���ַ���������,��UTF-8�ַ����� �£�һ������ռ3���ֽ�
            int charCount = word.length();
            //ת��һ���ַ�����
            char[] cArray = word.toCharArray();
            //�����û��������������̬ƴ��SQL,SQl�﷨�淶Լ���±��1��ʼ
            String sql = "SELECT * FROM cn_idiom WHERE 1=1 ";
            //����������ѯ
            if(charCount > 0){
                sql +="AND LENGTH(name)="+(charCount*3)+" ";
                for(int i=0;i<charCount;i++){
                    if(cArray[i] == ',' || cArray[i] == '��'){
                        sql += "AND SUBSTRING(name,"+(i+1)+",1)='��' ";
                        continue;
                    }
                    for(int j=i+1; j<charCount;j++){
                        if(cArray[j] == ','||cArray[j] == '��') {continue;}
                        String left = "SUBSTRING(name,"+(i+1)+",1)";
                        String right = "SUBSTRING(name,"+(j+1)+",1)";
                        String opt = (cArray[i]==cArray[j])?"=":"<>";
                        String and= "AND "+left+opt+right+" ";
                        sql+= and;
                    }
                }
            }
            //�����ֳ��ֵ�λ�ò�ѯ
            for(int i=0;i<POS_COUNT; i++){
                if(posChars[i].length()>0){
                    sql +="AND SUBSTRING(name,"+(i+1)+",1)='"+posChars[i]+"' ";
                }
            }

            sql +="ORDER BY name";
            int pageSize=AppEnv.PAGE_SIZE,page=1;//��ҳ���
            try{page=Integer.parseInt(paramMap.get("page"));}catch(Exception e){;}
            try{
                sqlBridge = SQLBridgeFactory.bindInstance(getClass().getName());// ��ȡ���ݿ���

                dataView = sqlBridge.executeQuery(sql, page, pageSize);//��ҳ

                list = dataView.getResult();//���������Ϊ�������������õ���ǰҳ���У�ʹ����JSPҳ���п�����JSTL�﷨���������
                result.put("rowsCount", dataView.getRowsCount());
                result.put("page", dataView.getPage());
                result.put("pageCount", dataView.getPagesCount());
                result.put("isFirstPage", dataView.isFirstPage());
                result.put("isLastPage", dataView.isLastPage());
                result.put("list", bean2Map(list));
            }catch(Exception e){
                setError("��ѯ�������"+e.getMessage()+"��");

            }finally {
                SQLBridgeFactory.freeInstance(sqlBridge);//�ͷ����ݿ���
            }

        }

        /*
         * ������
         */
        result.put("posCount",POS_COUNT);
        result.put("posChars",posChars);
        return result;

    }

}