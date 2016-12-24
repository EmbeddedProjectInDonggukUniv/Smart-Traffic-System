<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="daoimpl.*,dao.*,java.sql.*;"%>

<%
	request.setCharacterEncoding("UTF-8");
	
	String accident = request.getParameter("accident");
	
	AccidentDAO aDao = new AccidentDAOImpl();
	
	aDao.insert(accident);
%>