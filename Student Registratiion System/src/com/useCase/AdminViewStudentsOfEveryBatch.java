package com.useCase;

import com.dao.dao;
import com.dao.daoImpl;

public class AdminViewStudentsOfEveryBatch {
	public AdminViewStudentsOfEveryBatch() {
		dao daoObj = new daoImpl();
		daoObj.adminViewStudentsOfEveryBatch();
	}
}
