package com.coupons.dao;

import java.util.List;

import com.coupons.beans.Coupon;
import com.coupons.exceptions.DaoException;

public interface CouponDAO {

	public void createCoupon(Coupon c) throws DaoException;
	public void removeCoupon(Coupon c) throws DaoException;
	public void updateCoupon(Coupon c) throws DaoException;
	
	public Coupon getCoupon(long id) throws DaoException;
	public List<Coupon> getAllCoupon() throws DaoException;
	public List<Coupon> getCouponByType(String CouponType) throws DaoException;
}
