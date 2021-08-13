package operaciones;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.City;
import clases.Country;
import clases.DeliveryAddress;
import clases.State;

public class ManageDeliveryAddress {
	static final Logger logger = Logger.getLogger(Factory.class);
	/* Method to create an delivery address in the database */
	public Integer addDeliberyAddress(SessionFactory factory, City city, Country country, State state, String street, int number) {
		Session session = factory.openSession();
		logger.info("Open sessionFactory: (" + session + ") in ManageDeliveryAddress");
		Transaction tx = null;
		Integer DA_ID = null;
		
		try {
			logger.info("adding a Delivery Address - City: (" + city + ") - Country: (" + country + ") - State: (" + state 
					+ ") - Street: (" + street + ") - Number: (" + number + ").");
			tx = session.beginTransaction();
			DeliveryAddress DA = new DeliveryAddress(city, country, state, street, number);
			DA_ID = (Integer) session.save(DA);
			tx.commit();
			logger.info("Added data: (" + DA_ID + ")from addDeliberyAddress, finished");
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
			logger.error("Unsuccessful transaction, due to null - " + e);
		}finally {
			logger.info("Closing session: " + session);
			session.close();
		}
		return DA_ID;
	}

	/* Method to  READ data in delivery address */
	public void listDelAdd(SessionFactory factory, Integer nid) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			logger.info("showing Delivery Address data - id: " + nid + " - Query: FROM DeliveryAddress c WHERE c.delAddId = " + nid);
	         tx = session.beginTransaction();
	         System.out.println("--- Data of the delivery address created ---");
	         List deladd = session.createQuery("FROM DeliveryAddress c WHERE c.delAddId = " + nid).list(); 
	         for (Iterator iterator = deladd.iterator(); iterator.hasNext();){
	        	DeliveryAddress  DA = (DeliveryAddress) iterator.next(); 
	            System.out.print("City: " + DA.getCity().getName()); 
	            System.out.print("  Country: " + DA.getCountry().getName()); 
	            System.out.println("  State: " + DA.getState().getName());
	            System.out.println("  Street: " + DA.getStreet());
	            System.out.println("  Number: " + DA.getNumber());
	         }
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace();
	         logger.error("Unsuccessful transaction, due to null - " + e);
	      } finally {
	    	 logger.info("Closing session: " + session);
	         session.close();
	      }
	}
}
