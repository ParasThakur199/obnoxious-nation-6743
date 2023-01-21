package com.useCase;

import com.dao.dao;
import com.dao.daoImpl;

public class AdminAddNewCourse {
	public AdminAddNewCourse() {
		dao daoObj = new daoImpl();
		daoObj.adminAddNewCourse();
	}
}
