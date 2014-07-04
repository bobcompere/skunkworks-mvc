package net.fourstrategery.cloud.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.foyt.foursquare.api.entities.Checkin;

@Controller
public class CheckinReceiver {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/receiver/checkin", method = RequestMethod.POST, produces = "application/json", headers = {"Content-type=application/json"})
	public CheckinResponse postCheckin(@RequestBody Checkin checkin) {
		
		CheckinResponse retVal = new CheckinResponse();
		
		logger.debug("Request received : " + checkin.getId());
			
		retVal.setStatus("OK");
		retVal.setErrorMessage("id : " + checkin.getId() + " received");
		return retVal;
	}
}
