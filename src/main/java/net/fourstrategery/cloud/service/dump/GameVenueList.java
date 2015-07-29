package net.fourstrategery.cloud.service.dump;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.fourstrategery.cloud.entity.GameVenueEntity;
import net.fourstrategery.cloud.repository.GameVenueRepository;

@Controller
public class GameVenueList {

	@Autowired
	private GameVenueRepository gameVenueRepository;

	@RequestMapping(value = "/service/dump/gameVenue",  method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<GameVenueEntity> getGameVenues() {
		return gameVenueRepository.findAll();
	}
}
