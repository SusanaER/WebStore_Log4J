package operaciones;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.CreditCards;
import clases.TypeCc;

public class ManageCreditCard {
	static final Logger logger = Logger.getLogger(Factory.class);
	/* Method to create an credit_card in the database */
	public Integer addCreditCard(SessionFactory factory,Integer Number,String Cardholder,TypeCc Type_CC_ID) {
		Session session = factory.openSession();
		logger.info("Open sessionFactory: (" + session + ") in ManageCreditCard");
		Transaction tx = null;
		Integer Credit_Card_ID = null;
		
		try {
			logger.info("adding a Credit Card - Number: (" + Number + ") - Cardholder: (" + Cardholder + ") - Type Credit Card: (" + Type_CC_ID + ").");
			tx = session.beginTransaction();
			CreditCards creditcard = new CreditCards(Type_CC_ID, Number, Cardholder);
			Credit_Card_ID = (Integer) session.save(creditcard);
			tx.commit();
			logger.info("Added data: (" + Credit_Card_ID + ") from addCreditCard, finished");
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
			logger.error("Unsuccessful transaction, due to null - " + e);
		}finally {
			logger.info("Closing session: " + session);
			session.close();
		}
		return Credit_Card_ID;
	}
	
	/* Method to  READ data the credit_card */
	public void listCreditCards(SessionFactory factory, Integer nCreditCard) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			logger.info("showing Credit Card data - id: " + nCreditCard + " - Query: FROM CreditCards c WHERE c.creditCardId = " + nCreditCard);
	         tx = session.beginTransaction();
	         System.out.println("--- Data of the credit card created ---");
	         List creditcard = session.createQuery("FROM CreditCards c WHERE c.creditCardId = " + nCreditCard).list(); 
	         for (Iterator iterator = creditcard.iterator(); iterator.hasNext();){
	        	CreditCards creditcards = (CreditCards) iterator.next();
	            System.out.print("Number: " + creditcards.getNumber()); 
	            System.out.println("  Cardholder: " + creditcards.getCardholder());
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
