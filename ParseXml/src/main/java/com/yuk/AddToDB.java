package com.yuk;

import java.io.PrintWriter;
import java.sql.*;
import java.util.List;
public class AddToDB {
	public void addToDB(List<FullData> dataList){
		PersonDao dao = new PersonDao();
		dao.connection();
		dao.addPerson(dataList);
//		Person personOne = dao.getPerson("$3000");
//		System.out.println(personOne.name+" "+personOne.address+" "+personOne.pension+" "+personOne.salary+" "+personOne.phonenumber);
	}
}
class PersonDao {
	Connection con;
	public void  connection ()  {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/person","root","yukkishore111");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Person getPerson(String selectValue,String inputValue) {
		Person person = new Person(); 
		try {
			
			System.out.println(selectValue+" "+inputValue);
			String query = "select * from persondata where "+selectValue+" = '"+ inputValue +"';";
			Statement st = con.createStatement();
			System.out.println(query);
//			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = st.executeQuery(query);
			
			if(rs.next()){	
//				person.salary = string;
				person.name = rs.getString(1);
				person.address = rs.getString(5);
				person.pension  = rs.getString(4);
				person.phonenumber  = rs.getString(2);
//				System.out.println(person.name+"  "+person.address+" "+person.salary+"hurray");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;
	}
	public void addPerson(List<FullData> dataList){
		String query = "insert into persondata values (?,?,?,?,?)";
		for(FullData data: dataList) {
			try {
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, data.getName());
				ps.setString(2, data.getPhoneNumber());
				ps.setString(3, data.getSalary());
				ps.setString(4, data.getPension());
				ps.setString(5, data.getAddress());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
class Person{
	public String name,address,phonenumber,salary,pension;
	
}
