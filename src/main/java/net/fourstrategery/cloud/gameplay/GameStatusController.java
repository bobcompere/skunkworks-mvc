package net.fourstrategery.cloud.gameplay;

import java.util.ArrayList;
import java.util.List;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.GamePlayerEntity;
import net.fourstrategery.cloud.entity.GameVenueEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.meta.GameStatusModel;
import net.fourstrategery.cloud.repository.ActivityRepository;
import net.fourstrategery.cloud.repository.GamePlayerRepository;
import net.fourstrategery.cloud.repository.GameRepository;
import net.fourstrategery.cloud.repository.GameVenueRepository;
import net.fourstrategery.cloud.repository.UnitRepository;
import net.fourstrategery.cloud.security.PlayerAuthDetalService;
import net.fourstrategery.cloud.security.PlayerUserDetails;
import net.fourstrategery.cloud.utility.VenueUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameStatusController {
	
	@Autowired
	UnitRepository unitRepository;
	
	@Autowired 
	GameRepository gameRepository;
	
	@Autowired
	GameVenueRepository gameVenueReposity;
	
	@Autowired
	GamePlayerRepository gamePlayerRepository;
	
	@Autowired
	PlayerAuthDetalService playerAuthDetalService;
	
	@Autowired
	ActivityRepository activityRepository;

	@ModelAttribute("gameModel")
	GameStatusModel getModel(@RequestParam int gameId) {
		GameStatusModel returnVal = new GameStatusModel();
		
		PlayerUserDetails pud = (PlayerUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PlayerEntity player = pud.getPlayer();
		returnVal.setMyUnits(unitRepository.getCurrentUnitsForPlayerAndGame(player.getId(), gameId));
		
		List<UnitEntity> gameUnits = unitRepository.getCurrentUnitsForGame(gameId);
		
		GameEntity game = gameRepository.findOne(gameId);
		returnVal.setGame(game);
		
		returnVal.setActivities(activityRepository.getActivitiesForGameAndPlayer(game, player));
		
		List<GamePlayerEntity> players = gamePlayerRepository.getPlayersForGame(game);
		
		returnVal.setPlayers(players);
		
		int playerNum = 1;
		for (GamePlayerEntity playerX : players) {
			playerX.setPlayerNumber(playerNum);
			if (playerX.getPlayer().getId() == player.getId()) {
				returnVal.setMyPlayerNumber(playerNum);
			}
			playerNum++;
		}
		
		List<GameVenueEntity> venues = gameVenueReposity.getVenuesForGame(game);
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
		}
		
			
		return returnVal;
	}
	
	@RequestMapping(value = "nav/gameStatus",  method = RequestMethod.GET)
	String getGameStatus(ModelMap model, @ModelAttribute GameStatusModel gameStatusModel, @RequestParam(required = false) String message) {
		
		if (message != null) {
			model.addAttribute("message",message);
		}
		else {
			model.addAttribute("message","");
		}
		return "nav/gameStatus";
	}
	
	
}
