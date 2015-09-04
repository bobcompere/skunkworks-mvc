package net.fourstrategery.cloud.service.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.GamePlayerEntity;
import net.fourstrategery.cloud.entity.GamePlayerKey;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.meta.GameStatusModel;
import net.fourstrategery.cloud.gameplay.GameStatusModelService;
import net.fourstrategery.cloud.gameplay.MoveService;
import net.fourstrategery.cloud.repository.GamePlayerRepository;
import net.fourstrategery.cloud.repository.GameRepository;
import net.fourstrategery.cloud.repository.PlayerAuthRepository;

@Controller
public class GameServicesController {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private GamePlayerRepository gamePlayerRepository;
	
	@Autowired
	private PlayerAuthRepository playerAuthRepository;
	
	@Autowired
	private GameStatusModelService gameStatusModelService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MoveService moveService;
	
	@ModelAttribute("player")
	private PlayerEntity getPlayer(@RequestParam int playerId, @RequestParam String authToken) {
		PlayerEntity player = playerAuthRepository.getPlayerByIdAndToken(playerId, authToken);
		return player;
	}

	@RequestMapping(value = "/service/mobile/gamelist",  method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public GameList gameList(ModelMap model,  @RequestParam int status) {
		PlayerEntity player = (PlayerEntity)model.get("player");
		
		System.out.println("PLAYER : " + player);
		
		GameList list = new GameList();
		List<GameEntity> games = new ArrayList<GameEntity>();
		
		if (player == null) {
			list.setMessage("INVALID CREDENTIALS");
		} else {
			if (status == 0) { // active
				games = gameRepository.findActiveGamesForPlayer(player, new Date());
			} else { // completed
				games = gameRepository.findLast10GamesForPlayer(player, new Date());
			}
			list.setSuccess(true);
			
			List<GameListItem> gameList = new ArrayList<GameListItem>();
			list.setGames(gameList);
			
			for (GameEntity ge : games) {
				gameList.add(new GameListItem(ge));
			}
		}
		
		return list;
	}
	
	
	@RequestMapping(value = "/service/mobile/gameinfo",  method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public GameStatusResult gameInfo(ModelMap model,  @RequestParam int gameId,
			@RequestParam(required = false) Integer moveUnitId, @RequestParam(required = false) String venue) {
		GameStatusResult returnValue = new GameStatusResult();

		PlayerEntity player = (PlayerEntity)model.get("player");
		
		GameEntity game = gameRepository.findOne(gameId);
		if (game == null) {
			returnValue.setMessage("Invalid Game");
		}
		else {
			if (player == null) {
				returnValue.setMessage("INVALID CREDENTIALS");
			} else {
				GamePlayerKey gpk = new GamePlayerKey();
				gpk.setGame(game);
				gpk.setPlayer(player);
				GamePlayerEntity gpe = gamePlayerRepository.findOne(gpk);
				if (gpe == null) {
					returnValue.setMessage("Invalid Player for Game");
				} else {
					// 
					// if doing a move, do that first
					//
					String moveMess = null;
					logger.debug("Move? " + moveUnitId + " " + venue);
					if (moveUnitId != null && moveUnitId.intValue() != 0 && venue != null) {
						moveMess = moveService.moveUnit(player,moveUnitId, venue);
					}
					GameStatusModel mod = gameStatusModelService.getGameStatusModel(game, player);
					if (mod != null) {
						returnValue.setGameStatusModel(mod);
						returnValue.setMoveMessage(moveMess);
						returnValue.setSuccess(true);
					}
				}
			}
		}
		return returnValue;
	}
}
