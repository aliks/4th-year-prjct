package service;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
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

	public List<String> getAllUsers(String groupName) 
	{
		return groupManagementService.getAllUsers(groupName);
	}

	public List<String> searchUser(String name) 
	{
		return groupManagementService.searchUser(name);
	}

	public void updateLocation(Double latit, Double longt, String name, int age) 
	{
		groupManagementService.updateLocation(latit, longt, name, age);
	}

	public void createGroup(@WebParam(name = "groupname")String newGroup) 
	{
		groupManagementService.createGroup(newGroup);
	}

	public void removeGroup(String oldGroup) 
	{
		groupManagementService.removeGroup(oldGroup);
	}

	public List<String> viewAllGroups() 
	{
		return groupManagementService.viewAllGroups();
	}

	public List<String> searchGroup(String groupName) 
	{
		return groupManagementService.searchGroup(groupName);
	}

	public void subscribeToGroup(String name, int age, String groupName)
	{
		groupManagementService.subscribeToGroup(name, age, groupName);
	}

	public void unsubscribeUserFromGroup(String name, int age, String groupName)
	{
		groupManagementService.unsubscribeUserFromGroup(name, age, groupName);
	}
	
	public List<String> getUserLocation(String Username, String groupName)
	{
		return groupManagementService.getUserLocation(Username, groupName);
	}
	
	public List<String> getUserage(String Username, String groupName)
	{
		return groupManagementService.getUserage(Username, groupName);
	}
	
	@WebMethod(exclude=true)
	public void updateGroup(Group group) 
	{
		
	}

}
