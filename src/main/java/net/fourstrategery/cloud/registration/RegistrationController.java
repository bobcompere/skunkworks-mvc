package net.fourstrategery.cloud.registration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class RegistrationController {

	private final static String TOKEN_URL = "https://foursquare.com/oauth2/access_token?client_id=RATASDCULCIANGZIPPTBLCANVBISHWV2POUOTNQYDIJ1WDML"+
			"&client_secret=3I4JYKTNUFVZB1Q1TLVGFEQXG2GIARZEJMADJRG2DN2Q1NYW" +
			"&grant_type=authorization_code" +
			"&redirect_uri=https://services-fourstrategery.rhcloud.com/cloud/reg/registrationRedirect.html" +
			"&code=";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/reg/registration", method = RequestMethod.GET)
	public String registrationInit() {
		return "reg/registration";
	}
	
	@RequestMapping(value = "/reg/registrationRedirect", method  = RequestMethod.GET) 
	public String registrationRedirectedFrom4Sq(@RequestParam(value = "code", required = true) String codeFrom4Sq) {
		
		logger.debug("Got code [" + codeFrom4Sq + "]");
		
		RestTemplate restTemplate = new RestTemplate();
		
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		
		messageConverters.add(new MappingJacksonHttpMessageConverter());
		
		restTemplate.setMessageConverters(messageConverters);
		
		String url = TOKEN_URL + codeFrom4Sq;
		
		AccessToken token = restTemplate.getForObject(url, AccessToken.class);
		
		logger.debug("Got token [" + token.getAccess_token() + "]");
		
		return "reg/registrationComplete";
	}
}
