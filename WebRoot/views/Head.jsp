<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>

<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("PATH",path);
pageContext.setAttribute("BASE_PATH",basePath);
%>

<!DOCTYPE html>
<html>
<head>
    <base href="${BASE_PATH}">
    <title>${APP_NAME}</title>
	<link rel="stylesheet" type="text/css" href="${PATH}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${PATH}/css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="${PATH}/css/demo.css" />
	<link rel="stylesheet" type="text/css" href="${PATH}/css/component.css" />
	<script type="text/javascript" src="${PATH}/js/system.js"></script>
</head>

