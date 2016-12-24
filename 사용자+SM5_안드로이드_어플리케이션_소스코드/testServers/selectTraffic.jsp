<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="dao.*"%>
<%@page import="daoimpl.*"%>
<%@page import="dto.*"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.json.simple.*"%>

<%
	request.setCharacterEncoding("UTF-8");

	String year = request.getParameter("year");
	String month = request.getParameter("month");
	String day = request.getParameter("day");
	String hour = request.getParameter("hour");
	String min = request.getParameter("min");

	List<TrafficDataDTO> items = new ArrayList<TrafficDataDTO>();
	List<AccidentDTO> itemes2 = new ArrayList<AccidentDTO>();
	TrafficDataDAO tDao = new TrafficDataDAOImpl();
	AccidentDAO aDao = new AccidentDAOImpl();
	
	DecisionTreeModel dtm = tDao.decisionTree(year, month, day, hour, min);
	List<TrafficDataDTO> list = tDao.select(dtm);
	List<AccidentDTO> list2 = aDao.select();

	float averageSpeed = tDao.calculateSpeed(list);
	int accidentCount = aDao.countAccident(list2);

	JSONArray jArray = new JSONArray();

	JSONObject tempJson = new JSONObject();

	tempJson.put("speed", averageSpeed);
	tempJson.put("count", accidentCount);

	jArray.add(tempJson);

	out.println(jArray.toJSONString());
%>
