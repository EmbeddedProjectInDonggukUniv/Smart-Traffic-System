<%@ page import="dao.*"%>
<%@ page import="daoimpl.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");
	
		
	MemberDAO mDao = new MemberDAOImpl();

	mDao.delete(name);
%>