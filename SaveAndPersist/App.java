package com.mkyong.common;

import java.io.Serializable;

import org.hibernate.Session;
import com.mkyong.persistence.HibernateUtil;

public class App {
	public static void main(String[] args) {
		System.out.println("Maven + Hibernate + MySQL");
		
		 saveStock();
		
		 //persistStock();
		
		//getStock();
		
		//loadStock();
		
		System.out.println("done");

	}

	private static void saveStock() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		
		Stock stock = new Stock();
		stock.setStockCode("4719");
		stock.setStockName("vikash");

		Serializable serializable = session.save(stock); //return type is Serializable
		Integer id = (Integer)serializable;
		
		session.getTransaction().commit();
		session.close();
		System.out.println("Id : " + id);
	}
	
	private static void persistStock() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		
		Stock stock = new Stock();
		stock.setStockCode("4717");
		stock.setStockName("guddu");

		session.persist(stock); //return type is void
		
		session.getTransaction().commit();
		session.close();
	}
	
	private static void getStock() {
		
		Stock stock = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		
		stock =(Stock)session.get(Stock.class, new Integer(1)); //database hit
		System.out.println("stock: id : " +stock.getStockId());
		System.out.println("stock: code : " +stock.getStockCode());
		System.out.println("stock:name : " +stock.getStockName());
		
		
	    stock =(Stock)session.get(Stock.class, new Integer(1)); //not database hit read data from cache
		System.out.println("stock: id : " +stock.getStockId());
		System.out.println("stock: code : " +stock.getStockCode());
		System.out.println("stock:name : " +stock.getStockName());
		
		stock =(Stock)session.get(Stock.class, new Integer(8)); //database hit no result
		if(stock!= null) {
			System.out.println("stock: id : " +stock.getStockId());
			System.out.println("stock: code : " +stock.getStockCode());
			System.out.println("stock:name : " +stock.getStockName());
			
		}
		
		
		session.getTransaction().commit();
		session.close();
	}
	
	private static void loadStock() {
		
		Stock stock = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		
	    stock =(Stock)session.load(Stock.class, new Integer(1));
		System.out.println("stock: id : " +stock.getStockId());
		 
		System.out.println("stock: code : " +stock.getStockCode()); 	//Database hit after this line 
		System.out.println("stock:name : " +stock.getStockName());
		
		
	    stock =(Stock)session.load(Stock.class, new Integer(1)); 
		System.out.println("stock: id : " +stock.getStockId());
		System.out.println("stock: code : " +stock.getStockCode());//no Database hit read  data from first level cache i.e session
		System.out.println("stock:name : " +stock.getStockName());
		
		
	    stock =(Stock)session.load(Stock.class, new Integer(5)); 
		System.out.println("stock: id : " +stock.getStockId()); //till create fake object
		System.out.println("stock: code : " +stock.getStockCode());//execute query and gives excepton
		System.out.println("stock:name : " +stock.getStockName());
		
		
		
		session.getTransaction().commit();
		session.close();
		
	}
}
