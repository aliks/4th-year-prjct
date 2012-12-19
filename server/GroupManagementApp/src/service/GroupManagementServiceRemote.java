package service;

import java.util.List;

import javax.ejb.Remote;

import domain.Group;
import domain.User;

@Remote
public interface GroupManagementServiceRemote {
	
	public void registerUser(User newUser);
	
	public List<User> getAllUsers(String groupName);
	
	public List<User> searchUser(String name);
	
	public void updateLocation(Double latit, Double longt, String name, int age);
	
	public void createGroup(Group newGroup);
	
	public void removeGroup(Group oldGroup);
	
	public List<Group> viewAllGroups();
	
	public Group searchGroup(String groupName);
	
	public void subscribeToGroup(User user, String groupName);
	
	public void unsubscribeUserFromGroup(User user, String groupName);
	
	
	public void updateGroup(Group group);
}
