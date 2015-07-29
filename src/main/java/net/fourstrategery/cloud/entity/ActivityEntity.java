package net.fourstrategery.cloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "activity")
public class ActivityEntity extends BaseEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "activity_id_seq")
	@SequenceGenerator(name="activity_id_seq", sequenceName="actvity_id_seq", allocationSize=1)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private GameEntity game;
	
	@Column(name = "message")
	private String message;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "specific_audience_player_id")
	@JsonIgnore
	private PlayerEntity specificAudience;

	public GameEntity getGame() {
		return game;
	}

	public void setGame(GameEntity game) {
		this.game = game;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PlayerEntity getSpecificAudience() {
		return specificAudience;
	}

	public void setSpecificAudience(PlayerEntity specificAudience) {
		this.specificAudience = specificAudience;
	}
	
	
}
