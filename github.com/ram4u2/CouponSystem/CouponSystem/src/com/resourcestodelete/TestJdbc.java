package com.resourcestodelete;

import java.sql.*;


public class TestJdbc {
	public static void main(String[] args) 
	{
		// 1. load the jdbc driver
		// you must add the driver's jar into the CLASSPATH
		try {
			//String driverName = loadFromFIle();
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("----------- DRIVER LOADED -----------------");
		
	
		// 2. get a connection
		String url = "jdbc:mysql://localhost:3306/world";
		
		// ARM - Connection will be automatically closed
		try (Connection con = 
				DriverManager.getConnection(url, "root", "password"))
		{
			
			
		System.out.println("------------ Connection established ----------");
		
		
		// 3. create statement
		Statement stat = con.createStatement();
		
		
		
		// 4. create query ((SELECT - query) or (INSERT, UPDATE, DELETE - update)
		
//		String sql = "DELETE FROM world.city WHERE ID=3";
//		int result = stat.executeUpdate(sql);
//		System.out.println(result);
		
		String sql = "SELECT * FROM world.city";
		
		// 5. execute the command on the statement
		ResultSet rs = stat.executeQuery(sql);
		while (rs.next())
		{
			System.out.println(rs.getInt("ID"));
			System.out.println(rs.getString(2));
			System.out.println(rs.getString(4));
			System.out.println(rs.getInt("Population"));
			System.out.println("*******************************");
		}
		
		
		//con.close(); // USE ARM
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
