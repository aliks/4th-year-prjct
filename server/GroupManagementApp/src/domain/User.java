package domain;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User implements Serializable
{
	private static final long serialVersionUID = -8636046618352460140L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long userId;
	private String userName;
	private int userAge;
	private double latitude;
	private double longitude;
	
	public User() {
		
	}

	public User(String userName, int userAge, double latitude, double longitude) {
		super();
		this.userName = userName;
		this.userAge = userAge;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String toString() 
	{
		return "Name: " + userName + "; Age: " + userAge + "; Last location: " + longitude + ", " + latitude;
	}

}
