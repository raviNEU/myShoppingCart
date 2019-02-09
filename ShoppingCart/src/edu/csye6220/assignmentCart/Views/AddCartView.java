package edu.csye6220.assignmentCart.Views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.csye6220.assignmentCart.Helpers.NavigationUtility;
import edu.csye6220.assignmentCart.Helpers.ShoppingCartController;
import edu.csye6220.assignmentCart.Helpers.StoreInventory;

/**
 * Servlet implementation class AddCartView
 */
@WebServlet("/AddCartView")
public class AddCartView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCartView() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		response.setContentType("text/html");

		String[] selectedItemIds = request.getParameterValues("item");
		PrintWriter prStore = response.getWriter();

		if (selectedItemIds == null || selectedItemIds.length == 0) {
			prStore.println("<p>You have not selected any items to add to the cart</p>\n");
		}
		else {

			String formURL = "ViewCart";
			formURL = response.encodeURL(formURL);


			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(900);
			ShoppingCartController cart;
			synchronized(session) {
				cart = (ShoppingCartController)session.getAttribute("shpCart");
				// New visitors get a fresh shopping cart.
				// Previous visitors keep using their existing cart.
				if (cart == null) {
					cart = new ShoppingCartController();
					session.setAttribute("shpCart", cart);
				}
				if(selectedItemIds.length !=0)
				{
					cart.addOrder(selectedItemIds);
				}

			}


			String docType = "<!DOCTYPE HTML>\n";
			prStore.println(docType + "<html>\n" + "<head><title>My Store</title></head>\n" + "<body>\n"
					+ "<h1>Shop World</h1>" + "<hr></hr>"
					+ "<p style=\"align:center\">The following item(s) has been added to your Shopping Cart successfully</p>\n"
					+ "<div id=\"menu\" >\n" + "<ul>\n"
					);

			
				for (String itemId : selectedItemIds) {
					prStore.println("<li>" + (StoreInventory.getProductById(itemId)).getProductDesc() + "</li>\n");
				}
			
				}
		prStore.println("</ul></div>"); 
		prStore.println(NavigationUtility.Navigator() + "</body></html>");
	}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
		 *      response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			doGet(request, response);
		}

	}
