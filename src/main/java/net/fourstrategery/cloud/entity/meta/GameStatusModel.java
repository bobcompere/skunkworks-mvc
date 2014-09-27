package net.fourstrategery.cloud.entity.meta;

import java.util.List;

import net.fourstrategery.cloud.entity.ActivityEntity;
import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.GamePlayerEntity;
import net.fourstrategery.cloud.entity.GameVenueEntity;
import net.fourstrategery.cloud.entity.UnitEntity;

public class GameStatusModel {

	private List<UnitEntity> myUnits;
	private GameEntity game;
	private boolean activeGame;
	private String gameStatus;
	private List<GameVenueEntity> venues;
	private int venueCount;
	private int myPlayerNumber;
	private List<GamePlayerEntity> players;
	private List<ActivityEntity> activities;

	public List<UnitEntity> getMyUnits() {
		return myUnits;
	}

	public void setMyUnits(List<UnitEntity> myUnits) {
		this.myUnits = myUnits;
	}

	public GameEntity getGame() {
		return game;
	}

	public void setGame(GameEntity game) {
		this.game = game;
	}

	public List<GameVenueEntity> getVenues() {
		return venues;
	}

	public void setVenues(List<GameVenueEntity> venues) {
		this.venues = venues;
	}

	public int getVenueCount() {
		return venueCount;
	}

	public void setVenueCount(int venueCount) {
		this.venueCount = venueCount;
	}

	public int getMyPlayerNumber() {
		return myPlayerNumber;
	}

	public void setMyPlayerNumber(int myPlayerNumber) {
		this.myPlayerNumber = myPlayerNumber;
	}

	public List<GamePlayerEntity> getPlayers() {
		return players;
	}

	public void setPlayers(List<GamePlayerEntity> players) {
		this.players = players;
	}

	public List<ActivityEntity> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityEntity> activities) {
		this.activities = activities;
	}

	public boolean isActiveGame() {
		return activeGame;
	}

	public void setActiveGame(boolean activeGame) {
		this.activeGame = activeGame;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}


}
