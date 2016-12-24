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
	List<TrafficDTO> items = new ArrayList<TrafficDTO>();
	List<AccidentDTO> itemes2 = new ArrayList<AccidentDTO>();
	TrafficDAO tDao =new TrafficDAOImpl();
	AccidentDAO aDao = new AccidentDAOImpl();
	List<TrafficDTO> list = tDao.select();
	List<AccidentDTO> list2 =aDao.select();
		
	float averageSpeed = tDao.calculateSpeed(list);	
	int accidentCount =aDao.countAccident(list2);
	
	JSONArray jArray = new JSONArray();
	
	
	JSONObject tempJson = new JSONObject();
	
	tempJson.put("speed",averageSpeed);
	tempJson.put("count",accidentCount);
	
	jArray.add( tempJson);

	out.println(jArray.toJSONString());
%>
	