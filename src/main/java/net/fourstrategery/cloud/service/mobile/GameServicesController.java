package net.fourstrategery.cloud.service.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.repository.GameRepository;
import net.fourstrategery.cloud.repository.PlayerAuthRepository;

@Controller
public class GameServicesController {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private PlayerAuthRepository playerAuthRepository;
	
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
}
