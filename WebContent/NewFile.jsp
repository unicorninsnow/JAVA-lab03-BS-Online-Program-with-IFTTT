<span style="font-size:12px;"><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
  
<%@ page import="java.sql.*,javax.sql.*,javax.naming.*" %>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><html>  
  
  <head>  
  
    <title>My JSP 'tomcatTest.jsp' starting page</title>  
  
  </head>  
  
  <body>  
  
    <%  
          Context  ctx = new InitialContext();  
          DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ifttt"); 
          Connection con = ds.getConnection();  
          Statement st = con.createStatement();  
          st.executeUpdate("create table nim");  
          st.close();  
          con.close();            
  
     %>  
  
  </body>  
  
</html>  
  
</span>  