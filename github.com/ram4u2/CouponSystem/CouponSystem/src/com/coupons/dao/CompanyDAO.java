package com.coupons.dao;

import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.exceptions.DaoException;

public interface CompanyDAO {

	public void createCompany(Company c) throws DaoException;
	public void removeCompany(Company c) throws DaoException;
	public void updateCompany(Company c) throws DaoException;
	public Company getCompany(long id) throws DaoException;
	
	public List<Company> getAllCompanies() throws DaoException;
	public List<Coupon> getAllCoupons() throws DaoException;
	public boolean login (String custName, String password);
}
