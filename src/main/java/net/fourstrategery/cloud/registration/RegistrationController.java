package net.fourstrategery.cloud.registration;

import net.fourstrategery.cloud.FourSquareApiFactory;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.repository.PlayerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompleteUser;

@Controller
public class RegistrationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private FourSquareApiFactory fourSquareApiFactory;
	
	@RequestMapping(value = "/reg/registration", method = RequestMethod.GET)
	public String registrationInit() {		return "reg/registration";
	}
	
	@RequestMapping(value = "/reg/registrationRedirect", method  = RequestMethod.GET) 
	public String registrationRedirectedFrom4Sq(Model model,@RequestParam(value = "code", required = true) String codeFrom4Sq) {
		
		logger.debug("Got code [" + codeFrom4Sq + "]");
		
		FoursquareApi api = fourSquareApiFactory.getInstance();
		try {
			api.authenticateCode(codeFrom4Sq);
			
			logger.debug("Got token [" + api.getOAuthToken() + "]");
			
			Result<CompleteUser> resultUser = api.user("self");
			
			CompleteUser user = resultUser.getResult();
			
			PlayerEntity player = playerRepository.getPlayerByfourSquareId(user.getId());
			
			if (player == null) {
				player = new PlayerEntity();
				player.setFourSquareId(user.getId());
			}
			
			player.setFourSquareToken(api.getOAuthToken());
			player.setEmailAddress(user.getContact().getEmail());
			player.setLastName(user.getLastName());
			player.setFirstName(user.getFirstName());
			player.setFourSquareId(user.getId());
			player.setScreenName(user.getFirstName());
			
			PlayerEntity existingPlayer = playerRepository.getPlayerByScreenName(player.getScreenName());
			if (existingPlayer != null) {
				int counter = 1;
				while(true) {
					player.setScreenName(user.getFirstName() + counter);
					existingPlayer = playerRepository.getPlayerByScreenName(player.getScreenName());
					if (existingPlayer == null) break;
				}
			}
			player.setPassword("password");
			player = playerRepository.save(player);
			
			
			model.addAttribute("user", player);
			
			logger.debug("Inserted new Player ID=" + player.getId());
			
			return "reg/registrationComplete";
		}
		
		catch (Exception e1) {
			logger.error("registration failed",e1);
			return "";
		}
	}
}
