package net.fourstrategery.cloud.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity(name="player")
public class Player extends BaseEntity implements Serializable {

	private int id;
	private String screenName;
	private String emailAddress;
	private String fourSquareToken;
}
