<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<% /* session.setAttribute("name","admin");  */
	if (session.getAttribute("name") == null || session.getAttribute("name").toString().equals("")) {
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		return;
	}
%>