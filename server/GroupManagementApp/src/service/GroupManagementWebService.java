package service;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import domain.DemandSpace;
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
	
	public long verifyUser(String login, String pass) 
	{	
		return groupManagementService.verifyUser(login, pass);
	}

	public List<String> getAllUsers(String groupName) 
	{
		return groupManagementService.getAllUsers(groupName);
	}

	public List<String> searchUser(String name) 
	{
		return groupManagementService.searchUser(name);
	}

	public void updateLocation(Double latit, Double longt, Long id) 
	{
		groupManagementService.updateLocation(latit, longt, id);
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

	public void subscribeToGroup(String groupName, Long id)
	{
		groupManagementService.subscribeToGroup(groupName, id);
	}

	public void unsubscribeUserFromGroup(String groupName, Long id)
	{
		groupManagementService.unsubscribeUserFromGroup(groupName, id);
	}
	
	public List<String> getUserLocation(String Username, String groupName)
	{
		return groupManagementService.getUserLocation(Username, groupName);
	}
	
	public List<String> getUserage(String Username, String groupName)
	{
		return groupManagementService.getUserage(Username, groupName);
	}
	
	public List<String> findUsersGrp(Long id) 
	{
		return groupManagementService.findUsersGrp(id);
	}

	public Integer numberOfMembers(String groupName)
	{
		return groupManagementService.numberOfMembers(groupName);
	}
	
	public List<String> findNN(String groupName, DemandSpace ds)
	{
		return groupManagementService.findNN(groupName, ds);
	}
	
	@WebMethod(exclude=true)
	public void updateGroup(Group group) 
	{
		
	}

}
