package net.fourstrategery.cloud.service.mobile;

import net.fourstrategery.cloud.entity.meta.GameStatusModel;

public class GameStatusResult {

	private boolean success;
	private String message;
	private GameStatusModel gameStatusModel;
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
	public GameStatusModel getGameStatusModel() {
		return gameStatusModel;
	}
	public void setGameStatusModel(GameStatusModel gameStatusModel) {
		this.gameStatusModel = gameStatusModel;
	}
	
	
}
