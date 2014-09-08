package net.fourstrategery.cloud.gameplay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ScoringTesterController {
	
	@Autowired
	GameControlAndScoringService gameControlAndScoringService;

	@RequestMapping(value="/nav/scoringYAYA",method=RequestMethod.GET)
	public String test() {
		gameControlAndScoringService.dailyScoreAndGameCloseOut();
		
		return  "redirect:/nav/home.html";
	}
	
}
