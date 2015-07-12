package net.fourstrategery.cloud.gameplay;

import java.util.ArrayList;
import java.util.Date;
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
import net.fourstrategery.cloud.security.PlayerAuthDetailService;
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
	PlayerAuthDetailService playerAuthDetalService;
	
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired 
	GameStatusModelService gameStatusModelService;

	@ModelAttribute("gameModel")
	GameStatusModel getModel(@RequestParam int gameId) {
		
		
		PlayerUserDetails pud = (PlayerUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PlayerEntity player = pud.getPlayer();
		GameEntity game = gameRepository.findOne(gameId);
		
		return gameStatusModelService.getGameStatusModel(game, player);
		
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
