package net.fourstrategery.cloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="checkin")
public class CheckinEntity extends BaseEntity {

	@Id
	@Column(name="id")
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id")
	private PlayerEntity player;
	
	@Column(name = "checkin_type")
	private String checkinType;
	
	@Column(name = "shout")
	private String shout;
	
	@Column(name = "user_id")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "venue_id")
	private VenueEntity venue;
	
	@Column(name = "venue_name")
	private String venueName;
	
	@Column(name = "venue_checkins_count")
	private int checkinsCount;
	
	@Column(name = "venue_users_count")
	private int usersCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PlayerEntity getPlayer() {
		return player;
	}

	public void setPlayer(PlayerEntity player) {
		this.player = player;
	}

	public String getCheckinType() {
		return checkinType;
	}

	public void setCheckinType(String checkinType) {
		this.checkinType = checkinType;
	}

	public String getShout() {
		return shout;
	}

	public void setShout(String shout) {
		this.shout = shout;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public VenueEntity getVenue() {
		return venue;
	}

	public void setVenue(VenueEntity venue) {
		this.venue = venue;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public int getCheckinsCount() {
		return checkinsCount;
	}

	public void setCheckinsCount(int checkinsCount) {
		this.checkinsCount = checkinsCount;
	}

	public int getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}
	
	
}
