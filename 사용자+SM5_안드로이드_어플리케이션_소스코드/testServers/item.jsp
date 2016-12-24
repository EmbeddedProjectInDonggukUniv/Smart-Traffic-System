<%@ page import="dao.*"%>
<%@ page import="daoimpl.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String item = request.getParameter("item");
	int price = Integer.parseInt(request.getParameter("price"));
	GregorianCalendar today = new GregorianCalendar ( ); 
	 
	 
	int year = today.get ( today.YEAR );
	int month = today.get ( today.MONTH ) + 1;
	int day = today.get ( today.DAY_OF_MONTH ); 

	String insertDay = year+"/"+month+"/"+day;
	
	ItemDAO itemDAO = new ItemDAOImpl();
	itemDAO.insert(item, price, insertDay);
%>