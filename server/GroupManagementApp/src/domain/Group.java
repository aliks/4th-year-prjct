package domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="USERS_GROUPS")
public class Group implements Serializable
{
	private static final long serialVersionUID = -9124043819358665396L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long groupId;
	private String groupName;
	@OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinTable(name="JOIN_USER_GROUP",
			joinColumns={@JoinColumn(name="groupId")},
			inverseJoinColumns={@JoinColumn(name="userId")})
	private List<User> users = null;
	
	public Group() {
		
	}
	
	public Group(String groupName) {
		super();
		this.groupName = groupName;
		this.users = new ArrayList<User>();
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public String toString() {
		return "Group name: " + groupName;
	}

}
