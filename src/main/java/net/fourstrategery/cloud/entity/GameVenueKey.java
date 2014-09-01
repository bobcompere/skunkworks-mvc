package net.fourstrategery.cloud.entity;

import java.io.Serializable;

public class GameVenueKey implements Serializable{

	private GameEntity game;
	private VenueEntity venue;
	
	
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
	
}
