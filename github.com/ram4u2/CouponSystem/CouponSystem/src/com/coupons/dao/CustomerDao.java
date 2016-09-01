package com.coupons.dao;

import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.beans.Customer;
import com.coupons.exceptions.DaoException;

public interface CustomerDao {
	
	public void createCustomer(Customer c) throws DaoException;
	public void removeCustomer(Customer c) throws DaoException;
	public void updateCustomer(Customer c) throws DaoException;
	public Customer getCustomer(long id) throws DaoException;
	
	public List<Company> getAllCustomer() throws DaoException;
	public List<Coupon> getAllCoupons() throws DaoException;
	public boolean login (String custName, String password);
	
	
	
	// Maybe more functions
}
