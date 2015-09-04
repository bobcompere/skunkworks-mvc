package net.fourstrategery.cloud.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "game_venues_view")
@IdClass(GameVenueKey.class)
public class GameVenueEntity {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	@JsonIgnore
	private GameEntity game;
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "venue_id")
	@JsonIgnore
	private VenueEntity venue;
	
	private String name;
	private String city;
	private String state;
	
	@Transient
	private List<Integer> distances;
	
	@Transient
	private UnitEntity currentUnit;
	
	@Transient
	private int currentUnitPlayerNumber;
	
	@Transient
	private String id;
	
	public GameEntity getGame() {
		return game;
	}
	public void setGame(GameEntity game) {
		this.game = game;
	}
	public VenueEntity getVenue() {
		return venue;
	}
	public void setVenue(VenueEntity venue) {
		this.venue = venue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<Integer> getDistances() {
		return distances;
	}
	public void setDistances(List<Integer> distances) {
		this.distances = distances;
	}
	public UnitEntity getCurrentUnit() {
		return currentUnit;
	}
	public void setCurrentUnit(UnitEntity currentUnit) {
		this.currentUnit = currentUnit;
	}
	public int getCurrentUnitPlayerNumber() {
		return currentUnitPlayerNumber;
	}
	public void setCurrentUnitPlayerNumber(int currentUnitPlayerNumber) {
		this.currentUnitPlayerNumber = currentUnitPlayerNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
}
