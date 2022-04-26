package dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import models.Address;
import models.User;
import utility.KeyGeneration;

public class UserDaoImpl implements UserDao, Cloneable {

	private static final Logger log = Logger.getLogger(UserDaoImpl.class);
	private SessionFactory factory;

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * 
	 * <p>
	 * The following method check if user is authorized or not if so it return all
	 * the data from database
	 * </p>
	 * 
	 * @param email, password email and password that user enters
	 * @return User {@link User}
	 * @throws SQLException if there is error in SQL
	 * 
	 * 
	 */
	@Override
	public User getUserData(String email, String password) {
		User user = null;
		Session session = factory.openSession();
		try {
			CriteriaBuilder cbuilder = session.getCriteriaBuilder();
			AbstractQuery<User> cquery = cbuilder.createQuery(User.class);
			Root<User> root = cquery.from(User.class);
			cquery.where(cbuilder.equal(root.get("email"), email));
			CriteriaQuery<User> select = ((CriteriaQuery<User>) cquery).select(root);
			TypedQuery<User> criteria = session.createQuery(select);
			user = criteria.getResultList().stream().findFirst().orElse(null);
			if (user != null) {
				if (password.equals(KeyGeneration.decrypt(user.getPassword())))
					return user;
				else
					return null;
			}
		} catch (HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
		return user;
	}

	/**
	 * <p>
	 * The following method add a user details in database
	 * </p>
	 * 
	 * @param user {@link User}
	 * @throws SQLException
	 * 
	 */
	@Override
	public boolean addUser(User user) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			log.error(e);
		} finally {
			session.close();
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method update the password of user if user is authorized
	 * </p>
	 * 
	 * @param user {@link User}
	 * @throws SQLException
	 * 
	 */
	@Override
	public boolean updatePassword(User user) {
		User userdata = null;
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			CriteriaBuilder cbuilder = session.getCriteriaBuilder();
			AbstractQuery<User> cquery = cbuilder.createQuery(User.class);
			Root<User> root = cquery.from(User.class);
			cquery.where(cbuilder.equal(root.get("email"), user.getEmail()));
			CriteriaQuery<User> select = ((CriteriaQuery<User>) cquery).select(root);
			TypedQuery<User> criteria = session.createQuery(select);
			userdata = criteria.getResultList().stream().findFirst().orElse(null);
			if (userdata != null) {
				if (userdata.getSecQues().equals(user.getSecQues()) && userdata.getGame().equals(user.getGame())) {
					transaction = session.beginTransaction();
					userdata.setPassword(user.getPassword());
					session.update(userdata);
					transaction.commit();
					return true;
				} else
					return false;
			} else
				return false;
		} catch (HibernateException exception) {
			log.error(exception);
		} finally {
			session.close();
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following get all user details from database except admin
	 * </p>
	 * 
	 * @return List of users
	 * @exception SQLException
	 * 
	 * 
	 */
	@Override
	public List<User> getAllUser() {
		Session session = factory.openSession();
		List<User> users = null;
		try {
			CriteriaBuilder cbuilder = session.getCriteriaBuilder();
			AbstractQuery<User> cquery = cbuilder.createQuery(User.class);
			Root<User> root = cquery.from(User.class);
			cquery.where(cbuilder.equal(root.get("isAdmin"), false));
			CriteriaQuery<User> select = ((CriteriaQuery<User>) cquery).select(root);
			TypedQuery<User> criteria = session.createQuery(select);
			users = criteria.getResultList();
		} catch (HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
		return users;
	}

	/**
	 * 
	 * <p>
	 * The following method delete a user details from the database
	 * </p>
	 * 
	 * @param id
	 * @exception SQLException
	 * 
	 */
	@Override
	public boolean deleteUser(int userId) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			User user = session.get(User.class, userId);
			session.delete(user);
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			log.error(e);
		} finally {
			session.close();
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method check if an email already exists or not in the database
	 * </p>
	 * 
	 * @param email
	 * @exception SQLException
	 */
	@Override
	public boolean emailCheck(String email) {
		Session session = factory.openSession();
		try {
			CriteriaBuilder cbuilder = session.getCriteriaBuilder();
			CriteriaQuery<User> cquery = cbuilder.createQuery(User.class);
			Root<User> root = cquery.from(User.class);
			cquery.where(cbuilder.equal(root.get("email"), email));
			CriteriaQuery<User> select = cquery.select(root);
			TypedQuery<User> criteria = session.createQuery(select);
			User user = criteria.getResultList().stream().findFirst().orElse(null);
			return user != null;
		} catch (HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method return user data of a particular id of user
	 * </p>
	 * 
	 * @param id
	 * @exception SQLException
	 */
	@Override
	public User getUserData(int id) {
		Session session = factory.openSession();
		User user = null;
		try {
			user = session.get(User.class, id);
			user.setPassword(KeyGeneration.decrypt(user.getPassword()));
		} catch (HibernateException e) {
			log.error(e);
		} finally {
			session.close();
		}
		return user;
	}

	/**
	 * 
	 * <p>
	 * The following method update the user data
	 * </p>
	 * 
	 * @param user {@link User}
	 * @exception SQLException
	 * 
	 * 
	 */
	public boolean updateUserData(User user) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			log.error(e);
		} finally {
			session.close();
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method add new address of the user
	 * </p>
	 * 
	 * @param address id
	 * @exception SQLException
	 * 
	 */
	@Override
	public boolean addNewAddress(Address address) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(address);
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			log.error(e);
		} finally {
			session.close();
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method Updates the address of the user
	 * </p>
	 * 
	 * @param address id
	 * @exception SQLException
	 * 
	 */

	@Override
	public boolean updateOldAddress(Address address) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(address);
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			log.error(e);
		} finally {
			session.close();
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * The following method deletes the address of user
	 * </p>
	 * 
	 * @param addressId
	 * @exception SQLException
	 * 
	 */

	@Override
	public boolean deleteOldAddress(Address oldAddress) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(oldAddress);
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			log.error(e);
		} finally {
			session.close();
		}
		return false;
	}

	public UserDaoImpl() {

	}

	public UserDaoImpl(SessionFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public Object clone() {
		try {
			super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return new UserDaoImpl(this.factory);
	}
}