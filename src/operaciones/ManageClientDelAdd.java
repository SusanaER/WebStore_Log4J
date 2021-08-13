package operaciones;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.ClientDelAdd;
import clases.Clients;
import clases.DeliveryAddress;

public class ManageClientDelAdd {
	static final Logger logger = Logger.getLogger(Factory.class);
	/* Method to create an relationship between  client and delivery address*/
	public Integer addClientCreditCard(SessionFactory factory, Clients clients, DeliveryAddress deliveryAddress) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer clientDelAddID = null;
		logger.info("Open sessionFactory: (" + session + ") in ManageClientDelAdd");
		
		try {
			logger.info("Adding relationship between Client: (" + clients + ") and DeliveryAddress: (" + deliveryAddress + ").");
			tx = session.beginTransaction();
			ClientDelAdd CDA = new ClientDelAdd(clients, deliveryAddress);
			clientDelAddID = (Integer) session.save(CDA);
			tx.commit();
			logger.info("Added data: (" + clientDelAddID + ") from addClientCreditCard, finished");
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
			logger.error("Unsuccessful transaction, due to null - " + e);
		}finally {
			logger.info("Closing session: " + session);
			session.close();
		}
		return clientDelAddID;
	}
}
