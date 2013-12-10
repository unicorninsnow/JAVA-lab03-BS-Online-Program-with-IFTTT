<%@ page import = "background.UserTableManager" %>
<jsp:useBean id="userTest"
class = "background.UserTableManager" scope="page" ></jsp:useBean>

<html>
<body>
<h1>Registration Using JSP</h1>

 <%
 if (addressId.getLastName() == null ||
 addressId.getFirstName() == null) {
 out.println("Last Name and First Name are required");
 return; // End the method
 }
 %>

 <p>You entered the following data</p>
 <p>Last name: <%= addressId.getLastName() %></p>
 <p>First name: <%= addressId.getFirstName() %></p>
 <p>MI: <%= addressId.getMi() %></p>
 <p>Telephone: <%= addressId.getTelephone() %></p>
 <p>Email: <%= addressId.getEmail() %></p>
 <p>Address: <%= addressId.getStreet() %></p>
 <p>City: <%= addressId.getCity() %></p>
 <p>State: <%= addressId.getState() %></p>
 <p>Zip: <%= addressId.getZip() %></p>

 <!-- Set the action for processing the answers -->
 <form method = "post" action = "StoreStudent.jsp">
 <input type = "submit" value = "Confirm">
33 </form>
34 </body>
35 </html>
GetRegistrationData.jsp invokes StoreStudent.jsp (line 31) when the user clicks
the Confirm button. In Listing 40.18, the same addressId is shared with the preceding page
within the scope of the same session in lines 3–4. A bean for StoreData is created in lines
5–6 with the scope of application.
<jsp:setProperty name = "addressId" property = "*" />
scope = "session"
id = "addressId"