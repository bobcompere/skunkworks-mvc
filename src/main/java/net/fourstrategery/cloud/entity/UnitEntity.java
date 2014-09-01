package net.fourstrategery.cloud.entity;

import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "unit")
public class UnitEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "unit_id_seq")
	@SequenceGenerator(name="unit_id_seq", sequenceName="unit_id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "status")
	private UnitStatus status;
	
	@Column(name = "troops")
	private int troops;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private VenueEntity location;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private GameEntity game;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id")
	private PlayerEntity player;
	
	@Column(name = "next_move_time")
	private Date nextMoveTime;
	
	@Transient
	private List<String> moveMethods;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public UnitStatus getStatus() {
		return status;
	}

	public void setStatus(UnitStatus status) {
		this.status = status;
	}

	public int getTroops() {
		return troops;
	}

	public void setTroops(int troops) {
		this.troops = troops;
	}

	public VenueEntity getLocation() {
		return location;
	}

	public void setLocation(VenueEntity location) {
		this.location = location;
	}

	public Date getNextMoveTime() {
		return nextMoveTime;
	}

	public void setNextMoveTime(Date nextMoveTime) {
		this.nextMoveTime = nextMoveTime;
	}

	public PlayerEntity getPlayer() {
		return player;
	}

	public void setPlayer(PlayerEntity player) {
		this.player = player;
	}

	public GameEntity getGame() {
		return game;
	}

	public void setGame(GameEntity game) {
		this.game = game;
	}

	public List<String> getMoveMethods() {
		return moveMethods;
	}

	public void setMoveMethods(List<String> moveMethods) {
		this.moveMethods = moveMethods;
	}
	
	
}
