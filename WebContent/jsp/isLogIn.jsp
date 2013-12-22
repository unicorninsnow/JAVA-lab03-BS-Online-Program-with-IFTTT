<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%session.setAttribute("user","Unicorninsnow");
	if (session.getAttribute("user") == null || session.getAttribute("user").toString().equals("")) {
		response.sendRedirect(request.getContextPath() + "/jsp/index.html");
		return;
	}
%>