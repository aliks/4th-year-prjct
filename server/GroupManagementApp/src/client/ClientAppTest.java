package client;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import service.GroupManagementService;
import domain.Group;
import domain.User;

public class ClientAppTest {

	public static void main(String[] args) 
	{
		try
		{
			Context jndi = new InitialContext();
			
			GroupManagementService service = (GroupManagementService)
			          jndi.lookup("java:global/GroupsManagement/GroupManagementServiceImp");
			
			/*
			service.updateLocation(100.0, 100.0, "Alex", 26);
			
			List<User> l = service.getAllUsers("home");
			
			for(User usr : l) {
				System.out.println(usr);
			}
			*/
			
		}
		catch (NamingException e)
		{
			System.out.println(e);
		}

	}
}
