package operaciones;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.Billing;
import clases.PurchaseOrders;

public class ManageBilling {
	static final Logger logger = Logger.getLogger(Factory.class);
	/* Method to create an Billing in the database */
	public Integer addBilling(SessionFactory factory, PurchaseOrders purchaseOrders, double amount) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer billingId = null;
		Date date = new Date();
		logger.info("Open sessionFactory: (" + session + ") in ManageBilling");
		
		try {
			logger.info("adding a billing - PurchaseOrder: (" + purchaseOrders + ") - Amount: " + amount);
			tx = session.beginTransaction();
			Billing B = new Billing(purchaseOrders, amount, date);
			billingId = (Integer) session.save(B);
			tx.commit();
			logger.info("Added data: " + billingId + " from addBilling, finished");
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
			logger.error("Unsuccessful transaction, due to null - " + e);
		}finally {
			logger.info("Closing session: " + session);
			session.close();
		}
		return billingId;
	}

	/* Method to  READ data the Billing */
	public void listBilling(SessionFactory factory, Integer id) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			logger.info("showing Billing data - id: " + id + " - Query: FROM Billing c WHERE c.billingId = " + id);
	         tx = session.beginTransaction();
	         System.out.println("--- Data of the Billing created ---");
	         List billing = session.createQuery("FROM Billing c WHERE c.billingId = " + id).list(); 
	         for (Iterator iterator = billing.iterator(); iterator.hasNext();){
	            Billing b = (Billing) iterator.next(); 
	            System.out.print("Amount: " + b.getAmount()); 
	            System.out.print("  Date: " + b.getDate()); 
	            System.out.println("  Purchase Order: " + b.getPurchaseOrders().getPurchaseOrderId());
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
