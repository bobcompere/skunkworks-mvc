package net.fourstrategery.cloud.gameplay;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.GamePlayerEntity;
import net.fourstrategery.cloud.entity.GamePlayerKey;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.VenueEntity;
import net.fourstrategery.cloud.repository.GamePlayerRepository;
import net.fourstrategery.cloud.repository.GameRepository;
import net.fourstrategery.cloud.repository.UnitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GameControlAndScoringServiceImpl implements
		GameControlAndScoringService {

	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private GamePlayerRepository gamePlayerRepository;
	
	@Autowired
	private ActivityService activityService;
	
	private static final long MILLI_IN_A_DAY = 24 * 60 * 60 * 1000;
	
	@Override
	@Transactional
	@Scheduled(cron = "0 1 * * * *")
	public void dailyScoreAndGameCloseOut() {
		// TODO Auto-generated method stub
		
		long milliNow = new Date().getTime();
		milliNow -= MILLI_IN_A_DAY;
		
		Date last24Hours = new Date(milliNow);
		
		List<GameEntity> gamesActiveYesterday = gameRepository.findActiveGames(last24Hours);
		
		for (GameEntity game : gamesActiveYesterday) {
			tabulateScores(game);
		}
	}


	private void tabulateScores(GameEntity game) {
		//
		// occupied venues get points, by venue size
		//
		List<UnitEntity> garrisonedUnits = unitRepository.getUnitListForVenueScoring(game);
		
		Map <Integer, ScoreAdder> scoreAdders = new HashMap<Integer,ScoreAdder>();
		
		int scoreToAdd = garrisonedUnits.size();
		for (UnitEntity unit : garrisonedUnits) {
			ScoreAdder adder = scoreAdders.get(unit.getPlayer().getId());
			if (adder == null) {
				adder = new ScoreAdder(unit.getPlayer());
				scoreAdders.put(unit.getPlayer().getId(),adder);
			}
			adder.increment(unit.getLocation(),scoreToAdd);
			scoreToAdd--;
		}
		
		for (ScoreAdder adder : scoreAdders.values()) {
			GamePlayerKey key = new GamePlayerKey();
			key.setGame(game);
			key.setPlayer(adder.player);
			GamePlayerEntity gamePlayer = gamePlayerRepository.findOne(key);
			if (gamePlayer != null) {
				gamePlayer.setScore(gamePlayer.getScore() + adder.addToScore);
				gamePlayerRepository.save(gamePlayer);
				
				activityService.simpleGameActivity(game, "Venue Occuppation Points Awarded to " +
						gamePlayer.getPlayer().getScreenName() + ". Total points: " + adder.addToScore + ". Details:" +
						adder.scoringDetails);
			}
		}
	}

}

class ScoreAdder {
	
	PlayerEntity player;
	int addToScore = 0;
	String scoringDetails;
	
	ScoreAdder(PlayerEntity player) {
		this.player = player;
		addToScore = 0;
		scoringDetails = "";
	}
	
	void increment(VenueEntity venue, int value) {
		addToScore += value;
		if (!scoringDetails.equals("")) {
			scoringDetails += ", ";
		}
		scoringDetails += venue.getName() + "(+" + value + " points)";
	}
}
