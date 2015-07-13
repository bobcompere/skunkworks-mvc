package net.fourstrategery.cloud.service.mobile;

import java.util.List;

public class GameList {

	private boolean success;
	private String message;
	private List<GameListItem> games;
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<GameListItem> getGames() {
		return games;
	}
	public void setGames(List<GameListItem> games) {
		this.games = games;
	}

}
