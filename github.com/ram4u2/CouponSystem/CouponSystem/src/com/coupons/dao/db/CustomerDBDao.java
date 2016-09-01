package com.coupons.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.CouponType;
import com.coupons.beans.Customer;
import com.coupons.beans.Coupon;
import com.coupons.dao.CustomerDao;
import com.coupons.exceptions.DaoException;

public class CustomerDBDao implements CustomerDao
{
	
	
	
	@Override
	public void createCustomer(Customer c) throws DaoException {
		// get connection from pool
		Connection con = null;
		try {
			con = getConnection();
		
			String sql = 
					"INSERT INTO Customer VALUES (?,?,?)";
			PreparedStatement stat = con.prepareStatement(sql);
			
			stat.setLong(1, c.getId());
			stat.setString(2, c.getName());
			stat.setString(3, c.getPassword());
			
			stat.executeUpdate();
		} catch (SQLException e) {
			// Translation exception
			throw new DaoException("Tere was an Error in Method createCustomer", e);
		}
		finally{
			// release connection to pool
			try {con.close();} catch (SQLException e) {}
		}
		

	}

	@Override
	public void removeCustomer(Customer c) throws DaoException {
		// get connection from pool
				Connection con = null;
				try {
					con = getConnection();
				  
					String sql = 
							"DELETE FROM Customer WHERE ID = (?)";
					PreparedStatement stat = con.prepareStatement(sql);
					
					stat.setLong(1, c.getId());
					
					stat.executeUpdate();
				} catch (SQLException e) {
					// Translation exception
					throw new DaoException("Tere was an Error in Method removeCustomer", e);
				}
				finally{
					// release connection to pool
					try {con.close();} catch (SQLException e) {}
				}
				
		
	}

		
	@Override
	public void updateCustomer(Customer c) throws DaoException {
		// get connection from pool
		Connection con = null;
		try {
			con = getConnection();
		
			String sql = 
					"UPDATE Customer SET CUST_NAME=(?) WHERE ID = (?)";
			PreparedStatement stat = con.prepareStatement(sql);
			
			stat.setString(1, c.getName());
			stat.setLong(2, c.getId());
			
			stat.executeUpdate();
		} catch (SQLException e) {
			// Translation exception
			throw new DaoException("Tere was an Error in Method updateCustomer", e);
		}
		finally{
			// release connection to pool
			try {con.close();} catch (SQLException e) {}
		}
		

		
	}

	@SuppressWarnings("null")
	@Override
	public Customer getCustomer(long id) throws DaoException {
				// get connection from pool
				Connection con = null;
				Customer custumer = new Customer();
				
				try {
					con = getConnection();
					ResultSet rs = null;
					
				
					String sql = 
							"SELECT * FROM Customer WERE ID = (?)";
					PreparedStatement stat = con.prepareStatement(sql);
					stat.setLong(1, id);
					
					stat.executeUpdate();
					while( rs.next() ) {
						
						custumer.setName(rs.getString("CUST_NAME"));
						custumer.setId(rs.getLong("CUST_ID"));
					}
				} catch (SQLException e) {
					// Translation exception
					throw new DaoException("Tere was an Error in Method getCustomer", e);
				}
				finally{
					// release connection to pool
					try {con.close();} catch (SQLException e) {}
				}
				return custumer;
				

	}

	
	@SuppressWarnings("null")
	@Override
	public List<Company> getAllCustomer() throws DaoException {
		// get connection from pool
		Connection con = null;
		Company company = new Company();
		List<Company> companies = null;
		
		try {
			con = getConnection();
			ResultSet rs = null;
			
		
			String sql = 
					"SELECT * FROM company";
			PreparedStatement stat = con.prepareStatement(sql);
			
			stat.executeUpdate();
			while( rs.next() ) {
				long n = Long.parseLong(rs.getString("ID"));
				company.setId(n);
				company.setName(rs.getString("COMP_NAME"));
				company.setEmail(rs.getString("EMAIL"));
				companies.add(company);
			}
		} catch (SQLException e) {
			// Translation exception
			throw new DaoException("Tere was an Error in Method getAllCustomers", e);
		}
		finally{
			// release connection to pool
			try {con.close();} catch (SQLException e) {}
		}
		return companies;
		


	}
	
	
	public List<Coupons> getCoupons()throws DaoException {
		
		// get connection from pool
				Connection con = null;
				Coupon coupon = new Coupon();
				List<Coupon> coupons = new ArrayList<>();
				
				try {
					con = getConnection();
					ResultSet rs = null;
					
				
					String sql = 
							"SELECT * FROM coupon";
					PreparedStatement stat = con.prepareStatement(sql);
					
					stat.executeUpdate();
					while( rs.next() ) {
						long n = Long.parseLong(rs.getString("ID"));
						coupon.setId(n);
						coupon.setTitle(rs.getString("TITLE"));
						coupon.setStartDate(rs.getDate("START_DATE"));
						coupon.setEndDate(rs.getDate("END_DATE"));
						coupon.setAmount(rs.getInt("AMOUNT"));
						CouponType.valueOf(rs.getString("type"));
						coupon.setMessage(rs.getString("MESSAGE"));
						coupon.setPrice(rs.getInt("PRICE"));
						coupon.setImage(rs.getString("IMAGE"));
						
						coupons.add(coupon);
					}
				} catch (SQLException e) {
					// Translation exception
					throw new DaoException("Tere was an Error in Method getCoupons", e);
				}
				finally{
					// release connection to pool
					try {con.close();} catch (SQLException e) {}
				}
				return coupons;
		
	}
	
	// A function that creates connection
	// TODO: Use the pool instead later on
	private Connection getConnection() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/copunsystem";
				
		Connection con = 
				DriverManager.getConnection(url, 
						"user", "password");
		return con;
				
	}
	
	
	
	
}
