package dataaccess;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Group;
import domain.User;

@Stateless
public class GroupDataAccessImp {
	@PersistenceContext
	private EntityManager em;

	// TESTED
	public void insert(User newUser) {
		em.persist(newUser);
	}

	// TESTED
	public List<String> viewUsersByGroup(String groupName) {
		Query q = em.createQuery("SELECT grp.users FROM Group grp "
				+ "WHERE grp.groupName = :grName");
		q.setParameter("grName", groupName);
		List<User> list = q.getResultList();
		return fetchUserNames(list);
	}
	// TESTED
	// return users object who are in groupName group
	public List<User> viewUsersObjByGroup(String groupName) {
		Query q = em.createQuery("SELECT grp.users FROM Group grp "
				+ "WHERE grp.groupName = :grName");
		q.setParameter("grName", groupName);
		List<User> list = q.getResultList();
		return list;
	}
	// TESTED
	// return list of strings
	public List<String> findByUserName(String name) {
		Query q = em.createQuery("SELECT usr FROM User usr "
				+ "WHERE usr.userName LIKE :name");
		q.setParameter("name", name);
		List<User> result = q.getResultList();
		return fetchUserNames(result);
	}

	public User findByID(Long id) {
		Query q = em.createQuery("SELECT usr FROM User usr "
				+ "WHERE usr.userId = :id");
		q.setParameter("id", id);
		List<User> result = q.getResultList();
		if (result.isEmpty())
			return null;
		return result.get(0);
	}
	
	// TESTED
	public void updateUser(User user) {
		em.merge(user);
	}

	// TESTED
	public void insertGroup(Group newGroup) {
		em.persist(newGroup);

	}

	// TESTED
	public void deleteGroup(Group oldGroup) {
		em.remove(em.merge(oldGroup));
	}

	// TESTED
	public List<String> getAllGroups() {
		Query q = em.createQuery("SELECT g.groupName FROM Group g");
		List<String> result = q.getResultList();
		return result;
	}

	//
	public List<Group> getAllGroupsObj() {
		Query q = em.createQuery("SELECT g FROM Group g");
		List<Group> result = q.getResultList();
		return result;
	}

	// TESTED
	public Group getGroupByName(String groupName) {
		Query q = em.createQuery("SELECT g FROM Group g "
				+ "WHERE g.groupName = :name");
		q.setParameter("name", groupName);
		List<Group> result = q.getResultList();
		if (result.isEmpty())
			return null;
		else
			return result.get(0);
	}

	// TESTED
	public void updateGroup(Group group) {
		em.merge(group);
	}

	//

	private List<String> fetchUserNames(List<User> source) {
		List<String> usrList = new ArrayList<String>();
		if (source != null) {
			for (User u : source)
				usrList.add(u.getUserName());
		} else
			return null;
		return usrList;
	}

	public long verify(String name, String password) {
		Query q = em.createQuery("SELECT u.userId FROM User u "
				+ "WHERE u.userName LIKE :name " + "AND u.password LIKE :pass");
		q.setParameter("name", name);
		q.setParameter("pass", password);
		List<Long> results = q.getResultList();
		if (results.isEmpty())
			return 0;
		return results.get(0);
	}
}
