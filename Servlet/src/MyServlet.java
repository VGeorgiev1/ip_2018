

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String,String> db = new HashMap<String,String>(); 
    /**
     * Default constructor. 
     */
    public MyServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("<form method='post'>"
								   +"<label for='key'>Key: </label>"
								   +"<input id='key' name='key'/>"
								   +"<br><label for='value'>Value: </label>"
								   +"<input id='value' name='value'/><br>"
								   +"<button type='submit' />Submit</button>"
								   + "</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.db.put(request.getParameter("key"),request.getParameter("value"));
		for (Map.Entry<String, String> entry : this.db.entrySet())
		{
			response.getWriter().append("<p>" + entry.getKey() + ":" + entry.getValue() + "</p>");
		}
		
	}

}
