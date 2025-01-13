package com.test.orderprocessingsystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import com.test.orderprocessingsystem.model.Order;
import com.test.orderprocessingsystem.model.Product;
import com.test.orderprocessingsystem.usecase.OrderUseCase;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/orderServlet")
public class OrderServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final OrderUseCase orderUseCase;
	
	public OrderServlet(OrderUseCase orderUseCase) {
		this.orderUseCase = orderUseCase;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String query = req.getParameter("query");
		Order order = this.orderUseCase.queryOrder(query);
		PrintWriter out = this.printOutput(res);
		
		if (order != null) {
			out.println("<p>Order</p>");
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>ID</th>");
			out.println("<th>Customer Name</th>");
			out.println("<th>Customer Type</th>");
			out.println("<th>Product</th>");
			out.println("<th>Amount</th>");
			out.println("<th>Currency</th>");
			out.println("<th>Status</th>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>" + order.getId() + "</td>");
			out.println("<td>" + order.getCustomerName() + "</td>");
			out.println("<td>" + order.getCustomerType() + "</td>");
			out.println("<td>" + order.getProduct().getType() + "</td>");
			out.println("<td>" + order.getAmount() + "</td>");
			out.println("<td>" + order.getCurrency() + "</td>");
			out.println("<td>" + order.getStatus() + "</td>");
			out.println("</tr>");
			out.println("</table>");
		} else {
			out.println("No record found.");
		}

		out.println("</body>");
		out.println("</html>");
		
		res.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Order order = new Order();
		order.setCustomerName(req.getParameter("customerName"));
		order.setCustomerType(req.getParameter("customerType"));
		Product product = new Product();
		product.setType(req.getParameter("productType"));
		order.setProduct(product);
		order.setAmount(new BigDecimal(req.getParameter("amount")));
		order.setCurrency(req.getParameter("currency"));
		
		PrintWriter out = this.printOutput(res);
		
		try {
			String id = this.orderUseCase.addOrder(order);
			
			if (id != null) {
				out.println(id);
				res.setStatus(HttpServletResponse.SC_OK);
			} else {
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				out.println("Error");
			}
		} catch (Exception ex) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.println(ex.getMessage());
		}
		out.println("</body>");
		out.println("</html>");
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Order order = new Order();
		order.setId(req.getParameter("id"));
		order.setCustomerName(req.getParameter("customerName"));
		order.setCustomerType(req.getParameter("customerType"));
		Product product = new Product();
		product.setType(req.getParameter("productType"));
		order.setProduct(product);
		order.setAmount(new BigDecimal(req.getParameter("amount")));
		order.setCurrency(req.getParameter("currency"));
		
		PrintWriter out = this.printOutput(res);
		
		try {
			order = this.orderUseCase.updateOrder(order);
			out.println(order);
			res.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception ex) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.println(ex.getMessage());
		}
		out.println("</body>");
		out.println("</html>");
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = req.getParameter("id");
		PrintWriter out = this.printOutput(res);
		try {
			this.orderUseCase.deleteOrder(id);
			res.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception ex) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.println(ex.getMessage());
		}
		out.println("</body>");
		out.println("</html>");
	}
	
	private PrintWriter printOutput(HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Welcome to Servlet page !!!</title>");
		out.println("</head>");
		out.println("<body>");
		
		res.setContentType("text/html");
		return out;
	}
}
