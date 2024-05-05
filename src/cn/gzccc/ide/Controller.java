/*
 * MVC�������֮������
 * ����������Ҫ��������Ϊǰ��ҳ�����̨���������
 */
package cn.gzccc.ide;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /*
     * ��Ӧǰ��ҳ�������
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // ��ȡӦ�ö���
        ServletContext application = super.getServletContext();


        /*
         * yi����Ӧǰ��ҳ�������
         */
        // ��ҳ��������õ�����-ֵ����ӳ�������
        Map<String, String> paramMap = new HashMap<String, String>();
        System.out.println(request);
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith("multipart/form-data")) {


            // ��ȡ���б�����
            Collection<Part> parts = request.getParts();

            for (Part part : parts) {
                // ��ȡ���ֶε�����
                String fieldName = part.getName();
                // �ж��Ƿ�Ϊ�ļ�����
                if (part.getContentType() != null) {
                    // �����ļ��ϴ�
                    String allFileName = part.getSubmittedFileName();
                    if (allFileName.isEmpty()) {
                        continue;
                    }
                    String fileName;//�ļ���
                    try (InputStream input = part.getInputStream()) {

                        String uploadPath = Path.of(application.getRealPath(""), "/upload").toString();
                        //��̨���������ͨ���ò�����ȡ�ϴ�·��
                        paramMap.put("uploadPath", uploadPath);
                        fileName = "Temp_" + System.currentTimeMillis() + "_" + allFileName;
                        String filePath = Path.of(uploadPath, "/temp/", fileName).toString();
                        Path outputPath = Paths.get(filePath);
                        Files.copy(input, outputPath, StandardCopyOption.REPLACE_EXISTING);
                        //��̨���������ͨ���ò�����ȡ�ϴ��ļ���
                        paramMap.put("fileName", fileName);
                        //��̨���������ͨ���ò�����ȡ�ϴ��ļ�·��
                        paramMap.put("filePath", filePath);
                        System.out.println("filePath:" + filePath);
                    }
                } else {
                    // ������ͨ���ֶ�
                    String fieldValue = request.getParameter(fieldName);
                    System.out.println(fieldValue);
                    System.out.println(fieldValue);
                    //���뵽������Ĳ�������
                    paramMap.put(fieldName, fieldValue);
                    //���õ���������еĻ�������
                    request.setAttribute(fieldName, fieldValue);
                    // ������Ҫ�����ֶ�ֵ

                }
            }


        } else {
            // ��ȡҳ�洫����������Ƽ���
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {// �������л�����ʣ��Ԫ��
                // �Ӽ���������һ��Ԫ�أ����ҽ�ָ���ƶ�����һ��Ԫ��
                String name = paramNames.nextElement();// ������
                String value = request.getParameter(name);// ����ֵ
                // ��ҳ�ı�����ַ�����Ĭ��ͳһʹ��ISO-8859-1��������Ҫװ����UTF-8���������������
//				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");

                // ��ҳ��������õ�����������
                request.setAttribute(name, value);
                // ��ӵ�ӳ�������
                paramMap.put(name, value);
            }

        }


        /*
         * ����Ѱ�ҽ���ִ���࣬���о�������ݴ���
         */
        /** ȡ��URI��ַ�еĽ����� */
        String uri = request.getRequestURI();
        int pos1 = uri.lastIndexOf("/");
        int pos2 = uri.lastIndexOf(".act");
        /** �ַ��Ӵ���ȡ */
        String txcode = uri.substring(pos1 + 1, pos2);
        request.setAttribute("TXCODE", txcode);


        String error = null;// ���׳�����Ϣ
        String className = null;// �������ȫ��
        String page = null; // Ŀ����ͼ
        String pageDesc = null; // ��������
        String viewName = null;//���ҳ�������
        /** ͨ���������ȡ����������Ϣ */
        Map<String, Object> actionMap = getActionConfig(txcode);
        if (actionMap == null) {
            // �������ļ���δ�ҵ���ý�����ƥ��Ľ��׽ڵ�
            error = "�����롾" + txcode + "��������";

        } else {
            className = (String) actionMap.get("class");
            pageDesc = (String) actionMap.get("desc");
            Map<String, String> views = (Map<String, String>) actionMap.get("views");

            request.setAttribute("PAGE_DESC", pageDesc); // �������������õ�����������
            try {
                // ʵ�������׽ӿ�
                IAction action = (IAction) Class.forName(className)
                        .newInstance();
                action.setSession(request.getSession());//�����ഫ��Ự����
                Map<String, Object> result = action.doAction(paramMap);// ִ�н���
                //���ݽ��ײ��setPage()�趨���ҳ��
                page = views.get("");//Ĭ�����ҳ��
                viewName = action.getPage();
                if (views.containsKey(viewName)) {
                    page = views.get(viewName);
                }
                // ��ȡ������Ϣ
                error = action.getError();
                int keyCount = result.size();// ���ĸ���
                String[] keys = result.keySet().toArray(new String[keyCount]);// ������
                for (String key : keys) {
                    Object value = result.get(key);// ֵ
                    request.setAttribute(key, value);// ���õ���������
                }
            } catch (InstantiationException e) {
                error = "����ʵ��������";
            } catch (IllegalAccessException e) {
                error = "�Ƿ����ʽ��׳���";
            } catch (ClassNotFoundException e) {
                error = "�Ҳ��������ࡾ" + className + "��";
            }
        }

        /*
         * ���������������Ŀ����ͼ
         */
        if (error != null) {
            request.setAttribute("ERROR", error);
            if (!"download".equalsIgnoreCase(viewName)) {
                page = "/views/Error.jsp";
            }

        }

        // �������ض��򣩵�Ŀ����ͼ
        application.getRequestDispatcher(page).forward(request, response);
    }

    /*
     * װ�ؽ��������ļ�actionlist.xml ���ݽ������ҳ���Ӧ�Ľ���������Ϣ������ִ�����·����Ŀ��ҳ�桢����������
     */
    private static java.util.List<java.util.Map<String, Object>> actionList = null;

    private static java.util.Map<String, Object> getActionConfig(String txcode) {
        java.util.Map<String, Object> actionMap = null;
        if (txcode == null || txcode.length() == 0) return actionMap;
        if (actionList == null) {
            //װ�������ļ�
            java.io.FileInputStream fis = null;
            try {
                String path = Controller.class.getClassLoader().getResource("config/").getPath();
                path = java.net.URLDecoder.decode(path, "UTF-8");//���·���е���������
                java.io.File file = new java.io.File(path + "actionlist.xml");//�ļ�����,���������ļ�
                fis = new java.io.FileInputStream(file);//���ļ����뵽��������
                javax.xml.parsers.DocumentBuilder docBuilder = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder();
                org.w3c.dom.Document doc = docBuilder.parse(fis);
                org.w3c.dom.Element root = doc.getDocumentElement();
                org.w3c.dom.NodeList nodes = root.getElementsByTagName("action");
                actionList = new java.util.ArrayList<java.util.Map<String, Object>>();
                for (int i = 0; i < nodes.getLength(); i++) {
                    org.w3c.dom.Node node = nodes.item(i);
                    if (node.hasAttributes()) {
                        java.util.Map<String, Object> map = new java.util.HashMap<String, Object>();
                        org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
                        String actionName = attrs.getNamedItem("name").getNodeValue();
                        String className = attrs.getNamedItem("class").getNodeValue();
                        String desc = attrs.getNamedItem("desc").getNodeValue();
                        map.put("name", actionName);
                        map.put("class", className);
                        map.put("desc", desc);
                        actionList.add(map);
                        org.w3c.dom.NodeList viewNodes = node.getChildNodes();
                        java.util.Map<String, String> views = new java.util.HashMap<String, String>();
                        for (int j = 0; j < viewNodes.getLength(); j++) {
                            org.w3c.dom.Node viewNode = viewNodes.item(j);
                            if (viewNode.getNodeName().equals("view")) {
                                attrs = viewNode.getAttributes();
                                String viewName = "";
                                org.w3c.dom.Node nameNode = attrs.getNamedItem("name");
                                if (nameNode != null) viewName = nameNode.getNodeValue();
                                String viewPage = viewNode.getTextContent();
                                views.put(viewName, viewPage);
                            }
                        }
                        map.put("views", views);
                    }
                }
            } catch (Exception e) {
                System.err.println("װ�ؽ��������ļ�����");
                e.printStackTrace();
                return null;
            } finally {
                if (fis != null) try {
                    fis.close();
                } catch (java.io.IOException e) {
                }
            }
        }
        if (actionList != null) {
            for (java.util.Map<String, Object> map : actionList) {
                if (txcode.equals(map.get("name"))) {
                    return map;
                }
            }
        }
        return actionMap;
    }
}
