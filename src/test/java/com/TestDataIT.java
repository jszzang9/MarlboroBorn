package com;

import com.integration_test.protocol.ProtocolIT;

public class TestDataIT extends ProtocolIT{
//	private TestDataManager dataManager;
//	
//	@Before
//	public void before() {
//		dataManager = new TestDataManager();
//		HibernateUtil.beginTransaction();
//	}
//	
//	@After
//	public void after() {
//		HibernateUtil.closeSession();
//	}
//	
//	@Test 
//	public void insertTestData() {
//		try {
//			TestData testData = new TestData(makeRandomString(), makeRandomSmallLetters(), makeRandomString());
//			dataManager.putTestData(testData);
//			HibernateUtil.commit();
//		}
//		catch (Throwable ex) {
//			HibernateUtil.rollBack();
//			fail(ex.getMessage());
//		}
//	}
//	
//	@Test 
//	public void putTestData() {
//		try {
//			dataManager.putTestData(makeRandomString(), makeRandomSmallLetters(), makeRandomString());
//			
//			HibernateUtil.commit();
//		}
//		catch (Throwable ex) {
//			HibernateUtil.rollBack();  
//            fail(ex.getMessage());  
//		}
//	}
}
