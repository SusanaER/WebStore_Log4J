package operaciones;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import clases.Articles;
import clases.ArticlesPurchase;
import clases.PurchaseOrders;

public class ManageArticlePurchase {
	static final Logger logger = Logger.getLogger(Factory.class);
	/* Method to create an relationship between  Article and Purchase Order*/
	public Integer addArticlePurchase(SessionFactory factory, Articles articles, PurchaseOrders purchaseOrders) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer articlesPurchaseId = null;
		logger.info("Open sessionFactory: (" + session + ") in ManageArticlePurchase");
		
		try {
			logger.info("Adding relationship between Article: (" + articles.getArticlesId() + ") and Purchase: (" + purchaseOrders.getPurchaseOrderId() + ").");
			tx = session.beginTransaction();
			ArticlesPurchase AP = new ArticlesPurchase(articles, purchaseOrders);
			articlesPurchaseId = (Integer) session.save(AP);
			tx.commit();
			logger.info("Added data: (" + articlesPurchaseId + ") from addArticlePurchase, finished");
		}catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
			logger.error("Unsuccessful transaction, due to null (" + e + ").");
		}finally {
			logger.info("Closing session " + session);
			session.close();
		}
		return articlesPurchaseId;
	}
}
