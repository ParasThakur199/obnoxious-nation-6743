package com.useCase;

import com.dao.dao;
import com.dao.daoImpl;

public class AdminAllocateStudentsInaBatchUnderaCourse {
	
	public AdminAllocateStudentsInaBatchUnderaCourse() {
		dao daoObj = new daoImpl();
		daoObj.adminAllocateStudentsInaBatchUnderaCourse();
	}
}
