<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="daoimpl.*,dao.*,java.sql.*,model.*;"%>

<%
	request.setCharacterEncoding("UTF-8");
	float speed = Float.parseFloat(request.getParameter("speed"));
	String year = request.getParameter("year");
	String month = request.getParameter("month");
	String day = request.getParameter("day");
	String hour = request.getParameter("hour");
	String min = request.getParameter("min");
	
	TrafficDataDAO tDao = new TrafficDataDAOImpl();
	
	DecisionTreeModel dtm = tDao.decisionTree(year, month, day, hour, min);
	
	tDao.makeRDD(speed, dtm);
			
%>