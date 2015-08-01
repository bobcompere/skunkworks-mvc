package net.fourstrategery.cloud.gameplay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.GamePlayerEntity;
import net.fourstrategery.cloud.entity.GameVenueEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.meta.GameStatusModel;
import net.fourstrategery.cloud.repository.ActivityRepository;
import net.fourstrategery.cloud.repository.GamePlayerRepository;
import net.fourstrategery.cloud.repository.GameVenueRepository;
import net.fourstrategery.cloud.repository.UnitRepository;
import net.fourstrategery.cloud.utility.VenueUtility;

@Service
public class GameStatusModelServiceImpl implements GameStatusModelService {
	
	@Autowired
	UnitRepository unitRepository;
	
	@Autowired
	GamePlayerRepository gamePlayerRepository;
	
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	GameVenueRepository gameVenueRepository;

	@Override
	public GameStatusModel getGameStatusModel(GameEntity game, PlayerEntity player) {
		
		GameStatusModel returnVal = new GameStatusModel();
		returnVal.setMyUnits(unitRepository.getCurrentUnitsForPlayerAndGame(player.getId(), game.getId()));
		List<UnitEntity> gameUnits = unitRepository.getCurrentUnitsForGame(game.getId());

		returnVal.setGame(game);
		
		Date now = new Date();
		if (now.after(game.getEnds()) || now.before(game.getStarts())) {
			returnVal.setActiveGame(false);
			if (now.after(game.getEnds())) {
				returnVal.setGameStatus("Completed Game");
			} else {
				returnVal.setGameStatus("Game Not Started Yet");
			}
		}
		else {
			returnVal.setActiveGame(true);
			returnVal.setGameStatus("Active Game");
		}
		
		returnVal.setActivities(activityRepository.getActivitiesForGameAndPlayer(game, player));
		
		List<GamePlayerEntity> players = gamePlayerRepository.getPlayersForGame(game);
		
		returnVal.setPlayers(players);
		
		List<PlayerEntity> playerDetails = new ArrayList<PlayerEntity>();
		returnVal.setPlayerDetails(playerDetails);
		
		int playerNum = 1;
		for (GamePlayerEntity playerX : players) {
			playerX.setPlayerNumber(playerNum);
			playerDetails.add((PlayerEntity)playerX.getPlayer().clone());
			playerX.setPlayerId(playerX.getPlayer().getId());
			if (playerX.getPlayer().getId() == player.getId()) {
				returnVal.setMyPlayerNumber(playerNum);
			}
			playerNum++;
		}
		
		List<GameVenueEntity> venues = gameVenueRepository.getVenuesForGame(game);
		returnVal.setVenues(venues);
		
		returnVal.setVenueCount(venues.size());
				
		for (int i1=0;i1<venues.size();i1++) {
			List<Integer> dists = new ArrayList<Integer>();
			for (int i2 = 0;i2<venues.size();i2++) {
				dists.add(VenueUtility.distanceBetween(venues.get(i1).getVenue(), venues.get(i2).getVenue()));
			}
			venues.get(i1).setDistances(dists);
			
			for (UnitEntity unit : gameUnits) {
				if (VenueUtility.isInVenue(unit, venues.get(i1).getVenue())) {
					venues.get(i1).setCurrentUnit(unit);
					
					playerNum = 1;
					for (GamePlayerEntity playerX : players) {
						if (playerX.getPlayer().getId() == unit.getPlayer().getId()) {
							venues.get(i1).setCurrentUnitPlayerNumber(playerNum);
							playerX.setActiveUnitCount(playerX.getActiveUnitCount() + 1);
							break;
						}
						playerNum++;
					}
					
					
					break;
				}
			}
		}
		
		for (UnitEntity unit : returnVal.getMyUnits()) {
			List<String> moves = new ArrayList<String>();
			for (GameVenueEntity gameVenue : venues) {
				moves.add(VenueUtility.getMoveMethod(unit, gameVenue.getCurrentUnit(), gameVenue.getVenue()));
			}
			unit.setMoveMethods(moves);
			unit.setVenueId(unit.getLocation().getId());
		}
		
			
		return returnVal;
	}

}
