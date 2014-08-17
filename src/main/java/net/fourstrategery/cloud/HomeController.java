package net.fourstrategery.cloud;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.repository.GameRepository;
import net.fourstrategery.cloud.security.PlayerUserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	public static final String REDIRECT = "redirect:/nav/home.html";
	
	@Autowired
	private GameRepository gameRepository;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/nav/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		PlayerUserDetails pud = (PlayerUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<GameEntity> activeGames = gameRepository.findActiveGamesForPlayer(pud.getPlayer() , new Date());
		
		model.addAttribute("games",activeGames);
		
		return "nav/home";
	}
	
}
