package operaciones;

import java.util.List; 
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import clases.Clients;

import java.util.List;

public class ManageClients {
	static final Logger logger = Logger.getLogger(Factory.class);
	/* Method to create an client in the database */
	public Integer addClient(SessionFactory factory, String Name,String Last_Name,String Username,String Password,String Email) {
		Session session = factory.openSession();
		logger.info("Open sessionFactory: (" + session + ") in ManageClients");
		Transaction tx = null;
		Integer clientID = null;
		
		try {
			logger.info("adding a client - Name: (" + Name + ") - Last Name: (" + Last_Name + ") - Username: (" + Username + 
					") - Password: (" + Password + ") - Email: (" + Email + ").");
			tx = session.beginTransaction();
			Clients clients = new Clients(Name, Last_Name, Username, Password, Email);
			clientID = (Integer) session.save(clients);
			tx.commit();
			logger.info("Added data: (" + clientID + ") from addClient, finished");
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
			logger.error("Unsuccessful transaction, due to null - " + e);
		}finally {
			logger.info("Closing session: " + session);
			session.close();
		}
		return clientID;
	}

	/* Method to  READ data clients */
	public void listClients(SessionFactory factory, Integer nclient) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			logger.info("showing Client data - id: " + nclient + " - Query: FROM Clients c WHERE c.clientId = " + nclient);
	         tx = session.beginTransaction();
	         System.out.println("--- Data of the client created ---");
	         List clients = session.createQuery("FROM Clients c WHERE c.clientId = " + nclient).list(); 
	         for (Iterator iterator = clients.iterator(); iterator.hasNext();){
	            Clients client = (Clients) iterator.next(); 
	            System.out.print("First Name: " + client.getName()); 
	            System.out.print("  Last Name: " + client.getLastName()); 
	            System.out.println("  Password: " + client.getPassword());
	            System.out.println("  Email: " + client.getPassword());
	            System.out.println("  Username: " + client.getPassword());
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
