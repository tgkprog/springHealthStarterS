package com.sel2in.smsWebSend.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.sel2in.smsWebSend.dao.UserDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	protected HibernateTemplate template = null;

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(UserDaoImpl.class.getName());

	// TODO inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		template = new HibernateTemplate(sessionFactory);
	}

	public Integer createUser(User user) throws DataAccessException {
		logger.log(Sel2inLogger.INFO, "Creating User: " + user);
		try {
			template.saveOrUpdate(user);
			logger.log(Sel2inLogger.INFO, "User Created");
		} catch (DataAccessException e) {
			logger.log(Sel2inLogger.ERROR, "DataAccessException: User Not Created");
			throw e;
		}
		return template.get(User.class, user.getId()).getId();
	}

	public User getUserById(int id) {
		logger.log(Sel2inLogger.INFO, "UserId is attempting to login:" + id);
		return template.get(User.class, id);
	}

	public void updateUser(User user) {
		logger.log(Sel2inLogger.INFO, "Updating User: {}" + user);
		template.update(user);
	}

	public void deleteUser(User user) {
		logger.log(Sel2inLogger.INFO, "Deleting User: {}" + user);
		template.delete(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> listUsers() {
		logger.log(Sel2inLogger.INFO, "Getting All Users");
		return (List<User>) template.find("from User");
	}

	@SuppressWarnings("unchecked")
	public User getUserByEmail(String email) {
		User user = null;
		logger.log(Sel2inLogger.INFO, "User data is fetching using email id:" + email);
		List<User> users = (List<User>) template.find("from User u where u.email = ?", email);
		if (users != null && !users.isEmpty()) {
			user = users.get(0);
		} else {
			logger.log(Sel2inLogger.WARN, "No user found with email: " + email);
		}
		logger.log(Sel2inLogger.INFO, "" + user);
		return user;
	}

	@SuppressWarnings("unchecked")
	public User getUserByContact(String contact) {
		logger.log(Sel2inLogger.INFO, "getUserByContact(): " + contact);
		List<User> users = (List<User>) template.find("from User u where u.email=? or u.mobile=?", contact, contact);
		if (users != null && !users.isEmpty()) {
			return users.get(0);
		} else {
			logger.log(Sel2inLogger.WARN, "No user found with contact: {}" + contact);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public User getUserByMobile(String mobile) {
		logger.log(Sel2inLogger.INFO, "getUserByMobile(): " + mobile);
		List<User> users = (List<User>) template.find("from User u where u.mobile = ?", mobile);
		if (users != null && !users.isEmpty()) {
			return users.get(0);
		} else {
			logger.log(Sel2inLogger.WARN, "No user found with mobile: " + mobile);
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> searchUsersByEmailOrMobile(String searchText) {
		logger.log(Sel2inLogger.INFO, "searchUsersByEmailOrMobile(): " + searchText);
		String query = "from User u where u.email like '%" + searchText + "%' OR u.mobile like '%" + searchText + "%' ";
		List<User> users = (List<User>) template.find(query);
		logger.log(Sel2inLogger.INFO, "users: " + users);
		return users;
	}

}