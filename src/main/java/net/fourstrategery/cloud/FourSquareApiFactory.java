package net.fourstrategery.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;

@Component
public class FourSquareApiFactory {

	@Value("${CLIENTID:RATASDCULCIANGZIPPTBLCANVBISHWV2POUOTNQYDIJ1WDML}")
	private String CLIENTID;
	
	@Value("${CLIENTSECRET:3I4JYKTNUFVZB1Q1TLVGFEQXG2GIARZEJMADJRG2DN2Q1NYW}")
	private String CLIENTSECRET;
	
	@Value("${PUSHSECRET:ROKTLFICRREYDPT5UZULJSABDVD20XZXODQDPWF4RM45P2A5}")
	public String PUSHSECRET;
	
	
	@Value("${REDIRECTURL:https://services-fourstrategery.rhcloud.com/cloud/reg/registrationRedirect.html}")
	private String REDIRECTURL;
	
	public FoursquareApi getInstance() {
		FoursquareApi returnVal = new FoursquareApi(CLIENTID, CLIENTSECRET, REDIRECTURL);

		returnVal.setVersion("20140101");
		
		return returnVal;
	}
}
