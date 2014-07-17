package net.fourstrategery.cloud.entity;

import java.io.Serializable;

public class GameCheckinKey implements Serializable {

	private GameEntity game;
	
	private CheckinEntity checkin;

	public GameEntity getGame() {
		return game;
	}

	public void setGame(GameEntity game) {
		this.game = game;
	}

	public CheckinEntity getCheckin() {
		return checkin;
	}

	public void setCheckin(CheckinEntity checkIn) {
		this.checkin = checkIn;
	}

	@Override
	public int hashCode() {
		return checkin.hashCode() * 100000 + game.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameCheckinKey) {
			return this.hashCode() == obj.hashCode();
		}
		throw new IllegalArgumentException("Cannot compare " + this.getClass().getName() + " to " + obj.getClass().getName());
	}
	
	

}
