package com.marlboro.core.model.manager;

import java.sql.SQLException;

import org.apache.log4j.Level;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.marlboro.core.model.HibernateUtil;
import com.marlboro.core.model.dto.TestData;
import com.marlboro.util.Generator;
import com.marlboro.util.QueuedLogger.QueuedLogger;
import com.marlboro.util.QueuedLogger.QueuedTransactionLogs;

public class TestDataManager {
	public TestData putTestData(String marlboroId, String marlboroPw, String marlboroNumber) {
		Session session = HibernateUtil.getCurrentSession();
		
		String primaryKey = Generator.makeSynthesisKey(marlboroId, marlboroNumber);
		
		TestData data = new TestData();
		data.setPrimaryKey(primaryKey);
		data.setMarlboroId(marlboroId);
		data.setMarlboroPw(marlboroPw);
		data.setMarlboroNumber(marlboroNumber);
		session.save(data);
		return data;
	}
	
	public TestData putTestData(TestData testData) {
		QueuedTransactionLogs logs =  new QueuedTransactionLogs();
		logs.add(Level.INFO, "========^^============");
		logs.add(Level.INFO, testData.getMarlboroId());
		logs.add(Level.INFO, "=========^^===========");
		QueuedLogger.push(logs);
		
		Session session = HibernateUtil.getCurrentSession();
		
		String primaryKey = Generator.makeSynthesisKey(testData.getMarlboroId(), testData.getMarlboroNumber());
		
		TestData data = new TestData();
		data.setPrimaryKey(primaryKey);
		data.setMarlboroId(testData.getMarlboroId());
		data.setMarlboroPw(testData.getMarlboroPw());
		data.setMarlboroNumber(testData.getMarlboroNumber());
		session.save(data);
		return data;
	}
	
}
