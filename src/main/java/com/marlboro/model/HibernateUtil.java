package com.marlboro.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	public static final SessionFactory sessionFactory;
	
	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		catch (Throwable ex) {
			System.out.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public static Transaction beginTransaction() {
		return getCurrentSession().beginTransaction();
	}
	
	public static void  commit() {
		getCurrentSession().getTransaction().commit();
	}
	
	public static void closeSession() {
		getCurrentSession().close();
	}
	
	public static void rollBack() {
		if (getCurrentSession().isOpen()) {
			Transaction tx = getCurrentSession().getTransaction();
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}
}
