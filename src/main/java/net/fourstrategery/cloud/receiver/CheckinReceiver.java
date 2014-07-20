package net.fourstrategery.cloud.receiver;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fourstrategery.cloud.FourSquareApiFactory;
import net.fourstrategery.cloud.entity.CheckinEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.VenueEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.foyt.foursquare.api.entities.Checkin;


@Controller
public class CheckinReceiver {
	
	@Autowired
	FourSquareApiFactory fourSquareApiFactory;
	
	@Autowired
	CheckinProcessor checkinProcessor;
	
	
	@Value("${CHECKIN_FOLDER:${OPENSHIFT_DATA_DIR:c:/temp/fs}/checkins}")
	String checkinsFolder;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/receiver/checkin", method = RequestMethod.POST)
	public String postCheckin(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="checkin") String checkin, 
			@RequestParam(value = "secret") String secret) {
		
		logger.debug("CHECKIN:" + checkin);
		
		if (secret.equalsIgnoreCase(fourSquareApiFactory.PUSHSECRET)) {
			save(checkin);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			Checkin checkinObj = null;
			try {
				checkinObj = mapper.readValue(checkin, Checkin.class);
				
				checkinProcessor.processCheckin(checkinObj);
				
			}
			catch (Exception e1) {
				logger.error("Error processing checkin : ",e1);
			}
			
		}
		else {
			logger.debug("BAD SECRET [" + secret + "]");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		
		return "checkin/gotit";
	}
	
	private void save(String data) {
		try {
			logger.debug("Saving");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH-mm-ss-SSS");
			File folder = new File(checkinsFolder + File.separator + sdf.format(new Date()));
			folder.mkdirs();
			
			String filename = folder.getAbsolutePath() + File.separator + sdf2.format(new Date());
			logger.debug("Saving to:" + filename);
			FileOutputStream fos = new FileOutputStream(filename);
			fos.write(data.getBytes());
			fos.close();
		}
		catch (Exception e1) {
			logger.error("Can't save checking : ",e1);
		}
		
		
	}
}
