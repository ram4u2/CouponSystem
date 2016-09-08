package com.coupons.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coupons.beans.Coupon;
import com.coupons.beans.CouponType;
import com.coupons.dao.CouponDAO;
import com.coupons.exceptions.DaoException;
import com.coupons.pool.Pool;

public class CouponDBDao implements CouponDAO{
	
	@Override
	public void createCoupon(Coupon coupon) throws DaoException {
		// get connection from pool
		Connection con = Pool.getConnection();
		try {
			
		
			String sql = 
					"INSERT INTO coupon VALUES (coupon_id,title,start_date,end_date,amount,type,message,price,image)VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement stat = con.prepareStatement(sql);
			
			stat.setLong(1, coupon.getId());
			stat.setString(2, coupon.getTitle());
			stat.setDate(3,(java.sql.Date)coupon.getStartDate());
			stat.setDate(4,(java.sql.Date)coupon.getEndDate());
			stat.setInt(5,coupon.getAmount());
			stat.setString(6,coupon.getType().toString());
			stat.setString(7,coupon.getMessage());
			stat.setDouble(8,coupon.getPrice());
			stat.setString(9,coupon.getImage());
			
			stat.executeUpdate();
		} catch (SQLException e) {
			// Translation exception
			throw new DaoException("Coupon was not created ", e);
		}
	
	}
		@Override
		public void removeCoupon(Coupon coupon) throws DaoException{
			
			Connection con = Pool.getConnection();
			String sql ="DELETE FROM coupon"
					+ " WHERE title=?";
			PreparedStatement stat;
			
			try {
			stat=con.prepareStatement(sql);
			stat.setString(1, coupon.getTitle());
			stat.executeQuery();
	        } catch (SQLException e) {
				throw new DaoException("Coupon was not removed "+e);
			}
		}
		
	@Override
		public void updateCoupon(Coupon coupon) throws DaoException{
			Connection con = Pool.getConnection();
			String sql ="UPDATE coupon "
					+ "SET end_date=?,amount=?,type=?,message=?,price=?,image=? "
					+ "WHERE title=? ";
			PreparedStatement stat;
			try {
				stat=con.prepareStatement(sql);
			
			stat.setDate(1,(java.sql.Date) coupon.getEndDate());
			stat.setInt(2, coupon.getAmount());
			stat.setString(3, coupon.getType().toString());
			stat.setString(4, coupon.getMessage());
			stat.setDouble(5, coupon.getPrice());
			stat.setString(6, coupon.getImage());
			
			stat.executeUpdate();
			} catch (SQLException e) {
				throw new DaoException("Update was not successful"+e);
			}		
			
		}
	
	
	public Coupon getCoupon(long id)throws DaoException{
		Coupon coupon =null;
		String title = null;
		Date start_date = null;
		Date end_date = null;
		Integer amount=null;
		CouponType type=null;
		Double price =null;
		String message=null;
		String image=null;
		
		Connection con = Pool.getConnection();//setting up connections from class pool
		String sql = "SELECT * FROM coupon WHERE coupon_id=?";
		PreparedStatement stat;
		try {
			stat = con.prepareStatement(sql);
		
		stat.setLong(1, id);
		ResultSet rs = stat.executeQuery();
		rs.next();
		title=rs.getString(1);
		start_date=rs.getDate("Start Date");
		end_date=rs.getDate("End Date");
		amount=rs.getInt("Ammount");
		type=CouponType.valueOf(rs.getString("Type"));
		price=rs.getDouble("Price");
		message=rs.getString("Message");
		image=rs.getString("Image");
		coupon = new Coupon(id, title, start_date, end_date, amount, type, message, price, image);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DaoException("failed to retrieve from the database"+e);
		}
		// obj company gets its values 
		
		return coupon;
		
	}


	// A function that return all coupons
		@SuppressWarnings("null")
		public List<Coupon> getAllCoupons()throws DaoException {
			
					// get connection from pool
					Connection con = Pool.getConnection();
					
					Coupon coupon = new Coupon();
					List<Coupon> coupons = new ArrayList<>();
					
					try {

					
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
	
	
	public List<Coupon> getCouponByType (CouponType type) throws DaoException {
		List<Coupon> couponsList= new ArrayList<>(); 
		Connection con = Pool.getConnection();
		String sql="SELECT * FROM coupon WHERE coupon.type =?";
		PreparedStatement stat;
		try {
			stat = con.prepareStatement(sql);
			ResultSet rs=stat.executeQuery();
			
			while(rs.next()){
				 Coupon coupon = new Coupon();
				 
				rs.getLong("coupon_id");
				rs.getString("title");
				rs.getDate("start_date");
				rs.getDate("end_date");
				CouponType.valueOf(rs.getString("type"));
				rs.getInt("amount");
				rs.getString("message");
				rs.getDouble("price");
				rs.getString("image");
				 couponsList.add(coupon);
			}
		
			
			} catch (SQLException e) {	
		    	 DaoException.showErrorMessage(e);
			}
			
			return couponsList ;
			
		
	}
	
	public List<Coupon> getCouponByPrice(long price, long id) throws DaoException {
		
		List<Coupon> couponsList= new ArrayList<>(); 
		Connection con = Pool.getConnection();
		String sql="SELECT coupon.*,customer_cupon.id FROM coupon  "
				+ "INNER JOIN customer_cupon ON coupon.id=customer_cupon.coupon_id "
				+ "WHERE customer_cupon.id=? AND coupon.price =?";
		PreparedStatement stat;
		try {
			stat = con.prepareStatement(sql);
			stat.setLong(1, id);
			stat.setLong(2, price);
			ResultSet rs=stat.executeQuery();
			while(rs.next()){
				 Coupon coupon = new Coupon();
				rs.getLong("coupon_id");
				rs.getString("title");
				rs.getDate("start_date");
				rs.getDate("end_date");
				CouponType.valueOf(rs.getString("type"));
				rs.getInt("amount");
				rs.getString("message");
				rs.getDouble("price");
				rs.getString("image");
				couponsList.add(coupon);
			}
		
			
			} catch (SQLException e) {	
		    	 DaoException.showErrorMessage(e);
			}
			
			return couponsList ;
			
	}
	@Override
	public Coupon getCoupon(long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Coupon> getCouponByType(String CouponType) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
		

		
	
		
		
		
//		finally{
//			try {
//				con.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}


}
