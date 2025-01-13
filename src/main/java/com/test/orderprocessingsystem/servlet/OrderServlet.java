package com.test.orderprocessingsystem.servlet;

import java.io.IOException;

import com.test.orderprocessingsystem.model.Order;
import com.test.orderprocessingsystem.usecase.OrderUseCase;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/servlet/order")
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
		
		req.setAttribute("order", order);
		res.setContentType("text/html");
		res.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Order order = (Order) req.getAttribute("order");
		String id = this.orderUseCase.addOrder(order);
		res.setContentType("text/html");
		if (id == null) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			req.setAttribute("id", id);
			res.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Order order = (Order) req.getAttribute("order");
		Order orderResponse = this.orderUseCase.updateOrder(order);
		res.setContentType("text/html");
		if (orderResponse == null) {
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			req.setAttribute("order", orderResponse);
			res.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = req.getParameter("id");
		this.orderUseCase.deleteOrder(id);
		res.setContentType("text/html");
		res.setStatus(HttpServletResponse.SC_OK);
	}
}
