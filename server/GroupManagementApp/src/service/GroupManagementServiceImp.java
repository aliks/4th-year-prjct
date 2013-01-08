package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import dataaccess.GroupDataAccessImp;
import domain.Group;
import domain.User;

@Stateless
public class GroupManagementServiceImp implements GroupManagementServiceRemote,
													GroupManagementServiceLocal {

	@EJB
	private GroupDataAccessImp dao;

	@Override
	public void registerUser(User newUser) 
	{
		dao.insert(newUser);
	}

	@Override
	public List<String> getAllUsers(String groupName) 
	{
		return dao.viewUsersByGroup(groupName);
	}

	@Override
	public List<String> searchUser(String name) 
	{
		return dao.findByUserName(name);
	}

	@Override
	public void updateLocation(Double latit, Double longt, String name, int age) 
	{
		// find user 
		User u = findUser(name, age);
		// update location
		if(u != null) {
			u.setLatitude(latit);
			u.setLongitude(longt);
			//update user
			dao.updateUser(u);
		}
	}

	@Override
	public void createGroup(String newGroupString) 
	{
		Group newGroup = new Group(newGroupString);
		dao.insertGroup(newGroup);
	}

	@Override
	public List<String> viewAllGroups() 
	{
		return dao.getAllGroups();
	}

	@Override
	public List<String> searchGroup(String groupName) 
	{
		Group gr = dao.getGroupByName(groupName);
		List<String> result = new ArrayList<String>();
		if(gr != null) {
			result.add(gr.getGroupName());
			for(User u : gr.getUsers())
				result.add(u.getUserName());
			return result;
		} else {
			return null;
		}
	}

	@Override
	public void subscribeToGroup(String name, int age, String groupName) 
	{
		User user = findUser(name, age);
		Group group = findGroup(groupName);
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
	public void removeGroup(String groupName) 
	{
		Group oldGroup = findGroup(groupName);
		dao.deleteGroup(oldGroup);
	}

	@Override
	public void unsubscribeUserFromGroup(String name, int age, String groupName) 
	{
		User user = findUser(name, age);
		Group group = findGroup(groupName);
		// find user in a group
		int i = getUserInList(group, user);
		// delete user in a group
		group.getUsers().remove(i); 
		// update new list
		updateGroup(group);
	}
	
	@Override
	public List<String> getUserLocation(String Username, String groupName)
	{
		List<String> result = new ArrayList<String>();
		String sb = null;
		List<User> listOfUser = findGroup(groupName).getUsers();
		if(listOfUser != null) {
			for(User u : listOfUser) {
				if(u.getUserName().equalsIgnoreCase(Username)) {
					sb = "name: " + Username + "; location: " + u.getLatitude() + "," + u.getLongitude();
					result.add(sb);
				}	
			}
			return result;	
		} else 
			return null;
	}
	
	@Override
	public List<String> getUserage(String Username, String groupName)
	{
		List<String> result = new ArrayList<String>();
		String sb = null;
		List<User> listOfUser = findGroup(groupName).getUsers();
		if(listOfUser != null) {
			for(User u : listOfUser) {
				if(u.getUserName().equalsIgnoreCase(Username)) {
					sb = "name: " + Username + "; age: " + u.getUserAge();
					result.add(sb);
				}	
			}
			return result;	
		} else 
			return null;
	}
	
	public List<String> findUsersGrp(String user, int age)
	{
		List<Group> listOfGrpObj = getAllGroupsObj();
		List<String> resultList = new ArrayList<String>();
		if(listOfGrpObj != null)
		{
			for(Group group : listOfGrpObj) {
				for(User users : group.getUsers()) {
					if(isInGroup(user, age, group.getGroupName()))
						resultList.add(group.getGroupName());
				}
			}
			return resultList;
		}
		return null;
	}
	
	private boolean isInGroup(String name, int age, String groupName) 
	{
		Group foundGroup = findGroup(groupName);
		for(User u : foundGroup.getUsers())
		{
			if(u.getUserAge() == age && u.getUserName().equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
	
	private List<Group> getAllGroupsObj()
	{
		return dao.getAllGroupsObj();
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
	
	private Group findGroup(String groupName)
	{
		return dao.getGroupByName(groupName);
	}
	
	private User findUser(String name, int age) 
	{
		List<User> list = dao.findByUserName2(name);
		for(User usr : list) {
			if(usr.getUserAge() == age)
				return usr;
		}
		return null;
	}
	

}
