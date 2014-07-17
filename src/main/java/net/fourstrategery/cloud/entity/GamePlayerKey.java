package net.fourstrategery.cloud.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class GamePlayerKey implements Serializable {

	private GameEntity game;
	private PlayerEntity player;
	public GameEntity getGame() {
		return game;
	}
	public void setGame(GameEntity game) {
		this.game = game;
	}
	public PlayerEntity getPlayer() {
		return player;
	}
	public void setPlayer(PlayerEntity player) {
		this.player = player;
	}
	@Override
	public int hashCode() {
		return game.getId() * 100000 + player.getId();
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GamePlayerKey) {
			return this.hashCode() == obj.hashCode();
		}
		throw new IllegalArgumentException("Cannot compare " + this.getClass().getName() + " to " + obj.getClass().getName());
	}
	
	
	
}
