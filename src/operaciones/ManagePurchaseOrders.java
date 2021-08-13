package operaciones;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.Clients;
import clases.DeliveryPackeges;
import clases.PurchaseOrders;

public class ManagePurchaseOrders {
	static final Logger logger = Logger.getLogger(Factory.class);
	/* Method to create an manage order in the database */
	public Integer addPurchaseOrder(SessionFactory factory, Clients clients, DeliveryPackeges deliveryPackeges) {
		Session session = factory.openSession();
		logger.info("Open sessionFactory: (" + session + ") in ManageDeliveryAddress");
		Transaction tx = null;
		Integer purchaseOrderId = null;
		Date date = new Date();
		
		try {
			logger.info("adding a Purchase Order - Client: (" + clients + ") - Delivery Packeges: (" + deliveryPackeges + ").");
			tx = session.beginTransaction();
			PurchaseOrders PO = new PurchaseOrders(clients, deliveryPackeges, date);
			purchaseOrderId = (Integer) session.save(PO);
			tx.commit();
			logger.info("Added data: (" + purchaseOrderId + ") from addPurchaseOrder, finished");
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
			logger.error("Unsuccessful transaction, due to null - " + e);
		}finally {
			logger.info("Closing session: " + session);
			session.close();
		}
		return purchaseOrderId;
	}
	
	/* Method to  READ data the manage order */
	public void listPurchaseOrder(SessionFactory factory, Integer nid) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			logger.info("showing Purchase Order data - id: " + nid + " - Query: FROM PurchaseOrders c WHERE c.purchaseOrderId = " + nid);
	         tx = session.beginTransaction();
	         System.out.println("--- Data of the purchase orders ---");
	         List order = session.createQuery("FROM PurchaseOrders c WHERE c.purchaseOrderId = " + nid).list(); 
	         for (Iterator iterator = order.iterator(); iterator.hasNext();){
	        	 PurchaseOrders pOrders = (PurchaseOrders) iterator.next();
	            System.out.print("Clients: " + pOrders.getClients().getName()); 
	            System.out.println("  Date: " + pOrders.getDate());
	            System.out.println("  Delivery Packeges: " + pOrders.getDeliveryPackeges().getPackegeNumber());
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
