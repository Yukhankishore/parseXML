package com.yuk;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class PerformParse {

	public void performParse(String file,List<User> contactList,List<Salary> salaryList,List<FullData> dataList) {
		
		File xmlFile = new File(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder =  dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();	
			NodeList nodelist = doc.getElementsByTagName("person");   
			
			for(int i=0;i<nodelist.getLength();i++) {
				if(doc.getDocumentElement().getNodeName() == "geodata") {
					contactList.add(getAddress(nodelist.item(i)));
				}
				else if(doc.getDocumentElement().getNodeName() == "salarydata") {
					salaryList.add(getSalary(nodelist.item(i)));
				}
				else if(doc.getDocumentElement().getNodeName() == "persondata"){
					dataList.add(getPerson(nodelist.item(i)));
				}
				
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	private static FullData getPerson(Node node) {
		// TODO Auto-generated method stub
	FullData data = new FullData();	
	if (node.getNodeType() == Node.ELEMENT_NODE) {
		
        Element element = (Element) node; 
        System.out.println("name of the  "+element.getTagName());
        data.setName(element.getAttribute("name"));
        data.setAddress(getTagValue("address", element));
        data.setPhoneNumber(getTagValue("phonenumber", element)); 
        data.setSalary(getTagValue("salary", element));
        data.setPension(getTagValue("pension", element));
    }
   
	return data;
	}
private static User getAddress(Node node) {
//		 TODO Auto-generated method stub
	User user = new User();	
	if (node.getNodeType() == Node.ELEMENT_NODE) {
		
        Element element = (Element) node; 
        System.out.println("name of the  "+element.getTagName());
        user.setName(element.getAttribute("name"));
        user.setAddress(getTagValue("address", element));
        user.setPhoneNumber(getTagValue("phonenumber", element)); 
    }
   
	return user;
		
	}
private static Salary getSalary(Node node) {
	// TODO Auto-generated method stub
	Salary salary = new Salary();	
	if (node.getNodeType() == Node.ELEMENT_NODE) {
		
        Element element = (Element) node; 
        
        salary.setName(element.getAttribute("name"));
        salary.setPension(getTagValue("pension", element));
        salary.setSalary(getTagValue("salary", element)); 
    }
   
	return salary;
	
}
 private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
