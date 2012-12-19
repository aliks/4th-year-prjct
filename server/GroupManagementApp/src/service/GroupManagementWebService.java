package service;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebService;

import domain.Group;
import domain.User;

@WebService(name="groupManagementWebService")
public class GroupManagementWebService {

	@EJB
	private GroupManagementServiceLocal groupManagementService;
	
	public void registerUser(User newUser) 
	{	
		groupManagementService.registerUser(newUser);
	}

	public List<User> getAllUsers(String groupName) 
	{
		return groupManagementService.getAllUsers(groupName);
	}

	public List<User> searchUser(String name) 
	{
		return groupManagementService.searchUser(name);
	}

	public void updateLocation(Double latit, Double longt, String name, int age) 
	{
		groupManagementService.updateLocation(latit, longt, name, age);
	}

	public void createGroup(Group newGroup) 
	{
		groupManagementService.createGroup(newGroup);
	}

	public void removeGroup(Group oldGroup) 
	{
		groupManagementService.removeGroup(oldGroup);
	}

	public List<Group> viewAllGroups() 
	{
		return groupManagementService.viewAllGroups();
	}

	public Group searchGroup(String groupName) 
	{
		return groupManagementService.searchGroup(groupName);
	}

	public void subscribeToGroup(User user, String groupName) 
	{
		groupManagementService.subscribeToGroup(user, groupName);
	}

	public void unsubscribeUserFromGroup(User user, String groupName) 
	{
		groupManagementService.unsubscribeUserFromGroup(user, groupName);
	}

	public void updateGroup(Group group) 
	{
		groupManagementService.updateGroup(group);
	}

}
