package net.fourstrategery.cloud.service.mobile;

import net.fourstrategery.cloud.entity.GameEntity;

public class GameListItem {

	private int gameId;
	private String description;
	
	public GameListItem(GameEntity ge) {
		setGameId(ge.getId());
		setDescription(ge.getDescription());
	}
	
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
