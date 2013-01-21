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
	private static final long serialVersionUID = 4453873927203505335L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long userId;
	private String userName;
	private int userAge;
	private String password;
	private double latitude;
	private double longitude;
	
	public User() {
		
	}

	public User(String userName, String password, int userAge, double latitude, double longitude) {
		super();
		this.userName = userName;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
