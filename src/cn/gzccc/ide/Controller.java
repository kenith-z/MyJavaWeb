/*
 * MVC开发框架之控制器
 * 控制器的主要作用是作为前端页面与后台程序的桥梁
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
     * 相应前端页面的请求
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
        // 获取应用对象
        ServletContext application = super.getServletContext();


        /*
         * yi、相应前端页面的请求
         */
        // 将页面参数设置到“键-值”对映射对象中
        Map<String, String> paramMap = new HashMap<String, String>();
        System.out.println(request);
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith("multipart/form-data")) {


            // 获取所有表单部件
            Collection<Part> parts = request.getParts();

            for (Part part : parts) {
                // 获取表单字段的名称
                String fieldName = part.getName();
                // 判断是否为文件部件
                if (part.getContentType() != null) {
                    // 处理文件上传
                    String allFileName = part.getSubmittedFileName();
                    if (allFileName.isEmpty()) {
                        continue;
                    }
                    String fileName;//文件名
                    try (InputStream input = part.getInputStream()) {

                        String uploadPath = Path.of(application.getRealPath(""), "/upload").toString();
                        //后台交易类可以通过该参数获取上传路径
                        paramMap.put("uploadPath", uploadPath);
                        fileName = "Temp_" + System.currentTimeMillis() + "_" + allFileName;
                        String filePath = Path.of(uploadPath, "/temp/", fileName).toString();
                        Path outputPath = Paths.get(filePath);
                        Files.copy(input, outputPath, StandardCopyOption.REPLACE_EXISTING);
                        //后台交易类可以通过该参数获取上传文件名
                        paramMap.put("fileName", fileName);
                        //后台交易类可以通过该参数获取上传文件路径
                        paramMap.put("filePath", filePath);
                        System.out.println("filePath:" + filePath);
                    }
                } else {
                    // 处理普通表单字段
                    String fieldValue = request.getParameter(fieldName);
                    System.out.println(fieldValue);
                    System.out.println(fieldValue);
                    //传入到交易类的参数对象
                    paramMap.put(fieldName, fieldValue);
                    //设置到请求对象中的环境变量
                    request.setAttribute(fieldName, fieldValue);
                    // 根据需要处理字段值

                }
            }


        } else {
            // 获取页面传入参数的名称集合
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {// 当集合中还存在剩余元素
                // 从集合中区属一个元素，并且将指针移动到下一个元素
                String name = paramNames.nextElement();// 参数名
                String value = request.getParameter(name);// 参数值
                // 网页文本框的字符编码默认统一使用ISO-8859-1，这里需要装换成UTF-8避免出现中午乱码
//				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");

                // 将页面参数设置到环境变量中
                request.setAttribute(name, value);
                // 添加到映射对象中
                paramMap.put(name, value);
            }

        }


        /*
         * 二、寻找交易执行类，进行具体的数据处理
         */
        /** 取出URI地址中的交易码 */
        String uri = request.getRequestURI();
        int pos1 = uri.lastIndexOf("/");
        int pos2 = uri.lastIndexOf(".act");
        /** 字符子串截取 */
        String txcode = uri.substring(pos1 + 1, pos2);
        request.setAttribute("TXCODE", txcode);


        String error = null;// 交易出错信息
        String className = null;// 交易类的全名
        String page = null; // 目标视图
        String pageDesc = null; // 交易描述
        String viewName = null;//输出页面的名字
        /** 通过交易码获取交易配置信息 */
        Map<String, Object> actionMap = getActionConfig(txcode);
        if (actionMap == null) {
            // 在配置文件中未找到与该交易码匹配的交易节点
            error = "交易码【" + txcode + "】不存在";

        } else {
            className = (String) actionMap.get("class");
            pageDesc = (String) actionMap.get("desc");
            Map<String, String> views = (Map<String, String>) actionMap.get("views");

            request.setAttribute("PAGE_DESC", pageDesc); // 将交易描述设置到环境变量中
            try {
                // 实例化交易接口
                IAction action = (IAction) Class.forName(className)
                        .newInstance();
                action.setSession(request.getSession());//向交易类传入会话对象
                Map<String, Object> result = action.doAction(paramMap);// 执行交易
                //根据交易层的setPage()设定输出页面
                page = views.get("");//默认输出页面
                viewName = action.getPage();
                if (views.containsKey(viewName)) {
                    page = views.get(viewName);
                }
                // 获取错误信息
                error = action.getError();
                int keyCount = result.size();// 键的个数
                String[] keys = result.keySet().toArray(new String[keyCount]);// 键数组
                for (String key : keys) {
                    Object value = result.get(key);// 值
                    request.setAttribute(key, value);// 设置到环境变量
                }
            } catch (InstantiationException e) {
                error = "交易实例化出错！";
            } catch (IllegalAccessException e) {
                error = "非法访问交易出错！";
            } catch (ClassNotFoundException e) {
                error = "找不到交易类【" + className + "】";
            }
        }

        /*
         * 三、将结果发布到目标视图
         */
        if (error != null) {
            request.setAttribute("ERROR", error);
            if (!"download".equalsIgnoreCase(viewName)) {
                page = "/views/Error.jsp";
            }

        }

        // 发布（重定向）到目标视图
        application.getRequestDispatcher(page).forward(request, response);
    }

    /*
     * 装载交易配置文件actionlist.xml 根据交易码找出对应的交易配置信息（交易执行类的路径、目标页面、交易描述）
     */
    private static java.util.List<java.util.Map<String, Object>> actionList = null;

    private static java.util.Map<String, Object> getActionConfig(String txcode) {
        java.util.Map<String, Object> actionMap = null;
        if (txcode == null || txcode.length() == 0) return actionMap;
        if (actionList == null) {
            //装载配置文件
            java.io.FileInputStream fis = null;
            try {
                String path = Controller.class.getClassLoader().getResource("config/").getPath();
                path = java.net.URLDecoder.decode(path, "UTF-8");//解决路径中的中文乱码
                java.io.File file = new java.io.File(path + "actionlist.xml");//文件对象,并联配置文件
                fis = new java.io.FileInputStream(file);//将文件读入到输入流中
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
                System.err.println("装载交易配置文件出错！");
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
