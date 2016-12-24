<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="daoimpl.*,dao.*,java.sql.*;"%>

<%
	request.setCharacterEncoding("UTF-8");
	float speed = Float.parseFloat(request.getParameter("speed"));
	
	TrafficDAO tDao = new TrafficDAOImpl();
	
	tDao.insert(speed);
	
%>