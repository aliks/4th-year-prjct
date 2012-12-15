package service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dataaccess.GroupDataAccessImp;
import domain.Group;
import domain.User;

@Stateless
public class GroupManagementServiceImp implements GroupManagementService {

	@EJB
	private GroupDataAccessImp dao;

	@Override
	public void registerUser(User newUser) 
	{
		dao.insert(newUser);
	}

	@Override
	public List<User> getAllUsers(String groupName) 
	{
		return dao.viewUsersByGroup(groupName);
	}

	@Override
	public List<User> searchUser(String name) 
	{
		return dao.findByUserName(name);
	}

	@Override
	public void updateLocation(Double latit, Double longt, String name, int age) 
	{
		// find user 
		User u = findUser(name, age);
		// update location
		u.setLatitude(latit);
		u.setLongitude(longt);
		//update user
		dao.updateUser(u);
	}
	
	private User findUser(String name, int age) 
	{
		List<User> list = dao.findByUserName(name);
		for(User usr : list) {
			if(usr.getUserAge() == age)
				return usr;
		}
		return null;
	}

	@Override
	public void createGroup(Group newGroup) 
	{
		dao.insertGroup(newGroup);
	}

	@Override
	public List<Group> viewAllGroups() 
	{
		return dao.getAllGroups();
	}

	@Override
	public Group searchGroup(String groupName) 
	{
		return dao.getGroupByName(groupName);
	}

	@Override
	public void subscribeToGroup(User user, String groupName) 
	{
		Group group = searchGroup(groupName);
		//group.getUsers().size();
		group.getUsers().add(user);
		updateGroup(group);
	}
	@Override
	public void updateGroup(Group group) 
	{
		dao.updateGroup(group);
	}

	@Override
	public void removeGroup(Group oldGroup) 
	{
		dao.deleteGroup(oldGroup);
	}

	@Override
	public void unsubscribeUserFromGroup(User user, String groupName) 
	{
		Group group = searchGroup(groupName);
		// find user in a group
		int i = getUserInList(group, user);
		// delete user in a group
		group.getUsers().remove(i); 
		// update new list
		updateGroup(group);
	}
	
	private int getUserInList(Group grp, User u) 
	{
		int index = 0;
		List<User> list = grp.getUsers();
		for(User usr : list) {
			if(usr.getUserName().equalsIgnoreCase(u.getUserName())
					&& usr.getUserAge() == u.getUserAge())
				index = list.indexOf(usr);
		}
		return index;
	}
	
	
	
	

}
