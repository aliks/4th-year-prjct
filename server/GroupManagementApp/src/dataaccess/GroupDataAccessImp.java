package dataaccess;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Group;
import domain.User;

@Stateless
public class GroupDataAccessImp 
{
	@PersistenceContext
	private EntityManager em;
	// TESTED
	public void insert(User newUser) 
	{
		em.persist(newUser);
	}
	// TESTED
	public List<User> viewUsersByGroup(String groupName) 
	{
		Query q = em.createQuery("SELECT grp.users FROM Group grp " +
				"WHERE grp.groupName = :grName");
		q.setParameter("grName", groupName);
		List<User> usrList = q.getResultList();
		return usrList;
	}
	// TESTED
	public List<User> findByUserName(String name) 
	{
		Query q = em.createQuery("SELECT usr FROM User usr " +
				"WHERE usr.userName LIKE :name");
		q.setParameter("name", name);
		List<User> result = q.getResultList();
		return result;
	}
	// TESTED
	public void updateUser(User user) 
	{
		em.merge(user);
	}
	// TESTED
	public void insertGroup(Group newGroup) 
	{
		em.persist(newGroup);
		
	}
	// TESTED
	public void deleteGroup(Group oldGroup) 
	{
		em.remove(em.merge(oldGroup));
	}
	// TESTED
	public List<Group> getAllGroups() 
	{
		Query q = em.createQuery("SELECT g FROM Group g");
		List<Group> result = q.getResultList();
		return result;
	}
	// TESTED
	public Group getGroupByName(String groupName) {
		Query q = em.createQuery("SELECT g FROM Group g " +
				"WHERE g.groupName = :name");
		q.setParameter("name", groupName);
		Group result = (Group) q.getResultList().get(0);
		//result.getUsers().size();
		return result;
	}
	// TESTED
	public void updateGroup(Group group) 
	{
		em.merge(group);
	}

}
