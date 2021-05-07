package com.yuk;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Base extends HttpServlet {
//	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException {
		
		String selectValue = req.getParameter("selectValue");
		String inputValue = req.getParameter("inputValue");
		
		if(selectValue=="" || inputValue=="") {
			res.sendRedirect("index.html");
//			res.sendError(0, "invalid");
		}
		else {
		
		String[] files = {"C:\\Users\\yukha\\eclipse-workspace\\ParseXml\\geodata.xml","C:\\Users\\yukha\\eclipse-workspace\\ParseXml\\salarydata.xml"};
		List<User> contactList = new ArrayList<User>();
		List<Salary> salaryList = new ArrayList<Salary>();
		List<FullData> dataList = new ArrayList<FullData>();
		PerformParse parser =  new PerformParse();
		for(String file : files) {
			parser.performParse(file, contactList, salaryList, dataList);
		}
		BuildXml xmlBuilder = new BuildXml();
		xmlBuilder.xmlBuild(contactList,salaryList);
		
		parser.performParse("persondata.xml", contactList, salaryList, dataList);
		for(FullData data:dataList) {
			System.out.println(data.getName()+""+data.getAddress()+""+data.getPension()+""+data.getPhoneNumber()+""+data.getSalary());
		}
		AddToDB db = new AddToDB();
		db.addToDB(dataList);
		
		PrintWriter out = res.getWriter();
		PersonDao deo = new PersonDao();
		
		deo.connection();
		Person personOne = deo.getPerson(selectValue,inputValue);
		if(personOne.name==null ) {
			res.sendRedirect("index.html");
		}
		else {
			HttpSession session =  req.getSession();
			session.setAttribute("name", personOne.name);
			session.setAttribute("phonenumber", personOne.phonenumber);
			session.setAttribute("salary", personOne.salary);
			session.setAttribute("pension", personOne.pension);
			session.setAttribute("address", personOne.address);
			res.sendRedirect("OutputPage.jsp");
		}
		}
		
	}
}
