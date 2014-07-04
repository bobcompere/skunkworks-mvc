package net.fourstrategery.cloud.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/reg/registration", method = RequestMethod.GET)
	public String registrationInit() {
		return "reg/registration";
	}
	
	@RequestMapping(value = "/reg/registrationRedirect", method  = RequestMethod.GET) 
	public String registrationRedirectedFrom4Sq(@RequestParam(value = "code", required = true) String codeFrom4Sq) {
		
		logger.debug("Got code [" + codeFrom4Sq + "]");
		
		return "reg/registrationComplete";
	}
}
