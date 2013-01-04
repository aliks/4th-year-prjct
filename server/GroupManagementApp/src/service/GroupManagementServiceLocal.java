package service;

import java.util.List;

import javax.ejb.Local;

import domain.Group;
import domain.User;

@Local
public interface GroupManagementServiceLocal {
	
	public void registerUser(User newUser);
	
	public List<String> getAllUsers(String groupName);
	
	public List<String> searchUser(String name);
	
	public void updateLocation(Double latit, Double longt, String name, int age);
	
	public void createGroup(String newGroup);
	
	public void removeGroup(String groupName);
	
	public List<String> viewAllGroups();
	
	public List<String> searchGroup(String groupName);
	
	public void subscribeToGroup(String name, int age, String groupName);
	
	public void unsubscribeUserFromGroup(String name, int age, String groupName);
	
	public void updateGroup(Group group);

	public List<String> getUserLocation(String Username, String groupName);
	
	public List<String> getUserage(String Username, String groupName);
}
