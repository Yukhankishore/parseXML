package com.yuk;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class BuildXml {

	public  void xmlBuild(List<User> contactList,List<Salary> salaryList){
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder;
    try {
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        // add elements to Document
        Element rootElement = doc.createElement("persondata");
        // append root element to document
        doc.appendChild(rootElement);
        
        for(User user : contactList) {
        	System.out.println("name"+user.getName()+"address"+user.getAddress()+"phone"+user.getPhoneNumber());
		}
        for(Salary salary : salaryList) {
    		System.out.println("name"+salary.getName()+"salary"+salary.getSalary()+"pension"+salary.getPension());
    	}
        
        for(User user : contactList) {
	        for(Salary salary : salaryList) {
	    		if(user.getName().equals(salary.getName())) {
//	    			System.out.println(" name "+salary.getName()+" salary "+salary.getSalary()+" pension "+salary.getPension()+" address "+user.getAddress()+" phone "+user.getPhoneNumber());
	    			rootElement.appendChild(createUserElement(doc, salary.getName(), user.getAddress(), user.getPhoneNumber(), salary.getSalary(), salary.getPension()));
	    		}
	    	}
        }
        
        
        // for output to file, console
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // for pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);

        // write to console or file
        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(new File("persondata.xml"));

        // write data
        transformer.transform(source, console);
        transformer.transform(source, file);
        
//        return file;

    } catch (Exception e) {
        e.printStackTrace();
    }
//	return null;
}
	
private static Node createUserElement(Document doc, String name, String address, String phonenumber, String salary,String pension) {
    Element person = doc.createElement("person");

    // set id attribute
    person.setAttribute("name", name);

    // create firstName element
    person.appendChild(createUserElements(doc, person, "address", address));

    // create lastName element
    person.appendChild(createUserElements(doc, person, "phonenumber", phonenumber));

    // create age element
    person.appendChild(createUserElements(doc, person, "salary", salary));

    // create gender element
    person.appendChild(createUserElements(doc, person, "pension", pension));

    return person;
}

// utility method to create text node
private static Node createUserElements(Document doc, Element element, String name, String value) {
    Element node = doc.createElement(name);
    node.appendChild(doc.createTextNode(value));
    return node;
}
}
