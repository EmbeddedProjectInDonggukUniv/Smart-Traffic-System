<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="daoimpl.*,dao.*,java.sql.*;"%>

<%
	request.setCharacterEncoding("UTF-8");
	
	String accident = request.getParameter("accident_status");
	
	FiremanDAO fDao = new FiremanDAOImpl();
	
	if(accident.equals("no")){
		fDao.update("yes", "no");
	}else if(accident.equals("yes")){
		fDao.update("no", "yes");
	}
%>