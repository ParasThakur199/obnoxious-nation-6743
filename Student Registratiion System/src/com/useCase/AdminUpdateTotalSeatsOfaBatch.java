package com.useCase;

import com.dao.dao;
import com.dao.daoImpl;

public class AdminUpdateTotalSeatsOfaBatch {
	
	public AdminUpdateTotalSeatsOfaBatch() {
		dao daoObj = new daoImpl();
		daoObj.adminUpdateTotalSeatsOfaBatch();
	}
}
