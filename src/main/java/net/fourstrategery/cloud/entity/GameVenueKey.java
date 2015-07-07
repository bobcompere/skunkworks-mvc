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
	@Override
	public int hashCode() {
		return game.getId() + venue.getId().hashCode() * 100000; // should be safe for 100,000 games
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GameVenueKey)) {
			throw new IllegalArgumentException("Cannot compare a " + obj.getClass().getName() + " to a " + this.getClass().getName());
		}
		return (obj.hashCode() == this.hashCode());
	}
	
	
	
}
