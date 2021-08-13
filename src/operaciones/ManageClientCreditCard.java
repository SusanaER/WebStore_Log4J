package operaciones;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.ClientCreditCard;
import clases.Clients;
import clases.CreditCards;

public class ManageClientCreditCard {
	static final Logger logger = Logger.getLogger(Factory.class);
	/* Method to create an relationship between  client and credit card*/
	public Integer addClientCreditCard(SessionFactory factory, Clients client, CreditCards creditcard) {
		Session session = factory.openSession();
		logger.info("Open sessionFactory (" + session + ") in ManageArticlePurchase");
		Transaction tx = null;
		Integer clientCreditCardId = null;
		
		try {
			logger.info("Adding relationship between Client: (" + client + ") and CreditCard: (" + creditcard + ")");
			tx = session.beginTransaction();
			ClientCreditCard CCC = new ClientCreditCard(client, creditcard);
			clientCreditCardId = (Integer) session.save(CCC);
			tx.commit();
			logger.info("Added data: (" + clientCreditCardId + ") from addClientCreditCard, finished");
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
			logger.error("Unsuccessful transaction, due to null - " + e);
		}finally {
			logger.info("Closing session: " + session);
			session.close();
		}
		return clientCreditCardId;
	}
}
