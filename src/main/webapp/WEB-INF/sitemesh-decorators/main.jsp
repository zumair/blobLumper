<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" isELIgnored="false"	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"	prefix="decorator" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<link rel="stylesheet" href="<c:url value='/web-resources/css/bootstrap.css' />" /> 
	<link rel="stylesheet" href="<c:url value='/web-resources/css/app.css' />" />
	<script  src="<c:url value='/web-resources/js/lib/jquery-2.1.1.min.js' />" ></script>
	<script  src="<c:url value='/web-resources/js/lib/angular.min.js' />"></script>
	
	
	<decorator:head />
</head>
<body>
	<div class="container">
	<decorator:body />
	</div>
</body>
</html>



