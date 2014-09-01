package net.fourstrategery.cloud.gameplay;

import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.repository.UnitRepository;
import net.fourstrategery.cloud.repository.VenueRepository;
import net.fourstrategery.cloud.security.PlayerUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MoveUnitController {
	
	@Autowired
	UnitRepository unitRepository;
	
	@Autowired
	VenueRepository venueRepository;
	
	@Autowired
	MoveService moveService;

	@RequestMapping(value = "nav/moveUnit", method = RequestMethod.POST)
	public String moveUnit(@RequestParam String location, @RequestParam int unit) {
		PlayerUserDetails pud = (PlayerUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PlayerEntity player = pud.getPlayer();
		
		UnitEntity unitEnt = unitRepository.findOne(unit);
		
		String message = moveService.moveUnit(player,unit,location);
		
		return "redirect:/nav/gameStatus.html?gameId=" + unitEnt.getGame().getId() +"&message=" + message;
	}
}
