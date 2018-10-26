package com.mkyong.common;

import java.io.Serializable;

import org.hibernate.Session;
import com.mkyong.persistence.HibernateUtil;

public class App {
	public static void main(String[] args) {
		
		System.out.println("Maven + Hibernate + MySQL");
		
		 //saveStock();
		
		 //persistStock();
		
		//getStock();
		
		//loadStock();
		
		//update();
		
		merge();
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
	

	private static void update() {
		Stock stock = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		
		stock =(Stock)session.get(Stock.class, new Integer(1)); //database hit
		
		if(stock!= null) {
			System.out.println("stock: id : " +stock.getStockId());
			System.out.println("stock: code : " +stock.getStockCode());
			System.out.println("stock:name : " +stock.getStockName());
			
		}
		
		stock.setStockName("akash");
		session.getTransaction().commit(); //here updated automatically happnens.
		
		/*The automatic dirty checking feature of hibernate, calls update statement automatically on the objects that are modified in a transaction.
		Here, after getting Stock instance stock and we are changing the state of Stock i.e  "stock.setStockName("akash");"
        After changing the state, we are committing the transaction. In such case, state will be updated automatically. This is known as dirty checking in Hibernate.*/
		
		
		session.flush();
		session.close();
		
		
		
		/*here we just get one object stock into session cache and closed session, so now object stock in the session cache will be destroyed as session cache will expires 
		 when ever we call session.close() .Now stock object will be in some RAM location, not in the session cache.stock is in detached state */
		
		
		try {
		stock.setStockName("Test");
		session.update(stock);
		}catch (Exception e) {
			e.printStackTrace();
			
		}

		/*here stock is in detached state, and we modified that detached object stock, now if we call update() method then hibernate will throws an Exception,
		 because we can update the object in the session only.*/

		
	}
	
	private static void merge() {
		Stock stock = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		
		stock =(Stock)session.get(Stock.class, new Integer(1)); //database hit
		
		if(stock!= null) {
			System.out.println("stock: id : " +stock.getStockId());
			System.out.println("stock: code : " +stock.getStockCode());
			System.out.println("stock:name : " +stock.getStockName());
			
		}
		
		stock.setStockName("akash");
		session.getTransaction().commit(); //here updated automatically happnens.
		
		/*The automatic dirty checking feature of hibernate, calls update statement automatically on the objects that are modified in a transaction.
		Here, after getting Stock instance stock and we are changing the state of Stock i.e  "stock.setStockName("akash");"
        After changing the state, we are committing the transaction. In such case, state will be updated automatically. This is known as dirty checking in Hibernate.*/
		
		
		session.flush();
		session.close();
		
		
		
		/*here we just get one object stock into session cache and closed session, so now object stock in the session cache will be destroyed as session cache will expires 
		 when ever we call session.close() .Now stock object will be in some RAM location, not in the session cache.stock is in detached state */
		
		
		try {
		stock.setStockName("mergeName");
		session.update(stock);
		}catch (Exception e) {
			e.printStackTrace();
			
		}

		/*here stock is in detached state, and we modified that detached object stock, now if we call update() method then hibernate will throws an Exception,
		 because we can update the object in the session only.*/
		
		//open another session
		Session anotherSession = HibernateUtil.getSessionFactory().openSession();

		anotherSession.beginTransaction();
		
		Stock anotherStock =(Stock)anotherSession.get(Stock.class, new Integer(1)); //database hit
		
		anotherStock.setStockCode("mergecode");
		
		anotherSession.merge(stock);
		
		anotherSession.getTransaction().commit();
		
		
		anotherSession.flush();
		anotherSession.close();
		
	/*	here stock is in detached state, and we modified that detached object stock, now if we call update() method then hibernate will throws an error,
		because we can update the object in the session only.
		
		So we opened another session [anotherSession],  and again loaded the same stock object from the database, but with name anotherStock ,
		so in this anotherSession, we called anotherSession.merge(stock); now into anotherStock object stock changes will be merged and saved into the database.
		Here actually update and merge methods will come into picture when ever we loaded the same object again and again into the database, like above.*/

		
	}
}
