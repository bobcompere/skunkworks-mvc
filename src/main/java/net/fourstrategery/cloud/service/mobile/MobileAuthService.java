package net.fourstrategery.cloud.service.mobile;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MobileAuthService {

	

	@RequestMapping(value = "/service/mobile/auth",  method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public AuthToken authenticate(@RequestParam String name,
			@RequestParam String password) {
		
		AuthToken retVal = new AuthToken();
		System.out.println("Auth Request " + new Date());
		return retVal;
	}
}
