package paymentLogServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.MsgTableManager;
import background.PaymentLogTableManager;
import background.ResultSet2Json;

/**
 * 查询消费记录
 * @author mzs
 *created on 2013-12-19
 */
public class GetPaymentLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPaymentLog() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		PaymentLogTableManager opObj = new PaymentLogTableManager();
		String name = request.getParameter("name");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String result = ResultSet2Json.resultSetToJson(opObj.getPaymentLog(name));
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		PaymentLogTableManager opObj = new PaymentLogTableManager();
		String name = request.getParameter("name");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String result = ResultSet2Json.resultSetToJson(opObj.getPaymentLog(name));
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
