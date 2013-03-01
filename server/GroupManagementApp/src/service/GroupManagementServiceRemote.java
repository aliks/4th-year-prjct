package service;

import java.util.List;

import javax.ejb.Remote;

import domain.DemandSpace;
import domain.Group;
import domain.User;

@Remote
public interface GroupManagementServiceRemote {
	
	public void registerUser(User newUser);
	
	public long verifyUser(String name, String password);
	
	public List<String> getAllUsers(String groupName);
	
	public List<String> searchUser(String name);
	
	public void updateLocation(Double latit, Double longt, Long id);
	
	public void createGroup(String newGroup);
	
	public void removeGroup(String oldGroup);
	
	public List<String> viewAllGroups();
	
	public List<String> searchGroup(String groupName);
	
	public void subscribeToGroup(String groupName, Long id);
	
	public void unsubscribeUserFromGroup(String groupName, Long id);
	
	public void updateGroup(Group group);

	public List<String> getUserLocation(String Username, String groupName);

	public List<String> getUserage(String Username, String groupName);
	
	public List<String> findUsersGrp(Long id);
	
	public Integer numberOfMembers(String groupName);
	
	public List<String> findNN(String groupName, DemandSpace ds);
}
