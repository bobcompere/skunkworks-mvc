package net.fourstrategery.cloud.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="player")
public class PlayerEntity extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "player_id_seq")
	@SequenceGenerator(name="player_id_seq", sequenceName="player_id_seq", allocationSize=1)
	private int id;
	
	@Column(name="screen_name")
	private String screenName;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email_address")
	private String emailAddress;
	
	@Column(name="foursquare_id")
	private String fourSquareId;
	
	@Column(name = "last_activity")
	private Date lastActivity;
	
	@Column(name = "password")
	private String password;

	
	public String getFourSquareId() {
		return fourSquareId;
	}
	

	public void setFourSquareId(String fourSquareId) {
		this.fourSquareId = fourSquareId;
	}

	@Column(name="foursquare_token")
	private String fourSquareToken;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFourSquareToken() {
		return fourSquareToken;
	}

	public void setFourSquareToken(String fourSquareToken) {
		this.fourSquareToken = fourSquareToken;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
