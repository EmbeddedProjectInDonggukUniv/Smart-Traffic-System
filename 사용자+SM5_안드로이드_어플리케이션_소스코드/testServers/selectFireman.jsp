<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="dao.*"%>
<%@page import="daoimpl.*"%>
<%@page import="dto.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.json.simple.*"%>

<%	
	request.setCharacterEncoding("UTF-8");
	FiremanDAO fDao = new FiremanDAOImpl();
	FiremanDTO fDto = fDao.select();
	
	JSONArray jArray = new JSONArray();
	
	
	JSONObject tempJson = new JSONObject();
	
	tempJson.put("accident_status",fDto.getAccidentState());
	
	jArray.add( tempJson);

	out.println(jArray.toJSONString());
%>
	