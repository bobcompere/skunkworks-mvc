package net.fourstrategery.cloud.profile;

import javax.persistence.Column;

import org.apache.commons.beanutils.BeanUtils;

import net.fourstrategery.cloud.entity.PlayerEntity;

public class PlayerEditModel {
	
	private int id;

	private String screenName;
	
	private String firstName;
	
	private String lastName;
	
	private String emailAddress;
	
	private String passwordChange1;
	
	private String passwordChange2;
	
	public PlayerEditModel(PlayerEntity player) {
		try {
			BeanUtils.copyProperties(this,player);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPasswordChange1() {
		return passwordChange1;
	}

	public void setPasswordChange1(String passwordChange1) {
		this.passwordChange1 = passwordChange1;
	}

	public String getPasswordChange2() {
		return passwordChange2;
	}

	public void setPasswordChange2(String passwordChange2) {
		this.passwordChange2 = passwordChange2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
