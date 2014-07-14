package net.fourstrategery.cloud.service.dump;

import java.util.List;

import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PlayerList  {
	
	@Autowired
	private PlayerRepository playerRepository;

	@RequestMapping(value = "/service/dump/players",  method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<PlayerEntity> getPlayers() {
		return playerRepository.findAll();
	}
}
