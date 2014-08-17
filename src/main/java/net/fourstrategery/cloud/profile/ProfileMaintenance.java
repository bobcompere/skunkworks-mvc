package net.fourstrategery.cloud.profile;

import javax.validation.Valid;

import net.fourstrategery.cloud.HomeController;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.repository.PlayerRepository;
import net.fourstrategery.cloud.security.FsPasswordEncoder;
import net.fourstrategery.cloud.security.PlayerUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProfileMaintenance {
	
	
	@Autowired
	FsPasswordEncoder passwordEncoder;

	@Autowired
	PlayerRepository playerRepository;
	
	@Autowired
	PlayerEditModelValidator playerEditModelValidator;
	
	private final static String PATH = "profile/edit";
	private final static String TEMPLATE = "profile/edit";
	
	@InitBinder("playerEditModel")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(playerEditModelValidator);
	}
	
	@ModelAttribute
	PlayerEditModel getPlayerEditModel() {
		PlayerUserDetails pud = (PlayerUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new PlayerEditModel(pud.getPlayer());
	}
	
	@RequestMapping(value = PATH, method = RequestMethod.GET)
	String edit(@ModelAttribute PlayerEditModel playerEditModel) {
		return TEMPLATE;
	}
	
	@RequestMapping(value = PATH, method = RequestMethod.POST)
	String update(@Valid @ModelAttribute PlayerEditModel playerEditModel, BindingResult result) {
		
		if (result.hasErrors()) {
			return TEMPLATE;
		}
		PlayerEntity player = playerRepository.findOne(playerEditModel.getId());
		
		player.setScreenName(playerEditModel.getScreenName());
		player.setPassword(passwordEncoder.encode(playerEditModel.getPasswordChange1()));
		playerRepository.save(player);
		
		return HomeController.REDIRECT;
	}
}
