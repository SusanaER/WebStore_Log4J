package operaciones;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Factory {
	static final Logger logger = Logger.getLogger(Factory.class);
	private static SessionFactory factory;
	public static SessionFactory createFactory() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
			logger.info("Creating factory: " + factory);
			
		} catch (Throwable e){
			System.err.print("Failed to create sessionFactory object. " + e);
			logger.error("Failed to create sessionFactory object. " + e);
			throw new ExceptionInInitializerError(e);
		}
		return factory;
	}
}
